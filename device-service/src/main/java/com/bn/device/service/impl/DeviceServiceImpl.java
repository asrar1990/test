package com.bn.device.service.impl;

import com.bn.common.dto.common.*;
import com.bn.device.service.DeviceService;
import com.bn.device.service.IDeviceUtility;
import com.bn.device.service.IMasterDataUtility;
import com.bn.gpb.util.GPBAppConstants;
import com.bn.common.constant.Constants;
import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.dto.device.DeviceModelInfo;
import com.bn.common.dto.device.HashKeyInfo;
import com.bn.common.exception.BNException;
import com.bn.common.generic.Pair;
import com.bn.common.generic.Triple;
import com.bn.common.service.ConfigService;
import com.bn.common.util.ExpressionUtil;
import com.bn.common.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final RestTemplate restTemplate;
    
    @Autowired
    @Qualifier("deviceUtility")
    IDeviceUtility deviceUtility;

    @Autowired
    @Qualifier("masterDataUtility")
    IMasterDataUtility masterDataUtility;

    @Autowired
    @Qualifier("configServiceImpl")
    ConfigService configService;

    @Override
    public String endpointGetHash(String uniqueid, String model) throws BNException {
        //validate format of unique id
        final String sb = "endpointGetHash, uniqueId=" + uniqueid + ", model=" + model;
        final String serialRegX = configService.getStringValue(Constants.ACCOUNT_SERVICE, "ACCOUNT_CHECK_DEVICE", "[0-9A-Za-z-_]{2,64}");
        if(!ExpressionUtil.isValidValue(uniqueid, serialRegX)) {
            throw BNException.getInstance("AD3001", sb);
        }

        final DeviceModelInfo dmi = deviceUtility.validateDeviceModel(model);
        //<TODO: need to revisit during logger implementation>
        DeviceUtility.setLogContext(model, uniqueid);

        //validate that this is used by external clients
        if(dmi.getBnModel() == 1) {
            throw BNException.getInstance("AD3004");
        }

        //validate serial number starts with the appropriate prefix
        final String devFamily = masterDataUtility.getDeviceFamilyByFamilyId(dmi.getFamilyid());
        //NOOKCLOUD-1534
        //Avatar and Piper and future devices based on Avatar code base will not use the Android-NOOK- SN pre-fix
        List<String> bnModelsThatShouldSkipThisValidation = configService.getStringArrayValue(Constants.ACCOUNT_SERVICE, "BN_MODEL_NO_PREFIX", "");
        if( ! bnModelsThatShouldSkipThisValidation.contains(String.valueOf(dmi.getBnModel())) ){
            if(!uniqueid.startsWith(devFamily)) {
                throw BNException.getInstance("AD3005", sb);
            }
        }

        final Pair<String, String> result = deviceUtility.getPassPhrase(uniqueid, dmi);
        return result.getFirst();
    }
    
    @Override
    public AuthInfo protectDeviceAuth(final AuthInfo dAuth) {
        AuthInfo auth = new AuthInfo();
        if(dAuth != null && dAuth.getDevice() != null) {
            auth = dAuth.clone();
            auth.getDevice().setHashPrivateKey(null);
            auth.getDevice().setPublicKey(null);
            auth.getDevice().setSourceID(null);
            auth.getDevice().setEndpointType(null);
            auth.getDevice().setEncoding(0);
            auth.getDevice().setInStoreId(null);
            auth.getDevice().setInStoreStatus(0);
            auth.getDevice().setModemFirmware(null);
            auth.getDevice().setCurrentBuildNumber(null);
            auth.getDevice().setSoftwareVersion(null);
            auth.getDevice().setInitialBuildNumber(null);
            auth.getDevice().setSupportedVersion(0);
        }
        return auth;
    }

    /**
     * Perform an endpoint registration
     */
    @Override
    public AuthInfo endpointRegister(DeviceInfo deviceInfo, String deviceRand, String deviceHash) throws BNException {
        return endpointRegister(deviceInfo, deviceRand, deviceHash,false);
    }

    @Override
    public AuthInfo endpointRegister(DeviceInfo deviceInfo, String deviceRand, String deviceHash, boolean internalCall) throws BNException {
        if(deviceInfo == null) {
            throw BNException.getInstance("AD1200", "endpointRegister:device is null");
        }

        log.debug("endpointRegister:device:" + deviceInfo + ", deviceRand=" + deviceRand + ", deviceHash=" + deviceHash);

        final AuthInfo deviceAuth;
        final StringWriter sw = new StringWriter();
        sw.append("endpointRegister:devInfo:").append(deviceInfo.toString())
                .append(", deviceRand=").append(deviceRand)
                .append(", deviceHash=").append(deviceHash);

        try {
            deviceUtility.validateDeviceAttributes(deviceInfo);

            //validate additional required fields
            //version of the software e.g Client API 2.1
            if(Validator.isBlank(deviceInfo.getSoftwareVersion())) {
                sw.append(":invalid software version:");
                throw BNException.getInstance("AD1227", sw.toString());
            }

            //device os version
            if(Validator.isBlank(deviceInfo.getCurrentBuildNumber())) {
                sw.append(":invalid buildnumber:");
                throw BNException.getInstance("AD1228", sw.toString());
            }

            if(!Validator.isBlank(deviceInfo.getHashPrivateKey())) {
                sw.append(":invalid hash private key:");
                throw BNException.getInstance("AD1229", sw.toString());
            }

            //type of endpoint (iPhone3G, iPhone3GS, iPhone4G, etc)
            if(Validator.isBlank(deviceInfo.getEndpointType())) {
                sw.append(":invalid endpointType:");
                throw BNException.getInstance("AD1230", sw.toString());
            }

            long startTime = System.currentTimeMillis();
            //validate device model
            final DeviceModelInfo dmi = deviceUtility.validateDeviceDetail(deviceInfo.getSerialNumber(),
                    deviceInfo.getModel());

            DeviceUtility.setLogContext(deviceInfo);

            //Validate the device
            startTime = System.currentTimeMillis();
            DeviceInfo di = deviceUtility.getDeviceBySerialNumber(deviceInfo.getSerialNumber());
            boolean prereg = di != null && di.getPreReg() == 1 ;

            final String cor = StringUtils.defaultIfEmpty(deviceInfo.getCountryOfResidence(), Constants.DEFAULT_COUNTRY);
            deviceInfo.setCountryOfResidence(cor);

            RetailerInfo retailerInfo = new RetailerInfo("BN2", "US");
            final String retailer = retailerInfo.getRetailerCode();
            deviceInfo.setRetailer(retailer);

            //device does not exists, create record
            boolean deviceRecordedCreated = false;
            if(di == null) {
                di = createDevice(deviceInfo, dmi);
                deviceRecordedCreated=true;
            }

            final HashKeyInfo hashKey = deviceUtility.getHashKey(deviceInfo.getSerialNumber());
            if(hashKey == null) {
                throw BNException.getInstance("AD2001", sw.toString());
            }
            di.setHashPrivateKey(hashKey.getHashkey());

            if(di.getBlackListed() == 1) {
                sw.append(":deviceBlacklisted");
                throw BNException.getInstance("AD1221", sw.toString());
            }

            //populate device model details
            di = deviceUtility.populateDeviceFamilyModel(di);

            //setting country of residence and retailer
            di.setCountryOfResidence(cor);
            di.setRetailer(retailer);

            //validate the encrypted data
            startTime = System.currentTimeMillis();
            final DeviceModelInfo dmiN = masterDataUtility.getDeviceModelByName(di.getModel());
            if( !internalCall ){
                DeviceUtility.validateEncryption(di.getSerialNumber(), dmiN.getEncoding()
                        , dmiN.getName(), di.getHashPrivateKey(), deviceHash, deviceRand);
            }

            if( ! deviceRecordedCreated ){
                DeviceUtility.migrateDeviceDetail(di, deviceInfo);
            }

            deviceAuth = deviceAuth(di, dmiN.getTokenLimit());
            deviceAuth.getExtraInfo().addAll(getRetailerExtras(di.getRetailer(), cor));
            
            //expire tokens
            expireTokens(deviceAuth.getDevice().getId(), deviceAuth.getDeviceToken().getToken());
        }
        catch(BNException be) {
            if(!"AD1133".equalsIgnoreCase(be.getErrorCode())){
                log.error(String.valueOf(sw), be);
            }
            throw be;
        }
        catch(Exception e) {
            log.error("AD8217:" + sw, e);
            throw BNException.getInstance("AD8217", sw.toString());
        }

        return deviceAuth;
    }


    private DeviceInfo createDevice(DeviceInfo di, DeviceModelInfo dmi) {
        //only needed if there is no device feed coming to us
        DeviceInfo diNew = null;

        if(di != null && !Validator.isBlank(di.getSerialNumber())) {
            diNew = new DeviceInfo();
            diNew.setSerialNumber(di.getSerialNumber());
            if(!Validator.isBlank(di.getImsi())) {
                diNew.setImei(di.getImei());
            }
            if(!Validator.isBlank(di.getImsi())) {
                diNew.setImsi(di.getImsi());
            }
            diNew.setPreReg(di.getPreReg());
            di.setCreateDate(new Date());
            diNew.setCurrentBuildMajor(1);
            diNew.setCurrentBuildMinor(1);
            diNew.setCurrentBuildRevision(0);
            if(!Validator.isBlank(di.getCurrentBuildNumber())) {
                diNew.setInitialBuildNumber(di.getCurrentBuildNumber());
                diNew.setCurrentBuildNumber(di.getCurrentBuildNumber());
            }

            diNew.setModelId(dmi.getModelID());
            diNew.setModel(dmi.getName());
            diNew.setModelDesc(dmi.getShortDescription());
            diNew.setSourceID(dmi.getSourceID());
            diNew.setCountryOfResidence(di.getCountryOfResidence());
            DeviceUtility.migrateDeviceDetail(diNew, di);
        }

        return diNew;
    }

    public List<Triple<String, String, String>> getRetailerExtras(String retailerId, String countryId) {
        final List<Triple<String, String, String>> extras = new ArrayList<>();
        extras.add(new Triple<>(GPBAppConstants.EXTRAINFO_TYPE_RETAILER, GPBAppConstants.EXTRAINFO_KEY_RETAILERID, retailerId));
        extras.add(new Triple<>(GPBAppConstants.EXTRAINFO_TYPE_RETAILER, GPBAppConstants.EXTRAINFO_KEY_COUNTRYID, countryId));
        return extras;
    }

    private AuthInfo deviceAuth(DeviceInfo di, long tokenLimit)
            throws BNException {
        AccountInfo ai = null;
        di.setPreReg(0); //reset prereg flag to 0 during device auth
        //if ai is not null, create account, then device, device token, and auth log
        final AuthInfo auth = createAndSaveDeviceToken(ai, di, tokenLimit);
        auth.setDevice(di);
        return auth;
    }

    private AuthInfo createAndSaveDeviceToken(AccountInfo account, DeviceInfo device, long tokenLimit) throws BNException {
        // create new device token, assign the user to the device
        final TokenTypeInfo deviceTokenType = masterDataUtility.getTokenType(TokenType.DEVICE_AUTH);
        // auth is guaranteed not null after this
        final AuthInfo auth = DeviceUtility.createDeviceToken(deviceTokenType, tokenLimit);
        insertDeviceWithToken(device, auth.getDeviceToken(), account);
        device.setDeviceToken(auth.getDeviceToken().getToken());
        return auth;
    }

    private void insertDeviceWithToken(DeviceInfo dInfo, TokenInfo dti, AccountInfo aInfo) {
        final AuthenticateLogInfo ali = createAuthLogInfo(dti, null);
        deviceUtility.insertDeviceWithToken(dInfo, dti, ali, aInfo);
    }

    protected static AuthenticateLogInfo createAuthLogInfo(TokenInfo ati, String reason) {
        final AuthenticateLogInfo ali = new AuthenticateLogInfo();
        ali.setDeviceId(ati.getDeviceId());
        ali.setCreateDate(ati.getTokenCreateDate());
        ali.setExpireDate(ati.getTokenExpiresOn());
        ali.setReason(reason);
        ali.setToken(ati.getToken());
        ali.setStatus(Constants.TOKENSTATUS_ACTIVE);
        return ali;
    }

    private void expireTokens(long deviceid, String token) {
        //get a list of active tokens
        final List<AuthInfo> activeTokens = deviceUtility.getDeviceTokensByDeviceId(deviceid);
        final int size = activeTokens.size();
        final List<TokenInfo> tokenList = new ArrayList<TokenInfo>(size);
        for(final AuthInfo tInfo : activeTokens) {
            if(!tInfo.getDeviceToken().getToken().equals(token)) {
                tokenList.add(tInfo.getDeviceToken());
            }
        }
        deviceUtility.expireTokens(deviceid, 0, tokenList, Constants.TOKEN_EXPIRE_DEVICE_REGISTER);
    }

}

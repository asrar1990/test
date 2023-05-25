package com.bn.device.service.impl;

import com.bn.common.constant.Constants;
import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.common.AuthInfo;
import com.bn.common.dto.common.AuthenticateLogInfo;
import com.bn.common.dto.common.TokenInfo;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.dto.util.StringUtil;
import com.bn.common.generic.Pair;
import com.bn.common.dto.device.DeviceModelInfo;
import com.bn.common.dto.device.HashKeyInfo;
import com.bn.common.dto.security.SecretKey;
import com.bn.common.dto.util.RandomStringUtil;
import com.bn.common.exception.BNException;
import com.bn.common.service.ConfigService;
import com.bn.common.util.ExpressionUtil;
import com.bn.common.util.GUID;
import com.bn.common.validator.Validator;
import com.bn.device.dao.DeviceDao;
import com.bn.device.service.IDeviceUtility;
import com.bn.device.service.IMasterDataUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by sbose on 23/4/23.
 */
@Service
@Slf4j
public class DeviceUtility implements IDeviceUtility {

    @Autowired
    @Qualifier("masterDataUtility")
    private IMasterDataUtility masterDataUtility;

    @Autowired
    @Qualifier("configServiceImpl")
    ConfigService configService;

    @Autowired
    @Qualifier("deviceDaoImpl")
    DeviceDao deviceDao;
    
    @Override
    public DeviceModelInfo validateDeviceModel(String model) throws BNException {
        final DeviceModelInfo dmi = masterDataUtility.getDeviceModelByName(model);
        if(dmi == null) {
            throw BNException.getInstance("AD1203", "model=" + model);
        }
        return dmi;
    }

    @Override
    public Pair<String, String> getPassPhrase(String uniqueid, DeviceModelInfo dmi) throws BNException {
        final String passphrase;
        String hashKey = null;
        HashKeyInfo hki = getHashKey(uniqueid);
        if(hki == null) {
            //there is no record of this therefore add an entry
            final Pair<String, String> keys = generateHashKey(dmi);
            hashKey = keys.getFirst();
            passphrase = keys.getSecond();
            hki = new HashKeyInfo(uniqueid, dmi.getModelID(), passphrase, hashKey);
            deviceDao.insertHashKey(hki);
        }
        else {
            final boolean updateHash = configService.getBooleanValue(Constants.ACCOUNT_SERVICE, "UPDATE_PRIVATEKEYHASH", true);
            if(updateHash) {
                //update the hashPrivateKey on the device and endpoint_hashkey
                final SecretKey sk = new SecretKey();
                final String hashkey = sk.getDigest(hki.getPassphrase(), dmi.getDigest(), dmi.getIteration());
                if(!hashkey.equals(hki.getHashkey()) || !hki.getModelid().equals(dmi.getModelID())) {
                    hki.setHashkey(hashkey);
                    hki.setModelid(dmi.getModelID());
                    updateHashKey(hki);
                }
            }
            passphrase = hki.getPassphrase();
        }
        return new Pair<>(passphrase, hashKey);
    }

    @Override
    public HashKeyInfo getHashKey(String uniqueid) throws BNException {
        final List<HashKeyInfo> hkList = deviceDao.getHashKey(uniqueid);
        if(hkList == null || hkList.isEmpty()) {
            return null;
        }
        if(hkList.size() > 1) {
            final StringWriter sw = new StringWriter();
            sw.append("modelId0=").append(hkList.get(0).getModelid())
                    .append(", modelId1=").append(hkList.get(1).getModelid());
            throw BNException.getInstance("AD1207", sw.toString());
        }

        //check if the model matches otherwise throw an error
        return hkList.get(0);
    }

    protected static Pair<String, String> generateHashKey(DeviceModelInfo dmi) {
        final int randomNum = 16 + ((int)(Math.random() * 100) % 4);
        final RandomStringUtil rs = new RandomStringUtil(randomNum);
        final SecretKey sk = new SecretKey();
        final String passphrase = rs.nextString();
        String hashkey = passphrase;
        hashkey = sk.getDigest(hashkey, dmi.getDigest(), dmi.getIteration());
        return new Pair<String, String>(hashkey, passphrase);
    }

    protected void updateHashKey(HashKeyInfo hki) {
        final int result = deviceDao.updateHashKey(hki);
        if(result > 0) {
            removeDeviceFromCache(0, hki.getUniqueid());
        }
    }

    protected static void setLogContext(String model, String serialNumber) {
        
    }

    protected static void setLogContext(DeviceInfo dInfo) {

    }

    @Override
    public void removeDeviceFromCache(long deviceId, String serialNumber) {

    }

    public void correctModelIdForAvatar8(DeviceInfo deviceInfo) {
        if( deviceInfo == null || StringUtil.isEmpty(deviceInfo.getModel()) || StringUtil.isEmpty(deviceInfo.getSourceID())) return;
        if( "SGTV300".equalsIgnoreCase(deviceInfo.getModel().trim())
                && "P001000086".equalsIgnoreCase(deviceInfo.getSourceID().trim())){
            deviceInfo.setModel("SM-T710");
            deviceInfo.setModelId("SM-T710");
            log.info("nookcloud-1579 changed SGTV300/P001000086 to SM-T710/P001000086");
        }
    }

    @Override
    public void validateDeviceAttributes(DeviceInfo dInfo) throws BNException {
        if(!Validator.isBlank(dInfo.getImei())) {
            final String imeiReg = configService.getStringValue(Constants.ACCOUNT_SERVICE
                    , "ACCOUNT_CHECK_IMEI", "[0-9]{14,17}");
            if(!ExpressionUtil.isValidValue(dInfo.getImei(), imeiReg)) {
                throw BNException.getInstance("AD1206", "imei=" + dInfo.getImei());
            }
        }

        if(!Validator.isBlank(dInfo.getSerialNumber()) && Validator.isLengthGreaterThan(dInfo.getSerialNumber(), 64)) {
            throw BNException.getInstance("AD1218");
        }
        final String serialRegX = configService.getStringValue(Constants.ACCOUNT_SERVICE
                , "ACCOUNT_CHECK_DEVICE", "[0-9A-Za-z-_]{2,64}");
        if(!ExpressionUtil.isValidValue(dInfo.getSerialNumber(), serialRegX)) {
            throw BNException.getInstance("AD1201", "serialNumber=" + dInfo.getSerialNumber());
        }
        if(!Validator.isBlank(dInfo.getHashPrivateKey()) && Validator.isLengthGreaterThan(dInfo.getHashPrivateKey(), 512)) {
            throw BNException.getInstance("AD1219", "hashPrivateKey=" + dInfo.getHashPrivateKey());
        }
        if(!Validator.isBlank(dInfo.getPublicKey()) && Validator.isLengthGreaterThan(dInfo.getPublicKey(), 512)) {
            throw BNException.getInstance("AD1220");
        }
        if(!Validator.isBlank(dInfo.getImsi()) && Validator.isLengthGreaterThan(dInfo.getImsi(), 30)) {
            throw BNException.getInstance("AD1214", "imsi=" + dInfo.getImsi());
        }
        if(!Validator.isBlank(dInfo.getModemFirmware()) && Validator.isLengthGreaterThan(dInfo.getModemFirmware(), 50)) {
            throw BNException.getInstance("AD1215", "modemFirmware=" + dInfo.getModemFirmware());
        }
        if(!Validator.isBlank(dInfo.getCurrentBuildNumber()) && Validator.isLengthGreaterThan(dInfo.getCurrentBuildNumber(), 50)) {
            throw BNException.getInstance("AD1216", "currentBuildNumber=" + dInfo.getCurrentBuildNumber());
        }
        if(!Validator.isBlank(dInfo.getInitialBuildNumber()) && Validator.isLengthGreaterThan(dInfo.getInitialBuildNumber(), 50)) {
            throw BNException.getInstance("AD1217", "initialBuildNumber=" + dInfo.getInitialBuildNumber());
        }
        if(!Validator.isBlank(dInfo.getIccid()) && Validator.isLengthGreaterThan(dInfo.getIccid(), 50)) {
            throw BNException.getInstance("AD1224", "iccId=" + dInfo.getIccid());
        }
        if(!Validator.isBlank(dInfo.getSoftwareVersion()) && Validator.isLengthGreaterThan(dInfo.getSoftwareVersion(), 50)) {
            throw BNException.getInstance("AD1223", "softwareVersion=" + dInfo.getSoftwareVersion());
        }
        if(!Validator.isBlank(dInfo.getEndpointType()) && Validator.isLengthGreaterThan(dInfo.getEndpointType(), 64)) {
            throw BNException.getInstance("AD1230", "endpointType=" + dInfo.getEndpointType());
        }
    }

    @Override
    public DeviceModelInfo validateDeviceDetail(String serialNumber, String model) throws BNException {
        final DeviceModelInfo dmi = validateDeviceModel(model);

        //validate serial number starts with the appropriate prefix
        final String devFamily = masterDataUtility.getDeviceFamilyByFamilyId(dmi.getFamilyid());

        //NOOKCLOUD-1534
        //Avatar and Piper and future devices based on Avatar code base will not use the Android-NOOK- SN pre-fix
        List<String> bnModelsThatShouldSkipThisValidation = configService.getStringArrayValue("ACCOUNT", "BN_MODEL_NO_PREFIX", "");
        if(  bnModelsThatShouldSkipThisValidation.contains(String.valueOf(dmi.getBnModel())) ){
            return dmi;
        }

        if(!serialNumber.startsWith(devFamily)) {
            throw BNException.getInstance("AD3005", "serialNumber=" + serialNumber);
        }
        return dmi;
    }

    @Override
    public DeviceInfo getDeviceBySerialNumber(String serialNumber) throws BNException {
        DeviceInfo dInfo = deviceDao.getDeviceBySerialNumber(serialNumber);
        if(dInfo != null) {
            dInfo = populateDeviceFamilyModel(dInfo);
        }

        return dInfo;
    }

    @Override
    public DeviceInfo populateDeviceFamilyModel(DeviceInfo dInfo) throws BNException {
        if (dInfo != null && dInfo.getModelId() != null) {
            final DeviceModelInfo dmi = masterDataUtility.getDeviceModelById(dInfo.getModelId());
            validateDeviceModel(dmi);
            dInfo.setModel(dmi.getName());
            dInfo.setModelDesc(dmi.getShortDescription());
            dInfo.setEncoding(dmi.getEncoding());
            dInfo.setBnModel(dmi.getBnModel());
            if (StringUtils.isBlank(dInfo.getEan())) {
                dInfo.setEan(dmi.getEan());
            }
            //populate source id from device model only if it's not set on device table
            //This can happen only when edm service did not return source id for an device.
            if (StringUtils.isEmpty(dInfo.getSourceID())) {
                dInfo.setSourceID(dmi.getSourceID());
            }
            dInfo.setFamilyId(dmi.getFamilyid());
        }

        return dInfo;
    }

    @Override
    public void insertDeviceWithToken(DeviceInfo dInfo, TokenInfo dti, AuthenticateLogInfo ali, AccountInfo aInfo) {
          deviceDao.insertDeviceWithToken(dInfo, dti, ali);
    }

    private static void validateDeviceModel(DeviceModelInfo dmi) throws BNException {
        if(dmi == null) {
            throw BNException.getInstance("AD1203");
        }
    }

    protected static void migrateDeviceDetail(DeviceInfo di, DeviceInfo deviceInfo) {
        //update device information
        if(!Validator.isBlank(deviceInfo.getModemFirmware())) {
            di.setModemFirmware(deviceInfo.getModemFirmware());
        }
        if(getVersion(di.getCurrentBuildNumber(), 0) > 0) {
            di.setCurrentBuildMajor(getVersion(di.getCurrentBuildNumber(), 0));
        }
        if(getVersion(di.getCurrentBuildNumber(), 1) > 0) {
            di.setCurrentBuildMinor(getVersion(di.getCurrentBuildNumber(), 1));
        }
        if(getVersion(di.getCurrentBuildNumber(), 2) > 0) {
            di.setCurrentBuildRevision(getVersion(di.getCurrentBuildNumber(), 2));
        }
        if(!Validator.isBlank(deviceInfo.getCurrentBuildNumber())) {
            di.setCurrentBuildNumber(deviceInfo.getCurrentBuildNumber());
        }
        if(!Validator.isBlank(deviceInfo.getImei())) {
            di.setImei(deviceInfo.getImei());
        }
        if(!Validator.isBlank(deviceInfo.getImsi())) {
            di.setImsi(deviceInfo.getImsi());
        }
        if(!Validator.isBlank(deviceInfo.getSoftwareVersion())) {
            di.setSoftwareVersion(deviceInfo.getSoftwareVersion());
        }
        if(!Validator.isBlank(deviceInfo.getEndpointType())) {
            di.setEndpointType(deviceInfo.getEndpointType());
        }
        if(!Validator.isBlank(deviceInfo.getIccid())) {
            di.setIccid(deviceInfo.getIccid());
        }
        if(!Validator.isBlank(deviceInfo.getEan())) {
            di.setEan(deviceInfo.getEan());
        }
        if(!Validator.isBlank(deviceInfo.getRetailer())) {
            di.setRetailer(deviceInfo.getRetailer());
        }
        if(!Validator.isBlank(deviceInfo.getCountryId())) {
            di.setCountryId(deviceInfo.getCountryId());
        }
        if(!Validator.isBlank(deviceInfo.getLanguageId())) {
            di.setLanguageId(deviceInfo.getLanguageId());
        }
        if(!Validator.isBlank(deviceInfo.getSourceID())) {
            di.setSourceID(deviceInfo.getSourceID());
        }
        if(!Validator.isBlank(deviceInfo.getCountryOfResidence())) {
            di.setCountryOfResidence(deviceInfo.getCountryOfResidence());
        }
        if(!Validator.isBlank(deviceInfo.getSiliconId())) {
            di.setSiliconId(deviceInfo.getSiliconId());
        }
    }

    private static int getVersion(String cVersion, int pos) {
        int v = 0;

        if(!Validator.isBlank(cVersion)) {
            final String[] va = cVersion.split("\\.");
            if(va != null && pos < va.length) {
                String s = va[pos];
                s = s.replaceAll("[^0-9]", "");
                if(Validator.isInteger(s)) {
                    v = Integer.parseInt(s);
                }
            }
        }

        return v;
    }

    public static void validateEncryption(String serialNumber, int encoding, String deviceModelName
            , String hashKey, String hashData, String randNum)
            throws BNException {
        //validate the encrypted data
        final SecretKey sk = new SecretKey();
        if(Validator.isBlank(hashKey)) {
            throw BNException.getInstance("AD2001", "validateEncryption:hashkey is empty, serialNumber=" + serialNumber);
        }
        byte[] secretKey = hashKey.getBytes();
        if(encoding == 1) {
            secretKey = Base64.decodeBase64(hashKey.getBytes());
        }

        if(log.isDebugEnabled()) {
            log.debug("validateEncryption:serial#:" + serialNumber
                    + " :devModel:" + deviceModelName + " :randNum:" + randNum + " :hashData:" + hashData
                    + " :secretKey:" + Arrays.toString(secretKey));
        }

        if(!sk.isHashValid(hashData, secretKey, serialNumber, deviceModelName, randNum)) {
            final String error = ":validateEncryption:invalid Hash, serialNumber=" + serialNumber
                    + ", randNum=" + randNum + ", hashData=" + hashData
                    + ", hashPrivKey=" + hashKey + ", secretKey=" + Base64.encodeBase64String(secretKey);
            log.info(error);
            throw BNException.getInstance("AD2000", error);
        }
    }

    protected static AuthInfo createDeviceToken(TokenTypeInfo tokenType, long tokenLimit) {
        final String token = GUID.getToken();

        final Date createDate = new Date();
        long duration = tokenType.getDuration() * 1000;
        if(tokenLimit > 0) {
            duration = tokenLimit * 1000;
        }
        final long expireDate = createDate.getTime() + duration;
        final TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setTokenCreateDate(createDate);
        tokenInfo.setTokenExpiresOn(new Date(expireDate));
        tokenInfo.setDuration(duration);
        tokenInfo.setToken(token);
        tokenInfo.setTokenType(tokenType.getId());
        tokenInfo.setTokenState(Constants.TOKEN_STATE_ACTIVE);
        final AuthInfo auth = new AuthInfo();
        auth.setDeviceToken(tokenInfo);

        return auth;
    }

    @Override
    public List<AuthInfo> getDeviceTokensByDeviceId(long deviceid)  {
        return deviceDao.getDeviceTokensByDeviceId(deviceid);
    }

    @Override
    public void expireTokens(long deviceid, int i, List<TokenInfo> tokenList, String tokenExpireDeviceRegister) {
        List<String> tokenArr = new ArrayList<>();
        for(TokenInfo token : tokenList) {
            if(token.getToken() != null) {
                tokenArr.add(token.getToken());
            }
        }
        deviceDao.expireDeviceTokenNow(deviceid, tokenArr, tokenExpireDeviceRegister);
    }

}

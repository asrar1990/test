package com.bn.device.command.gpb;

import com.bn.device.dto.VersionInfo;
import com.bn.gpb.GpbCommons;
import com.bn.gpb.device.GpbDevice;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageLite;
import com.bn.common.command.gpb.AbstractCommand;
import com.bn.common.command.gpb.AbstractCommandResponse;
import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.common.AuthInfo;
import com.bn.common.dto.common.TokenInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.dto.util.DateUtils;
import com.bn.common.exception.BNException;
import com.bn.common.exception.BNFrameWorkException;
import com.bn.common.generic.Triple;
import com.bn.common.validator.AccountValidator;
import com.bn.common.validator.Validator;
import com.bn.device.command.gpb.response.DeviceAuthCommandResponse;
import com.bn.device.service.DeviceService;
import com.bn.device.service.IDeviceUtility;
import com.bn.device.service.VersionMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sbose on 23/4/23.
 */
@Service
@Slf4j
public class EndpointRegisterCommand extends AbstractCommand {

    @Autowired
    @Qualifier("deviceServiceImpl")
    DeviceService deviceService;

    @Autowired
    @Qualifier("versionMapServiceImpl")
    VersionMapService versionMapService;

    @Autowired
    @Qualifier("deviceUtility")
    IDeviceUtility deviceUtility;


    private int version;
    private DeviceInfo deviceInfo;
    private String deviceHash;
    private String deviceRand;

    public void deSerialize(int version, ByteString message) throws Exception {
        GpbDevice.DeviceAuthRequestV1 registerDeviceReq =
                GpbDevice.DeviceAuthRequestV1.parseFrom(message.toByteArray());

        this.version = version;
        if (registerDeviceReq==null)
            throw BNFrameWorkException.getInstance("AD1200");

        else if (registerDeviceReq.getDevice()==null)
            throw BNException.getInstance("AD1200");

        DeviceInfo deviceInfo = new DeviceInfo();
        GpbCommons.DeviceV1 deviceV1 = registerDeviceReq.getDevice();
        deviceInfo.setCurrentBuildNumber(deviceV1.getBuildNumber());
        deviceInfo.setHashPrivateKey(deviceV1.getHashPrivateKey());
        deviceInfo.setPublicKey(deviceV1.getPublicKey());
        deviceInfo.setImei(deviceV1.getImei());
        deviceInfo.setImsi(deviceV1.getImsi());
        deviceInfo.setModel(deviceV1.getModel());
        deviceInfo.setModemFirmware(deviceV1.getModemFirmware());
        deviceInfo.setSerialNumber(deviceV1.getSerialNum());
        deviceInfo.setSoftwareVersion(deviceV1.getSoftwareVersion());
        deviceInfo.setEndpointType(deviceV1.getEndpointType());
        deviceInfo.setIccid(deviceV1.getIccid());
        if(deviceV1.hasCountryOfResidence())
            deviceInfo.setCountryOfResidence(deviceV1.getCountryOfResidence());

        this.deviceHash = registerDeviceReq.getDeviceHash();
        this.deviceRand = registerDeviceReq.getDeviceRand();
        this.deviceInfo = deviceInfo;
    }

    public GeneratedMessageLite serialize() throws BNException {
        GpbDevice.DeviceAuthResponseV1.Builder result = GpbDevice.DeviceAuthResponseV1.newBuilder();
        try {
            DeviceAuthCommandResponse response = (DeviceAuthCommandResponse)this.getResponse();
            if (response!=null) {
                TokenInfo tokenInfo = response.getTokenInfo();
                if (tokenInfo!=null)
                {
                    String expiredDateStr = DateUtils.fromDateToString(tokenInfo.getTokenExpiresOn());
                    result.setDeviceToken(GpbCommons.AuthTokenV1.newBuilder()
                            .setToken(tokenInfo.getToken())
                            .setType(tokenInfo.getTokenType())
                            .setTokenExpireTime(expiredDateStr));
                }

                DeviceInfo dInfo = response.getDeviceInfo();
                if (dInfo!=null) {
                    result.setDeviceId(dInfo.getId());
                }

                if (response.getExtraInfo() !=null && !response.getExtraInfo().isEmpty())
                {
                    List<Triple<String, String, String>> retailData = response.getExtraInfo();
                    for(Triple<String, String, String> t : retailData)
                    {
                        GpbCommons.ExtraInfoV1.Builder extraInfoBld = GpbCommons.ExtraInfoV1.newBuilder();
                        if (!Validator.isBlank(t.getSecond())
                                && !Validator.isBlank(t.getThird()))
                        {
                            if (!Validator.isBlank(t.getFirst()))
                                extraInfoBld.setType(t.first);
                            extraInfoBld.setKey(t.second);
                            extraInfoBld.setValue(t.third);
                            result.addExtraInfo(extraInfoBld);
                        }
                    }
                }

                // Device Auth changes - Needed for Preregistration functionality
                final AccountInfo aInfo = response.getAccountInfo();
                if(aInfo != null) {
                    final GpbCommons.AccountV1.Builder accBld = GpbCommons.AccountV1.newBuilder();
                    if(aInfo.getCustId() != null) {
                        accBld.setCustid(aInfo.getCustId());
                    }
                    if(aInfo.getFirstName() != null) {
                        accBld.setFirstName(aInfo.getFirstName());
                    }
                    if(aInfo.getLastName() != null) {
                        accBld.setLastName(aInfo.getLastName());
                    }
                    if(aInfo.getEmail() != null) {
                        accBld.setEmail(aInfo.getEmail());
                    }
                    if(!Validator.isBlank(aInfo.getAccountHash())) {
                        accBld.setAccountHash(aInfo.getAccountHash());
                    }
                    accBld.setAccountid(aInfo.getId());
                    result.setAccount(accBld);
                    final TokenInfo tInfo = response.getAccountTokenInfo();
                    if(tInfo != null) {
                        final GpbCommons.AuthTokenV1.Builder accAuth = GpbCommons.AuthTokenV1.newBuilder();
                        accAuth.setDuration(tInfo.getDuration());
                        if(tInfo.getToken() != null) {
                            accAuth.setToken(tInfo.getToken());
                        }
                        if(tInfo.getTokenExpiresOn() != null) {
                            final String expiredDateStr = DateUtils.fromDateToString(tokenInfo.getTokenExpiresOn());
                            accAuth.setTokenExpireTime(expiredDateStr);
                        }
                        if(tInfo.getTokenType() != null) {
                            accAuth.setType(tInfo.getTokenType());
                        }
                        result.setAccountToken(accAuth);
                    }
                }

            }

        } catch (BNException be) {
            if (log.isDebugEnabled())
                log.debug("serialize:" + be.getMessage());
            throw be;

        } catch (Exception e) {
            String desc = "serialize:" + e.getMessage();
            log.error(desc);
            throw BNException.getInstance("AD8217", desc);
        }

        return result.build();

    }

    @Override
    protected AbstractCommandResponse execute() throws Exception {
        //Validate required data
        AccountValidator.validateDeviceAuth(deviceInfo, deviceRand, deviceHash);
        final DeviceAuthCommandResponse response = new DeviceAuthCommandResponse(getCommand());
        try {
            deviceInfo.setRetailer(getRetailer().getRetailerCode());
            deviceInfo.setCountryId(getLocale().getCountry());
            deviceInfo.setLanguageId(getLocale().getLanguage());
            deviceUtility.correctModelIdForAvatar8(deviceInfo);
            final AuthInfo dai = deviceService.endpointRegister(deviceInfo, deviceRand, deviceHash);
            if(dai != null) {
                final AuthInfo devAuth = deviceService.protectDeviceAuth(dai);
                response.setTokenInfo(devAuth.getDeviceToken());
                response.setDeviceInfo(devAuth.getDevice());
                final VersionInfo versionInfo = versionMapService.getSupportVersion(devAuth.getDevice().getModelId(),
                        deviceInfo.getSoftwareVersion());
                if(versionInfo!=null){
                    devAuth.getExtraInfo().add(new Triple<String, String, String>("DEVICE_DETAIL", "product_Device_Id", versionInfo.getProductDeviceId().toString()));
                    //NOOKCLOUD-3670 - To block/unblock the in-app purchase.
                    devAuth.getExtraInfo().add(new Triple<>("PURCHASE_BLOCK", "STATUS", String.valueOf(versionInfo.isPurchaseBlockStatus())));
                    //NOOKCLOUD-4143 - To block or unblock the in-app purchase or display site link.
                    devAuth.getExtraInfo().add(new Triple<>("PURCHASE_ALLOW", "TYPE", versionInfo.getPurchaseAllowType()));
                }
                response.setExtraInfo(devAuth.getExtraInfo());
                if( dai.getAccount() != null ){
                    response.setAccountInfo(dai.getAccount());
                }
                if( dai.getAccountToken() != null ){
                    response.setAccountTokenInfo(dai.getAccountToken());
                }
                log.info("DeviceToken:" + devAuth.getDeviceToken());
            }
        }
        catch(BNException be) {
            throw be;
        }
        catch(Exception e) {
            log.error("AD9999:execute:" + deviceInfo + ", deviceHash=" + deviceHash + ", deviceRand=" + deviceRand, e);
            throw BNException.getInstance("AD9999", "execute:" + deviceInfo + ", " + e.getMessage(), e);
        }
//        getAuthInfo().setDevice(deviceInfo);
        return response;
    }
}
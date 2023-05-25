package com.bn.device.service;

import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.common.AuthInfo;
import com.bn.common.dto.common.AuthenticateLogInfo;
import com.bn.common.dto.common.TokenInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.generic.Pair;
import com.bn.common.dto.device.DeviceModelInfo;
import com.bn.common.dto.device.HashKeyInfo;
import com.bn.common.exception.BNException;

import java.util.List;

/**
 * Created by sbose on 23/4/23.
 */
public interface IDeviceUtility {
    DeviceModelInfo validateDeviceModel(String model) throws BNException;
    Pair<String, String> getPassPhrase(String uniqueId, DeviceModelInfo dmi) throws BNException;
    HashKeyInfo getHashKey(String uniqueid) throws BNException;
    void removeDeviceFromCache(long deviceId, String serialNumber);
    void correctModelIdForAvatar8(DeviceInfo deviceInfo);
    void validateDeviceAttributes(DeviceInfo dInfo) throws BNException;
    DeviceModelInfo validateDeviceDetail(String serialNumber, String model) throws BNException;
    DeviceInfo getDeviceBySerialNumber(String serialNumber) throws BNException;
    DeviceInfo populateDeviceFamilyModel(DeviceInfo dInfo) throws BNException;
    void insertDeviceWithToken(DeviceInfo dInfo, TokenInfo dti, AuthenticateLogInfo ali, AccountInfo aInfo);
    List<AuthInfo> getDeviceTokensByDeviceId(long deviceid);

    void expireTokens(long deviceid, int i, List<TokenInfo> tokenList, String tokenExpireDeviceRegister);
}

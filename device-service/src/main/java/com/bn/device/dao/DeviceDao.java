package com.bn.device.dao;

import com.bn.common.dto.common.AuthInfo;
import com.bn.common.dto.common.AuthenticateLogInfo;
import com.bn.common.dto.common.TokenInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.dto.device.HashKeyInfo;

import java.util.Collection;
import java.util.List;

/**
 * Created by sbose on 23/4/23.
 */
public interface DeviceDao {
    void insertHashKey(HashKeyInfo hki);
    int updateHashKey(HashKeyInfo hki);
    List<HashKeyInfo> getHashKey(String uniqueid);
    DeviceInfo getDeviceBySerialNumber(String serialNumber);
    int insertDeviceWithToken(DeviceInfo dInfo, TokenInfo dti, AuthenticateLogInfo ali);
    List<AuthInfo> getDeviceTokensByDeviceId(long deviceid);
    void expireDeviceTokenNow(long deviceid, Collection<String> tokens, String msg);
}

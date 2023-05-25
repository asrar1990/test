package com.bn.device.service;

import com.bn.common.dto.common.AuthInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.exception.BNException;
import com.bn.common.generic.Triple;

import java.util.List;

/**
 * Created by sbose on 23/4/23.
 */
public interface DeviceService {
    String endpointGetHash(String uniqueid, String model) throws BNException;
    AuthInfo protectDeviceAuth(final AuthInfo dAuth);
    AuthInfo endpointRegister(DeviceInfo deviceInfo, String deviceRand, String deviceHash) throws BNException;
    AuthInfo endpointRegister(DeviceInfo deviceInfo, String deviceRand, String deviceHash,
            boolean internalCall) throws BNException;
    List<Triple<String, String, String>> getRetailerExtras(String retailerId, String countryId);
}

package com.bn.device.service;

import com.bn.common.dto.common.TokenType;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.dto.device.DeviceModelInfo;
import com.bn.common.exception.BNException;
import com.bn.device.dto.VersionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbose on 23/4/23.
 */
public interface IMasterDataUtility {
    String getDeviceFamilyByFamilyId(int familyId) throws BNException;
    DeviceModelInfo getDeviceModelByName(String modelName) throws BNException;
    ArrayList<DeviceModelInfo> getDeviceModels() throws BNException;
    List<VersionInfo> getVersionInfoList() throws BNException;
    DeviceModelInfo getDeviceModelById(String modelId) throws BNException;
    TokenTypeInfo getTokenType(TokenType tokenType) throws BNException;
}

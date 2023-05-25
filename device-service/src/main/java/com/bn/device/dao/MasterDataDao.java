package com.bn.device.dao;

import com.bn.common.dto.common.TokenStateInfo;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.dto.device.DeviceFamilyInfo;
import com.bn.common.dto.device.DeviceModelInfo;
import com.bn.common.exception.BNException;
import com.bn.device.dto.VersionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sbose on 23/4/23.
 */
public interface MasterDataDao {
    ArrayList<DeviceModelInfo> getDeviceModels() throws BNException;
    ArrayList<DeviceFamilyInfo> getDeviceFamily() throws BNException;
    List<VersionInfo> loadDBVersionInfo() throws BNException;
    ArrayList<TokenStateInfo> getTokenStates() throws BNException;
    ArrayList<TokenTypeInfo> getTokenTypes() throws BNException;
}

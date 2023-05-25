package com.bn.device.service.impl;

import com.bn.common.dto.common.TokenType;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.dto.device.DeviceFamilyInfo;
import com.bn.common.dto.device.DeviceModelInfo;
import com.bn.common.exception.BNException;
import com.bn.device.dao.MasterDataDao;
import com.bn.device.dto.VersionInfo;
import com.bn.device.service.IMasterDataUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sbose on 23/4/23.
 */
@Service
@Slf4j
public class MasterDataUtility implements IMasterDataUtility {

    private static final String UNKNOWN = "AD9999";
    private final Map<Long, DeviceFamilyInfo> dfiHashMap = new HashMap<Long, DeviceFamilyInfo>();
    private ArrayList<DeviceModelInfo> dmiList;
    private List<TokenTypeInfo> ttiList;
    
    private final Map<String, DeviceModelInfo> dmiHashMapByName = new HashMap<String, DeviceModelInfo>();
    private final Map<String, DeviceModelInfo> dmiHashMapById = new HashMap<String, DeviceModelInfo>();
    private final Map<String, TokenTypeInfo> ttiHashMap = new HashMap<String, TokenTypeInfo>();

    @Autowired
    @Qualifier("masterDataDaoImpl")
    MasterDataDao masterDataDao;

    @Override
    public DeviceModelInfo getDeviceModelByName(String modelName) throws BNException {
        if(dmiList == null || dmiList.isEmpty()) {
            dmiList = getDeviceModels();
        }
        final DeviceModelInfo dmi = dmiHashMapByName.get(modelName);
        if(dmi == null) {
            final String error = "getDeviceModelByName: DeviceModelInfo is null for modelName=" + modelName;
            log.error(error);
            throw BNException.getInstance("AD1203", error);
        }
        return dmi;
    }

    @Override
    public ArrayList<DeviceModelInfo> getDeviceModels() throws BNException {
        try {
            if(dmiList == null || dmiList.isEmpty()) {
                dmiList = masterDataDao.getDeviceModels();
                for(DeviceModelInfo dmi : dmiList) {
                    dmiHashMapByName.put(dmi.getName(), dmi);
                    dmiHashMapById.put(dmi.getModelID(), dmi);
                }
            }
        }
        catch(Exception e) {
            throw handle(UNKNOWN, "getDeviceModels:", e);
        }
        return dmiList;
    }

    @Override
    public List<VersionInfo> getVersionInfoList() throws BNException {
        return masterDataDao.loadDBVersionInfo();
    }

    @Override
    public String getDeviceFamilyByFamilyId(int familyId) throws BNException {
        try {
            if(dfiHashMap.isEmpty()) {
                final Iterable<DeviceFamilyInfo> dfList = masterDataDao.getDeviceFamily();
                for(DeviceFamilyInfo df : dfList) {
                    dfiHashMap.put(new Long(df.getId()), df);
                }
            }
            final DeviceFamilyInfo dfi = dfiHashMap.get((long)familyId);
            return dfi.getName();
        }
        catch(Exception e) {
            throw handle(UNKNOWN, "getDeviceFamilyByModelId:family=" + familyId, e);
        }
    }

    private static BNException handle(String errorCode, String error, Exception e) {
        log.error(error, e);
        if(e instanceof BNException) {
            return (BNException)e;
        }
        return BNException.getInstance(errorCode, error + ", " + e.getMessage(), e);
    }

    @Override
    public DeviceModelInfo getDeviceModelById(String modelId) throws BNException {
        if(dmiList == null || dmiList.isEmpty()) {
            dmiList = getDeviceModels();
        }
        final DeviceModelInfo dmi = dmiHashMapById.get(modelId);
        if(dmi == null) {
            final String error = "getDeviceModelById: DeviceModelInfo is null for modeId=" + modelId;
            log.error(error);
            throw BNException.getInstance("AD1203", error);
        }
        return dmi;
    }

    @Override
    public TokenTypeInfo getTokenType(TokenType tokenType) throws BNException {
        try {
            if(ttiHashMap.isEmpty()) {
                getTokenTypes();
            }
            return ttiHashMap.get(tokenType.getTokenType());
        }
        catch(Exception e) {
            throw handle(UNKNOWN, "getTokenType:tokenType=" + tokenType, e);
        }
    }

    private void getTokenTypes() throws BNException {
        try {
            if(ttiList == null || ttiList.isEmpty()) {
                ttiList = masterDataDao.getTokenTypes();
                for(TokenTypeInfo tti : ttiList) {
                    ttiHashMap.put(tti.getId(), tti);
                }
            }
        }
        catch(Exception e) {
            throw handle(UNKNOWN, "getTokenTypes:", e);
        }
    }
}

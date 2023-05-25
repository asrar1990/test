package com.bn.device.service.impl;

import com.bn.device.dao.DevicePlatformDao;
import com.bn.device.dto.DevicePlatform;
import com.bn.device.service.DevicePlatformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class DevicePlatformServiceImpl implements DevicePlatformService {

    @Autowired
    @Qualifier("devicePlatformDaoImpl")
    private DevicePlatformDao devicePlatformDao;

    HashMap<Integer, DevicePlatform> devicePlatformMap = null;

    @Override
    public HashMap<Integer, DevicePlatform> getDevicePlatformsMap() {
        if(devicePlatformMap == null) {
            reloadDevicePlatform();
        }
        return devicePlatformMap;
    }

    @Override
    public void reloadDevicePlatform() {
        List<DevicePlatform> devicePlatforms = getDevicePlatforms();
        HashMap<Integer, DevicePlatform> result = new HashMap<>();
        for (DevicePlatform devicePlatform : devicePlatforms) {
            result.put(devicePlatform.getId(), devicePlatform);
        }
        this.devicePlatformMap = result;
        log.info("Reloaded the device platform data from database.");
    }
    
    private List<DevicePlatform> getDevicePlatforms() {
        return devicePlatformDao.getAllDevicePlatform();
    }
}

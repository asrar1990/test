package com.bn.device.service;

import com.bn.device.dto.DevicePlatform;

import java.util.HashMap;

public interface DevicePlatformService {
    HashMap<Integer, DevicePlatform> getDevicePlatformsMap();
    void reloadDevicePlatform();
}

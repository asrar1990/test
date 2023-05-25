package com.bn.common.service;

import com.bn.common.dto.common.ConfigInfo;

import java.util.HashSet;
import java.util.List;

public interface ConfigService {
	
	boolean getBooleanValue(String serviceName, String key);
	
	boolean getBooleanValue(String serviceName, String key, boolean defaultValue);
	
	int getNumberValue(String serviceName, String key, int defaultValue);
	
	int getNumberValue(String serviceName, String key);
	
	long getLongValue(String serviceName, String key, long defaultValue);
	
	long getLongValue(String serviceName, String key);
	
    String getStringValue(String serviceName, String key);

    String getStringValue(String serviceName, String key, String defaultValue);

    List<String> getStringArrayValue(String serviceName, String key);

    List<String> getStringArrayValue(String serviceName, String key, String defaultValue);

	HashSet<String> getStringSetValue(String serviceName, String key);
	
	List<ConfigInfo> getAllConfig();
	
}

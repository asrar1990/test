package com.bn.common.service.impl;

import com.bn.common.dao.CommonDao;
import com.bn.common.dto.common.ConfigInfo;
import com.bn.common.service.ConfigService;
import com.bn.common.util.ConverterUtil;
import com.bn.common.validator.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor 
@Slf4j
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	@Qualifier("commonDaoImpl")
	private CommonDao commonDao;
	
	private ConcurrentHashMap<String, ConcurrentHashMap<String, String>> configMap = null;

    @Override
    public boolean getBooleanValue(String serviceName, String key) {
        return getBooleanValue(serviceName, key, false);
	}

    @Override
    public boolean getBooleanValue(String serviceName, String key, boolean defaultValue) {
        boolean result = defaultValue;
        try {
            final String value = getValueFromMap(serviceName, key);
            if(StringUtils.isNotBlank(value)) {
                result = "true".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value);
            }
            log.debug("getBooleanValue:" + serviceName + ':' + key + ':' + result);
        }
        catch(Exception e) {
            log.warn("getBooleanValue, serviceName=" + serviceName + ", key=" + key, e);
        }
        return result;
    }

    @Override
    public int getNumberValue(String serviceName, String key) {
		int result = 0;
		
		try {
			String value = this.getValueFromMap(serviceName, key);
			if (!Validator.isBlank(value) && Validator.isInteger(value))
				result = ConverterUtil.convertToInt(value);
			
            log.debug("getNumberValue:" + serviceName + ":" + key + ":" + result);
			
		} catch (Exception e) {
			log.warn("getNumberValue, serviceName=" + serviceName + ", key=" + key, e);
		}
		
		return result;
	}
	
	@Override
    public int getNumberValue(String serviceName, String key, int defaultValue) {
		int result = 0;
		
		try {
			String value = this.getValueFromMap(serviceName, key);
			if (!Validator.isBlank(value) && Validator.isInteger(value))
				result = ConverterUtil.convertToInt(value);
			else
				result = defaultValue;
			
            log.debug("getNumberValue:" + serviceName + ":" + key + ":" + result);
			
		} catch (Exception e) {
			log.warn("getNumberValue, serviceName=" + serviceName + ", key=" + key, e);
		}
		
		return result;
	}
	
	@Override
    public long getLongValue(String serviceName, String key) {
		long result = 0;
		
		try {
			String value = this.getValueFromMap(serviceName, key);
			if (!Validator.isBlank(value) && Validator.isLong(value))
				result = ConverterUtil.convertToLong(value);
			
            log.debug("getLongValue:" + serviceName + ":" + key + ":" + result);
			
		} catch (Exception e) {
			log.warn("getLongValue, serviceName=" + serviceName + ", key=" + key, e);
		}
		
		return result;
	}
	
	@Override
    public long getLongValue(String serviceName, String key, long defaultValue) {
		long result = 0;
		
		try {
			String value = this.getValueFromMap(serviceName, key);
			if (!Validator.isBlank(value) && Validator.isLong(value))
				result = ConverterUtil.convertToLong(value);
			else
				result = defaultValue;
			
            log.debug("getLongValue:" + serviceName + ":" + key + ":" + result);
			
		} catch (Exception e) {
			log.warn("getLongValue, serviceName=" + serviceName + ", key=" + key, e);
		}
		
		return result;
	}

    @Override
    public String getStringValue(String serviceName, String key) {
        String result = null;
        try {
            result = this.getValueFromMap(serviceName, key);
        }
        // MEJ this is absolutely terrible (to catch exception and just eat it) but I'm following the convention
        catch(Exception e) {
            log.warn("getStringValue, serviceName=" + serviceName + ", key=" + key, e);
        }
        return result;
    }

    @Override
    public String getStringValue(String serviceName, String key, String defaultValue) {
        String result = this.getValueFromMap(serviceName, key);
        if (Validator.isBlank(result)) {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public List<String> getStringArrayValue(String serviceName, String key) {
    	List<String> result = null;
        String tmp = this.getStringValue(serviceName, key);
        if (!Validator.isBlank(tmp)) {
            result = tokenize(tmp,",");
        }
        return result;
    }

    @Override
    public List<String> getStringArrayValue(String serviceName, String key, String defaultValue) {
    	List<String> result = getStringArrayValue(serviceName, key);
        if (result == null) {
            result = tokenize(defaultValue,",");	// defaultValue.split(',') ??
        }
        return result;
    }

	@Override
    public HashSet<String> getStringSetValue(String serviceName, String key) {
		List<String> sList = getStringArrayValue(serviceName, key);
		if (sList!=null && sList.size()>0) {
			HashSet<String> sSet = new HashSet<String>(sList.size());
			for (String s:sList)
				sSet.add(s.trim());
			return sSet;
		}
		return null;
	}

    private static List<String> tokenize(String src, String delimiter) {
        StringTokenizer st = new StringTokenizer(src, delimiter);
        List<String> result = new ArrayList<String>(st.countTokens());
        while (st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result;
    }
	
	@Override
    public List<ConfigInfo> getAllConfig() {
		return commonDao.getAllConfig();
	}

	
	private void loadAllConfig(boolean reload) {
		if (reload || configMap==null) {
			List<ConfigInfo> allConfig = this.getAllConfig();
			ConcurrentHashMap<String, ConcurrentHashMap<String, String>> tempHashMap = new ConcurrentHashMap<>();
			for (ConfigInfo c:allConfig) {
				ConcurrentHashMap<String, String> serviceMap = null;
				String service = c.getService();
				if (tempHashMap.get(service)==null) {
					serviceMap = new ConcurrentHashMap<String, String>();
				} else {
					serviceMap = tempHashMap.get(service);
				}
				if (c.getSite().equals("-"))
					serviceMap.put(c.getKey(), c.getValue());
				else
					serviceMap.put(c.getKey()+c.getSite(), c.getValue());
				tempHashMap.put(service, serviceMap);
			}
			
			configMap = tempHashMap;
            log.debug("loadAllConfig:" + reload);
		}
	}

	private String getValueFromMap(String service, String key) {
		String value = null;
		String site = System.getProperty("BNSERVICE_SITE");

		if (configMap==null)
			this.loadAllConfig(false);

		ConcurrentHashMap<String, String> aMap = configMap.get(service);
		if (aMap!=null) {
			if (Validator.isBlank(site))
				value = aMap.get(key);
			else {
				value = aMap.get(key+site);
				if (Validator.isBlank(value))
					value = aMap.get(key);
			}
		}

		return value;
	}
}

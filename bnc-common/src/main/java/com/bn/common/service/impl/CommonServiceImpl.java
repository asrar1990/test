package com.bn.common.service.impl;

import com.bn.common.service.CommonService;
import com.bn.common.exception.CommonErrorInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Component 
@RequiredArgsConstructor 
@Slf4j
public class CommonServiceImpl implements CommonService {
	private Map<String, CommonErrorInfo> errorCodeMap;
	
    public CommonErrorInfo getErrorCode(String errorCode) {
		if (errorCodeMap==null) {
			errorCodeMap = getErrorTable(Locale.US);
		}
		return errorCodeMap!=null && errorCodeMap.containsKey(errorCode) ? errorCodeMap.get(errorCode) :
				new CommonErrorInfo("",errorCode,errorCode + ":NO SUCH ERROR CODE IN DATABASE");
	}
	
	private Map<String,CommonErrorInfo> getErrorTable(Locale l) {
		Map<String,CommonErrorInfo> tbl = new HashMap<String,CommonErrorInfo>();
		try {
			ResourceBundle bun = ResourceBundle.getBundle("ErrorCodeTable", l);
			for(String k : bun.keySet()) {   // Another needless copy of data...
				String[] ar = bun.getString(k).split("\\|");
				tbl.put(k, new CommonErrorInfo(ar[1], k, ar[3], ar[2]));
			}
		} catch(Exception ex) {
			log.error("Unable to load ErrorCodeTable.properties:" + ex.getMessage());
		}
		return tbl;
	}
}

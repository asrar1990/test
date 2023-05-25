package com.bn.account.service.impl;

import com.bn.account.dao.MasterDataDao;
import com.bn.account.service.IMasterDataUtility;
import com.bn.common.dto.common.TokenStateInfo;
import com.bn.common.dto.common.TokenType;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.exception.BNException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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

    private List<TokenStateInfo> tsiList;
    private List<TokenTypeInfo> ttiList;
    private final Map<String, TokenStateInfo> tsiHashMap = new HashMap<>();
    private final Map<String, TokenTypeInfo> ttiHashMap = new HashMap<String, TokenTypeInfo>();


    @Autowired
    @Qualifier("masterDataDaoImpl")
    MasterDataDao masterDataDao;

    @Override
    public TokenStateInfo getTokenState(int stateId) throws BNException {
        try {
            if(tsiList == null || tsiList.isEmpty()) {
                tsiList = masterDataDao.getTokenStates();
                for(TokenStateInfo tsi : tsiList) {
                    tsiHashMap.put(String.valueOf(tsi.getId()), tsi);
                }
            }
            return tsiHashMap.get(String.valueOf(stateId));
        }
        catch(Exception e) {
            throw handle(UNKNOWN, "getTokenState:state=" + stateId, e);
        }
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

    private static BNException handle(String errorCode, String error, Exception e) {
        log.error(error, e);
        if(e instanceof BNException) {
            return (BNException)e;
        }
        return BNException.getInstance(errorCode, error + ", " + e.getMessage(), e);
    }
}

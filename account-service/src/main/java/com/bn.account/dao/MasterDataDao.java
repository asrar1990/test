package com.bn.account.dao;

import com.bn.common.dto.common.TokenStateInfo;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.exception.BNException;

import java.util.ArrayList;

/**
 * Created by sbose on 23/4/23.
 */
public interface MasterDataDao {
    ArrayList<TokenStateInfo> getTokenStates() throws BNException;
    ArrayList<TokenTypeInfo> getTokenTypes() throws BNException;
}

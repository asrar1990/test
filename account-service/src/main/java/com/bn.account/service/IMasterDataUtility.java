package com.bn.account.service;

import com.bn.common.dto.common.TokenStateInfo;
import com.bn.common.dto.common.TokenType;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.exception.BNException;

/**
 * Created by sbose on 23/4/23.
 */
public interface IMasterDataUtility {
    TokenStateInfo getTokenState(int stateId) throws BNException;
    TokenTypeInfo getTokenType(TokenType tokenType) throws BNException;
}

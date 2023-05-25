package com.bn.account.dao;

import com.bn.account.rest.model.Account;
import com.bn.account.rest.model.Device;
import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.exception.BNException;

import java.util.Locale;

/**
 * Created by sbose on 16/5/23.
 */
public interface AccountDao {
    Account createAndSaveUserToken(Account account, Device device, TokenTypeInfo accountTokenType, Locale locale) throws BNException;
    AccountInfo getAccountByEmail(String email, String partnerId) throws BNException;
}

package com.bn.account.service;

import com.bn.account.rest.model.Account;
import com.bn.account.rest.model.Device;
import com.bn.account.rest.model.RegisterAccountResponse;
import com.bn.common.exception.BNException;

/**
 * Created by sbose on 16/5/23.
 */
public interface AccountService {
    RegisterAccountResponse endpointRegisterAccount(Account account, Device device, String password, String userHash,
            String userRand, boolean validateAccount) throws BNException;
}

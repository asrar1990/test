package com.bn.account.service.impl;

import com.bn.account.dao.AccountDao;
import com.bn.account.rest.model.*;
import com.bn.account.service.AccountService;
import com.bn.account.service.IMasterDataUtility;
import com.bn.account.util.AppCalendar;
import com.bn.account.util.Constants;
import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.common.TokenInfo;
import com.bn.common.dto.common.TokenStateInfo;
import com.bn.common.dto.common.TokenType;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.exception.BNException;
import com.bn.common.util.GUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by sbose on 16/5/23.
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    public static final String INVALID_EMAIL = "AD1101";

    @Autowired
    @Qualifier("accountDaoImpl")
    AccountDao accountDao;


    @Autowired
    @Qualifier("masterDataUtility")
    IMasterDataUtility masterDataUtil;

    @Override
    public RegisterAccountResponse endpointRegisterAccount(Account aInfo, Device deviceInfo, String password,
            String userHash, String userRand, boolean validateAccount) throws BNException {
        RegisterAccountResponse registerAccountResponse;
        final long beginTime = System.currentTimeMillis();
        if(aInfo == null || StringUtils.isBlank(aInfo.getEmail())) {
            throw BNException.getInstance(INVALID_EMAIL);
        }
        if(deviceInfo == null) {
            throw BNException.getInstance("AD1200", "endpointRegisterAccount:device info is null");
        }

        String email = aInfo.getEmail();
        final StringWriter sw = new StringWriter();
        sw.append("email=").append(email);
        sw.append(", devInfo=").append(deviceInfo.toString());
        sw.append(", userRand=").append(userRand).append(", userHash=").append(userHash);
        Locale locale = new Locale("en", "US");
        registerAccountResponse = registerUserDevice(aInfo, deviceInfo, locale);
        return registerAccountResponse;
    }

    private RegisterAccountResponse registerUserDevice(Account aInfo, Device di, Locale locale)
            throws BNException {
        log.debug("registerUserDevice, email=" + aInfo.getEmail() + ", device=" + di);
        final StringWriter sw = new StringWriter();
        sw.append("registerUserDevice").append(", custid=").append(aInfo.getCustId());
        sw.append(", serial#=").append(di.getSerialNumber());

        if(di.getDeviceId() <= 0) {
            throw BNException.getInstance("AD1222");
        }

        final TokenTypeInfo accountTokenType = masterDataUtil.getTokenType(TokenType.USER_AUTH);
        TokenInfo userToken = createUserToken(accountTokenType, 1);
        AccountInfo accountInfoFromDB = accountDao.getAccountByEmail(aInfo.getEmail(), Constants.DEFAULT_PARTNER);
        if(accountInfoFromDB != null) {
            aInfo.setAccountId(accountInfoFromDB.getId());
            aInfo.setCustId(accountInfoFromDB.getCustId());
            aInfo.setAccountHash(accountInfoFromDB.getAccountHash());
        }
        Account updatedAccount = accountDao.createAndSaveUserToken(aInfo, di, accountTokenType, locale);

        RegisterAccountResponse registerAccountResponse = new RegisterAccountResponse();
        
        RegisterAccountResponseAuthToken registerAccountResponseAuthToken = new RegisterAccountResponseAuthToken();
        registerAccountResponseAuthToken.setToken(userToken.getToken());
        registerAccountResponseAuthToken.setDuration(String.valueOf(userToken.getDuration()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strExpiryDate = dateFormat.format(userToken.getTokenExpiresOn());
        registerAccountResponseAuthToken.setTokenExpireTime(strExpiryDate);
        registerAccountResponseAuthToken.setType(userToken.getTokenType());
        
        registerAccountResponse.setAccount(updatedAccount);
        registerAccountResponse.setAuthToken(registerAccountResponseAuthToken);

        List<RegisterAccountResponseExtraInfo> extraInfo = new ArrayList<>();
        registerAccountResponse.setExtraInfo(extraInfo);

        return registerAccountResponse;
    }

    private TokenInfo createUserToken(TokenTypeInfo tokenType, long tokenLimit) throws BNException {
        final Date createDate = AppCalendar.getCalendar().getTime();
        final String token = GUID.getToken();
        long duration = tokenType.getDuration() * 1000;
        if(tokenLimit > 0) {
            duration = tokenLimit * 1000;
        }
        final long expireDate = createDate.getTime() + duration;
        final TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(token);
        tokenInfo.setTokenType(tokenType.getId());
        tokenInfo.setTokenExpiresOn(new Date(expireDate));
        tokenInfo.setTokenCreateDate(createDate);

        //get active token state
        final TokenStateInfo tsi = masterDataUtil.getTokenState(Constants.TOKEN_STATE_ACTIVE);
        tokenInfo.setTokenState(tsi.getId());
        return tokenInfo;
    }
}

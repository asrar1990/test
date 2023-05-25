package com.bn.account.dao.impl;

import com.bn.account.dao.AccountDao;
import com.bn.account.rest.model.Account;
import com.bn.account.rest.model.Device;
import com.bn.account.util.Constants;
import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.common.AccountOptions;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.dto.util.DBUtil;
import com.bn.common.dto.util.ResourceUtil;
import com.bn.common.exception.BNException;
import com.bn.common.security.KeyManager;
import com.bn.common.util.CryptoUtil;
import com.bn.common.util.GUID;
import com.bn.common.util.ResultSetUtil;
import com.bn.common.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by sbose on 16/5/23.
 */
@Component
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class AccountDaoImpl implements AccountDao {
    private static final String JDBC_FILE = "/jdbc/accountsJDBC.properties";
    private final Properties jdbcAccountSql = ResourceUtil.loadPropertiesThrowRunnable(JDBC_FILE);

    @Autowired
    private DataSource dataSource;

    @Override
    public Account createAndSaveUserToken(Account account, Device device, TokenTypeInfo accountTokenType, Locale locale) throws BNException {
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            if(account.getAccountId() == null || account.getAccountId() <= 0) {
                final String token = GUID.getToken();
                account.setCustId(token.substring(0, 16));
                createAccount(account, device, accountTokenType, locale);
            } else if(account.getAccountId() != null && account.getAccountId() > 0) {
                updateAccountDetail(account, account.getAccountId(), locale);
            }
            return account;
        }
        catch(Exception e) {
            throw BNException.getInstance("AD8201", "Failed to insert account_token account=" + account);
        } finally {
            DBUtil.releaseResources(ps, conn);
        }
    }

    @Override
    public AccountInfo getAccountByEmail(String email, String partnerId) throws BNException {
        AccountInfo aInfo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if(Validator.isBlank(email)) {
            return null;
        }

        try {
            final String sql = jdbcAccountSql.getProperty("GET_ACCOUNTBYEMAIL");
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, StringUtils.defaultIfEmpty(partnerId, Constants.DEFAULT_PARTNER));
            rs = ps.executeQuery();
            // single row expected                              
            if(rs.next()) {
                aInfo = getAccountInfoFromResultSet(rs);
            }
        }
        catch(Exception e) {
            throw BNException.getInstance("AD9999", "database exception while inserting account " + e.getMessage(), e);
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }
        return aInfo;
    }

    private Account createAccount(Account account, Device device, TokenTypeInfo accountTokenType, Locale locale) throws BNException {
        PreparedStatement ps = null;
        int retVal = 0;
        Long prefStartTime = null;
        Connection conn = null;
        try {
            // get next account id sequence value
            final String sql = jdbcAccountSql.getProperty("INSERT_ACCOUNT");
            final int[] cols = { 1 };
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql, cols);
            ps.setString(1, account.getEmail());
            ps.setString(2, locale.getLanguage());
            ps.setString(3, locale.getCountry());
            ps.setString(4, account.getCustId());
            ps.setString(5, account.getFirstName());
            ps.setString(6, account.getLastName());
            ps.setInt(7, BooleanUtils.toInteger(true));
            ps.setInt(8, BooleanUtils.toInteger(true));
            ps.setTimestamp(9, null);
            ps.setString(10, Constants.DEFAULT_RETAILER);
            ps.setString(11, Constants.DEFAULT_RETAILER_COUNTRY);
            ps.setString(12, Constants.DEFAULT_RETAILER_COUNTRY);
            ps.setString(13, null);
            ps.setString(14, createAccountHash(account.getCustId()));
            ps.setString(15, Constants.DEFAULT_PARTNER);
            ps.setObject(16, AccountOptions.NONE.getValue());
            ps.setString(17, null);
            ps.setString(18, null);
            ps.setString(19, null);
            ps.setString(20, null);
            ps.setDate(21,null);
            ps.setDate(22,null);

            prefStartTime = System.currentTimeMillis();
            retVal = ps.executeUpdate();
            log.info( "InsertAccount",  prefStartTime, System.currentTimeMillis(), true);
            if(account.getAccountId() == null && retVal > 0) {
                account.setAccountId(ResultSetUtil.getId(ps));
            }
        }
        catch(Exception e) {
            if(prefStartTime!=null) {
                log.info("InsertAccount", prefStartTime, System.currentTimeMillis(), false);
            }
            // If ORA-00001 (unique constraint violated), then log as debug not fatal and throw AD1142 (account exists) instead of AD1100 (invalid account)
            if(e instanceof SQLException && ((SQLException)e).getErrorCode() == 1) {
                log.debug("insertAccount, account already exists " + account, e);
                throw BNException.getInstance(Constants.ACCOUNT_EXISTS_ERROR, "account already exists " + e.getMessage(), e);
            }
            log.error("insertAccount " + account, e);
            throw BNException.getInstance("AD1100", "database exception while inserting account " + e.getMessage(), e);
        } finally {
            DBUtil.releaseResources(ps, conn);
        }
        return account;
    }

    private int updateAccountDetail(Account aInfo, long accountId, Locale locale) throws BNException {
        final StringBuffer fields = new StringBuffer(250);
        final Collection<String> values = new ArrayList<String>(9);
        addFields(aInfo, fields, values, locale);
        if(values.isEmpty()) {
            log.debug("updateAccountDetail: All fields are blank! accountId=" + accountId + ", aInfo=" + aInfo);
            return 0;
        }
        PreparedStatement ps = null;
        int result = 0;
        Connection conn = null;
        try {
            final String sql = String.format(jdbcAccountSql.getProperty("UPDATE_ACCOUNTDETAILBYID"), fields);
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            int i = 0;
            for(String value : values) {
                ps.setString(++i, value);
            }
            ps.setLong(++i, accountId);
            result = ps.executeUpdate();
            DBUtil.commit(conn);
            log.debug("updateAccountDetail, accountId=" + accountId + ", result=" + result + ", sql=" + sql + ", " +
                    "values=" + values + ", aInfo=" + aInfo);
        }
        catch(Exception e) {
            log.error("updateAccountDetail, accountId=" + accountId + ':' + aInfo, e);
            throw BNException.getInstance("AD1100", "database exception while inserting account " + e.getMessage(), e);
        }
        finally {
            DBUtil.releaseResources(ps, conn);
        }
        return result;
    }

    private static void addFields(Account aInfo, StringBuffer fields, Collection<String> values, Locale locale) {
        add("BACKENDCUSTID", aInfo.getCustId(), fields, values);
        add("FIRSTNAME", aInfo.getFirstName(), fields, values);
        add("LASTNAME", aInfo.getLastName(), fields, values);
        add("EMAIL_ADDRESS", aInfo.getEmail(), fields, values);
        add("LANGUAGEID", locale.getLanguage(), fields, values);
        add("COUNTRYID", Constants.DEFAULT_RETAILER_COUNTRY, fields, values);
        add("RETAILERID", Constants.DEFAULT_RETAILER, fields, values);
        add("RETAILER_ACTIVECOUNTRYID", Constants.DEFAULT_RETAILER_COUNTRY, fields, values);
        add("ACCOUNT_HASH", aInfo.getAccountHash(), fields, values);
    }

    private static void add(String field, String value, StringBuffer fields, Collection<String> values) {
        if(StringUtils.isNotBlank(value)) {
            fields.append(field).append("=?,");
            values.add(value);
        }
    }
    
    private AccountInfo getAccountInfoFromResultSet(ResultSet rs) throws SQLException {
        final AccountInfo aInfo = fillAccountInfo(rs, null);
        aInfo.setFreeTitlesLastAddedDate(rs.getTimestamp("FREE_TITLES_LAST_ADDED_DATE"));
        return aInfo;
    }

    private AccountInfo fillAccountInfo(ResultSet rs, String prefix) throws SQLException {
        if( prefix == null ){
            prefix = "";
        }
        AccountInfo aInfo = null;
        if(rs != null) {
            aInfo = new AccountInfo();
            aInfo.setId(rs.getLong(prefix+"ID"));
            aInfo.setEmail(rs.getString(prefix+"EMAIL_ADDRESS"));
            aInfo.setLanguageId(rs.getString(prefix+"LANGUAGEID"));
            aInfo.setCountryId(rs.getString(prefix+"COUNTRYID"));
            aInfo.setCustId(rs.getString(prefix+"BACKENDCUSTID"));
            aInfo.setCreateDate(rs.getTimestamp(prefix+"CREATED_TIME"));
            aInfo.setModifyDate(rs.getTimestamp(prefix+"LAST_UPDATED_TIME"));
            aInfo.setFirstName(rs.getString(prefix+"FIRSTNAME"));
            aInfo.setLastName(rs.getString(prefix+"LASTNAME"));
            aInfo.setPrivacy(rs.getBoolean(prefix+"PRIVACY"));
            aInfo.setAdobeId(rs.getString(prefix+"ADOBE_ID"));
            aInfo.setWebReaderId(rs.getString(prefix+"WEBREADER_ID"));
            aInfo.setWebReaderTime(rs.getTimestamp(prefix+"WEBREADER_TIME"));
            aInfo.setWebReaderCount(rs.getInt(prefix+"WEBREADER_COUNT"));
            aInfo.setFeatures(rs.getString(prefix+"FEATURES"));

            if(!Validator.isBlank(rs.getString(prefix+"RETAILERID"))) {
                aInfo.setRetailer(rs.getString(prefix+"RETAILERID"));
            }
            if(!Validator.isBlank(rs.getString(prefix+"RETAILER_COUNTRYID"))) {
                aInfo.setRetailerCountryId(rs.getString(prefix+"RETAILER_COUNTRYID"));
            }
            if(!Validator.isBlank(rs.getString(prefix+"RETAILER_ACTIVECOUNTRYID"))) {
                aInfo.setRetailerActiveCountryId(rs.getString(prefix+"RETAILER_ACTIVECOUNTRYID"));
            }
            aInfo.setRetailerAccountId(rs.getString(prefix+"RETAILER_ACCOUNTID"));
            aInfo.setLinkId(rs.getString(prefix+"LINKID"));
            aInfo.setAccountOptions(decode(rs.getInt(prefix+"ACCOUNT_OPTIONS")));
            aInfo.setAccountHash(rs.getString(prefix+"ACCOUNT_HASH"));
            aInfo.setProfileId(rs.getLong(prefix+"PROFILEID"));
            aInfo.setPartnerId(rs.getString(prefix+"PARTNERID"));
            aInfo.setLinkTime(rs.getTimestamp(prefix+"LINK_TIME"));
        }
        return aInfo;
    }

    /**
     * Returns an EnumSet from the integer value of a bit mask
     * @param code integer value of bit mask
     * @return set of options
     */
    private static EnumSet<AccountOptions> decode(int code) {
        final AccountOptions[] values = AccountOptions.values();
        final EnumSet<AccountOptions> result = EnumSet.noneOf(AccountOptions.class);
        int newCode = code;
        while(newCode != 0) {
            final int ordinal = Integer.numberOfTrailingZeros(newCode);  //get index of the first enum flag that's on
            if(ordinal < values.length) {   //now add that flag to the return set
                result.add(values[ordinal]);
            }
            newCode ^= Integer.lowestOneBit(newCode); //once added, exclude that flag from the mask
        }
        return result;
    }

    private static String createAccountHash(String id) {
        try {
            if(!Validator.isBlank(id)) {
                return CryptoUtil.encrypt(id, KeyManager.getInstance().getAccountDeviceKey());
            }
        }
        catch(Exception e) {
            log.info("createAccountHash, id=" + id, e);
        }
        return null;
    }
}

package com.bn.device.dao.impl;

import com.bn.common.dto.common.AuthInfo;
import com.bn.common.dto.common.AuthenticateLogInfo;
import com.bn.common.dto.common.TokenInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.dto.device.HashKeyInfo;
import com.bn.common.dto.util.DBUtil;
import com.bn.common.dto.util.ResourceUtil;
import com.bn.common.exception.BNException;
import com.bn.common.util.ResultSetUtil;
import com.bn.common.validator.Validator;
import com.bn.device.dao.DeviceDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created by sbose on 23/4/23.
 */
@Component
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class DeviceDaoImpl implements DeviceDao {

    private static final String JDBC_FILE = "/jdbc/deviceJDBC.properties";
    private final Properties jdbcDeviceSql = ResourceUtil.loadPropertiesThrowRunnable(JDBC_FILE);

    @Autowired
    private DataSource dataSource;

    @Override
    public void insertHashKey(HashKeyInfo hki) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            final String sql = jdbcDeviceSql.getProperty("INSERT_HASHKEY");
            ps = conn.prepareStatement(sql);
            ps.setString(1, hki.getUniqueid());
            ps.setString(2, hki.getModelid());
            ps.setString(3, hki.getPassphrase());
            ps.setString(4, hki.getHashkey());
            final int result = ps.executeUpdate();
            log.debug("insertHashKey:" + hki + ':' + result);
        }
        catch(SQLException e) {
            log.error("getHashKey:" + hki, e);
        }
        catch(Exception ex) {
            log.error("getHashKey:" + hki, ex);
        }
        finally {
            DBUtil.releaseResources(ps, conn);
        }
    }

    @Override
    public List<HashKeyInfo> getHashKey(String uniqueid) {
        final List<HashKeyInfo> hkList = new ArrayList<HashKeyInfo>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            final String sql = jdbcDeviceSql.getProperty("GET_HASHKEY");
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, uniqueid);
            rs = ps.executeQuery();
            while(rs.next()) {
                final HashKeyInfo hki = new HashKeyInfo();
                hki.setHashkey(rs.getString("HASHKEY"));
                hki.setPassphrase(rs.getString("PASSPHRASE"));
                hki.setModelid(rs.getString("MODELID"));
                hki.setUniqueid(uniqueid);
                hkList.add(hki);
            }
        }
        catch(SQLException e) {
            log.error("getHashKey:uniqueId=" + uniqueid, e);
        }
        catch(Exception ex) {
            log.error("getHashKey:uniqueId=" + uniqueid, ex);
            return null;
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }
        return hkList;
    }

    @Override
    public int updateHashKey(HashKeyInfo hki) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            String sql = jdbcDeviceSql.getProperty("UPDATE_HASHKEY");
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, hki.getHashkey());
            ps.setString(2, hki.getModelid());
            ps.setString(3, hki.getUniqueid());
            result = ps.executeUpdate();
            log.debug("updateHashKey-1:" + hki + ", result=" + result);

            sql = jdbcDeviceSql.getProperty("UPDATE_DEVICE_HASHKEY");
            ps = conn.prepareStatement(sql);
            ps.setString(1, hki.getHashkey());
            ps.setString(2, hki.getModelid());
            ps.setString(3, hki.getUniqueid());
            result = ps.executeUpdate();
            DBUtil.commit(conn);
            log.debug("updateHashKey-2:" + hki + ", result=" + result);
        }
        catch(SQLException e) {
            log.error("updateHashKey:" + hki + ", result=" + result, e);
        }
        catch(Exception ex) {
            log.error("updateHashKey:" + hki + ", result=" + result, ex);
        }
        finally {
            DBUtil.releaseResources(ps, conn);
        }
        return result;
    }

    @Override
    public DeviceInfo getDeviceBySerialNumber(String serialNumber) {
        DeviceInfo di = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if(Validator.isBlank(serialNumber)) {
                throw BNException.getInstance("AD1201", serialNumber);
            }

            final String sql = jdbcDeviceSql.getProperty("GET_DEVICE_BY_SERIAL_NUMBER");
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, serialNumber);
            rs = ps.executeQuery();
            if(rs.next()) { // expecting a single row back
                di = fillDeviceInfo(rs, null);
            }
        }
        catch(SQLException e) {
            log.error("getDeviceBySerialNumber:serialNumber=" + serialNumber, e);
        }
        catch(BNException be) {
            log.debug("getDeviceBySerialNumber:serialNumber=" + serialNumber, be);
            return null;
        }
        catch(Exception ex) {
            log.error("getDeviceBySerialNumber:serialNumber=" + serialNumber, ex);
            return null;
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }

        return di;
    }

    private DeviceInfo fillDeviceInfo(ResultSet rs, String prefix) throws SQLException {
        if( prefix == null ){
            prefix = "";
        }
        DeviceInfo di = null;
        if(rs != null) {
            di = new DeviceInfo();
            di.setId(rs.getLong(prefix+"ID"));
            di.setSerialNumber(rs.getString(prefix+"SERIAL_NUMBER"));
            di.setAccountId(rs.getLong(prefix+"ACCOUNTID"));
            di.setModelId(rs.getString(prefix+"DEVICE_MODELID"));
            di.setImei(rs.getString(prefix+"IMEI"));
            di.setImsi(rs.getString(prefix+"IMSI"));
            di.setModemFirmware(rs.getString(prefix+"MODEM_FIRMWARE"));
            di.setInitialBuildNumber(rs.getString(prefix+"INITIAL_BUILDNUMBER"));
            di.setCurrentBuildNumber(rs.getString(prefix+"CURRENT_BUILDNUMBER"));
            di.setCurrentBuildMajor(rs.getInt(prefix+"CURRENT_BUILDMAJOR"));
            di.setCurrentBuildMinor(rs.getInt(prefix+"CURRENT_BUILDMINOR"));
            di.setCurrentBuildRevision(rs.getInt(prefix+"CURRENT_BUILDREVISION"));
            di.setCreateDate(rs.getTimestamp(prefix+"CREATED_TIME"));
            di.setModifyDate(rs.getTimestamp(prefix+"LAST_UPDATED_TIME"));
            di.setPublicKey(rs.getString(prefix+"PUBLIC_KEY"));
            di.setHashPrivateKey(rs.getString(prefix+"HASH_PRIVATE_KEY"));
            di.setPreReg(rs.getInt(prefix+"PREREG"));
            di.setBlackListed(rs.getInt(prefix+"BLACKLISTED"));
            di.setTimeZone(rs.getString(prefix+"TIME_ZONE"));
            di.setInStoreStatus(rs.getInt(prefix+"INSTORE_STATUS"));
            di.setInStoreDate(rs.getTimestamp(prefix+"INSTORE_TIME"));
            di.setInStoreId(rs.getString(prefix+"INSTORE_STOREID"));
            di.setSoftwareVersion(rs.getString(prefix+"SOFTWARE_VERSION"));
            di.setIccid(rs.getString(prefix+"ICCID"));
            di.setEndpointType(rs.getString(prefix+"ENDPOINT_TYPE"));
            di.setAdobeDeviceId(rs.getString(prefix+"ADOBE_DEVICEID"));
            di.setAddFreeTitleTime(rs.getTimestamp(prefix+"ADDFREETITLETIME"));
            di.setActiveWebReader(rs.getInt(prefix+"ISACTIVE_WEBREADER") == 1);
            di.setwRRefreshFrequency(rs.getInt(prefix+"WEBREADER_REFRESH_FREQUENCY"));
            di.setwRRefreshOffset(rs.getInt(prefix+"WEBREADER_REFRESH_OFFSET"));
            di.setEan(rs.getString(prefix+"EAN"));
            final String language = rs.getString(prefix+"LANGUAGEID");
            di.setLanguageId(StringUtils.defaultString(language, "en"));
            final String country = rs.getString(prefix+"COUNTRYID");
            di.setCountryId(StringUtils.defaultString(country, "US"));
            di.setAdsSupported(rs.getInt(prefix+"ISADSUPPORTED") == 1);
            di.setSourceID(rs.getString(prefix+"SOURCEID"));
            di.setSoftwareUpdatedDate(rs.getDate(prefix+"SOFTWARE_UPDATED_ON"));
            final String cor = rs.getString(prefix+"COUNTRY_OF_RESIDENCE");
            di.setCountryOfResidence(StringUtils.defaultIfEmpty(cor, "US"));
            di.setSiliconId(rs.getString(prefix+"SILICON_ID"));
            di.setHdcpBlocked(rs.getBoolean(prefix+"HDCP_BLOCKED"));
            di.setVideoEnabled(rs.getInt(prefix+"VIDEOENABLED") != 0);
            final String retailerID = rs.getString(prefix+"RETAILERID");
            di.setRetailer(StringUtils.defaultString(retailerID, "BN2"));
        }
        return di;
    }
    
    @Override
    public int insertDeviceWithToken(DeviceInfo dInfo, TokenInfo dti, AuthenticateLogInfo ali) {
        int retVal = 0;
        Long prefStartTime = null;
        if(dInfo != null) {
            Connection conn = null;
            PreparedStatement ps = null;
            try {
                conn = dataSource.getConnection();
                if(dInfo.getId() == 0) {
                    insertDevice(dInfo, conn);
                }
                else {
                    updateDevice(dInfo, conn);
                }
                if(dInfo.getId() > 0) {
                    dti.setDeviceId(dInfo.getId());
                    final String sql = jdbcDeviceSql.getProperty("INSERT_DEVICETOKEN");
                    ps = conn.prepareStatement(sql);
                    ps.setLong(1, dti.getDeviceId());
                    ps.setString(2, dti.getToken());
                    ps.setString(3, dti.getTokenType());
                    ps.setTimestamp(4, new Timestamp(dti.getTokenExpiresOn().getTime()));
                    ps.setInt(5, dti.getTokenState());
                    prefStartTime = System.currentTimeMillis();
                    retVal = ps.executeUpdate();

                    //insert into authenticate log
                    if(retVal == 1) {
                        ali.setDeviceId(dInfo.getId());
                        retVal += insertAuthenticateLog(ali, conn);
                    }

                    if(retVal == 2) {
                        //All inserts were successful, commit the data
                        DBUtil.commit(conn);
                    }
                    else {
                        DBUtil.rollback(conn);
                    }
                }
            }
            catch(Exception e) {
                DBUtil.rollback(conn);
                log.error("insertDeviceToken:" + dti + ", " + dInfo, e);
            }
            finally {
                DBUtil.releaseResources(ps, conn);
            }
        }
        return retVal;
    }

    @Override
    public List<AuthInfo> getDeviceTokensByDeviceId(long deviceid) {
        final List<AuthInfo> authList = new ArrayList<AuthInfo>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // String sql = queries.get(JDBC_GET_DEVICE_TOKENS);
            final String sql = jdbcDeviceSql.getProperty("GET_DEVICE_TOKENS");
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, deviceid);
            rs = ps.executeQuery();
            while(rs.next()) {
                final AuthInfo auth = new AuthInfo();
                auth.setDeviceToken(fillDeviceTokenInfo(rs, ""));
                authList.add(auth);
            }
        }
        catch(SQLException e) {
            log.error("getDeviceTokensByDeviceId:deviceId=" + deviceid, e);
            throw new RuntimeException("Can not acquire cloud DB connection:deviceId=" + deviceid, e);
        }
        catch(RuntimeException e) {
            throw e;
        }
        catch(Exception ex) {
            log.error("getDeviceTokensByDeviceId:deviceId=" + deviceid, ex);
            throw new RuntimeException("Can not acquire cloud DB connection:deviceId=" + deviceid, ex);
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }
        return authList;
    }

    public void expireDeviceTokenNow(long deviceid, Collection<String> tokens, String msg) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            String sql = jdbcDeviceSql.getProperty("EXPIREDEVICETOKENNOW");
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            for (String token : tokens)
            {
                ps.clearParameters();
                ps.setLong(1, deviceid);
                ps.setString(2, token);
                ps.addBatch();
            }
            ps.executeBatch();

            sql = jdbcDeviceSql.getProperty("EXPIREAUTHLOGDEVICETOKENNOW");
            ps = conn.prepareStatement(sql);
            for (String token : tokens)
            {
                ps.clearBatch();
                ps.setString(1, msg);
                ps.setLong(2, deviceid);
                ps.setString(3, token);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (Exception e) {
            log.error("expireDeviceTokenNow:deviceId=" + deviceid + ":token=" + tokens.size(), e);

        } finally {
            DBUtil.releaseResources(null, ps, conn);
        }
    }

    private int insertAuthenticateLog(AuthenticateLogInfo ali, Connection conn) {
        int retVal = 0;
        if(ali != null && ali.getDeviceId() > 0) {
            PreparedStatement ps = null;
            try {
                final String sql = jdbcDeviceSql.getProperty("INSERT_AUTHENTICATELOG");
                ps = conn.prepareStatement(sql);
                ps.setLong(1, ali.getDeviceId());
                ResultSetUtil.setIdOrNull(ps, 2, ali.getAccountId());
                ps.setString(3, ali.getToken());
                ps.setInt(4, ali.getStatus());
                ps.setString(5, ali.getReason());
                ps.setTimestamp(6, ResultSetUtil.getTime(ali.getExpireDate()));
                retVal = ps.executeUpdate();
            }
            catch(SQLException e) {
                log.error("insertDeviceToken:" + ali, e);
            }
            finally {
                DBUtil.releaseResources(ps, null);
            }
        }
        return retVal;
    }

    private int insertDevice(DeviceInfo di, Connection conn) {
        int retVal = 0;
        PreparedStatement ps = null;
        final boolean isOpen = conn != null;
        Long prefStartTime = null;
        try {
            // get next device id sequence value
            final String sql = jdbcDeviceSql.getProperty("INSERT_DEVICE");
            final int[] cols = { 1 };
            ps = conn.prepareStatement(sql, cols);
            ps.setString(1, di.getSerialNumber());
            ResultSetUtil.setIdOrNull(ps, 2, di.getAccountId());
            ps.setString(3, di.getModelId());
            ps.setString(4, di.getImei());
            ps.setString(5, di.getImsi());
            ps.setString(6, di.getModemFirmware());
            ps.setString(7, di.getInitialBuildNumber());
            ps.setString(8, trimCurrentBuildNumber(di.getCurrentBuildNumber()));
            ps.setInt(9, di.getCurrentBuildMajor());
            ps.setInt(10, di.getCurrentBuildMinor());
            ps.setInt(11, di.getCurrentBuildRevision());
            ps.setString(12, di.getPublicKey());
            ps.setString(13, di.getHashPrivateKey());
            ps.setInt(14, di.getPreReg());
            ps.setInt(15, di.getBlackListed());
            ps.setString(16, di.getTimeZone());
            ps.setString(17, di.getSoftwareVersion());
            ps.setString(18, di.getIccid());
            ps.setString(19, di.getEndpointType());
            ps.setString(20, di.getEan());
            ps.setString(21, di.getLanguageId());
            ps.setString(22, di.getCountryId());
            ps.setString(23, di.getSourceID());
            ps.setString(24, di.getCountryOfResidence());
            ps.setString(25, di.getRetailer());
            ps.setString(26, di.getSiliconId());

            prefStartTime = System.currentTimeMillis();
            retVal = ps.executeUpdate();
            if(retVal > 0) {
                di.setId(ResultSetUtil.getId(ps));
            }

            if(!isOpen) {
                DBUtil.commit(conn);
            }
        }
        catch(SQLException e) {
            DBUtil.rollback(conn);
            log.error("insertDevice:" + di, e);
        }
        catch(Exception ex) {
            DBUtil.rollback(conn);
            log.error("insertDevice:" + di, ex);
        }
        finally {
            final ResultSet rs = null;
            if(isOpen) {
                DBUtil.releaseResources(rs, ps, null);
            }
            else {
                DBUtil.releaseResources(rs, ps, conn);
            }
        }
        return retVal;
    }

    private int updateDevice(DeviceInfo di, Connection conn) {
        //TODO: ideally instead of passing the entire object we should pass/indicate just the fields changed...essentially looking at the code,
        //I am updating the fields anticipated to change.
        int retVal = 0;
        PreparedStatement ps = null;
        final boolean isOpen = conn != null;
        Long prefStartTime = null;
        try {
            final String sql = jdbcDeviceSql.getProperty("UPDATE_DEVICE");
            ps = conn.prepareStatement(sql);
            ResultSetUtil.setIdOrNull(ps, 1, di.getAccountId());
            ps.setString(2, di.getModemFirmware());
            ps.setInt(3, di.getCurrentBuildMajor());
            ps.setInt(4, di.getCurrentBuildMinor());
            ps.setInt(5, di.getCurrentBuildRevision());
            ps.setString(6, trimCurrentBuildNumber(di.getCurrentBuildNumber()));
            ps.setString(7, di.getImei());
            ps.setString(8, di.getImsi());
            ps.setString(9, di.getSoftwareVersion());
            ps.setString(10, di.getEndpointType());
            ps.setString(11, di.getIccid());
            ps.setString(12, di.getLanguageId());
            ps.setString(13, di.getCountryId());
            ps.setInt(14, di.getPreReg());
            ps.setInt(15, BooleanUtils.toInteger(di.isAdsSupported()));
            ps.setString(16, di.getEan());
            ps.setString(17, di.getSourceID());
            ps.setString(18, di.getCountryOfResidence());
            ps.setString(19, di.getRetailer());
            ps.setString(20, di.getSiliconId());
            ps.setLong(21, di.getId());
            prefStartTime = System.currentTimeMillis();
            retVal = ps.executeUpdate();
            if(!isOpen) {
                DBUtil.commit(conn);
            }
        }
        catch(SQLException e) {
            DBUtil.rollback(conn);
            log.error("updateDevice:" + di, e);
        }
        catch(Exception ex) {
            DBUtil.rollback(conn);
            log.error("updateDevice:" + di, ex);
            return retVal;
        }
        finally {
            if(isOpen) {
                DBUtil.releaseResources(ps, null);
            }
            else {
                DBUtil.releaseResources(ps, conn);
            }
        }
        return retVal;
    }

    private static String trimCurrentBuildNumber(String buildNumber) {
        String newBuildNumber = buildNumber;
        if(!Validator.isBlank(buildNumber) && buildNumber.length() > 50) {
            newBuildNumber = buildNumber.substring(0, 49);
        }
        return newBuildNumber;
    }

    private TokenInfo fillDeviceTokenInfo(final ResultSet rs,String prefix) throws SQLException {
        TokenInfo tInfo = null;
        if( prefix == null ){
            prefix = "";
        }
        if(rs != null) {
            tInfo = new TokenInfo();
            tInfo.setDeviceId(rs.getLong(prefix+"DEVICEID"));
            tInfo.setToken(rs.getString(prefix+"TOKEN"));
            tInfo.setTokenType(rs.getString(prefix+"TOKEN_TYPEID"));
            tInfo.setTokenCreateDate(rs.getTimestamp(prefix+"CREATED_TIME"));
            tInfo.setTokenExpiresOn(rs.getTimestamp(prefix+"EXPIRED_TIME"));
            tInfo.setTokenState(rs.getInt(prefix+"TOKEN_STATEID"));
        }
        return tInfo;
    }
}

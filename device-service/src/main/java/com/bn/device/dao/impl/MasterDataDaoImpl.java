package com.bn.device.dao.impl;

import com.bn.common.dto.common.TokenStateInfo;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.dto.device.DeviceFamilyInfo;
import com.bn.common.dto.device.DeviceModelInfo;
import com.bn.common.dto.util.DBUtil;
import com.bn.common.dto.util.ResourceUtil;
import com.bn.common.exception.BNException;
import com.bn.device.dao.MasterDataDao;
import com.bn.device.dto.VersionInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by sbose on 23/4/23.
 */
@Component
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class MasterDataDaoImpl implements MasterDataDao {
    private static final String JDBC_FILE = "/jdbc/masterDataJDBC.properties";
    private final Properties jdbcMasterDataSql = ResourceUtil.loadPropertiesThrowRunnable(JDBC_FILE);
    private static final String UNKNOWN = "AD9999";

    
    @Autowired
    private DataSource dataSource;
    
    @Override
    public ArrayList<DeviceModelInfo> getDeviceModels() throws BNException {
        final ArrayList<DeviceModelInfo> dmList = new ArrayList<DeviceModelInfo>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            final String sql = jdbcMasterDataSql.getProperty("GET_ALL_DEVICE_MODELS");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                final DeviceModelInfo dmi = new DeviceModelInfo();
                dmi.setModelID(rs.getString("id"));
                dmi.setName(rs.getString("name"));
                dmi.setDescription(rs.getString("description"));
                dmi.setBnModel(rs.getInt("bn_model"));
                dmi.setShortDescription(rs.getString("short_description"));
                dmi.setFamilyid(rs.getInt("family_id"));
                dmi.setSourceID(rs.getString("sourceid"));
                dmi.setEncoding(rs.getInt("encoding"));
                dmi.setDigest(rs.getString("digest"));
                dmi.setIteration(rs.getInt("iteration"));
                dmi.setMaxAllowable(rs.getInt("max_allowable"));
                dmi.setTokenLimit(rs.getLong("token_limit"));
                dmi.setVideo(rs.getInt("video"));
                dmi.setProfile(rs.getInt("profile"));
                dmi.setEnabled(rs.getInt("enabled"));
                dmi.setEan(rs.getString("ean"));
                dmList.add(dmi);
            }
        }
        catch(Exception ex) {
            log.error("getDeviceModels:", ex);
            throw BNException.getInstance(UNKNOWN, ex);
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }
        return dmList;
    }

    @Override
    public ArrayList<DeviceFamilyInfo> getDeviceFamily() throws BNException {
        final ArrayList<DeviceFamilyInfo> dfList = new ArrayList<DeviceFamilyInfo>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            final String sql = jdbcMasterDataSql.getProperty("GET_ALL_DEVICE_FAMILY");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                final DeviceFamilyInfo dfi = new DeviceFamilyInfo();
                dfi.setId(rs.getString("id"));
                dfi.setName(rs.getString("name"));
                dfList.add(dfi);
            }
        }
        catch(Exception ex) {
            log.error("getDeviceFamily:", ex);
            throw BNException.getInstance(UNKNOWN, ex);
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }
        return dfList;
    }
    
    public ArrayList<VersionInfo> loadDBVersionInfo() throws BNException {
        ArrayList<VersionInfo> cloudDBVersionList = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cloudDBVersionList = new ArrayList<VersionInfo>();
            conn = dataSource.getConnection();
            final String sql = jdbcMasterDataSql.getProperty("GET_VERSIONMAP_MODELIDCVERSION");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                final String clientModel = rs.getString("MODELID");
                final String clientDescription = rs.getString("SHORT_DESCRIPTION");
                final float clientVersion = rs.getFloat("PRODUCT_VERSION");
                final float deviceVersion = rs.getFloat("CLIENT_VERSION");
                final float serverVersion = rs.getFloat("SERVER_VERSION");
                final String licenseType = rs.getString("LICENSE_TYPE");
                final boolean isCategoryCachingEnabled = "Y".equalsIgnoreCase(rs.getString("ENABLE_CATEGORY_CACHING"));
                final boolean purchaseBlockStatus = "Y".equalsIgnoreCase(rs.getString("PURCHASE_BLOCK_STATUS"));
                final String purchaseAllowType = rs.getString("PURCHASE_ALLOW_TYPE");
                final VersionInfo vInfo = new VersionInfo(clientModel, Float.toString(clientVersion)
                        , Float.toString(deviceVersion), serverVersion, licenseType, purchaseBlockStatus, purchaseAllowType);
                vInfo.setClientDescription(clientDescription);
                vInfo.setCategoryCachingEnabled(isCategoryCachingEnabled);
                cloudDBVersionList.add(vInfo);
            }
        }
        catch(SQLException e) {
            log.error("VERSION MAP ERROR: loadDBVersionInfo=", e);
            throw BNException.getInstance("AD9120", e);
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }
        return cloudDBVersionList;
    }

    @Override
    public ArrayList<TokenStateInfo> getTokenStates() throws BNException {
        final ArrayList<TokenStateInfo> tsList = new ArrayList<TokenStateInfo>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            final String sql = jdbcMasterDataSql.getProperty("GET_ALL_TOKEN_STATES");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                final TokenStateInfo tsi = new TokenStateInfo();
                tsi.setId(rs.getInt("id"));
                tsi.setDescription(rs.getString("description"));
                tsList.add(tsi);
            }
        }
        catch(Exception ex) {
            log.error("getTokenStates:", ex);
            throw BNException.getInstance(UNKNOWN, ex);
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }
        return tsList;
    }

    @Override
    public ArrayList<TokenTypeInfo> getTokenTypes() throws BNException {
        final ArrayList<TokenTypeInfo> ttList = new ArrayList<TokenTypeInfo>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            final String sql = jdbcMasterDataSql.getProperty("GET_ALL_TOKEN_TYPES");
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                final TokenTypeInfo tti = new TokenTypeInfo();
                tti.setId(rs.getString("id"));
                tti.setName(rs.getString("name"));
                tti.setDuration(rs.getLong("duration"));
                tti.setDescription(rs.getString("description"));
                ttList.add(tti);
            }
        }
        catch(Exception ex) {
            log.error("getTokenTypes:", ex);
            throw BNException.getInstance(UNKNOWN, ex);
        }
        finally {
            DBUtil.releaseResources(rs, ps, conn);
        }
        return ttList;
    }
}

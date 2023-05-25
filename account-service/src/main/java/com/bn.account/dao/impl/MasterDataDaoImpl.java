package com.bn.account.dao.impl;

import com.bn.account.dao.MasterDataDao;
import com.bn.common.dto.common.TokenStateInfo;
import com.bn.common.dto.common.TokenTypeInfo;
import com.bn.common.dto.util.DBUtil;
import com.bn.common.dto.util.ResourceUtil;
import com.bn.common.exception.BNException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

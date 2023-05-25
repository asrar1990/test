package com.bn.common.dao.impl;

import com.bn.common.dao.CommonDao;
import com.bn.common.dto.common.ConfigInfo;
import com.bn.common.dto.util.DBUtil;
import com.bn.common.dto.util.ResourceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@RequiredArgsConstructor 
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class CommonDaoImpl implements CommonDao {

    private static final String JDBC_FILE = "/jdbc/commonJDBC.properties";
    private final Properties jdbcConfigSql = ResourceUtil.loadPropertiesThrowRunnable(JDBC_FILE);
    private static final String JDBC_SELECT_CONFIG = "SELECT_CONFIG";

    @Autowired
    private DataSource dataSource;
    
    @Override
    public List<ConfigInfo> getAllConfig() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ConfigInfo> cList = new ArrayList<ConfigInfo>();
        try {
            String sql = jdbcConfigSql.getProperty(JDBC_SELECT_CONFIG);
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ConfigInfo ci = new ConfigInfo(rs.getString("service")
                        , rs.getString("key")
                        , rs.getString("value")
                        , rs.getString("site"));
                cList.add(ci);
            }

        } catch (SQLException e) {
            log.error("getAllConfig:"+e.getMessage());

        } catch(Exception ex) {
            log.error("getAllConfig:"+ex.getMessage());

        } finally{
            DBUtil.releaseResources(rs,ps,conn);
        }
        return cList;
    }
}

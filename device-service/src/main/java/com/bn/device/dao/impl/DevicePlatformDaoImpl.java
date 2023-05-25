package com.bn.device.dao.impl;

import com.bn.common.dto.util.DBUtil;
import com.bn.common.dto.util.ResourceUtil;
import com.bn.device.dao.DevicePlatformDao;
import com.bn.device.dto.DevicePlatform;
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
@Slf4j
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class DevicePlatformDaoImpl implements DevicePlatformDao {
    private static final String JDBC_FILE = "/jdbc/devicePlatformJDBC.properties";
    private final Properties jdbcConfigSql = ResourceUtil.loadPropertiesThrowRunnable(JDBC_FILE);

    private static final String JDBC_SELECT_DEVICE_PLATFORM = "SELECT_DEVICE_PLATFORM";

    @Autowired
    private DataSource dataSource;

    @Override
    public List<DevicePlatform> getAllDevicePlatform() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DevicePlatform> cList = new ArrayList<>();
        try {
            String sql = jdbcConfigSql.getProperty(JDBC_SELECT_DEVICE_PLATFORM);
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                DevicePlatform dp = new DevicePlatform();
                dp.setId(rs.getInt("ID"));
                dp.setModel(rs.getString("NAME"));
                dp.setVersion(rs.getString("VERSION"));
                dp.setName(rs.getString("DESCRIPTION"));
                cList.add(dp);
            }

        } catch (SQLException e) {
            log.error("getAllDevicePlatform:"+e.getMessage());

        } catch(Exception ex) {
            log.error("getAllDevicePlatform:"+ex.getMessage());

        } finally{
            DBUtil.releaseResources(rs,ps,conn);
        }
        return cList;
    }
}

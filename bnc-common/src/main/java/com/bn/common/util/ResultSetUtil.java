package com.bn.common.util;

import com.bn.common.dto.util.DBUtil;

import java.sql.*;
import java.util.Date;

public final class ResultSetUtil {
    
    public static Timestamp getTime(Date date) {
        return new Timestamp(date.getTime());
    }

    public static void setIdOrNull(PreparedStatement ps, int pos, long id) throws SQLException {
        if(ps != null) {
            if(id == 0) {
                ps.setNull(pos, Types.NUMERIC);
            }
            else {
                ps.setLong(pos, id);
            }
        }
    }

     public static long getId(Statement ps) throws SQLException {
        final ResultSet rs = ps.getGeneratedKeys();
        try {
            long id = 0;
            if(rs.next()) {
                id = rs.getLong(1);
            }
            return id;
        }
        finally {
            DBUtil.closeResultSet(rs);
        }
    }
}

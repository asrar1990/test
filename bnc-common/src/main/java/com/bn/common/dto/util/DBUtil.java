package com.bn.common.dto.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
public class DBUtil {
	public static void closeConnection(Connection con) {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.error("closeConnection ", e);
			}
		}
	}
	
	public static void closeStatement(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error("closeStatement ", e);
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("closeResultSet ", e);
			}
		}
	}
	
	public static void commit(Connection con) {
		if(con != null) {
			try {
				if (!con.isClosed() && !con.getAutoCommit())
					con.commit();
			} catch (SQLException e) {
				log.error("commit ", e);
			}
		}
	}
	
	public static void rollback(Connection con) {
		if(con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				log.error("rollback ", e);
			}
		}
	}
	
	public static void releaseResources(Statement st, Connection conn) {
		closeStatement(st);
		closeConnection(conn);		
	}

	public static void releaseResources(ResultSet rs, Statement st, Connection conn) {
		closeResultSet(rs);
		closeStatement(st);
		closeConnection(conn);
	}
	
	public static void commitAndClose(ResultSet rs, Statement st, Connection conn) {
        commit(conn);
		closeResultSet(rs);
		closeStatement(st);
		closeConnection(conn);
	}

	public static long getNextSquence(Connection con, String seq) throws SQLException {
		long nextSeq = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			final String sql = "SELECT "+seq+".nextval FROM dual";
			ps = con.prepareStatement(sql);	
			rs = ps.executeQuery();
			if ( rs.next() )
				nextSeq = rs.getLong(1);
		} finally {
			DBUtil.releaseResources(rs, ps, null);
		}

		log.debug("getNextSquence for sequence="+seq+" value="+nextSeq);
        return nextSeq;
	}
}


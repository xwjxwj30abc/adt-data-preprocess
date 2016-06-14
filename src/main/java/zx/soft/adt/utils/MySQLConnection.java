package zx.soft.adt.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import zx.soft.utils.config.ConfigUtil;

public class MySQLConnection {

	private DataSource dataSource;

	public MySQLConnection() {
		initDataSource();
	}

	public Connection getConnection() {
		Connection con = null;
		if (dataSource == null) {
			initDataSource();
		}
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	private void initDataSource() {
		Properties props = ConfigUtil.getProps("db.properties");
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl(props.getProperty("mysql.connection.url"));
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername(props.getProperty("user.db.username"));
		ds.setPassword(props.getProperty("user.db.password"));
		ds.setMaxActive(15);
		ds.setMinIdle(10);
		ds.setMaxIdle(20);
		ds.setMaxWait(50000);
		ds.setMinEvictableIdleTimeMillis(60000);
		ds.setRemoveAbandoned(true);
		ds.setRemoveAbandonedTimeout(2000);
		dataSource = ds;
	}

	//	public static void main(String[] args) throws SQLException, UnsupportedEncodingException {
	//		String sqlStatement = "describe vpn.plcnetinfo";
	//		try (Connection conn = MySQLConnection.getConnection(); Statement statement = conn.createStatement();) {
	//			ResultSet resultSet = statement.executeQuery(sqlStatement);
	//			if (resultSet != null) {
	//				while (resultSet.next()) {
	//					System.out.println(new String(resultSet.getBytes(1)));
	//				}
	//				resultSet.close();
	//			}
	//		}
}

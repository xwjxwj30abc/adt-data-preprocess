package zx.soft.adt.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.dao.DataMapper;
import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.AlertList;
import zx.soft.adt.domain.GEO;
import zx.soft.adt.domain.HotPlugLog;
import zx.soft.adt.domain.PlcNetInfo;
import zx.soft.adt.domain.VPNTraffic;
import zx.soft.adt.domain.WanIpv4;

/**
 * 数据库操作类,实现DataMapper接口
 *
 * @author fgq
 *
 */
public class SQLOperation implements DataMapper {

	private static Logger logger = LoggerFactory.getLogger(SQLOperation.class);
	private SqlSessionFactory sqlSessionFactory_development;
	private SqlSessionFactory sqlSessionFactory_adt;
	private MySQLConnection mysqlConnection;

	public SQLOperation() {
		MybatisConfig mybatisConfig = new MybatisConfig();
		sqlSessionFactory_adt = mybatisConfig.getAdtSqlSessionFactory();
		sqlSessionFactory_development = mybatisConfig.getDevelopmentSqlSessionFactory();
		mysqlConnection = new MySQLConnection();
	}

	@Override
	public List<AlertList> getAlertListData(String tablename, int from) {

		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<AlertList> alertlists = dataMapper.getAlertListData(tablename, from);
			logger.info("get id from " + from + " to " + (from + alertlists.size()));
			return alertlists;
		}
	}

	@Override
	public List<AccessList> getAccessListData(String tablename, int from) {

		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<AccessList> accesslists = dataMapper.getAccessListData(tablename, from);
			logger.info("get id from " + from + " to " + (from + accesslists.size()));
			return accesslists;
		}
	}

	@Override
	public int getMaxId(String tablename) {
		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			int count = dataMapper.getMaxId(tablename);
			logger.info("table\'s max id: " + count);
			return count;
		} catch (BindingException e) {
			logger.warn("getMaxId attempted to return null");
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int getCount(String tablename) {
		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			int count = dataMapper.getCount(tablename);
			logger.info("table\'s size: " + count);
			return count;
		} catch (BindingException e) {
			logger.warn("getMaxId attempted to return null");
			return 0;
		}
	}

	@Override
	public GEO getGEO(String tablename, String country) {
		try (SqlSession sqlSession = sqlSessionFactory_adt.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			GEO geo = dataMapper.getGEO(tablename, country);
			return geo;
		}
	}

	@Override
	public List<GEO> getALLGEO(String tablename) {
		try (SqlSession sqlSession = sqlSessionFactory_adt.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<GEO> geos = new ArrayList<>();
			geos = dataMapper.getALLGEO(tablename);
			return geos;
		}
	}

	@Override
	public List<String> getAllTableNames(String db) {
		List<String> tableNames = new ArrayList<>();
		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			tableNames = dataMapper.getAllTableNames(db);
			return tableNames;
		}
	}

	/**
	 * 获取部分plcNetInfo数据
	 */
	@Override
	public List<PlcNetInfo> getPlcNetInfoData(String tablename, int from) {
		String sqlStatement = "SELECT Rule_id,Rule_name,Matching_level,Rule_action,Service_type,Keyword1,Keyword2,"
				+ "Keyword3,Matching_word,Set_time,Validation_time,Manual_pause_time,Filter_method,Filter_argument FROM "
				+ tablename + " WHERE id BETWEEN " + from + " AND " + (from + Constant.PAGE_SIZE);
		return this.getPlcNetInfo(sqlStatement);
	}

	/**
	 * 获取所有plcNetInfo数据
	 */
	@Override
	public List<PlcNetInfo> getAllPlcNetInfo(String tablename) {
		String sqlStatement = "select Rule_id,Rule_name,Matching_level,Rule_action,Service_type,Keyword1,Keyword2,"
				+ "Keyword3,Matching_word,Set_time,Validation_time,Manual_pause_time,Filter_method,Filter_argument from "
				+ tablename;
		return this.getPlcNetInfo(sqlStatement);
	}

	/**
	 * 根据查询条件获取plcNetInfo信息
	 * @param sqlStatement
	 * @return
	 */
	private List<PlcNetInfo> getPlcNetInfo(String sqlStatement) {
		List<PlcNetInfo> plcNetInfos = new ArrayList<>();
		try (Connection conn = mysqlConnection.getConnection();
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlStatement);) {
			if (resultSet != null) {
				while (resultSet.next()) {
					PlcNetInfo plcNetInfo = new PlcNetInfo();
					plcNetInfo.setRule_id(resultSet.getBytes(1) == null ? "" : new String(resultSet.getBytes(1)));
					plcNetInfo.setRule_name(resultSet.getBytes(2) == null ? "" : new String(resultSet.getBytes(2)));
					plcNetInfo.setMatching_level(resultSet.getInt(3));
					plcNetInfo.setRule_action(resultSet.getInt(4));
					plcNetInfo.setService_type(resultSet.getInt(5));
					plcNetInfo.setKeyword1(resultSet.getBytes(6) == null ? "" : new String(resultSet.getBytes(6)));
					plcNetInfo.setKeyword2(resultSet.getBytes(7) == null ? "" : new String(resultSet.getBytes(7)));
					plcNetInfo.setKeyword3(resultSet.getBytes(8) == null ? "" : new String(resultSet.getBytes(8)));
					plcNetInfo.setMatching_word(resultSet.getInt(9));
					plcNetInfo.setSet_time(resultSet.getLong(10));
					plcNetInfo.setValidation_time(resultSet.getLong(11));
					plcNetInfo.setManual_pause_time(resultSet.getLong(12));
					plcNetInfo.setFilter_method(resultSet.getInt(13));
					plcNetInfo.setFilter_argument(resultSet.getBytes(14) == null ? "" : new String(resultSet
							.getBytes(14)));
					plcNetInfos.add(plcNetInfo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return plcNetInfos;
	}

	@Override
	public List<VPNTraffic> getVPNTrafficData(String tablename, int from) {
		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<VPNTraffic> vPNTraffic = dataMapper.getVPNTrafficData(tablename, from);
			logger.info("get id from " + from + " to " + (from + vPNTraffic.size()));
			return vPNTraffic;
		}
	}

	@Override
	public long getServiceCode(String tablename) {
		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			long service_code = dataMapper.getServiceCode(tablename);
			logger.info("get service_code=" + service_code);
			return service_code;
		}
	}

	@Override
	public List<WanIpv4> getWanIpv4Data(String tablename, int from) {
		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<WanIpv4> wanIpv4s = dataMapper.getWanIpv4Data(tablename, from);
			logger.info("get id from " + from + " to " + (from + wanIpv4s.size()));
			return wanIpv4s;
		}
	}

	@Override
	public int existsServiceCode(String tablename, long Service_code) {
		try (SqlSession sqlSession = sqlSessionFactory_adt.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			int exist = dataMapper.existsServiceCode(tablename, Service_code);
			if (exist == 0) {
				logger.info("do not exist the service_code");
			} else {
				logger.info(" exist the service_code");
			}
			return exist;
		}
	}

	@Override
	public void insertServiceCode(String tablename, long Service_code, String Service_name) {
		try (SqlSession sqlSession = sqlSessionFactory_adt.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			dataMapper.insertServiceCode(tablename, Service_code, Service_name);
			logger.info("insert a new service_code to plcClient");
		}
	}

	@Override
	public List<HotPlugLog> getHotPlugLog(String tablename, int from) {
		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<HotPlugLog> hotPlugLogs = dataMapper.getHotPlugLog(tablename, from);
			logger.info("get id from " + from + " to " + (from + hotPlugLogs.size()));
			return hotPlugLogs;
		}
	}

	public static void main(String[] args) throws IOException {
		SQLOperation o = new SQLOperation();
		List<PlcNetInfo> plcNetInfos = o.getPlcNetInfoData("plcnetinfo", 0);
		System.out.println(plcNetInfos);
		//		List<PlcNetInfo> plcNetInfos = o.getAllPlcNetInfo("plcnetinfo");
		//		System.out.println(plcNetInfos);
		//		if (o.existsServiceCode(Constant.adt_plcclient_table_name, service_code) == 0) {
		//			o.insertServiceCode(Constant.adt_plcclient_table_name, service_code,
		//					String.valueOf(System.currentTimeMillis()));
		//		}

	}
}

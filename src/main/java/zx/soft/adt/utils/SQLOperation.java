package zx.soft.adt.utils;

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
import zx.soft.adt.domain.HotPlugLog;
import zx.soft.adt.domain.IP2GEO;
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

	public SQLOperation() {
		sqlSessionFactory_adt = MybatisConfig.getAdtSqlSessionFactory();
		sqlSessionFactory_development = new MybatisConfig().getDevelopmentSqlSessionFactory();
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

	//	@Override
	//	public PlcClient getPlcClientData(String tablename, long Service_code) {
	//
	//		try (SqlSession sqlSession = sqlSessionFactory_adt.openSession();) {
	//			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
	//			PlcClient plcclient = dataMapper.getPlcClientData(tablename, Service_code);
	//			return plcclient;
	//		}
	//}

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
	public IP2GEO getGEO(String tablename, String country) {
		try (SqlSession sqlSession = sqlSessionFactory_adt.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			IP2GEO geo = dataMapper.getGEO(tablename, country);
			return geo;
		}
	}

	@Override
	public List<IP2GEO> getALLGEO(String tablename) {
		try (SqlSession sqlSession = sqlSessionFactory_adt.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<IP2GEO> geos = new ArrayList<>();
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

	@Override
	public List<PlcNetInfo> getPlcNetInfoData(String tablename, int from) {

		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<PlcNetInfo> plcnetinfos = dataMapper.getPlcNetInfoData(tablename, from);
			logger.info("get id from " + from + " to " + (from + plcnetinfos.size()));
			return plcnetinfos;
		}
	}

	@Override
	public List<PlcNetInfo> getAllPlcNetInfo(String tablename) {
		try (SqlSession sqlSession = sqlSessionFactory_development.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			List<PlcNetInfo> plcnetinfos = dataMapper.getAllPlcNetInfo(tablename);
			logger.info("plcnetinfo size = " + plcnetinfos.size());
			return plcnetinfos;
		}
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
				logger.info("exist the service_code");
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

	public static void main(String[] args) {
		SQLOperation o = new SQLOperation();
		List<PlcNetInfo> lists = o.getAllPlcNetInfo("plcNetInfo");
		System.out.println(lists);
		//		long service_code = o.getServiceCode("plcClient");
		//		System.out.println(service_code);
		//		if (o.existsServiceCode(Constant.adt_plcclient_table_name, service_code) == 0) {
		//			o.insertServiceCode(Constant.adt_plcclient_table_name, service_code,
		//					String.valueOf(System.currentTimeMillis()));
		//		}

	}

}

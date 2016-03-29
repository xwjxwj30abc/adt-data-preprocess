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
import zx.soft.adt.domain.IP2GEO;
import zx.soft.adt.domain.PlcClient;
import zx.soft.adt.domain.PlcNetInfo;

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

	@Override
	public PlcClient getPlcClientData(String tablename, long Service_code) {

		try (SqlSession sqlSession = sqlSessionFactory_adt.openSession();) {
			DataMapper dataMapper = sqlSession.getMapper(DataMapper.class);
			PlcClient plcclient = dataMapper.getPlcClientData(tablename, Service_code);
			return plcclient;
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

	public static void main(String[] args) {
		SQLOperation o = new SQLOperation();
		//		System.out.println(o.getCount(Constant.adt_mysql_countryinfo_name));
		//System.out.println(o.getMaxId(Constant.adt_mysql_countryinfo_name));
		//System.out.println(o.getAccessListData("AccessList", 0));
		//		System.out.println(o.getAlertListData("AlertList", 0));
		//		System.out.println(o.getAllTableNames(Constant.adt_mysql_database_name));
		//System.out.println(o.getPlcClientData(Constant.adt_plcclient_table_name, 903));
		//System.out.println(o.getPlcNetInfoData("plcNetInfo", 0));
		System.out.println(o.getALLGEO(Constant.adt_mysql_countryinfo_name));
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

}

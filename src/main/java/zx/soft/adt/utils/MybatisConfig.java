package zx.soft.adt.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mybatis配置文件读取类
 *
 * @author fgq
 *
 */
public class MybatisConfig {

	private static Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

	private static final String CONFIGURATION_PATH = "mybatis-config.xml";

	//private static SqlSessionFactory adt;

	static enum DataSourceEnvironment {
		development, adt;
	}

	//	public static SqlSessionFactory getAdtSqlSessionFactory() {
	//		if (adt != null) {
	//			return adt;
	//		} else {
	//			try (InputStream inputStream = Resources.getResourceAsStream(CONFIGURATION_PATH);) {
	//				adt = new SqlSessionFactoryBuilder().build(inputStream, DataSourceEnvironment.adt.name());
	//				logger.info("Get {} SqlSessionFactory successfully.", DataSourceEnvironment.adt.name());
	//			} catch (IOException e) {
	//				logger.warn("Get {} SqlSessionFactory error.", DataSourceEnvironment.adt.name());
	//				logger.error(e.getMessage(), e);
	//			}
	//			return adt;
	//		}
	//	}

	public SqlSessionFactory getAdtSqlSessionFactory() {
		SqlSessionFactory sqlSessionFactory = null;
		try (InputStream inputStream = Resources.getResourceAsStream(CONFIGURATION_PATH);) {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, DataSourceEnvironment.adt.name());
			logger.info("update {} SqlSessionFactory successfully.", DataSourceEnvironment.adt.name());
		} catch (IOException e) {
			logger.warn("update {} SqlSessionFactory error.", DataSourceEnvironment.adt.name());
			logger.error(e.getMessage(), e);
		}
		return sqlSessionFactory;
	}

	public SqlSessionFactory getDevelopmentSqlSessionFactory() {
		SqlSessionFactory sqlSessionFactory = null;
		try (InputStream inputStream = Resources.getResourceAsStream(CONFIGURATION_PATH);) {
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,
					DataSourceEnvironment.development.name());
			logger.info("update {} SqlSessionFactory successfully.", DataSourceEnvironment.development.name());
		} catch (IOException e) {
			logger.warn("update {} SqlSessionFactory error.", DataSourceEnvironment.development.name());
			logger.error(e.getMessage(), e);
		}
		return sqlSessionFactory;
	}
}

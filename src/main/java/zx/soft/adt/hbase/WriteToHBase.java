package zx.soft.adt.hbase;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HConnection;

import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.ExcuteShellThread;
import zx.soft.adt.utils.IP;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseClient;
import zx.soft.hbase.api.core.HConn;
import zx.soft.utils.config.ConfigUtil;

import com.google.protobuf.ServiceException;

/**
 * 同时处理过滤结果表和上网日志表
 *
 * @author fgq
 *
 */
public class WriteToHBase {

	static {
		//IP.load("src/main/resources/17monipdb.dat");
		Properties p = ConfigUtil.getProps("ipdb.properties");
		String ipDatabase = p.getProperty("dir");
		IP.load(ipDatabase);
	}

	public void write() throws MasterNotRunningException, ZooKeeperConnectionException,
			IOException, ServiceException {
		HBaseClient client = new HBaseClient();
		if (client.isTableExists(Constant.adt_alertList_table_name)) {
			client.deleteTable(Constant.adt_alertList_table_name);
			client.createTable(Constant.adt_alertList_table_name, Constant.adt_cf);
		} else {
			client.createTable(Constant.adt_alertList_table_name, Constant.adt_cf);
		}
		if (client.isTableExists(Constant.adt_accesslist_table_name)) {
			client.deleteTable(Constant.adt_accesslist_table_name);
			client.createTable(Constant.adt_accesslist_table_name, Constant.adt_cf);
		} else {
			client.createTable(Constant.adt_accesslist_table_name, Constant.adt_cf);
		}
		if (!client.isTableExists(Constant.adt_plcnetinfo_table_name)) {
			client.createTable(Constant.adt_plcnetinfo_table_name, Constant.adt_cf);
		}
		client.close();

		HConnection conn = HConn.getHConnection();
		SQLOperation sqlOperation = new SQLOperation();
		ThreadCore threadCore = new ThreadCore();
		List<String> access_tablenames = sqlOperation
				.getAllTableNames(Constant.adt_mysql_database_name);
		//统计所有待导入的数据总量
		int sum = 0;
		for (String tablename : access_tablenames) {
			if (tablename.toLowerCase().matches(Constant.adt_plcnetinfo_table_name + ".*")) {
				int count = sqlOperation.getCount(tablename);
				sum = sum + count;

			} else if (tablename.toLowerCase().matches(Constant.adt_alertList_table_name + ".*")) {
				int count = sqlOperation.getCount(tablename);
				sum = sum + count;

			} else if (tablename.toLowerCase().matches(Constant.adt_accesslist_table_name + ".*")) {
				int count = sqlOperation.getCount(tablename);
				sum = sum + count;
			}
		}
		Constant.SUM_OF_DATA = sum;

		//多线程导入数据
		for (String tablename : access_tablenames) {
			if (tablename.toLowerCase().matches(Constant.adt_plcnetinfo_table_name + ".*")) {
				int count = sqlOperation.getMaxId(tablename);
				int pageCount = count / 1000;
				for (int i = 0; i < pageCount + 1; i++) {
					int from = i * 1000;
					threadCore.getPlcNetInfoData(conn, tablename, from);
				}
			} else if (tablename.toLowerCase().matches(Constant.adt_alertList_table_name + ".*")) {
				int count = sqlOperation.getMaxId(tablename);
				int pageCount = count / 1000;
				for (int i = 0; i < pageCount + 1; i++) {
					int from = i * 1000;
					threadCore.getAlertListData(conn, tablename, from);
				}
			} else if (tablename.toLowerCase().matches(Constant.adt_accesslist_table_name + ".*")) {
				int count = sqlOperation.getMaxId(tablename);
				int pageCount = count / 1000;
				for (int i = 0; i < pageCount + 1; i++) {
					int from = i * 1000;
					threadCore.getAccessListData(conn, tablename, from);
				}
			}
		}
		threadCore.close();
	}

	public static void main(String[] args) throws MasterNotRunningException,
			ZooKeeperConnectionException, IOException, ServiceException {
		WriteToHBase w = new WriteToHBase();
		w.write();
		ExcuteShellThread th = new ExcuteShellThread("src/main/resources/clean.sh");
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("end");
	}

}

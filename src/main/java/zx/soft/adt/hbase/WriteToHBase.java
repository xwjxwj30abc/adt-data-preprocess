package zx.soft.adt.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HConnection;

import zx.soft.adt.domain.PlcNetInfo;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.IP;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseClient;
import zx.soft.hbase.api.core.HConn;
import zx.soft.utils.config.ConfigUtil;

import com.google.protobuf.ServiceException;

/**
 * 写数据到HBase主类
 *
 * @author fgq
 *
 */
public class WriteToHBase {

	static {
		Properties p = ConfigUtil.getProps("ipdb.properties");
		String ipDatabase = p.getProperty("dir");
		IP.load(ipDatabase);
	}
	private SQLOperation sqlOperation;

	public WriteToHBase(SQLOperation sqlOperation) {
		this.sqlOperation = sqlOperation;
	}

	public void write() throws MasterNotRunningException, ZooKeeperConnectionException, IOException, ServiceException {
		//初始化全局变量Service_code
		this.initServiceCode();
		//创建或更新HBase中的表
		this.initHBaseEnv();
		//获取vpn库中所有待处理的表名
		List<String> usefultablenames = this.getUsefulTableNames();
		//初始化规则PlcNetInfo Map
		String plcNetInfoTableName = this.getPlcNetInfoTableName(usefultablenames);
		this.initPlcNetInfoMap(plcNetInfoTableName);
		//获取待发送数据总量
		Constant.SUM_OF_DATA = getSumOfData(usefultablenames);
		//发送所有待处理表中的数据
		this.sendData(usefultablenames);
	}

	//创建或更新HBase中的表
	private void initHBaseEnv() throws MasterNotRunningException, ZooKeeperConnectionException, IOException,
	ServiceException {
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
		if (!client.isTableExists(Constant.adt_traffic_table_name)) {
			client.createTable(Constant.adt_traffic_table_name, Constant.adt_cf);
		}
		if (!client.isTableExists(Constant.adt_wanipv4_table_name)) {
			client.createTable(Constant.adt_wanipv4_table_name, Constant.adt_cf);
		}
		if (!client.isTableExists(Constant.adt_hotpluglog_table_name)) {
			client.createTable(Constant.adt_hotpluglog_table_name, Constant.adt_cf);
		}
		client.close();
	}

	//统计所有待导入的数据总量
	private int getSumOfData(List<String> usefultablenames) {
		int sum = 0;
		for (String tablename : usefultablenames) {
			int count = this.sqlOperation.getCount(tablename);
			sum = sum + count;
		}
		return sum;
	}

	//多线程导入数据
	private void sendData(List<String> usefultablenames) {
		HConnection conn = HConn.getHConnection();
		ThreadPoolExecutorService threadCore = new ThreadPoolExecutorService();
		for (String tablename : usefultablenames) {
			int count = this.sqlOperation.getMaxId(tablename);
			int pageCount = count / Constant.PAGE_SIZE;
			for (int i = 0; i < pageCount + 1; i++) {
				int from = i * Constant.PAGE_SIZE;
				threadCore.addRunnable(conn, this.sqlOperation, tablename, from);
			}
		}
		threadCore.close();
	}

	//初始化全局变量Service_code
	private void initServiceCode() {
		long Service_code_tmp = this.sqlOperation.getServiceCode(Constant.adt_plcclient_table_name);
		int exists = this.sqlOperation.existsServiceCode(Constant.adt_plcclient_table_name, Service_code_tmp);
		//不存在该service_code,执行插入,新增service_code
		if (exists == 0) {
			this.sqlOperation.insertServiceCode(Constant.adt_plcclient_table_name, Service_code_tmp,
					String.valueOf(System.currentTimeMillis()));
		}
		Constant.Service_code = Service_code_tmp;
	}

	//初始化规则plcNetInfo　Map
	private void initPlcNetInfoMap(String plcNetInfoTableName) {
		List<PlcNetInfo> lists = this.sqlOperation.getAllPlcNetInfo(plcNetInfoTableName);
		Map<String, PlcNetInfo> maps = new HashMap<>();
		for (PlcNetInfo plcNetInfo : lists) {
			maps.put(plcNetInfo.getRule_id(), plcNetInfo);
		}
		Constant.plcNetInfoMap = maps;
	}

	//筛选出规则表
	private String getPlcNetInfoTableName(List<String> alltablenames) {
		for (String tablename : alltablenames) {
			if (tablename.toLowerCase().matches(Constant.adt_plcnetinfo_table_name + ".*")) {
				return tablename;
			}
		}
		return "";
	}

	//获取vpn库中所有待处理的表表名
	private List<String> getUsefulTableNames() {
		List<String> alltablenames = this.sqlOperation.getAllTableNames(Constant.adt_mysql_database_name);
		List<String> usefulTableNames = new ArrayList<>();
		for (String tablename : alltablenames) {
			if (tablename.toLowerCase().matches(Constant.adt_plcnetinfo_table_name + ".*")
					| tablename.toLowerCase().matches(Constant.adt_alertList_table_name + ".*")
					| tablename.toLowerCase().matches(Constant.adt_accesslist_table_name + ".*")
					| tablename.toLowerCase().matches(Constant.adt_traffic_table_name + ".*")
					| tablename.toLowerCase().matches(Constant.adt_wanipv4_table_name + ".*")
					| tablename.toLowerCase().matches(Constant.adt_hotpluglog_table_name + ".*")) {
				usefulTableNames.add(tablename);
			}
		}
		return usefulTableNames;
	}

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException,
			ServiceException {
		WriteToHBase w = new WriteToHBase(new SQLOperation());
		w.write();
		//		String tableName = "plcNetInfo";
		//		System.out.println(tableName.toLowerCase().matches(Constant.adt_plcnetinfo_table_name + ".*"));
	}
}

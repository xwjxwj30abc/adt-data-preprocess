package zx.soft.adt.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.AlertList;
import zx.soft.adt.domain.IP2GEO;
import zx.soft.adt.domain.PlcClient;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.IPToGEO;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.utils.checksum.CheckSumUtils;

public class GetAlertListThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(GetAlertListThread.class);

	private int from;
	private String mysqlTablename;
	private HConnection conn;

	public GetAlertListThread(HConnection conn, String mysqlTablename, int from) {
		this.conn = conn;
		this.from = from;
		this.mysqlTablename = mysqlTablename;
	}

	@Override
	public void run() {
		try {
			SQLOperation sqlOperation = new SQLOperation();
			List<AlertList> alertlists = sqlOperation.getAlertListData(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_alertList_table_name);
			IP2GEO geo = null;
			for (AlertList alertlist : alertlists) {
				String keyWord = CheckSumUtils.getMD5(String.valueOf(Constant.Service_code) + mysqlTablename
						+ String.valueOf(alertlist.getId()));
				try {
					if (alertlist.getDestination_ip() != 0) {
						geo = IPToGEO.get(alertlist.getDestination_ip(), sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "con", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd", geo.getWD());
					}
				} catch (NullPointerException ee) {
					logger.warn(alertlist.getDestination_ip() + "");
				}
				hbaseTable.put(keyWord, Constant.adt_cf, "id", alertlist.getId());
				hbaseTable.put(keyWord, Constant.adt_cf, "sec", Constant.Service_code);
				hbaseTable.put(keyWord, Constant.adt_cf, "ru", alertlist.getRule_id());
				hbaseTable.put(keyWord, Constant.adt_cf, "de4", alertlist.getDestination_ip());
				hbaseTable.put(keyWord, Constant.adt_cf, "nep", alertlist.getNet_ending_ip());
				hbaseTable.put(keyWord, Constant.adt_cf, "nem", alertlist.getNet_ending_mac());
				hbaseTable.put(keyWord, Constant.adt_cf, "de6", alertlist.getDestination_ipv6());
				hbaseTable.put(keyWord, Constant.adt_cf, "ne6", alertlist.getNet_ending_ipv6());
				hbaseTable.put(keyWord, Constant.adt_cf, "ma", alertlist.getMatching_time());
				hbaseTable.put(keyWord, Constant.adt_cf, "set", alertlist.getService_type());
				hbaseTable.put(keyWord, Constant.adt_cf, "k1", alertlist.getKeyword1());
				hbaseTable.put(keyWord, Constant.adt_cf, "k2", alertlist.getKeyword2());
				hbaseTable.put(keyWord, Constant.adt_cf, "k3", alertlist.getKeyword3());
				PlcClient plcClient = sqlOperation.getPlcClientData(Constant.adt_plcclient_table_name,
						Constant.Service_code);
				if (plcClient != null) {
					hbaseTable.put(keyWord, Constant.adt_cf, "ys", plcClient.getUser_name());
					hbaseTable.put(keyWord, Constant.adt_cf, "cet", plcClient.getCertificate_type());
					hbaseTable.put(keyWord, Constant.adt_cf, "cec", plcClient.getCertificate_code());
					hbaseTable.put(keyWord, Constant.adt_cf, "or", plcClient.getOrg_name());
					hbaseTable.put(keyWord, Constant.adt_cf, "co", plcClient.getCountry());
				} else {
					hbaseTable.put(keyWord, Constant.adt_cf, "ys", "");
					hbaseTable.put(keyWord, Constant.adt_cf, "cet", "");
					hbaseTable.put(keyWord, Constant.adt_cf, "cec", "");
					hbaseTable.put(keyWord, Constant.adt_cf, "or", "");
					hbaseTable.put(keyWord, Constant.adt_cf, "co", "");
					logger.warn("存在service_code=" + Constant.Service_code + "对应的设备用户信息未提供，请尽快添加");
				}

				hbaseTable.execute();
				hbaseTable.close();
			}
			logger.info("succeed insert" + alertlists.size());
			Constant.CURRENT_NUM.addAndGet(alertlists.size());
			logger.info("插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

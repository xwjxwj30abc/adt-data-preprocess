package zx.soft.adt.hbase;

import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.VPNTraffic;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.IP;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.utils.checksum.CheckSumUtils;

public class GetVPNTrafficThread implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(GetVPNTrafficThread.class);

	private int from;
	private String mysqlTablename;
	private HConnection conn;

	public GetVPNTrafficThread(HConnection conn, String mysqlTablename, int from) {
		this.conn = conn;
		this.from = from;
		this.mysqlTablename = mysqlTablename;
	}

	@Override
	public void run() {
		try {
			SQLOperation sqlOperation = new SQLOperation();
			List<VPNTraffic> vpnTraffics = sqlOperation.getVPNTrafficData(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_traffic_table_name);
			for (VPNTraffic vpnTraffic : vpnTraffics) {
				String keyWord = CheckSumUtils.getMD5(Constant.Service_code
						+ String.valueOf(vpnTraffic.getBegin_time()));
				hbaseTable.put(keyWord, Constant.adt_cf, "id", vpnTraffic.getId());
				hbaseTable.put(keyWord, Constant.adt_cf, "ip", IP.getIP(vpnTraffic.getIpv4()));
				hbaseTable.put(keyWord, Constant.adt_cf, "bt", vpnTraffic.getBegin_time());
				hbaseTable.put(keyWord, Constant.adt_cf, "et", vpnTraffic.getEnd_time());
				hbaseTable.put(keyWord, Constant.adt_cf, "tr", vpnTraffic.getTraffic());
				hbaseTable.put(keyWord, Constant.adt_cf, "se", Constant.Service_code);
			}
			hbaseTable.execute();
			hbaseTable.close();
			Constant.CURRENT_NUM.addAndGet(vpnTraffics.size());
			logger.info("共插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
}

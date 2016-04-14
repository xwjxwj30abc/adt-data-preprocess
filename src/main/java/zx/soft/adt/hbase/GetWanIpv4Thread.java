package zx.soft.adt.hbase;

import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.WanIpv4;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.IP;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.utils.checksum.CheckSumUtils;

public class GetWanIpv4Thread implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(GetWanIpv4Thread.class);

	private int from;
	private String mysqlTablename;
	private HConnection conn;

	public GetWanIpv4Thread(HConnection conn, String mysqlTablename, int from) {
		this.conn = conn;
		this.from = from;
		this.mysqlTablename = mysqlTablename;
	}

	@Override
	public void run() {
		try {
			SQLOperation sqlOperation = new SQLOperation();
			List<WanIpv4> wanIpv4s = sqlOperation.getWanIpv4Data(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_wanipv4_table_name);
			for (WanIpv4 wanIpv4 : wanIpv4s) {
				String keyWord = CheckSumUtils.getMD5(String.valueOf(System.currentTimeMillis()) + mysqlTablename
						+ String.valueOf(wanIpv4.getId()));
				hbaseTable.put(keyWord, Constant.adt_cf, "id", wanIpv4.getId());
				hbaseTable.put(keyWord, Constant.adt_cf, "ip", IP.getIP(wanIpv4.getIpv4()));
				hbaseTable.put(keyWord, Constant.adt_cf, "at", wanIpv4.getAdd_time());
				hbaseTable.put(keyWord, Constant.adt_cf, "se", Constant.Service_code);
			}
			hbaseTable.execute();
			hbaseTable.close();
			Constant.CURRENT_NUM.addAndGet(wanIpv4s.size());
			logger.info("共插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
}

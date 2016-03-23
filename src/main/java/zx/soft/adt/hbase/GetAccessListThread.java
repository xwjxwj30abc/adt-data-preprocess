package zx.soft.adt.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.IP2GEO;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.IPToGEO;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.utils.checksum.CheckSumUtils;

public class GetAccessListThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(GetAccessListThread.class);

	private int from;
	private String mysqlTablename;
	private HConnection conn;

	public GetAccessListThread(HConnection conn, String mysqlTablename, int from) {
		this.conn = conn;
		this.from = from;
		this.mysqlTablename = mysqlTablename;
	}

	@Override
	public void run() {
		try {
			SQLOperation sqlOperation = new SQLOperation();
			List<AccessList> accesslists = sqlOperation.getAccessListData(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_accesslist_table_name);
			IP2GEO geo = null;
			for (AccessList accesslist : accesslists) {
				String keyWord = CheckSumUtils.getMD5(String.valueOf(System.currentTimeMillis())
						+ mysqlTablename + String.valueOf(accesslist.getId()));
				try {
					if (accesslist.getDestination_ip() != 0) {
						geo = IPToGEO.get(accesslist.getDestination_ip(), sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "cn", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd", geo.getWD());
					}
					if (accesslist.getVpn1_ip() != 0) {
						geo = IPToGEO.get(accesslist.getVpn1_ip(), sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "cn1", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd1", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd1", geo.getWD());
					}
					if (accesslist.getVpn2_ip() != 0) {
						geo = IPToGEO.get(accesslist.getVpn2_ip(), sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "cn2", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd2", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd2", geo.getWD());
					}
					if (accesslist.getVpn3_ip() != 0) {
						geo = IPToGEO.get(accesslist.getVpn3_ip(), sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "cn3", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd3", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd3", geo.getWD());
					}
				} catch (NullPointerException ee) {
					logger.warn(accesslist.getDestination_ip() + "");
					logger.warn(accesslist.getVpn1_ip() + "");
					logger.warn(accesslist.getVpn2_ip() + "");
					logger.warn(accesslist.getVpn3_ip() + "");
				}
				hbaseTable.put(keyWord, Constant.adt_cf, "id", accesslist.getId());
				hbaseTable.put(keyWord, Constant.adt_cf, "sec", accesslist.getService_code());
				hbaseTable.put(keyWord, Constant.adt_cf, "nei", accesslist.getNet_ending_ip());
				hbaseTable.put(keyWord, Constant.adt_cf, "nen", accesslist.getNet_ending_name());
				hbaseTable.put(keyWord, Constant.adt_cf, "ti", accesslist.getTime());
				hbaseTable.put(keyWord, Constant.adt_cf, "nem", accesslist.getNet_ending_mac());
				hbaseTable.put(keyWord, Constant.adt_cf, "de", accesslist.getDestination_ip());
				hbaseTable.put(keyWord, Constant.adt_cf, "po", accesslist.getPort());
				hbaseTable.put(keyWord, Constant.adt_cf, "set", accesslist.getService_type());
				hbaseTable.put(keyWord, Constant.adt_cf, "k1", accesslist.getKeyword1());
				hbaseTable.put(keyWord, Constant.adt_cf, "k2", accesslist.getKeyword2());
				hbaseTable.put(keyWord, Constant.adt_cf, "k3", accesslist.getKeyword3());
				hbaseTable.put(keyWord, Constant.adt_cf, "ma", accesslist.getMac());
				hbaseTable.put(keyWord, Constant.adt_cf, "so", accesslist.getSource_port());
				hbaseTable.put(keyWord, Constant.adt_cf, "ne6", accesslist.getNet_ending_ipv6());
				hbaseTable.put(keyWord, Constant.adt_cf, "de6", accesslist.getDestination_ipv6());
				hbaseTable.put(keyWord, Constant.adt_cf, "k11", accesslist.getKeyword11());
				hbaseTable.put(keyWord, Constant.adt_cf, "k12", accesslist.getKeyword12());
				hbaseTable.put(keyWord, Constant.adt_cf, "k13", accesslist.getKeyword13());
				hbaseTable.put(keyWord, Constant.adt_cf, "k14", accesslist.getKeyword14());
				hbaseTable.put(keyWord, Constant.adt_cf, "k15", accesslist.getKeyword15());
				hbaseTable.put(keyWord, Constant.adt_cf, "k21", accesslist.getKeyword21());
				hbaseTable.put(keyWord, Constant.adt_cf, "k22", accesslist.getKeyword22());
				hbaseTable.put(keyWord, Constant.adt_cf, "k23", accesslist.getKeyword23());
				hbaseTable.put(keyWord, Constant.adt_cf, "k24", accesslist.getKeyword24());
				hbaseTable.put(keyWord, Constant.adt_cf, "k25", accesslist.getKeyword25());
				hbaseTable.put(keyWord, Constant.adt_cf, "cj", accesslist.getTime());
				hbaseTable.put(keyWord, Constant.adt_cf, "vp1", accesslist.getVpn1_ip());
				hbaseTable.put(keyWord, Constant.adt_cf, "vp2", accesslist.getVpn2_ip());
				hbaseTable.put(keyWord, Constant.adt_cf, "vp3", accesslist.getVpn3_ip());
			}
			hbaseTable.execute();
			hbaseTable.close();
			Constant.CURRENT_NUM.addAndGet(accesslists.size());
			logger.info("当前插入数据量" + accesslists.size() + ";共插入数据量：" + Constant.CURRENT_NUM
					+ ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

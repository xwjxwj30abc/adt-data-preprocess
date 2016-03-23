package zx.soft.adt.data;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;

import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.IP2GEO;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.IP;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.hbase.api.core.HConn;
import zx.soft.utils.checksum.CheckSumUtils;

public class Creater {
	static {
		IP.load("src/main/resources/17monipdb.dat");
	}

	public static void main(String[] args) throws IOException {
		HConnection conn = HConn.getHConnection();
		SQLOperation sqlOperation = new SQLOperation();
		HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_accesslist_table_name);
		IP2GEO geo = null;
		IP2GEO geov1 = null;
		IP2GEO geov2 = null;
		IP2GEO geov3 = null;

		for (int i = 0; i < 100000; i++) {
			AccessList accesslist = ProduceAccessList.create();
			String keyWord = CheckSumUtils.getMD5(String.valueOf(System.currentTimeMillis())
					+ String.valueOf(accesslist.getId()));
			try {
				//				if (accesslist.getVpn1_ip() != 0) {
				//					geov1 = getGEO(accesslist.getVpn1_ip(), sqlOperation);
				//
				//				}
				//				if (accesslist.getVpn2_ip() != 0) {
				//					geov2 = getGEO(accesslist.getVpn2_ip(), sqlOperation);
				//
				//				}
				//				if (accesslist.getVpn3_ip() != 0) {
				//					geov3 = getGEO(accesslist.getVpn3_ip(), sqlOperation);
				//
				//				}

				List<IP2GEO> geos = ProduceAccessList.create3RandomInternalCountry();
				geov1 = geos.get(0);
				geov2 = geos.get(1);
				geov3 = geos.get(2);
				if ((!accesslist.getCountry_name().equals(geov1.getCOUNTRY()))
						&& (!accesslist.getCountry_name().equals(geov2.getCOUNTRY()))
						&& (!accesslist.getCountry_name().equals(geov3.getCOUNTRY()))
						&& (!geov1.getCOUNTRY().equals(geov2.getCOUNTRY()))
						&& (!geov1.getCOUNTRY().equals(geov3.getCOUNTRY()))
						&& (!geov2.getCOUNTRY().equals(geov3.getCOUNTRY()))) {
					if ((!(accesslist.getCountry_name().equals("未知") | accesslist.getCountry_name().equals("局域网")))
							&& (!(geov1.getCOUNTRY().equals("未知") | geov1.getCOUNTRY().equals("局域网")))
							&& (!(geov2.getCOUNTRY().equals("未知") | geov2.getCOUNTRY().equals("局域网")))
							&& (!(geov3.getCOUNTRY().equals("未知") | geov3.getCOUNTRY().equals("局域网")))) {
						//System.err.println("成功创造数据");
						hbaseTable.put(keyWord, Constant.adt_cf, "cn", accesslist.getCountry_name());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd", accesslist.getJd());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd", accesslist.getWd());

						hbaseTable.put(keyWord, Constant.adt_cf, "cn1", geov1.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd1", geov1.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd1", geov1.getWD());

						hbaseTable.put(keyWord, Constant.adt_cf, "cn2", geov2.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd2", geov2.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd2", geov2.getWD());

						hbaseTable.put(keyWord, Constant.adt_cf, "cn3", geov3.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd3", geov3.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd3", geov3.getWD());

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
						hbaseTable.execute();
					}

				}
			} catch (Exception ee) {
				ee.printStackTrace();
			}
		}

	}

	public static IP2GEO getGEO(long ipaddr, SQLOperation sqlOperation) {
		String ip = IP.getIP(ipaddr);
		String country = null;
		String country_city = null;
		IP2GEO geo = null;
		String[] adds = new String[4];
		try {
			adds = IP.find(ip);
			try {
				if (adds[0].equals("局域网")) {
					//局域网IP，设置为("局域网", 0.0, 0.0)
					geo = new IP2GEO("局域网", 0.0, 0.0);
				} else if (adds[0].equals("中国")) {
					//国内IP,设置为"中国＋省份"
					geo = sqlOperation.getGEO(Constant.adt_mysql_countryinfo_name, adds[1]);
					if (geo.getCOUNTRY().equals("中国")) {
						country_city = adds[0];
					} else {
						country_city = adds[0] + adds[1];
					}
					geo.setCOUNTRY(country_city);
				} else {
					//国外IP
					geo = sqlOperation.getGEO(Constant.adt_mysql_countryinfo_name, adds[0]);
					country = adds[0];
				}
			} catch (NullPointerException e) {
				//System.err.println(adds[0] + adds[1]);
				geo = new IP2GEO("未知", 0.0, 0.0);
				e.printStackTrace();
			}

		} catch (NullPointerException e) {
			//System.err.println("无法查找到该IP对应的地址");
			country = "未知";
			geo = new IP2GEO(country, 0.0, 0.0);
			e.printStackTrace();
		}
		return geo;
	}

}

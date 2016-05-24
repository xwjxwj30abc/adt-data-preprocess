package zx.soft.adt.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.client.HConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.AlertList;
import zx.soft.adt.domain.GEO;
import zx.soft.adt.domain.HotPlugLog;
import zx.soft.adt.domain.PlcNetInfo;
import zx.soft.adt.domain.VPNTraffic;
import zx.soft.adt.domain.WanIpv4;
import zx.soft.adt.utils.CalendarUtil;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.IP;
import zx.soft.adt.utils.IPToGEO;
import zx.soft.adt.utils.SQLOperation;
import zx.soft.hbase.api.core.HBaseTable;
import zx.soft.utils.checksum.CheckSumUtils;

public class ImportDataThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ImportDataThread.class);
	private int from;
	private String mysqlTablename;
	private HConnection conn;
	private SQLOperation sqlOperation;

	public ImportDataThread(HConnection conn, SQLOperation sqlOperation, String mysqlTablename, int from) {
		this.conn = conn;
		this.from = from;
		this.sqlOperation = sqlOperation;
		this.mysqlTablename = mysqlTablename;
	}

	@Override
	public void run() {
		if (mysqlTablename.toLowerCase().matches(Constant.adt_plcnetinfo_table_name + ".*")) {
			this.importPlcNetInfo();
		} else if (mysqlTablename.toLowerCase().matches(Constant.adt_alertList_table_name + ".*")) {
			this.importAlertList();
		} else if (mysqlTablename.toLowerCase().matches(Constant.adt_accesslist_table_name + ".*")) {
			this.importAccessList();
		} else if (mysqlTablename.toLowerCase().matches(Constant.adt_traffic_table_name + ".*")) {
			this.importVpnTraffic();
		} else if (mysqlTablename.toLowerCase().matches(Constant.adt_wanipv4_table_name + ".*")) {
			this.importWlanIp();
		} else if (mysqlTablename.toLowerCase().matches(Constant.adt_hotpluglog_table_name + ".*")) {
			this.importHotPlugLog();
		}
	}

	private void importPlcNetInfo() {
		try {
			List<PlcNetInfo> plcnetinfos = this.sqlOperation.getPlcNetInfoData(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_plcnetinfo_table_name);
			for (PlcNetInfo plcnetinfo : plcnetinfos) {
				String rowKey = String.valueOf(Constant.Service_code) + plcnetinfo.getRule_id();
				hbaseTable.put(rowKey, Constant.adt_cf, "sc", Constant.Service_code);
				hbaseTable.put(rowKey, Constant.adt_cf, "ri", plcnetinfo.getRule_id());
				hbaseTable.put(rowKey, Constant.adt_cf, "rn", plcnetinfo.getRule_name());
				hbaseTable.put(rowKey, Constant.adt_cf, "ml", plcnetinfo.getMatching_level());
				hbaseTable.put(rowKey, Constant.adt_cf, "ra", plcnetinfo.getRule_action());
				hbaseTable.put(rowKey, Constant.adt_cf, "st", plcnetinfo.getService_type());
				hbaseTable.put(rowKey, Constant.adt_cf, "k1", plcnetinfo.getKeyword1());
				hbaseTable.put(rowKey, Constant.adt_cf, "k2", plcnetinfo.getKeyword2());
				hbaseTable.put(rowKey, Constant.adt_cf, "k3", plcnetinfo.getKeyword3());
				hbaseTable.put(rowKey, Constant.adt_cf, "mw", plcnetinfo.getMatching_word());
				hbaseTable.put(rowKey, Constant.adt_cf, "set", plcnetinfo.getSet_time());
				hbaseTable.put(rowKey, Constant.adt_cf, "vt", plcnetinfo.getValidation_time());
				hbaseTable.put(rowKey, Constant.adt_cf, "mpt", plcnetinfo.getManual_pause_time());
				hbaseTable.put(rowKey, Constant.adt_cf, "fm", plcnetinfo.getFilter_method());
				hbaseTable.put(rowKey, Constant.adt_cf, "fa", plcnetinfo.getFilter_argument());
			}
			hbaseTable.execute();
			hbaseTable.close();
			logger.info("succeed insert" + plcnetinfos.size());
			Constant.CURRENT_NUM.addAndGet(plcnetinfos.size());
			logger.info("插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	//将获取的AlertList信息并关联规则表，设备表
	private void importAlertList() {
		try {
			List<AlertList> alertlists = this.sqlOperation.getAlertListData(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_alertList_table_name);
			GEO geo = null;
			for (AlertList alertlist : alertlists) {
				String keyWord = CheckSumUtils.getMD5(String.valueOf(System.currentTimeMillis())
						+ String.valueOf(Constant.Service_code) + String.valueOf(alertlist.getId()));
				try {
					if (alertlist.getDestination_ip() != 0) {
						geo = IPToGEO.get(alertlist.getDestination_ip(), this.sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "con", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd", geo.getWD());
					}
				} catch (NullPointerException ee) {
					logger.warn(alertlist.getDestination_ip() + ":无法解析该ip所处地址");
				}
				hbaseTable.put(keyWord, Constant.adt_cf, "de4", alertlist.getDestination_ip());
				hbaseTable.put(keyWord, Constant.adt_cf, "sec", Constant.Service_code);
				String rule_id = alertlist.getRule_id();
				hbaseTable.put(keyWord, Constant.adt_cf, "ru", rule_id);
				hbaseTable.put(keyWord, Constant.adt_cf, "sr", String.valueOf(Constant.Service_code) + rule_id);
				PlcNetInfo plcnetinfo = Constant.plcNetInfoMap.get(rule_id);
				if (plcnetinfo != null) {
					hbaseTable.put(keyWord, Constant.adt_cf, "rn", plcnetinfo.getRule_name());
					hbaseTable.put(keyWord, Constant.adt_cf, "ml", plcnetinfo.getMatching_level());
					hbaseTable.put(keyWord, Constant.adt_cf, "ra", plcnetinfo.getRule_action());
					hbaseTable.put(keyWord, Constant.adt_cf, "st", plcnetinfo.getService_type());
					hbaseTable.put(keyWord, Constant.adt_cf, "k1", plcnetinfo.getKeyword1());
					hbaseTable.put(keyWord, Constant.adt_cf, "k2", plcnetinfo.getKeyword2());
					hbaseTable.put(keyWord, Constant.adt_cf, "k3", plcnetinfo.getKeyword3());
					hbaseTable.put(keyWord, Constant.adt_cf, "mw", plcnetinfo.getMatching_word());
					hbaseTable.put(keyWord, Constant.adt_cf, "set", plcnetinfo.getSet_time());
					hbaseTable.put(keyWord, Constant.adt_cf, "vt", plcnetinfo.getValidation_time());
					hbaseTable.put(keyWord, Constant.adt_cf, "mpt", plcnetinfo.getManual_pause_time());
					hbaseTable.put(keyWord, Constant.adt_cf, "fm", plcnetinfo.getFilter_method());
					hbaseTable.put(keyWord, Constant.adt_cf, "fa", plcnetinfo.getFilter_argument());
				}
				hbaseTable.put(keyWord, Constant.adt_cf, "nep", alertlist.getNet_ending_ip());
				hbaseTable.put(keyWord, Constant.adt_cf, "nem", alertlist.getNet_ending_mac());
				hbaseTable.put(keyWord, Constant.adt_cf, "de6", alertlist.getDestination_ipv6());
				hbaseTable.put(keyWord, Constant.adt_cf, "ne6", alertlist.getNet_ending_ipv6());
				hbaseTable.put(keyWord, Constant.adt_cf, "ma", alertlist.getMatching_time());
			}
			hbaseTable.execute();
			hbaseTable.close();
			logger.info("succeed insert" + alertlists.size());
			Constant.CURRENT_NUM.addAndGet(alertlists.size());
			logger.info("插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void importAccessList() {
		try {
			List<AccessList> accesslists = this.sqlOperation.getAccessListData(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_accesslist_table_name);
			GEO geo = null;
			for (AccessList accesslist : accesslists) {
				String keyWord = CheckSumUtils.getMD5(String.valueOf(System.currentTimeMillis()) + mysqlTablename
						+ String.valueOf(accesslist.getId()));
				try {
					if (accesslist.getDestination_ip() != 0) {
						geo = IPToGEO.get(accesslist.getDestination_ip(), this.sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "cn", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd", geo.getWD());
					}
					if (accesslist.getVpn1_ip() != 0) {
						geo = IPToGEO.get(accesslist.getVpn1_ip(), this.sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "cn1", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd1", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd1", geo.getWD());
					}
					if (accesslist.getVpn2_ip() != 0) {
						geo = IPToGEO.get(accesslist.getVpn2_ip(), this.sqlOperation);
						hbaseTable.put(keyWord, Constant.adt_cf, "cn2", geo.getCOUNTRY());
						hbaseTable.put(keyWord, Constant.adt_cf, "jd2", geo.getJD());
						hbaseTable.put(keyWord, Constant.adt_cf, "wd2", geo.getWD());
					}
					if (accesslist.getVpn3_ip() != 0) {
						geo = IPToGEO.get(accesslist.getVpn3_ip(), this.sqlOperation);
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
				hbaseTable.put(keyWord, Constant.adt_cf, "sec", Constant.Service_code);
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
			logger.info("共插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void importVpnTraffic() {
		try {
			List<VPNTraffic> vpnTraffics = this.sqlOperation.getVPNTrafficData(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_traffic_table_name);
			for (VPNTraffic vpnTraffic : vpnTraffics) {
				long middle = CalendarUtil.belongToOneHour(vpnTraffic.getBegin_time(), vpnTraffic.getEnd_time());
				if (middle != 0) {
					String keyWord = CheckSumUtils.getMD5(Constant.Service_code
							+ String.valueOf(vpnTraffic.getBegin_time()) + String.valueOf(middle) + vpnTraffic.getId());
					hbaseTable.put(keyWord, Constant.adt_cf, "id", vpnTraffic.getId());
					hbaseTable.put(keyWord, Constant.adt_cf, "ip", IP.getIP(vpnTraffic.getIpv4()));
					hbaseTable.put(keyWord, Constant.adt_cf, "bt", vpnTraffic.getBegin_time());
					hbaseTable.put(keyWord, Constant.adt_cf, "et", middle);
					long traffic_before = (vpnTraffic.getTraffic()
							/ (vpnTraffic.getEnd_time() - vpnTraffic.getBegin_time()) * (middle - vpnTraffic
							.getBegin_time()));
					hbaseTable.put(keyWord, Constant.adt_cf, "tr", traffic_before);
					hbaseTable.put(keyWord, Constant.adt_cf, "se", Constant.Service_code);

					String keyWord2 = CheckSumUtils.getMD5(Constant.Service_code + String.valueOf(middle)
							+ String.valueOf(vpnTraffic.getEnd_time()) + vpnTraffic.getId());
					hbaseTable.put(keyWord2, Constant.adt_cf, "id", vpnTraffic.getId());
					hbaseTable.put(keyWord2, Constant.adt_cf, "ip", IP.getIP(vpnTraffic.getIpv4()));
					hbaseTable.put(keyWord2, Constant.adt_cf, "bt", middle);
					hbaseTable.put(keyWord2, Constant.adt_cf, "et", vpnTraffic.getEnd_time());
					hbaseTable.put(keyWord2, Constant.adt_cf, "tr", vpnTraffic.getTraffic() - traffic_before);
					hbaseTable.put(keyWord2, Constant.adt_cf, "se", Constant.Service_code);
				} else {
					String keyWord = CheckSumUtils.getMD5(Constant.Service_code
							+ String.valueOf(vpnTraffic.getBegin_time()) + String.valueOf(vpnTraffic.getEnd_time())
							+ vpnTraffic.getId());
					hbaseTable.put(keyWord, Constant.adt_cf, "id", vpnTraffic.getId());
					hbaseTable.put(keyWord, Constant.adt_cf, "ip", IP.getIP(vpnTraffic.getIpv4()));
					hbaseTable.put(keyWord, Constant.adt_cf, "bt", vpnTraffic.getBegin_time());
					hbaseTable.put(keyWord, Constant.adt_cf, "et", vpnTraffic.getEnd_time());
					hbaseTable.put(keyWord, Constant.adt_cf, "tr", vpnTraffic.getTraffic());
					hbaseTable.put(keyWord, Constant.adt_cf, "se", Constant.Service_code);
				}
			}
			hbaseTable.execute();
			hbaseTable.close();
			Constant.CURRENT_NUM.addAndGet(vpnTraffics.size());
			logger.info("共插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	private void importWlanIp() {
		try {
			List<WanIpv4> wanIpv4s = this.sqlOperation.getWanIpv4Data(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_wanipv4_table_name);
			for (WanIpv4 wanIpv4 : wanIpv4s) {
				String keyWord = CheckSumUtils.getMD5(Constant.Service_code + String.valueOf(wanIpv4.getAdd_time())
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

	private void importHotPlugLog() {
		try {
			List<HotPlugLog> hotPlugLogs = this.sqlOperation.getHotPlugLog(mysqlTablename, from);
			HBaseTable hbaseTable = new HBaseTable(conn, Constant.adt_hotpluglog_table_name);
			for (HotPlugLog hotPlugLog : hotPlugLogs) {
				String keyWord = CheckSumUtils.getMD5(String.valueOf(Constant.Service_code)
						+ String.valueOf(hotPlugLog.getAdd_time()) + String.valueOf(hotPlugLog.getId()));
				hbaseTable.put(keyWord, Constant.adt_cf, "id", hotPlugLog.getId());
				hbaseTable.put(keyWord, Constant.adt_cf, "ac", hotPlugLog.getAction());
				hbaseTable.put(keyWord, Constant.adt_cf, "de", hotPlugLog.getDevice());
				hbaseTable.put(keyWord, Constant.adt_cf, "at", hotPlugLog.getAdd_time());
				hbaseTable.put(keyWord, Constant.adt_cf, "no", hotPlugLog.getNote());
				hbaseTable.put(keyWord, Constant.adt_cf, "se", Constant.Service_code);
			}
			hbaseTable.execute();
			hbaseTable.close();
			Constant.CURRENT_NUM.addAndGet(hotPlugLogs.size());
			logger.info("共插入数据量：" + Constant.CURRENT_NUM + ";数据总量：" + Constant.SUM_OF_DATA);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}

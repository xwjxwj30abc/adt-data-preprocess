package zx.soft.adt.utils;

import java.util.concurrent.atomic.AtomicLong;

public class Constant {

	public static String adt_alertList_table_name = "alertlist";
	public static String adt_plcclient_table_name = "plcClient";
	public static String adt_accesslist_table_name = "accesslist";
	public static String adt_plcnetinfo_table_name = "plcnetinfo";
	public static String adt_traffic_table_name = "vpn_traffic";
	public static String adt_wanipv4_table_name = "vpn_wan_ipv4";

	public static String adt_mysql_database_name = "vpn";
	public static String adt_mysql_countryinfo_name = "countryinfo";
	public static String adt_cf = "cf";

	public static long Service_code = 0;
	public static AtomicLong CURRENT_NUM = new AtomicLong(0L);
	public static long SUM_OF_DATA = 0;

}

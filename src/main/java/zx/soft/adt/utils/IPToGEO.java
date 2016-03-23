package zx.soft.adt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.adt.domain.IP2GEO;

/**
 * 长整型数值转换成IP2GEO，包括地理位置的名称和经纬度
 *
 * @author fgq
 *
 */
public class IPToGEO {

	private static final Logger logger = LoggerFactory.getLogger(IPToGEO.class);

	public static IP2GEO get(long ipaddr, SQLOperation sqlOperation) {
		String ip = IP.getIP(ipaddr);
		String country = null;
		String country_city = null;
		IP2GEO geo = null;
		String[] adds = new String[4];
		try {
			try {
				adds = IP.find(ip);
			} catch (NegativeArraySizeException nae) {
				System.err.println(ip);

			}
			try {
				if (adds[0].equals("局域网")) {
					//局域网IP，设置为("局域网", 0.0, 0.0)
					geo = new IP2GEO("局域网", 0.0, 0.0);
				} else if (adds[0].equals("中国") && (!adds[1].equals("中国"))) {
					//国内IP,设置为"中国＋省份"
					geo = sqlOperation.getGEO(Constant.adt_mysql_countryinfo_name, adds[1]);
					country_city = adds[0] + adds[1];
					geo.setCOUNTRY(country_city);
				} else {
					//国外IP
					geo = sqlOperation.getGEO(Constant.adt_mysql_countryinfo_name, adds[0]);
					country = adds[0];
				}
			} catch (NullPointerException e) {
				logger.error(adds[0] + adds[1]);
				geo = new IP2GEO("未知", 0.0, 0.0);
			}

		} catch (NullPointerException e) {
			logger.error(e.getMessage() + ",无法查找到该IP对应的地址");
			country = "未知";
			geo = new IP2GEO(country, 0.0, 0.0);
		}

		return geo;
	}

}

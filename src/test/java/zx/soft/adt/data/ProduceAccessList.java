package zx.soft.adt.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import zx.soft.adt.domain.AccessList;
import zx.soft.adt.domain.IP2GEO;
import zx.soft.adt.utils.Constant;
import zx.soft.adt.utils.SQLOperation;

public class ProduceAccessList {

	private static int Increment = 0;
	private static int timeIncrement = 0;
	private static Random r = new Random();
	private static SQLOperation operation = new SQLOperation();
	private static List<Long> service_code = new ArrayList<>();
	private static List<Long> net_ending_ip = new ArrayList<>();
	private static List<Long> net_ending_mac = new ArrayList<>();
	private static List<String> keywords = new ArrayList<>();
	private static List<String> net_ending_name = new ArrayList<>();
	private static List<Integer> service_type = new ArrayList<>();
	private static List<String> des_country = new ArrayList<>();
	private static List<IP2GEO> internal_country = new ArrayList<>();

	static {
		service_code.add(1113L);
		service_code.add(1114L);
		service_code.add(1116L);
		service_code.add(1115L);
		service_code.add(1120L);
		service_code.add(1121L);
		service_code.add(1122L);

		des_country.add("台湾");
		des_country.add("香港");
		des_country.add("新加坡");
		des_country.add("阿富汗");
		des_country.add("孟加拉国");
		des_country.add("伊朗");
		des_country.add("台湾");
		des_country.add("香港");
		des_country.add("新加坡");
		des_country.add("南非");
		des_country.add("菲律宾");
		des_country.add("马来西亚");

		service_type.add(0);
		service_type.add(1);
		service_type.add(2);
		service_type.add(3);
		service_type.add(4);
		service_type.add(5);
		service_type.add(6);
		service_type.add(7);
		service_type.add(8);
		service_type.add(9);
		service_type.add(10);
		service_type.add(11);
		service_type.add(81);
		service_type.add(82);
		service_type.add(83);
		service_type.add(84);
		service_type.add(85);
		service_type.add(105);
		service_type.add(106);
		service_type.add(997);
		service_type.add(998);
		service_type.add(999);
		service_type.add(1001);
		service_type.add(1002);
		service_type.add(1004);

		net_ending_ip.add(2886747317L);
		net_ending_ip.add(2886747319L);

		net_ending_name.add("ac1044b5");
		net_ending_name.add("ad1044e5");
		net_ending_name.add("af104445");

		net_ending_mac.add(119779281620000L);
		net_ending_mac.add(119779221620000L);
		net_ending_mac.add(119779121620000L);
		net_ending_mac.add(112379281620000L);

		keywords.add("mail.safefw.com");
		keywords.add("stats.autohome.com.cn");
		keywords.add("gbfek.dfcfw.com/gubav3/css/guba.css");
		keywords.add("q4.qlogo.cn/g?b=qq&nk=714630407&s=40&t=1427638770");
		keywords.add("err.taobao.com/error1.html");

		internal_country = operation.getALLGEO(Constant.adt_mysql_countryinfo_name);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			AccessList a = create();
			System.out.println(a);
		}
	}

	public static AccessList create() {
		AccessList accessList = new AccessList();
		long t = System.currentTimeMillis();
		accessList.setCjsj(new Date(t));
		accessList.setDestination_ip(createRandomIP());
		accessList.setVpn1_ip(createRandomIP());
		accessList.setVpn2_ip(createRandomIP());
		accessList.setVpn3_ip(createRandomIP());
		accessList.setId(Increment++);
		accessList.setKeyword1(createRandomKeyword());
		accessList.setNet_ending_ip(createRandomNetEndingIp());
		accessList.setNet_ending_mac(createRandomNetEndingMac());
		accessList.setNet_ending_name(createRandomNetEndingName());
		IP2GEO geo = createRandomDesCountry();
		if (geo.getCOUNTRY().equals("台湾") | geo.getCOUNTRY().equals("香港")) {
			accessList.setCountry_name("中国" + geo.getCOUNTRY());
		} else {
			accessList.setCountry_name(geo.getCOUNTRY());
		}
		accessList.setWd(geo.getWD());
		accessList.setJd(geo.getJD());

		accessList.setPort(createRandomPort());
		accessList.setService_code(createRandomServiceCode());
		accessList.setService_type(createRandomServiceType());
		accessList.setSource_port(createRandomPort());
		accessList.setTime(t / 1000 + (timeIncrement++) * 4);
		return accessList;
	}

	private static int createRandomIP() {
		return r.nextInt(999999999) + r.nextInt(10);
	}

	private static int createRandomPort() {
		return r.nextInt(6000);
	}

	private static int createRandomServiceType() {
		return service_type.get(Increment % service_type.size());
	}

	private static long createRandomServiceCode() {
		int offset = r.nextInt(service_code.size());
		return service_code.get(offset);
	}

	private static String createRandomKeyword() {

		int offset = r.nextInt(keywords.size());
		String key = keywords.get(offset).substring(0, r.nextInt(keywords.get(offset).length()));
		return key;
	}

	private static long createRandomNetEndingMac() {
		int offset = r.nextInt(net_ending_mac.size());
		int little = r.nextInt(9999);
		return net_ending_mac.get(offset) + little;
	}

	private static long createRandomNetEndingIp() {

		int offset = r.nextInt(net_ending_ip.size());
		long key = net_ending_ip.get(offset);
		return key;
	}

	private static String createRandomNetEndingName() {

		int offset = r.nextInt(net_ending_name.size());
		String key = net_ending_name.get(offset);
		return key + r.nextInt(20);
	}

	private static IP2GEO createRandomDesCountry() {
		IP2GEO geo = operation.getGEO(Constant.adt_mysql_countryinfo_name,
				des_country.get(r.nextInt(des_country.size())));
		return geo;
	}

	public static List<IP2GEO> create3RandomInternalCountry() {
		List<IP2GEO> geos = new ArrayList<>();
		int offset = r.nextInt(internal_country.size());
		int offset1 = (offset + r.nextInt(20) + 1) % 188;
		int offset2 = (offset1 + r.nextInt(20) + 1) % 188;
		IP2GEO geo1 = internal_country.get(offset);
		IP2GEO geo2 = internal_country.get(offset1);
		IP2GEO geo3 = internal_country.get(offset2);
		geos.add(geo1);
		geos.add(geo2);
		geos.add(geo3);
		return geos;
	}

}

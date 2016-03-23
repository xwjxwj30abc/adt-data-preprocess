package zx.soft.adt.domain;

import java.util.Date;

/**
 * 上网日志表
 *
 * @author wanggang
 *
 */
public class AccessList {

	private long id;
	private long Service_code;
	private long Net_ending_ip;
	private String Net_ending_name = "";
	private long Time;
	private long Net_ending_mac;
	private long Destination_ip;
	private int Port;
	private int Service_type;
	private String Keyword1 = "";
	private String Keyword2 = "";
	private String Keyword3 = "";
	private long Mac;
	private int Source_port;
	private String Net_ending_ipv6 = "";
	private String Destination_ipv6 = "";
	private int Keyword11;
	private int Keyword12;
	private int Keyword13;
	private int Keyword14;
	private long Keyword15;
	private String Keyword21 = "";
	private String Keyword22 = "";
	private String Keyword23 = "";
	private String Keyword24 = "";
	private String Keyword25 = "";
	private double Jd;
	private double Wd;
	private String Country_name = "";
	private long vpn1_ip;
	private double Jd_vpn1;
	private double Wd_vpn1;
	private String Country_name_vpn1 = "";
	private long vpn2_ip;
	private double Jd_vpn2;
	private double Wd_vpn2;
	private String Country_name_vpn2 = "";
	private long vpn3_ip;
	private double Jd_vpn3;
	private double Wd_vpn3;
	private String Country_name_vpn3 = "";
	private Date Cjsj;

	public AccessList() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getService_code() {
		return Service_code;
	}

	public void setService_code(long service_code) {
		Service_code = service_code;
	}

	public long getNet_ending_ip() {
		return Net_ending_ip;
	}

	public void setNet_ending_ip(long net_ending_ip) {
		Net_ending_ip = net_ending_ip;
	}

	public String getNet_ending_name() {
		return Net_ending_name;
	}

	public void setNet_ending_name(String net_ending_name) {
		Net_ending_name = net_ending_name;
	}

	public long getTime() {
		return Time;
	}

	public void setTime(long time) {
		Time = time;
	}

	public long getNet_ending_mac() {
		return Net_ending_mac;
	}

	public void setNet_ending_mac(long net_ending_mac) {
		Net_ending_mac = net_ending_mac;
	}

	public long getDestination_ip() {
		return Destination_ip;
	}

	public void setDestination_ip(long destination_ip) {
		Destination_ip = destination_ip;
	}

	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}

	public int getService_type() {
		return Service_type;
	}

	public void setService_type(int service_type) {
		Service_type = service_type;
	}

	public String getKeyword1() {
		return Keyword1;
	}

	public void setKeyword1(String keyword1) {
		Keyword1 = keyword1;
	}

	public String getKeyword2() {
		return Keyword2;
	}

	public void setKeyword2(String keyword2) {
		Keyword2 = keyword2;
	}

	public String getKeyword3() {
		return Keyword3;
	}

	public void setKeyword3(String keyword3) {
		Keyword3 = keyword3;
	}

	public long getMac() {
		return Mac;
	}

	public void setMac(long mac) {
		Mac = mac;
	}

	public int getSource_port() {
		return Source_port;
	}

	public void setSource_port(int source_port) {
		Source_port = source_port;
	}

	public String getNet_ending_ipv6() {
		return Net_ending_ipv6;
	}

	public void setNet_ending_ipv6(String net_ending_ipv6) {
		Net_ending_ipv6 = net_ending_ipv6;
	}

	public String getDestination_ipv6() {
		return Destination_ipv6;
	}

	public void setDestination_ipv6(String destination_ipv6) {
		Destination_ipv6 = destination_ipv6;
	}

	public int getKeyword11() {
		return Keyword11;
	}

	public void setKeyword11(int keyword11) {
		Keyword11 = keyword11;
	}

	public int getKeyword12() {
		return Keyword12;
	}

	public void setKeyword12(int keyword12) {
		Keyword12 = keyword12;
	}

	public int getKeyword13() {
		return Keyword13;
	}

	public void setKeyword13(int keyword13) {
		Keyword13 = keyword13;
	}

	public int getKeyword14() {
		return Keyword14;
	}

	public void setKeyword14(int keyword14) {
		Keyword14 = keyword14;
	}

	public long getKeyword15() {
		return Keyword15;
	}

	public void setKeyword15(long keyword15) {
		Keyword15 = keyword15;
	}

	public String getKeyword21() {
		return Keyword21;
	}

	public void setKeyword21(String keyword21) {
		Keyword21 = keyword21;
	}

	public String getKeyword22() {
		return Keyword22;
	}

	public void setKeyword22(String keyword22) {
		Keyword22 = keyword22;
	}

	public String getKeyword23() {
		return Keyword23;
	}

	public void setKeyword23(String keyword23) {
		Keyword23 = keyword23;
	}

	public String getKeyword24() {
		return Keyword24;
	}

	public void setKeyword24(String keyword24) {
		Keyword24 = keyword24;
	}

	public String getKeyword25() {
		return Keyword25;
	}

	public void setKeyword25(String keyword25) {
		Keyword25 = keyword25;
	}

	public double getJd() {
		return Jd;
	}

	public void setJd(double jd) {
		Jd = jd;
	}

	public double getWd() {
		return Wd;
	}

	public void setWd(double wd) {
		Wd = wd;
	}

	public String getCountry_name() {
		return Country_name;
	}

	public void setCountry_name(String country_name) {
		Country_name = country_name;
	}

	public long getVpn1_ip() {
		return vpn1_ip;
	}

	public void setVpn1_ip(long vpn1_ip) {
		this.vpn1_ip = vpn1_ip;
	}

	public double getJd_vpn1() {
		return Jd_vpn1;
	}

	public void setJd_vpn1(double jd_vpn1) {
		Jd_vpn1 = jd_vpn1;
	}

	public double getWd_vpn1() {
		return Wd_vpn1;
	}

	public void setWd_vpn1(double wd_vpn1) {
		Wd_vpn1 = wd_vpn1;
	}

	public String getCountry_name_vpn1() {
		return Country_name_vpn1;
	}

	public void setCountry_name_vpn1(String country_name_vpn1) {
		Country_name_vpn1 = country_name_vpn1;
	}

	public long getVpn2_ip() {
		return vpn2_ip;
	}

	public void setVpn2_ip(long vpn2_ip) {
		this.vpn2_ip = vpn2_ip;
	}

	public double getJd_vpn2() {
		return Jd_vpn2;
	}

	public void setJd_vpn2(double jd_vpn2) {
		Jd_vpn2 = jd_vpn2;
	}

	public double getWd_vpn2() {
		return Wd_vpn2;
	}

	public void setWd_vpn2(double wd_vpn2) {
		Wd_vpn2 = wd_vpn2;
	}

	public String getCountry_name_vpn2() {
		return Country_name_vpn2;
	}

	public void setCountry_name_vpn2(String country_name_vpn2) {
		Country_name_vpn2 = country_name_vpn2;
	}

	public long getVpn3_ip() {
		return vpn3_ip;
	}

	public void setVpn3_ip(long vpn3_ip) {
		this.vpn3_ip = vpn3_ip;
	}

	public double getJd_vpn3() {
		return Jd_vpn3;
	}

	public void setJd_vpn3(double jd_vpn3) {
		Jd_vpn3 = jd_vpn3;
	}

	public double getWd_vpn3() {
		return Wd_vpn3;
	}

	public void setWd_vpn3(double wd_vpn3) {
		Wd_vpn3 = wd_vpn3;
	}

	public String getCountry_name_vpn3() {
		return Country_name_vpn3;
	}

	public void setCountry_name_vpn3(String country_name_vpn3) {
		Country_name_vpn3 = country_name_vpn3;
	}

	public Date getCjsj() {
		return Cjsj;
	}

	public void setCjsj(Date cjsj) {
		Cjsj = cjsj;
	}

	@Override
	public String toString() {
		return "AccessList [id=" + id + ", Service_code=" + Service_code + ", Net_ending_ip="
				+ Net_ending_ip + ", Net_ending_name=" + Net_ending_name + ", Time=" + Time
				+ ", Net_ending_mac=" + Net_ending_mac + ", Destination_ip=" + Destination_ip
				+ ", Port=" + Port + ", Service_type=" + Service_type + ", Keyword1=" + Keyword1
				+ ", Keyword2=" + Keyword2 + ", Keyword3=" + Keyword3 + ", Mac=" + Mac
				+ ", Source_port=" + Source_port + ", Net_ending_ipv6=" + Net_ending_ipv6
				+ ", Destination_ipv6=" + Destination_ipv6 + ", Keyword11=" + Keyword11
				+ ", Keyword12=" + Keyword12 + ", Keyword13=" + Keyword13 + ", Keyword14="
				+ Keyword14 + ", Keyword15=" + Keyword15 + ", Keyword21=" + Keyword21
				+ ", Keyword22=" + Keyword22 + ", Keyword23=" + Keyword23 + ", Keyword24="
				+ Keyword24 + ", Keyword25=" + Keyword25 + ", Jd=" + Jd + ", Wd=" + Wd
				+ ", Country_name=" + Country_name + ", vpn1_ip=" + vpn1_ip + ", Jd_vpn1="
				+ Jd_vpn1 + ", Wd_vpn1=" + Wd_vpn1 + ", Country_name_vpn1=" + Country_name_vpn1
				+ ", vpn2_ip=" + vpn2_ip + ", Jd_vpn2=" + Jd_vpn2 + ", Wd_vpn2=" + Wd_vpn2
				+ ", Country_name_vpn2=" + Country_name_vpn2 + ", vpn3_ip=" + vpn3_ip
				+ ", Jd_vpn3=" + Jd_vpn3 + ", Wd_vpn3=" + Wd_vpn3 + ", Country_name_vpn3="
				+ Country_name_vpn3 + ", Cjsj=" + Cjsj + "]";
	}

}

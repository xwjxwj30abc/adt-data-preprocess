package zx.soft.adt.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 设备信息表
 *
 * @author wanggang
 *
 */
public class PlcClient {

	@JsonProperty
	private long Service_code;
	@JsonProperty
	private String Service_name = "";
	@JsonProperty
	private String Address = "";
	@JsonProperty
	private String Zip = "";
	@JsonProperty
	private String Principal = "";
	@JsonProperty
	private String Principal_tel = "";
	@JsonProperty
	private String Infor_man = "";
	@JsonProperty
	private String Infor_man_tel = "";
	@JsonProperty
	private String Infor_man_email = "";
	@JsonProperty
	private int Producer_code = 99;
	@JsonProperty
	private int Status = 4;
	@JsonProperty
	private int Ending_number;
	@JsonProperty
	private int Server_number;
	@JsonProperty
	private String Ip = "";
	@JsonProperty
	private int Net_type = 99;
	@JsonProperty
	private int Practitioner_number;
	@JsonProperty
	private String Net_monitor_department = "";
	@JsonProperty
	private String Net_monitor_man = "";
	@JsonProperty
	private String Net_monitor_man_tel = "";
	@JsonProperty
	private String Remark = "";
	@JsonProperty
	private int NewSystem;
	@JsonProperty
	private int UnitNo;
	@JsonProperty
	private String SessionID = "";
	@JsonProperty
	private String UdpHost = "";
	@JsonProperty
	private int UdpPort;
	@JsonProperty
	private String UdpVer = "";
	@JsonProperty
	private int ComputerOnline;
	@JsonProperty
	private long ClientTime;
	@JsonProperty
	private int LogDays = 60;
	@JsonProperty
	private int CommStatus = 60;
	@JsonProperty
	private int CommNormal = 10;
	@JsonProperty
	private int CommTiming = 10;
	@JsonProperty
	private int AlertLogAttr;
	@JsonProperty
	private int UserLogAttr;
	@JsonProperty
	private int DefaultAccessRule;
	@JsonProperty
	private long Device_ipv4;
	@JsonProperty
	private String Device_ipv6 = "";
	@JsonProperty
	private int Device_port;
	@JsonProperty
	private int Udp_online;
	@JsonProperty
	private String Device_serial = "";
	@JsonProperty
	private String Device_version = "20120801";
	@JsonProperty
	private long Device_flow1;
	@JsonProperty
	private long Device_flow2;
	@JsonProperty
	private String Device_note = "";
	@JsonProperty
	private String User_name = "";
	@JsonProperty
	private String Certificate_type = "";
	@JsonProperty
	private String Certificate_code = "";
	@JsonProperty
	private String Org_name = "";
	@JsonProperty
	private String Country = "";

	public PlcClient() {
	}

	@JsonIgnore
	public long getService_code() {
		return Service_code;
	}

	@JsonIgnore
	public String getService_name() {
		return Service_name;
	}

	@JsonIgnore
	public String getAddress() {
		return Address;
	}

	@JsonIgnore
	public String getZip() {
		return Zip;
	}

	@JsonIgnore
	public String getPrincipal() {
		return Principal;
	}

	@JsonIgnore
	public String getPrincipal_tel() {
		return Principal_tel;
	}

	@JsonIgnore
	public String getInfor_man() {
		return Infor_man;
	}

	@JsonIgnore
	public String getInfor_man_tel() {
		return Infor_man_tel;
	}

	@JsonIgnore
	public String getInfor_man_email() {
		return Infor_man_email;
	}

	@JsonIgnore
	public int getProducer_code() {
		return Producer_code;
	}

	@JsonIgnore
	public int getStatus() {
		return Status;
	}

	@JsonIgnore
	public int getEnding_number() {
		return Ending_number;
	}

	@JsonIgnore
	public int getServer_number() {
		return Server_number;
	}

	@JsonIgnore
	public String getIp() {
		return Ip;
	}

	@JsonIgnore
	public int getNet_type() {
		return Net_type;
	}

	@JsonIgnore
	public int getPractitioner_number() {
		return Practitioner_number;
	}

	@JsonIgnore
	public String getNet_monitor_department() {
		return Net_monitor_department;
	}

	@JsonIgnore
	public String getNet_monitor_man() {
		return Net_monitor_man;
	}

	@JsonIgnore
	public String getNet_monitor_man_tel() {
		return Net_monitor_man_tel;
	}

	@JsonIgnore
	public String getRemark() {
		return Remark;
	}

	@JsonIgnore
	public int getNewSystem() {
		return NewSystem;
	}

	@JsonIgnore
	public int getUnitNo() {
		return UnitNo;
	}

	@JsonIgnore
	public String getSessionID() {
		return SessionID;
	}

	@JsonIgnore
	public String getUdpHost() {
		return UdpHost;
	}

	@JsonIgnore
	public int getUdpPort() {
		return UdpPort;
	}

	@JsonIgnore
	public String getUdpVer() {
		return UdpVer;
	}

	@JsonIgnore
	public int getComputerOnline() {
		return ComputerOnline;
	}

	@JsonIgnore
	public long getClientTime() {
		return ClientTime;
	}

	@JsonIgnore
	public int getLogDays() {
		return LogDays;
	}

	@JsonIgnore
	public int getCommStatus() {
		return CommStatus;
	}

	@JsonIgnore
	public int getCommNormal() {
		return CommNormal;
	}

	@JsonIgnore
	public int getCommTiming() {
		return CommTiming;
	}

	@JsonIgnore
	public int getAlertLogAttr() {
		return AlertLogAttr;
	}

	@JsonIgnore
	public int getUserLogAttr() {
		return UserLogAttr;
	}

	@JsonIgnore
	public int getDefaultAccessRule() {
		return DefaultAccessRule;
	}

	@JsonIgnore
	public long getDevice_ipv4() {
		return Device_ipv4;
	}

	@JsonIgnore
	public String getDevice_ipv6() {
		return Device_ipv6;
	}

	@JsonIgnore
	public int getDevice_port() {
		return Device_port;
	}

	@JsonIgnore
	public int getUdp_online() {
		return Udp_online;
	}

	@JsonIgnore
	public String getDevice_serial() {
		return Device_serial;
	}

	@JsonIgnore
	public String getDevice_version() {
		return Device_version;
	}

	@JsonIgnore
	public long getDevice_flow1() {
		return Device_flow1;
	}

	@JsonIgnore
	public long getDevice_flow2() {
		return Device_flow2;
	}

	@JsonIgnore
	public String getDevice_note() {
		return Device_note;
	}

	@JsonIgnore
	public void setService_code(long service_code) {
		Service_code = service_code;
	}

	@JsonIgnore
	public void setService_name(String service_name) {
		Service_name = service_name;
	}

	@JsonIgnore
	public void setAddress(String address) {
		Address = address;
	}

	@JsonIgnore
	public void setZip(String zip) {
		Zip = zip;
	}

	@JsonIgnore
	public void setPrincipal(String principal) {
		Principal = principal;
	}

	@JsonIgnore
	public void setPrincipal_tel(String principal_tel) {
		Principal_tel = principal_tel;
	}

	@JsonIgnore
	public void setInfor_man(String infor_man) {
		Infor_man = infor_man;
	}

	@JsonIgnore
	public void setInfor_man_tel(String infor_man_tel) {
		Infor_man_tel = infor_man_tel;
	}

	@JsonIgnore
	public void setInfor_man_email(String infor_man_email) {
		Infor_man_email = infor_man_email;
	}

	@JsonIgnore
	public void setProducer_code(int producer_code) {
		Producer_code = producer_code;
	}

	@JsonIgnore
	public void setStatus(int status) {
		Status = status;
	}

	@JsonIgnore
	public void setEnding_number(int ending_number) {
		Ending_number = ending_number;
	}

	@JsonIgnore
	public void setServer_number(int server_number) {
		Server_number = server_number;
	}

	@JsonIgnore
	public void setIp(String ip) {
		Ip = ip;
	}

	@JsonIgnore
	public void setNet_type(int net_type) {
		Net_type = net_type;
	}

	@JsonIgnore
	public void setPractitioner_number(int practitioner_number) {
		Practitioner_number = practitioner_number;
	}

	@JsonIgnore
	public void setNet_monitor_department(String net_monitor_department) {
		Net_monitor_department = net_monitor_department;
	}

	@JsonIgnore
	public void setNet_monitor_man(String net_monitor_man) {
		Net_monitor_man = net_monitor_man;
	}

	@JsonIgnore
	public void setNet_monitor_man_tel(String net_monitor_man_tel) {
		Net_monitor_man_tel = net_monitor_man_tel;
	}

	@JsonIgnore
	public void setRemark(String remark) {
		Remark = remark;
	}

	@JsonIgnore
	public void setNewSystem(int newSystem) {
		NewSystem = newSystem;
	}

	@JsonIgnore
	public void setUnitNo(int unitNo) {
		UnitNo = unitNo;
	}

	@JsonIgnore
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	@JsonIgnore
	public void setUdpHost(String udpHost) {
		UdpHost = udpHost;
	}

	@JsonIgnore
	public void setUdpPort(int udpPort) {
		UdpPort = udpPort;
	}

	@JsonIgnore
	public void setUdpVer(String udpVer) {
		UdpVer = udpVer;
	}

	@JsonIgnore
	public void setComputerOnline(int computerOnline) {
		ComputerOnline = computerOnline;
	}

	@JsonIgnore
	public void setClientTime(long clientTime) {
		ClientTime = clientTime;
	}

	@JsonIgnore
	public void setLogDays(int logDays) {
		LogDays = logDays;
	}

	@JsonIgnore
	public void setCommStatus(int commStatus) {
		CommStatus = commStatus;
	}

	@JsonIgnore
	public void setCommNormal(int commNormal) {
		CommNormal = commNormal;
	}

	@JsonIgnore
	public void setCommTiming(int commTiming) {
		CommTiming = commTiming;
	}

	@JsonIgnore
	public void setAlertLogAttr(int alertLogAttr) {
		AlertLogAttr = alertLogAttr;
	}

	@JsonIgnore
	public void setUserLogAttr(int userLogAttr) {
		UserLogAttr = userLogAttr;
	}

	@JsonIgnore
	public void setDefaultAccessRule(int defaultAccessRule) {
		DefaultAccessRule = defaultAccessRule;
	}

	@JsonIgnore
	public void setDevice_ipv4(long device_ipv4) {
		Device_ipv4 = device_ipv4;
	}

	@JsonIgnore
	public void setDevice_ipv6(String device_ipv6) {
		Device_ipv6 = device_ipv6;
	}

	@JsonIgnore
	public void setDevice_port(int device_port) {
		Device_port = device_port;
	}

	@JsonIgnore
	public void setUdp_online(int udp_online) {
		Udp_online = udp_online;
	}

	@JsonIgnore
	public void setDevice_serial(String device_serial) {
		Device_serial = device_serial;
	}

	@JsonIgnore
	public void setDevice_version(String device_version) {
		Device_version = device_version;
	}

	@JsonIgnore
	public void setDevice_flow1(long device_flow1) {
		Device_flow1 = device_flow1;
	}

	@JsonIgnore
	public void setDevice_flow2(long device_flow2) {
		Device_flow2 = device_flow2;
	}

	@JsonIgnore
	public void setDevice_note(String device_note) {
		Device_note = device_note;
	}

	@JsonIgnore
	public String getUser_name() {
		return User_name;
	}

	@JsonIgnore
	public String getCertificate_type() {
		return Certificate_type;
	}

	@JsonIgnore
	public String getCertificate_code() {
		return Certificate_code;
	}

	@JsonIgnore
	public String getOrg_name() {
		return Org_name;
	}

	@JsonIgnore
	public void setUser_name(String user_name) {
		User_name = user_name;
	}

	@JsonIgnore
	public void setCertificate_type(String certificate_type) {
		Certificate_type = certificate_type;
	}

	@JsonIgnore
	public void setCertificate_code(String certificate_code) {
		Certificate_code = certificate_code;
	}

	@JsonIgnore
	public void setOrg_name(String org_name) {
		Org_name = org_name;
	}

	@JsonIgnore
	public String getCountry() {
		return Country;
	}

	@JsonIgnore
	public void setCountry(String country) {
		Country = country;
	}

	@Override
	public String toString() {
		return "PlcClient [Service_code=" + Service_code + ", Service_name=" + Service_name
				+ ", Address=" + Address + ", Zip=" + Zip + ", Principal=" + Principal
				+ ", Principal_tel=" + Principal_tel + ", Infor_man=" + Infor_man
				+ ", Infor_man_tel=" + Infor_man_tel + ", Infor_man_email=" + Infor_man_email
				+ ", Producer_code=" + Producer_code + ", Status=" + Status + ", Ending_number="
				+ Ending_number + ", Server_number=" + Server_number + ", Ip=" + Ip + ", Net_type="
				+ Net_type + ", Practitioner_number=" + Practitioner_number
				+ ", Net_monitor_department=" + Net_monitor_department + ", Net_monitor_man="
				+ Net_monitor_man + ", Net_monitor_man_tel=" + Net_monitor_man_tel + ", Remark="
				+ Remark + ", NewSystem=" + NewSystem + ", UnitNo=" + UnitNo + ", SessionID="
				+ SessionID + ", UdpHost=" + UdpHost + ", UdpPort=" + UdpPort + ", UdpVer="
				+ UdpVer + ", ComputerOnline=" + ComputerOnline + ", ClientTime=" + ClientTime
				+ ", LogDays=" + LogDays + ", CommStatus=" + CommStatus + ", CommNormal="
				+ CommNormal + ", CommTiming=" + CommTiming + ", AlertLogAttr=" + AlertLogAttr
				+ ", UserLogAttr=" + UserLogAttr + ", DefaultAccessRule=" + DefaultAccessRule
				+ ", Device_ipv4=" + Device_ipv4 + ", Device_ipv6=" + Device_ipv6
				+ ", Device_port=" + Device_port + ", Udp_online=" + Udp_online
				+ ", Device_serial=" + Device_serial + ", Device_version=" + Device_version
				+ ", Device_flow1=" + Device_flow1 + ", Device_flow2=" + Device_flow2
				+ ", Device_note=" + Device_note + ", User_name=" + User_name
				+ ", Certificate_type=" + Certificate_type + ", Certificate_code="
				+ Certificate_code + ", Org_name=" + Org_name + ", Country=" + Country + "]";
	}

}

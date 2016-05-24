package zx.soft.adt.domain;

/**
 * 过滤结果表
 *
 * @author wanggang
 *
 */
public class AlertList {

	private long id;
	private long Service_code;
	private String Rule_id = "";
	private long Destination_ip;
	private long Net_ending_ip;
	private long Net_ending_mac;
	private String Destination_ipv6 = "";
	private String Net_ending_ipv6 = "";
	private long Matching_time;

	public AlertList() {
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

	public String getRule_id() {
		return Rule_id;
	}

	public void setRule_id(String rule_id) {
		Rule_id = rule_id;
	}

	public long getDestination_ip() {
		return Destination_ip;
	}

	public void setDestination_ip(long destination_ip) {
		Destination_ip = destination_ip;
	}

	public long getNet_ending_ip() {
		return Net_ending_ip;
	}

	public void setNet_ending_ip(long net_ending_ip) {
		Net_ending_ip = net_ending_ip;
	}

	public long getNet_ending_mac() {
		return Net_ending_mac;
	}

	public void setNet_ending_mac(long net_ending_mac) {
		Net_ending_mac = net_ending_mac;
	}

	public String getDestination_ipv6() {
		return Destination_ipv6;
	}

	public void setDestination_ipv6(String destination_ipv6) {
		Destination_ipv6 = destination_ipv6;
	}

	public String getNet_ending_ipv6() {
		return Net_ending_ipv6;
	}

	public void setNet_ending_ipv6(String net_ending_ipv6) {
		Net_ending_ipv6 = net_ending_ipv6;
	}

	public long getMatching_time() {
		return Matching_time;
	}

	public void setMatching_time(long matching_time) {
		Matching_time = matching_time;
	}

	@Override
	public String toString() {
		return "AlertList [id=" + id + ", Service_code=" + Service_code + ", Rule_id=" + Rule_id + ", Destination_ip="
				+ Destination_ip + ", Net_ending_ip=" + Net_ending_ip + ", Net_ending_mac=" + Net_ending_mac
				+ ", Destination_ipv6=" + Destination_ipv6 + ", Net_ending_ipv6=" + Net_ending_ipv6
				+ ", Matching_time=" + Matching_time + "]";
	}

}

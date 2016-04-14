package zx.soft.adt.domain;

/**
 * 流量表
 * @author fgq
 *
 */
public class VPNTraffic {

	private int id;
	private long ipv4;
	private long begin_time;
	private long end_time;
	private long traffic;

	public VPNTraffic() {
	}

	public int getId() {
		return id;
	}

	public long getIpv4() {
		return ipv4;
	}

	public long getBegin_time() {
		return begin_time;
	}

	public long getEnd_time() {
		return end_time;
	}

	public long getTraffic() {
		return traffic;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIpv4(long ipv4) {
		this.ipv4 = ipv4;
	}

	public void setBegin_time(long begin_time) {
		this.begin_time = begin_time;
	}

	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}

	public void setTraffic(long traffic) {
		this.traffic = traffic;
	}

	@Override
	public String toString() {
		return "VPNTraffic [id=" + id + ", ipv4=" + ipv4 + ", begin_time=" + begin_time + ", end_time=" + end_time
				+ ", traffic=" + traffic + "]";
	}

}

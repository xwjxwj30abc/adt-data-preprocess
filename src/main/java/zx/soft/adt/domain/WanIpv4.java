package zx.soft.adt.domain;

/**
 * 网络出口的公网地址记录
 * @author fgq
 *
 */
public class WanIpv4 {

	private int id;
	private long ipv4;
	private long add_time;

	public WanIpv4() {

	}

	public int getId() {
		return id;
	}

	public long getIpv4() {
		return ipv4;
	}

	public long getAdd_time() {
		return add_time;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIpv4(long ipv4) {
		this.ipv4 = ipv4;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	@Override
	public String toString() {
		return "WanIpv4 [id=" + id + ", ipv4=" + ipv4 + ", add_time=" + add_time + "]";
	}

}

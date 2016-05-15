package zx.soft.adt.domain;

public class HotPlugLog {

	private int id;//自增ID
	private int action;//热拔插动作：0-拔起 1-插入
	private String device = "";//网卡设备名称
	private long add_time;//添加时间
	private String note = "";//备注，扩展字段

	public HotPlugLog() {

	}

	public int getId() {
		return id;
	}

	public int getAction() {
		return action;
	}

	public String getDevice() {
		return device;
	}

	public long getAdd_time() {
		return add_time;
	}

	public String getNote() {
		return note;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setAdd_time(long add_time) {
		this.add_time = add_time;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "HotPlugLog [id=" + id + ", action=" + action + ", device=" + device + ", add_time=" + add_time
				+ ", note=" + note + "]";
	}

}

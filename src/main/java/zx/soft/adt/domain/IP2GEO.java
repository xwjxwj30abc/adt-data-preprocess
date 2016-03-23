package zx.soft.adt.domain;

/**
 * 地理位置信息
 *
 * @author wanggang
 *
 */
public class IP2GEO {

	private String COUNTRY;
	private double JD;
	private double WD;

	public IP2GEO() {
	}

	public IP2GEO(String COUNTRY, double JD, double WD) {
		this.COUNTRY = COUNTRY;
		this.JD = JD;
		this.WD = WD;
	}

	public String getCOUNTRY() {
		return COUNTRY;
	}

	public double getJD() {
		return JD;
	}

	public double getWD() {
		return WD;
	}

	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}

	public void setJD(double jD) {
		JD = jD;
	}

	public void setWD(double wD) {
		WD = wD;
	}

	@Override
	public String toString() {
		return "IP2GEO [COUNTRY=" + COUNTRY + ", JD=" + JD + ", WD=" + WD + "]";
	}

}

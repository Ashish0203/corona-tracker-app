package io.javabrains.coronavirustracker.services;


public class ReportRepo {

	private String province;
	private String country;
	private int latestCount;
	private int difference;
	
	
	public ReportRepo() {
		super();
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestCount() {
		return latestCount;
	}
	public void setLatestCount(int latestCount) {
		this.latestCount = latestCount;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

	@Override
	public String toString() {
		return "ReportRepo [province=" + province + ", country=" + country + ", latestCount=" + latestCount
				+ ", difference=" + difference + "]";
	}
	
	
}

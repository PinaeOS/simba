package org.pinae.simba.context.resource;

public class PostCode {
	private int code;
	private boolean active;
	private String station;
	
	public PostCode(int code, boolean active, String station) {
		this.code = code;
		this.active = active;
		this.station = station;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getStation() {
		return station;
	}
	
	public void setStation(String station) {
		this.station = station;
	}
	
	
}

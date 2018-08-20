package by.epam.task4.model;

import java.util.Date;

public class Certificate {

	private String registredBy;
	private Date registredDate;
	private Date expireDate;
	public String getRegistredBy() {
		return registredBy;
	}
	public void setRegistredBy(String registredBy) {
		this.registredBy = registredBy;
	}
	public Date getRegistredDate() {
		return registredDate;
	}
	public void setRegistredDate(Date registredDate) {
		this.registredDate = registredDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
}

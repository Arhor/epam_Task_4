package by.epam.task4.model;

import java.util.Date;

public class Certificate {

	private String registredBy;
	private Date registrationDate;
	private Date expireDate;
	
	public String getRegistredBy() {
		return registredBy;
	}
	
	public void setRegistredBy(String registredBy) {
		this.registredBy = registredBy;
	}
	
	public Date getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(Date registredDate) {
		this.registrationDate = registredDate;
	}
	
	public Date getExpireDate() {
		return expireDate;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode += registredBy == null ? 0 : registredBy.hashCode();
		hashCode += registrationDate == null ? 0 : registrationDate.hashCode();
		hashCode += expireDate == null ? 0 : expireDate.hashCode();
		return hashCode;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()
				+ "@"
				+ " registred by: " +  registredBy
				+ ", registration date: " + registrationDate
				+ ", Expire date: " + expireDate;
	}
}

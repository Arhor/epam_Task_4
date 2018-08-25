package by.epam.task4.model;

import java.util.ArrayList;

public abstract class Medicine {

	private String name;
	private String cas;
	private String drugBank;
	private String pharm;
	private ArrayList<Version> versions;
	
	
	public Medicine() {
		versions = new ArrayList<Version>();
	}
	
	public String getPharm() {
		return pharm;
	}
	
	public void setPharm(String pharm) {
		this.pharm = pharm;
	}
	public ArrayList<Version> getVersions() {
		return versions;
	}
	public void setVersions(ArrayList<Version> versions) {
		this.versions = versions;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCas() {
		return cas;
	}
	public void setCas(String cas) {
		this.cas = cas;
	}
	public String getDrugBank() {
		return drugBank;
	}
	public void setDrugBank(String drugBank) {
		this.drugBank = drugBank;
	}
	
	public void addVersion(Version version) {
		versions.add(version);
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode += name == null ? 0 : name.hashCode();
		hashCode += cas == null ? 0 : cas.hashCode();
		hashCode += drugBank == null ? 0 : drugBank.hashCode();
		hashCode += pharm == null ? 0 : pharm.hashCode();
		for (Version version : versions) {
			hashCode += version.hashCode();
		}
		return hashCode;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()
				+ "@"
				+ " name: " + name
				+ ", pharm: " + pharm
				+ ", CAS: " + cas
				+ ", DrugBank: " + drugBank
				+ ", versions: " + versions;
	}
}

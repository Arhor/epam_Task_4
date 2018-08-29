package by.epam.task4.model;

import java.util.HashSet;

public abstract class Medicine {

	private String name;
	private String cas;
	private String drugBank;
	private String pharm;
	private HashSet<Version> versions;
	
	
	public Medicine() {
		versions = new HashSet<Version>();
	}
	
	public String getPharm() {
		return pharm;
	}
	
	public void setPharm(String pharm) {
		this.pharm = pharm;
	}
	public HashSet<Version> getVersions() {
		return versions;
	}
	public void setVersions(HashSet<Version> versions) {
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
	public boolean equals(Object obj) {
		System.out.println("equals in: " + getClass());
		if (obj == this) { return true; }
		if (obj == null) { return false; }
		if (obj.getClass() != getClass()) { return false; }
		Medicine medicine = (Medicine) obj;
		if (name == null && medicine.name != null) {
			System.out.println("failed in: " + getClass());
			return false;
		} else if (!name.equals(medicine.name)) {
			System.out.println("failed in: " + getClass());
			return false;
		}
		if (cas == null && medicine.cas != null) {
			return false;
		} else if (!cas.equals(medicine.cas)) {
			return false;
		}
		if (drugBank == null && medicine.drugBank != null) {
			return false;
		} else if (!drugBank.equals(medicine.drugBank)) {
			return false;
		}
		if (versions == null && medicine.versions != null) {
			System.out.println("failed in: " + getClass());
			return false;
		} else if (!versions.equals(medicine.versions)) {
			// FAIL HERE !!!
			return false;
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		hashCode += name == null ? 0 : name.hashCode();
		hashCode += cas == null ? 0 : cas.hashCode();
		hashCode += drugBank == null ? 0 : drugBank.hashCode();
		hashCode += pharm == null ? 0 : pharm.hashCode();
		for (Version version : versions) {
			hashCode += version == null ? 0 : version.hashCode();
		}
		return hashCode;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder(
				getClass().getSimpleName() + ": "
				+ " name='" + name + "'"
				+ " CAS='" + cas + "'"
				+ " DrugBank='" + drugBank + "'"
				+ "\n    pharm: " + pharm);
		for (Version version : versions) {
			output.append(version);
		}
		return output.toString();
	}
}

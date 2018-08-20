package by.epam.task4.model;

import java.util.ArrayList;

public class Version {

	private String tradeName;
	private String producer;
	private String form;
	private Certificate certificate;
	private ArrayList<Pack> packs;
	private Dosage dosage;

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public ArrayList<Pack> getPacks() {
		return packs;
	}

	public void setPacks(ArrayList<Pack> packs) {
		this.packs = packs;
	}

	public Dosage getDosage() {
		return dosage;
	}

	public void setDosage(Dosage dosage) {
		this.dosage = dosage;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
}

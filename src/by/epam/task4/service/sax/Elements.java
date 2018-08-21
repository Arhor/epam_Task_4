package by.epam.task4.service.sax;

public enum Elements {

	MEDICINS("Medicins"),
	ANTIBIOTIC("Antibiotic"),
	VITAMIN("Vitamin"),
	ANALGETIC("Analgetic"),
	VERSION("Version"),
	CERTIFICATE("Certificate"),
	PACK("Package"),
	DOSAGE("Dosage"),
	NAME("name"),
	CAS("CAS"),
	DRUG_BANK("DrugBank"),
	RECIPE("recipe"),
	SOLUTION("solution"),
	NARCOTIC("narcotic"),
	SIZE("size"),
	TRADE_NAME("tradeName"),
	PHARM("Pharm"),
	PRODUCER("Producer"),
	FORM("Form"),
	REGISTRED_BY("RegistredBy"),
	REGISTRATION_DATE("RegistrationDate"),
	EXPIRE_DATE("ExpireDate"),
	QUANTITY("Quantity"),
	PRICE("Price"),
	AMOUNT("Amount"),
	FREQUENCY("Frequency");
	
	
	private String value;
	private Elements(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}

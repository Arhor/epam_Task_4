package by.epam.task4.runner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.model.Antibiotic;
import by.epam.task4.model.Certificate;
import by.epam.task4.model.Dosage;
import by.epam.task4.model.Medicine;
import by.epam.task4.model.Pack;
import by.epam.task4.model.Version;
import by.epam.task4.service.XMLValidator;
import by.epam.task4.service.exception.ParserNotPresentedException;
import by.epam.task4.service.factory.MedicinsBuilderFactory;
import by.epam.task4.service.parsing.MedicinsAbstractBuilder;

public class Runner {

	private static final Logger LOG = LogManager.getLogger(Runner.class);
	
	public static void main(String[] args) {
//		String xml = "Medicins.xml";
//		String xsd = "Medicins.xsd";
//		
//		LOG.info(XMLValidator.validate(xml, xsd) + "\n");
//		
//		MedicinsBuilderFactory factory = new MedicinsBuilderFactory();
//		
//		MedicinsAbstractBuilder builder;
//		try {
//			builder = factory.getBuilder("stax");
//		} catch (ParserNotPresentedException e) {
//			LOG.error("Parser not presented exception: ", e);
//			LOG.warn("Parser not presented exception");
//			return;
//		}
//		
//		builder.buildSetMedicins(xml);
//		
//		Set<Medicine> medicins = builder.getMedicins();
//		
//		for (Medicine medicine : medicins) {
//			LOG.info("\n" + medicine + "\n");
//		}
		
		Medicine m_1 = new Antibiotic();
		Medicine m_2 = new Antibiotic();
		
		setMedicine(m_1);
		setMedicine(m_2);
		
		LOG.info("Medicine #1:\n" + m_1);
		LOG.info("Medicine #2:\n" + m_2);
		
		Version v_1 = new Version();
		Version v_2 = new Version();
		
		LOG.info("eqivalency: " + v_1.equals(v_2));
	}
	
	private static void setMedicine(Medicine medicine) {
    	medicine.setName("Test antibiotic");
    	medicine.setCas("12345-67-8");
    	medicine.setDrugBank("DB00000");
    	medicine.setPharm("Test Pharmacy");
    	((Antibiotic) medicine).setRecipe(true);
    	
    	Version version = new Version();
    	version.setTradeName("Test trade name");
    	version.setProducer("Test producer");
    	version.setForm("pills");
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Certificate certificate = new Certificate();
    	certificate.setRegistredBy("Test registration");
    	try {
    		certificate.setRegistrationDate(dateFormat.parse("2010-01-01"));
			certificate.setExpireDate(dateFormat.parse("2020-01-01"));
		} catch (ParseException e) {
			LOG.error("Date parsing exception", e);
		}
    	
    	Pack pack = new Pack();
    	pack.setQuantity(100);
    	pack.setPrice(200.00);
    	HashSet<Pack> packs = new HashSet<Pack>();
    	packs.add(pack);
    	
    	
    	Dosage dosage = new Dosage();
    	dosage.setAmount("Test amount");
    	dosage.setFrequency("Test frequency");
    	
    	version.setCertificate(certificate);
    	version.setPacks(packs);
    	version.setDosage(dosage);
    	medicine.addVersion(version);
	}
	
	private static void setVersion(Version version) {
    	version.setTradeName("Test trade name");
    	version.setProducer("Test producer");
    	version.setForm("pills");
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Certificate certificate = new Certificate();
    	certificate.setRegistredBy("Test registration");
    	try {
    		certificate.setRegistrationDate(dateFormat.parse("2010-01-01"));
			certificate.setExpireDate(dateFormat.parse("2020-01-01"));
		} catch (ParseException e) {
			LOG.error("Date parsing exception", e);
		}
    	
    	Pack pack = new Pack();
    	pack.setQuantity(100);
    	pack.setPrice(200.00);
    	HashSet<Pack> packs = new HashSet<Pack>();
    	packs.add(pack);
    	
    	
    	Dosage dosage = new Dosage();
    	dosage.setAmount("Test amount");
    	dosage.setFrequency("Test frequency");
    	
    	version.setCertificate(certificate);
    	version.setPacks(packs);
    	version.setDosage(dosage);
	}
}

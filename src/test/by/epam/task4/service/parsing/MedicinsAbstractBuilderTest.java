package test.by.epam.task4.service.parsing;

import org.testng.annotations.Test;

import by.epam.task4.exception.ParserNotPresentedException;
import by.epam.task4.model.Antibiotic;
import by.epam.task4.model.Certificate;
import by.epam.task4.model.Dosage;
import by.epam.task4.model.Medicine;
import by.epam.task4.model.Pack;
import by.epam.task4.model.Version;
import by.epam.task4.service.factory.MedicinsBuilderFactory;
import by.epam.task4.service.parsing.MedicinsAbstractBuilder;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class MedicinsAbstractBuilderTest {
	
	private static final Logger LOG = LogManager.getLogger(
			MedicinsAbstractBuilderTest.class);
	
	private static Set<Medicine> validMedicinsSet;
	
    @Test(dataProvider = "medicinsBuilders")
    public void BuildeSetMedicinsTest(String jaxp) {
    	MedicinsBuilderFactory factory = new MedicinsBuilderFactory();
    	try {
			MedicinsAbstractBuilder builder = factory.getBuilder(jaxp);
			builder.buildSetMedicins("validTest.xml");
			Set<Medicine> actualMedicinsSet = builder.getMedicins();
			Assert.assertEquals(actualMedicinsSet, validMedicinsSet);
		} catch (ParserNotPresentedException e) {
			Assert.fail("Parser not presented", e);
		}
    }

    @BeforeClass
    public void beforeClass() {
    	validMedicinsSet = new HashSet<Medicine>();
    	
    	Medicine medicine = new Antibiotic();
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
    	validMedicinsSet.add(medicine);
    }

    @AfterClass
    public void afterClass() {
    	validMedicinsSet.clear();
    	validMedicinsSet = null;
    }
    
    @DataProvider(name = "medicinsBuilders")
    public static Object[][] createData() {
    	return new Object[][] {
    		{"sax"}, {"dom"}, {"stax"}
    	};
    } 
}

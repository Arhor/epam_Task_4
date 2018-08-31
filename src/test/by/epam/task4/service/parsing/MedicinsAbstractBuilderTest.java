package test.by.epam.task4.service.parsing;

import org.testng.annotations.Test;

import by.epam.task4.exception.ParserNotPresentedException;
import by.epam.task4.model.*;
import by.epam.task4.service.factory.MedicinsBuilderFactory;
import by.epam.task4.service.parsing.MedicinsAbstractBuilder;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class MedicinsAbstractBuilderTest {
    
	private static MedicinsBuilderFactory factory;
	
	MedicinsAbstractBuilder builder;
	
    private static Set<Medicine> validMedicinsSet;
    
    @Test(dataProvider = "medicinsBuilders")
    public void BuildeSetMedicinsTest(String jaxp)
    		throws ParserNotPresentedException {
        MedicinsAbstractBuilder builder = factory.getBuilder(jaxp);
        builder.buildSetMedicins("validTest.xml");
        Set<Medicine> actualMedicinsSet = builder.getMedicins();
        Assert.assertEquals(actualMedicinsSet, validMedicinsSet);
    }
    
    @DataProvider(name = "medicinsBuilders")
    public static Object[][] createData() {
        return new Object[][] {
            {"sax"}, {"dom"}, {"stax"}
        };
    }

    @BeforeClass
    public void beforeClass() throws ParseException {
    	factory = new MedicinsBuilderFactory();
    	
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
        certificate.setRegistrationDate(dateFormat.parse("2010-01-01"));
        certificate.setExpireDate(dateFormat.parse("2020-01-01"));
        
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
}

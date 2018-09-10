package test.by.epam.task4.service.validation;

import org.testng.Assert;
import org.testng.annotations.Test;

import by.epam.task4.service.validation.XMLValidator;

public class XMLValidatorTest {
    
    public static final String VALID_XML = "validTest.xml";
    public static final String INVALID_XML = "invalidTest.xml";
    public static final String VALIDATION_XSD = "Medicins.xsd";

    @Test
    public void validatePositiveTest() {
        boolean actual = XMLValidator.validate(VALID_XML, VALIDATION_XSD);
        Assert.assertTrue(actual);
    }

    @Test
    public void validateNegativeTest() {
        boolean actual = XMLValidator.validate(INVALID_XML, VALIDATION_XSD);
        Assert.assertFalse(actual);
    }
}

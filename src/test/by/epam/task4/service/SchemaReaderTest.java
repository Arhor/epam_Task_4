package test.by.epam.task4.service;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import by.epam.task4.service.SchemaReader;

public class SchemaReaderTest {

    @Test(expectedExceptions = SAXException.class)
    public void getSchemaTest() throws SAXException {
	    String xsd = "fictive_file";
	    SchemaReader.getSchema(xsd);
    }
}

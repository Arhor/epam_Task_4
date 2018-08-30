package by.epam.task4.service.validation;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public abstract class SchemaReader {
	
	public static Schema getSchema(String xsd) throws SAXException {
		Schema schema = null;
		String lang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory sf = SchemaFactory.newInstance(lang);
		schema = sf.newSchema(new File(xsd));
		return schema;
	}

}

package by.epam.task4.service;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

public class ValidatorSAX {
	
	private static final Logger LOG = LogManager.getLogger(ValidatorSAX.class);

	public static boolean validate(String xml, String xsd) {
		Schema schema = null;
		String lang = XMLConstants.W3C_XML_SCHEMA_NS_URI;
		SchemaFactory sf = SchemaFactory.newInstance(lang);
		try {
			schema = sf.newSchema(new File(xsd));
			
//			SAXParserFactory spf = SAXParserFactory.newInstance();
//			spf.setSchema(schema);
//			SAXParser parser = spf.newSAXParser();
//			parser.parse(xml, new CustomErrorHandler());
			
			Validator validator = schema.newValidator();
			Source source = new StreamSource(xml);
			validator.validate(source);
			
			LOG.info(xml + " is valid\n");
			return true;
		} catch (IOException e) {
			LOG.error("I/O exception: ", e);
			return false;
		} catch (SAXException e) {
			LOG.error("SAX exception: ", e);
			return false;
		} /*catch(ParserConfigurationException e) {
			LOG.error("Parser configuration exception: ", e);
			return false;
		}*/
	}
}

package by.epam.task4.service.sax;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ParserSAX {

	private static final Logger LOG = LogManager.getLogger(ParserSAX.class);
	private XMLReader reader;
	
	public void parse(String xml, CustomXMLHandler handler) {
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
			reader.parse(xml);
		} catch (SAXException e) {
			LOG.error("SAX parser exception: ", e);
		} catch (IOException e) {
			LOG.error("I/O exception: ", e);
		}
	}
}

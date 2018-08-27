package by.epam.task4.service.parsing.sax;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.epam.task4.service.parsing.MedicinsAbstractBuilder;

public class MedicinsSAXBuilder extends MedicinsAbstractBuilder {
	
	private static final Logger LOG = LogManager.getLogger();

	private SAXParsingHandler handler;
	private XMLReader reader;
	
	public MedicinsSAXBuilder() {
		handler = new SAXParsingHandler();
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
		} catch (SAXException e) {
			LOG.error("SAX parser error: ", e);
		}
	}

	@Override
	public void buildSetMedicins(String xml) {
		try {
			reader.parse(xml);
		} catch (SAXException e) {
			LOG.error("SAX parser exception: ", e);
		} catch (IOException e) {
			LOG.error("I/O exception: ", e);
		}
		medicins = handler.getMedicins();
	}
}

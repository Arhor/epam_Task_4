package by.epam.task4.service.sax;

import java.io.IOException;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.epam.task4.model.Medicine;

public class MedicinsSAXBuilder {
	
	private static final Logger LOG = LogManager.getLogger(MedicinsSAXBuilder.class);

	private Set<Medicine> medicins;
	private CustomXMLHandler handler;
	private XMLReader reader;
	
	public MedicinsSAXBuilder() {
		handler = new CustomXMLHandler();
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(handler);
			
		} catch (SAXException e) {
			LOG.error("SAX parser error: ", e);
		}
	}

	public Set<Medicine> getMedicins() {
		return medicins;
	}

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

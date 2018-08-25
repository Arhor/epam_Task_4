package by.epam.task4.service.parsing.stax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.model.Medicine;
import by.epam.task4.service.factory.MedicineFactory;
import by.epam.task4.service.parsing.ElementsEnum;
import by.epam.task4.service.parsing.MedicinsAbstractBuilder;

public class MedicinsStAXBuilder extends MedicinsAbstractBuilder{
	
	private static final Logger LOG = LogManager.getLogger(MedicinsStAXBuilder.class);
	
	private XMLInputFactory inputFactory;
	
	public MedicinsStAXBuilder() {
		super();
		inputFactory = XMLInputFactory.newInstance();
	}

	@Override
	public void buildSetMedicins(String xml) {
		XMLStreamReader reader = null;
		FileInputStream fis = null;
        try {
        	fis = new FileInputStream(new File(xml));
        	reader = inputFactory.createXMLStreamReader(fis);
        	while (reader.hasNext()) {
        		int type = reader.next();
        		if (type == XMLStreamConstants.START_ELEMENT) {
        			String name = reader.getLocalName();
        			ElementsEnum currentElement = ElementsEnum.valueOf(name.toUpperCase());
        			switch (currentElement) {
        				case ANTIBIOTIC:
        				case ANALGETIC:
        				case VITAMIN:
        					Medicine medicine = buildMedicine(reader);
        					medicins.add(medicine);
        				default:
        					break;
        			}
        		}
        	}
        } catch (FileNotFoundException e) {
        	LOG.fatal(xml + " file not found", e);
        	throw new RuntimeException();
        } catch (XMLStreamException e) {
        	LOG.error("StAX parsing exception", e);
        } finally {
        	if (reader != null) {
        		try {
        			reader.close();
        		} catch (XMLStreamException e) {
        			LOG.error("XML stream exception", e);
        		}
        	}
        }
	}

	private Medicine buildMedicine(XMLStreamReader reader) {
		
		String name = reader.getLocalName();
		ElementsEnum currentElement = ElementsEnum.valueOf(name.toUpperCase());
		MedicineFactory mFactory = new MedicineFactory();
		Medicine medicine = mFactory.getMedicine(currentElement);
		
		
		return null;
	}

}

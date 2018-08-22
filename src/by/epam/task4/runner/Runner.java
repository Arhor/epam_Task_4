package by.epam.task4.runner;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.model.Medicine;
import by.epam.task4.service.ValidatorXML;
import by.epam.task4.service.parsing.dom.MedicinsDOMBuilder;
import by.epam.task4.service.parsing.sax.MedicinsSAXBuilder;

public class Runner {

	private static final Logger LOG = LogManager.getLogger(Runner.class);
	
	public static void main(String[] args) {
		String xml = "Medicins.xml";
		String xsd = "Medicins.xsd";
		
		LOG.info(ValidatorXML.validate(xml, xsd) + "\n");
		
		MedicinsSAXBuilder saxBuilder = new MedicinsSAXBuilder();
		saxBuilder.buildSetMedicins(xml);
		
		MedicinsDOMBuilder domBuilder = new MedicinsDOMBuilder();
		domBuilder.buildSetMedicins(xml);
		
		Set<Medicine> medicins = saxBuilder.getMedicins();
//		Set<Medicine> medicins = domBuilder.getMedicins();
		
		for (Medicine medicine : medicins) {
			LOG.info(medicine + "\n\n");
		}
	}
}

package by.epam.task4.runner;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.model.Medicine;
import by.epam.task4.service.ValidatorXML;
import by.epam.task4.service.sax.MedicinsSAXBuilder;

public class Runner {

	private static final Logger LOG = LogManager.getLogger(Runner.class);
	
	public static void main(String[] args) {
		LOG.info(ValidatorXML.validate("Medicins.xml", "Medicins.xsd") + "\n");
		
		MedicinsSAXBuilder saxBuilder = new MedicinsSAXBuilder();
		saxBuilder.buildSetMedicins("Medicins.xml");
		
		Set<Medicine> medicins = saxBuilder.getMedicins();
		
		for (Medicine medicine : medicins) {
			LOG.info(medicine + "\n\n");
		}
	}
}

package by.epam.task4.runner;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.model.Medicine;
import by.epam.task4.service.XMLValidator;
import by.epam.task4.service.exception.ParserNotPresentedException;
import by.epam.task4.service.factory.MedicinsBuilderFactory;
import by.epam.task4.service.parsing.MedicinsAbstractBuilder;

public class Runner {

	private static final Logger LOG = LogManager.getLogger(Runner.class);
	
	public static void main(String[] args) {
		String xml = "Medicins.xml";
		String xsd = "Medicins.xsd";
		
		LOG.info(XMLValidator.validate(xml, xsd) + "\n");
		
		MedicinsBuilderFactory factory = new MedicinsBuilderFactory();
		
		MedicinsAbstractBuilder builder;
		try {
			builder = factory.getBuilder("stax");
		} catch (ParserNotPresentedException e) {
			LOG.error("Parser not presented exception: ", e);
			LOG.warn("Parser not presented exception");
			return;
		}
		
		builder.buildSetMedicins(xml);
		
		Set<Medicine> medicins = builder.getMedicins();
		
		for (Medicine medicine : medicins) {
			LOG.info("\n" + medicine + "\n");
		}
	}
}

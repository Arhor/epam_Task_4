package by.epam.task4.service.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.service.parsing.MedicinsAbstractBuilder;
import by.epam.task4.service.parsing.dom.MedicinsDOMBuilder;
import by.epam.task4.service.parsing.sax.MedicinsSAXBuilder;
import by.epam.task4.service.parsing.stax.MedicinsStAXBuilder;

public class MedicinsBuilderFactory {
	
	private static final Logger LOG = LogManager.getLogger(MedicinsBuilderFactory.class);
	
	private static final String SAX = "SAX";
	private static final String DOM = "DOM";
	private static final String STAX = "STAX";

	public MedicinsAbstractBuilder getBuilder(String name) {
		MedicinsAbstractBuilder builder = null;
		switch (name.toUpperCase()) {
			case SAX:
				builder = new MedicinsSAXBuilder();
				break;
			case DOM:
				builder = new MedicinsDOMBuilder();
				break;
			case STAX:
				builder = new MedicinsStAXBuilder();
				break;
			default:
				break;
		}
		return builder;
	}
}

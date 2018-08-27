package by.epam.task4.service.factory;

import by.epam.task4.service.exception.ParserNotPresentedException;
import by.epam.task4.service.parsing.MedicinsAbstractBuilder;
import by.epam.task4.service.parsing.dom.MedicinsDOMBuilder;
import by.epam.task4.service.parsing.sax.MedicinsSAXBuilder;
import by.epam.task4.service.parsing.stax.MedicinsStAXBuilder;

public class MedicinsBuilderFactory {
		
	private static final String SAX = "SAX";
	private static final String DOM = "DOM";
	private static final String STAX = "STAX";

	public MedicinsAbstractBuilder getBuilder(String name)
			throws ParserNotPresentedException {
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
				throw new ParserNotPresentedException(
						"There is not such parser: " + name);
		}
		return builder;
	}
}

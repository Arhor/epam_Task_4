package by.epam.task4.runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.task4.service.ValidatorXML;
import by.epam.task4.service.sax.CustomXMLHandler;
import by.epam.task4.service.sax.ParserSAX;

public class Runner {

	private static final Logger LOG = LogManager.getLogger(Runner.class);
	
	public static void main(String[] args) {
		LOG.info(ValidatorXML.validate("Medicins.xml", "Medicins.xsd") + "\n");
		
		ParserSAX parserSAX = new ParserSAX();
		parserSAX.parse("Medicins.xml", new CustomXMLHandler());
	}
}

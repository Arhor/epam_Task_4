package by.epam.task4.service.sax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class CustomXMLHandler extends DefaultHandler {

	private static final Logger LOG = LogManager.getLogger(CustomXMLHandler.class);
	
	@Override
	public void startDocument() {
		LOG.info("\nParsing started\n");
	}
	
	@Override
	public void endDocument() {
		LOG.info("\nParsing finished\n");
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attrs) {
		String line = "<" + localName;
		for (int i = 0; i < attrs.getLength(); i++) {
			line += " " + attrs.getLocalName(i) + " = " + attrs.getValue(i);
		}
		line += ">";
		LOG.info(line.trim());
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) {
		LOG.info("</" + localName + ">");
	}
	
	@Override
	public void characters(char[] ch, int start, int length) {
		LOG.info(new String(ch, start, length));
	}
	
	@Override
	public void warning(SAXParseException e) {
		LOG.warn(getLineAddress(e), e);
	}
	
	@Override
	public void error(SAXParseException e) {
		LOG.error(getLineAddress(e), e);
	}
	
	@Override
	public void fatalError(SAXParseException e) {
		LOG.fatal(getLineAddress(e), e);
	}
	
	public String getLineAddress(SAXParseException e) {
		return e.getLineNumber() + ":" + e.getColumnNumber();
	}
}

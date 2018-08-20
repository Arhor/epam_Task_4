package by.epam.task4.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class CustomErrorHandler extends DefaultHandler {

	private static final Logger LOG = LogManager.getLogger(CustomErrorHandler.class);
	
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

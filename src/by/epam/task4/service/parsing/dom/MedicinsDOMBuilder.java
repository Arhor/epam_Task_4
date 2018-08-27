package by.epam.task4.service.parsing.dom;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.w3c.dom.*;

import org.xml.sax.SAXException;

import by.epam.task4.model.*;
import by.epam.task4.service.factory.MedicineFactory;
import by.epam.task4.service.parsing.AttributesEnum;
import by.epam.task4.service.parsing.ElementsEnum;
import by.epam.task4.service.parsing.MedicinsAbstractBuilder;

public class MedicinsDOMBuilder extends MedicinsAbstractBuilder {
	
	private static final Logger LOG = LogManager.getLogger();

	private DocumentBuilder docBuilder;
	private MedicineFactory mFactory;
	private DateFormat dateFormat;
	
	private Version currentVersion;
	private Certificate currentCertificate;
	
	public MedicinsDOMBuilder() {
		medicins = new HashSet<Medicine>();
		mFactory = new MedicineFactory();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOG.error("Parser configuration exception: ", e);
		}
	}

	@Override
	public void buildSetMedicins(String xml) {
		Document document = null;
		try {
			document = docBuilder.parse(xml);
			Element root = document.getDocumentElement();
			NodeList medicinsList = root.getChildNodes();
			for (int i = 0; i < medicinsList.getLength(); i++) {
				Node node = medicinsList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element medicineElement = (Element) node;
					Medicine medicine = buildMedicine(medicineElement);
					medicins.add(medicine);
				}
			}
		} catch (IOException e) {
			LOG.error("I/O exception", e);
		} catch (SAXException e) {
			LOG.error("SAX pasring exception: ", e);
		}
	}
	
	private Medicine buildMedicine(Element medicineElement) {
		Medicine medicine = mFactory.getMedicine(
				ElementsEnum.valueOf(
						medicineElement.getTagName().toUpperCase()));
		
		// setting Medicine attributes
		NamedNodeMap attributes = medicineElement.getAttributes();
		for (int i = 0; i < attributes.getLength() ; i++) {
			Attr attribute = (Attr) attributes.item(i);
			String name = attribute.getName();
			String value = attribute.getValue();
			AttributesEnum currentAttribute = AttributesEnum.valueOf(
					name.toUpperCase());
			switch (currentAttribute) {
				case NAME:
					medicine.setName(value);
					break;
				case CAS:
					medicine.setCas(value);
					break;
				case DRUG_BANK:
					medicine.setDrugBank(value);
					break;
				case RECIPE:
					boolean recipe = Boolean.parseBoolean(value);
					((Antibiotic)medicine).setRecipe(recipe);
					break;
				case SOLUTION:
					((Vitamin)medicine).setSolution(value);
					break;
				case NARCOTIC:
					boolean narcotic = Boolean.parseBoolean(value);
					((Analgetic)medicine).setNarcotic(narcotic);
					break;
				default:
					break;
			}
		}
		
		// setting Medicine 'pharm' field
		Element pharm = (Element) medicineElement.getElementsByTagName(
				ElementsEnum.PHARM.getValue()).item(0);
		medicine.setPharm(pharm.getTextContent());
		
		// setting 'versions' field
		NodeList versions = medicineElement.getElementsByTagName(
				ElementsEnum.VERSION.getValue());
		for (int i = 0; i < versions.getLength(); i++) {
			Element versionElement = (Element) versions.item(i);
			currentVersion = new Version();
			
			// setting 'trade name' field
			currentVersion.setTradeName(versionElement.getAttribute(
					AttributesEnum.TRADE_NAME.getValue()));
			
			// setting 'producer' field
			currentVersion.setProducer(versionElement.getElementsByTagName(
					ElementsEnum.PRODUCER.getValue()).item(0).getTextContent());
			
			// setting 'certificate' field
			Element certificateElement = 
					(Element) versionElement.getElementsByTagName(
							ElementsEnum.CERTIFICATE.getValue()).item(0);
			currentCertificate = new Certificate();
			NodeList certificateFields = certificateElement.getChildNodes();
			for (int j = 0; j < certificateFields.getLength(); j++) {
				Node certField = certificateFields.item(j);
				if (certField.getNodeType() == Node.ELEMENT_NODE) {
					ElementsEnum currentField = 
							ElementsEnum.valueOf(
									((Element)certField).getTagName().toUpperCase());
					switch (currentField) {
						case REGISTRED_BY:
							currentCertificate.setRegistredBy(
									certField.getTextContent());
							break;
						case REGISTRATION_DATE:
							try {
								Date date = dateFormat.parse(
										certField.getTextContent());
								currentCertificate.setRegistrationDate(date);
							} catch (ParseException e) {
								LOG.error("Date parsing exception: ", e);
							}
							break;
						case EXPIRE_DATE:
							try {
								Date date = dateFormat.parse(
										certField.getTextContent());
								currentCertificate.setExpireDate(date);
							} catch (ParseException e) {
								LOG.error("Date parsing exception: ", e);
							}
							break;
						default:
							break;
					}
				}
			}
			currentVersion.setCertificate(currentCertificate);
			
			// setting 'packs' field
			NodeList packs = versionElement.getElementsByTagName(
					ElementsEnum.PACK.getValue());
			for (int k = 0; k < packs.getLength(); k++) {
				Pack currentPack = new Pack();
				Element packElement = (Element) packs.item(k);
				
				if (packElement.hasAttributes()) {
					Attr size = packElement.getAttributeNode(
							AttributesEnum.SIZE.getValue());
					currentPack.setSize(size.getValue());
				}
				
				NodeList packFields = packElement.getChildNodes();
				for (int n = 0; n < packFields.getLength(); n++) {
					Node packField = packFields.item(n);
					if (packField.getNodeType() == Node.ELEMENT_NODE) {
						ElementsEnum currentField = ElementsEnum.valueOf(
								((Element)packField).getTagName().toUpperCase());
						switch (currentField) {
							case QUANTITY:
								currentPack.setQuantity(
										Integer.parseInt(packField.getTextContent()));
								break;
							case PRICE:
								currentPack.setPrice(
										Double.parseDouble(packField.getTextContent()));
								break;
							default:
								break;
						}
					}
				}
				
				currentVersion.addPack(currentPack);
			}
			
			// setting 'dosage' field
			Element dosageElement =
					(Element) versionElement.getElementsByTagName(
							ElementsEnum.DOSAGE.getValue()).item(0);
			Dosage currentDosage = new Dosage();
			NodeList dosageFields = dosageElement.getChildNodes();
			for (int k = 0; k < dosageFields.getLength(); k++) {
				Node dosageField = dosageFields.item(k);
				if (dosageField.getNodeType() == Node.ELEMENT_NODE) {
					ElementsEnum currentField =
							ElementsEnum.valueOf(
									((Element)dosageField).getTagName().toUpperCase());
					switch (currentField) {
						case AMOUNT:
							currentDosage.setAmount(
									dosageField.getTextContent());
							break;
						case FREQUENCY:
							currentDosage.setFrequency(
									dosageField.getTextContent());
							break;
						default:
							break;
					}
				}
			}
			currentVersion.setDosage(currentDosage);
			
			medicine.addVersion(currentVersion);
		}
		
		return medicine;
	}
}

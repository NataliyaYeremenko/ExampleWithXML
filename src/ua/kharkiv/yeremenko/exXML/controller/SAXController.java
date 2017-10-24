package ua.kharkiv.yeremenko.exXML.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ua.kharkiv.yeremenko.exXML.constants.Constants;
import ua.kharkiv.yeremenko.exXML.constants.XML;
import ua.kharkiv.yeremenko.exXML.entity.Planes;
import ua.kharkiv.yeremenko.exXML.entity.Plane;
import ua.kharkiv.yeremenko.exXML.entity.PlaneChars;
import ua.kharkiv.yeremenko.exXML.entity.WarChars;
import ua.kharkiv.yeremenko.exXML.entity.Parameters;

/**
 * Controller for SAX parser.
 */
public class SAXController extends DefaultHandler {
	
	private String xmlFileName;

	// current element name holder
	private String currentElement;

	private static boolean check;

	// main container
	private Planes planes;
	
	private Plane plane;
	
	private PlaneChars planeChars;

	private WarChars warChars;

	private Parameters parameters;

	public SAXController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	/**
	 * Parses XML document.
	 * 
	 * @param validate
	 *            If true validate XML document against its XML schema. With
	 *            this parameter it is possible make parser validating.
	 */
	public void parse(boolean validate) 
			throws ParserConfigurationException, SAXException, IOException {
		
		// obtain sax parser factory
		SAXParserFactory factory = SAXParserFactory.newInstance();

		// XML document contains namespaces
		factory.setNamespaceAware(true);
		
		// set validation
		if (validate) {
			factory.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);
			factory.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
		}

		SAXParser parser = factory.newSAXParser();
		parser.parse(xmlFileName, this);
	}

	// ///////////////////////////////////////////////////////////
	// ERROR HANDLER IMPLEMENTATION
	// ///////////////////////////////////////////////////////////

	@Override
	public void error(org.xml.sax.SAXParseException e) throws SAXException {
		// if XML document not valid just throw exception
		throw e;
	};

	public Planes getPlanes() {
		return planes;
	}

	// ///////////////////////////////////////////////////////////
	// CONTENT HANDLER IMPLEMENTATION
	// ///////////////////////////////////////////////////////////


	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		currentElement = localName;

		if (XML.PLANES.equalsTo(currentElement)) {
			planes = new Planes();
			return;
		}

		if (XML.PLANE.equalsTo(currentElement)) {
			plane = new Plane();
			return;
		}

		if (XML.CHARS.equalsTo(currentElement)) {
			planeChars = new PlaneChars();
			warChars = new WarChars();
			return;
		}

		if (XML.PARAMETERS.equalsTo(currentElement)) {
			parameters = new Parameters();
			return;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		String elementText = new String(ch, start, length).trim();

		// return if content is empty
		if (elementText.isEmpty()) { 
			return;
		}

		if (XML.MODEL.equalsTo(currentElement)) {
			plane.setModel(elementText);
			return;
		}

		if (XML.ORIGIN.equalsTo(currentElement)) {
			plane.setOrigin(elementText);
			return;
		}

		if (XML.PLANE_TYPE.equalsTo(currentElement)) {
			planeChars.setType(elementText);
			warChars.setType(elementText);
			return;
		}

		if (XML.SIZE.equalsTo(currentElement)) {
			planeChars.setSize(Integer.valueOf(elementText));
			warChars.setSize(Integer.valueOf(elementText));
			return;
		}

		if (XML.AMMUNITION.equalsTo(currentElement)) {
			planeChars.setAmmunition(Boolean.valueOf(elementText));
			warChars.setAmmunition(Boolean.valueOf(elementText));
			return;
		}

		if (XML.RADAR.equalsTo(currentElement)) {
			planeChars.setRadar(Boolean.valueOf(elementText));
			warChars.setRadar(Boolean.valueOf(elementText));
			return;
		}

		if (XML.ROCKETS.equalsTo(currentElement)){
			check = true;
			warChars.setRockets(Integer.valueOf(elementText));
			return;
		}

		if (XML.PRICE.equalsTo(currentElement)) {
			plane.setPrice(elementText);
			return;
		}

		if (XML.LENGTH.equalsTo(currentElement)) {
			parameters.setLength(Integer.valueOf(elementText));
			return;
		}

		if (XML.WIDTH.equalsTo(currentElement)) {
			parameters.setWidth(Integer.valueOf(elementText));
			return;
		}

		if (XML.HEIGHT.equalsTo(currentElement)) {
			parameters.setHeight(Integer.valueOf(elementText));
			return;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (XML.CHARS.equalsTo(localName)) {
			if (check) {
				plane.setChars(warChars);
				check = false;
			}
			else plane.setChars(planeChars);
			return;
		}

		if (XML.PARAMETERS.equalsTo(localName)) {
			plane.setParameters(parameters);
			return;
		}

		if (XML.PLANE.equalsTo(localName)) {
			planes.getPlanes().add(plane);
			return;
		}
	}

	public static void main(String[] args) throws Exception {

		// try to parse valid XML file (success)
		SAXController saxContr = new SAXController(Constants.VALID_XML_FILE);
		
		// do parse with validation on (success)
		saxContr.parse(true);
		
		// obtain container
		Planes planes = saxContr.getPlanes();

		// we have Test object at this point:
		System.out.println("====================================");
		System.out.print("Here is the planes: \n" + planes);
		System.out.println("====================================");

		// now try to parse NOT valid XML (failed)
		saxContr = new SAXController(Constants.INVALID_XML_FILE);
		try {			
			// do parse with validation on (failed)
			saxContr.parse(true);
		} catch (Exception ex) {
			System.err.println("====================================");
			System.err.println("Validation is failed:\n" + ex.getMessage());
			System.err
					.println("Try to print planes object:" + saxContr.getPlanes());
			System.err.println("====================================");
		}

		// and now try to parse NOT valid XML with validation off (success)
		saxContr.parse(false);		

		// we have Planes object at this point:
		System.out.println("====================================");
		System.out.print("Here is the planes: \n" + saxContr.getPlanes());
		System.out.println("====================================");
	}
}
package ua.kharkiv.yeremenko.exXML.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import ua.kharkiv.yeremenko.exXML.constants.XML;
import ua.kharkiv.yeremenko.exXML.constants.Constants;
import ua.kharkiv.yeremenko.exXML.entity.Planes;
import ua.kharkiv.yeremenko.exXML.entity.Plane;
import ua.kharkiv.yeremenko.exXML.entity.PlaneChars;
import ua.kharkiv.yeremenko.exXML.entity.WarChars;
import ua.kharkiv.yeremenko.exXML.entity.Parameters;

/**
 * Controller for DOM parser.
 */
public class DOMController {

	private static ArrayList<Integer> rockets = new ArrayList<>();

	private String xmlFileName;

	// main container
	private Planes planes;

	public DOMController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Planes getPlanes() {
		return planes;
	}

	/**
	 * Parses XML document.
	 * 
	 * @param validate
	 *            If true validate XML document against its XML schema.
	 */
	public void parse(boolean validate) 
			throws ParserConfigurationException, SAXException, IOException {

		// obtain DOM parser 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// set properties for Factory
		
		// XML document contains namespaces
		dbf.setNamespaceAware(true);
		
		// make parser validating
		if (validate) {
			// turn validation on
			dbf.setFeature(Constants.FEATURE_TURN_VALIDATION_ON, true);
			
			// turn on xsd validation 
			dbf.setFeature(Constants.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
		}

		DocumentBuilder db = dbf.newDocumentBuilder();

		// set error handler
		db.setErrorHandler(new DefaultHandler() {
			@Override
			public void error(SAXParseException e) throws SAXException {
				// throw exception if XML document is NOT valid
				throw e;
			}
		});

		// parse XML document
		Document document = db.parse(xmlFileName);
		
		// get root element
		Element root = document.getDocumentElement();

		// create container
		planes = new Planes();

		// obtain plane nodes
		NodeList planeNodes = root
				.getElementsByTagName(XML.PLANE.value());

		// process questions nodes
		for (int j = 0; j < planeNodes.getLength(); j++) {
			Plane plane = getPlane(planeNodes.item(j));
			// add plane to container
			planes.getPlanes().add(plane);
		}
	}

	/**
	 * Extracts plane object from the plane XML node.
	 * 
	 * @param pNode
	 *            Plane node.
	 * @return Plane object.
	 */
	private Plane getPlane(Node pNode) {
		Plane plane = new Plane();
		Element pElement = (Element) pNode;

		// process model
		Node mNode = pElement.getElementsByTagName(XML.MODEL.value())
				.item(0);
		// set model
		plane.setModel(mNode.getTextContent());

		// process origin
		Node orNode = pElement.getElementsByTagName(XML.ORIGIN.value())
				.item(0);
		// set origin text
		plane.setOrigin(orNode.getTextContent());

		// process chars
		NodeList chNodeList = pElement.getElementsByTagName(XML.CHARS.value());
		for (int j = 0; j < chNodeList.getLength(); j++) {
			PlaneChars planeChars = getPlaneChars(chNodeList.item(j));
			// add chars
			plane.setChars(planeChars);
		}

		// process parameters
		NodeList paramNodeList = pElement.getElementsByTagName(XML.PARAMETERS.value());
		for (int j = 0; j < paramNodeList.getLength(); j++) {
			Parameters parameters = getParameters(paramNodeList.item(j));

			// add parameters
			plane.setParameters(parameters);
		}

		// process price
		Node prNode = pElement.getElementsByTagName(XML.PRICE.value())
				.item(0);
		// set price text
		plane.setPrice(prNode.getTextContent());

		return plane;
	}

	/**
	 * Extracts answer object from the answer XML node.
	 *
	 * @param chNode
	 *            PlaneChars node.
	 * @return PlaneChars object.
	 */
	private PlaneChars getPlaneChars(Node chNode) {
		WarChars warChars = new WarChars();
		PlaneChars planeChars = new PlaneChars();
		Element chElement = (Element) chNode;

		// process planeType
		Node ptNode = chElement.getElementsByTagName(XML.PLANE_TYPE.value())
				.item(0);
		// set planeType
		warChars.setType(ptNode.getTextContent());
		planeChars.setType(ptNode.getTextContent());

		// process size
		Node sNode = chElement.getElementsByTagName(XML.SIZE.value())
				.item(0);
		// set size
		warChars.setSize(Integer.valueOf(sNode.getTextContent()));
		planeChars.setSize(Integer.valueOf(sNode.getTextContent()));

		// process ammunition
		Node amNode = chElement.getElementsByTagName(XML.AMMUNITION.value())
				.item(0);
		// set ammunition
		warChars.setAmmunition(Boolean.valueOf(amNode.getTextContent()));
		planeChars.setAmmunition(Boolean.valueOf(amNode.getTextContent()));

		// process radar
		Node rNode = chElement.getElementsByTagName(XML.RADAR.value())
				.item(0);
		// set radar
		warChars.setRadar(Boolean.valueOf(rNode.getTextContent()));
		planeChars.setRadar(Boolean.valueOf(rNode.getTextContent()));

		// process rockets
		if (warChars.isAmmunition()) {
			Node rocNode = chElement.getElementsByTagName(XML.ROCKETS.value())
					.item(0);
			// set rockets
			warChars.setRockets(Integer.valueOf(rocNode.getTextContent()));
			rockets.add(Integer.valueOf(rocNode.getTextContent()));
			return warChars;
		}
		else return planeChars;
	}


	/**
	 * Extracts answer object from the answer XML node.
	 *
	 * @param paramNode
	 *            Parameters node.
	 * @return Parameters object.
	 */
	private Parameters getParameters(Node paramNode) {
		Parameters parameters = new Parameters();
		Element paramElement = (Element) paramNode;

		// process length
		Node lenNode = paramElement.getElementsByTagName(XML.LENGTH.value())
				.item(0);
		// set length
		parameters.setLength(Integer.valueOf(lenNode.getTextContent()));

		// process width
		Node widNode = paramElement.getElementsByTagName(XML.WIDTH.value())
				.item(0);
		// set width
		parameters.setWidth(Integer.valueOf(widNode.getTextContent()));

		// process height
		Node hNode = paramElement.getElementsByTagName(XML.HEIGHT.value())
				.item(0);
		// set height
		parameters.setHeight(Integer.valueOf(hNode.getTextContent()));

		return parameters;
	}

	// //////////////////////////////////////////////////////
	// Static util methods
	// //////////////////////////////////////////////////////

	/**
	 * Creates and returns DOM of the Planes container.
	 * 
	 * @param planes
	 *            Planes object.
	 * @throws ParserConfigurationException 
	 */
	public static Document getDocument(Planes planes) throws ParserConfigurationException {
	
		// obtain DOM parser
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// set properties for Factory

		// XML document contains namespaces
		dbf.setNamespaceAware(true);

		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();

		// create root element
		Element psElement = document.createElement(XML.PLANES.value());

		// add root element
		document.appendChild(psElement);

		int rockIndex = 0;
		// add plane elements
		for (Plane plane : planes.getPlanes()) {


			// add plane
			Element pElement = document.createElement(XML.PLANE.value());
			psElement.appendChild(pElement);

			// add model
			Element mElement =
					document.createElement(XML.MODEL.value());
			mElement.setTextContent(plane.getModel());
			pElement.appendChild(mElement);

			// add origin
			Element orElement =
					document.createElement(XML.ORIGIN.value());
			orElement.setTextContent(plane.getOrigin());
			pElement.appendChild(orElement);

			// add planeChars
			PlaneChars planeChars = plane.getChars();
			Element pChElement = document.createElement(XML.CHARS.value());
			Element pTElement = document.createElement(XML.PLANE_TYPE.value());
			pTElement.setTextContent(String.valueOf(planeChars.getType()));
			pChElement.appendChild(pTElement);
			Element sElement = document.createElement(XML.SIZE.value());
			sElement.setTextContent(String.valueOf(planeChars.getSize()));
			pChElement.appendChild(sElement);
			Element amElement = document.createElement(XML.AMMUNITION.value());
			amElement.setTextContent(String.valueOf(planeChars.isAmmunition()));
			pChElement.appendChild(amElement);
			Element radElement = document.createElement(XML.RADAR.value());
			radElement.setTextContent(String.valueOf(planeChars.isRadar()));
			pChElement.appendChild(radElement);
			if (planeChars.isAmmunition()){
				Element rockElement = document.createElement(XML.ROCKETS.value());
				rockElement.setTextContent(String.valueOf(rockets.get(rockIndex)));
				rockIndex++;
				pChElement.appendChild(rockElement);
			}
			pElement.appendChild(pChElement);

			// add parameters
			Parameters parameters = plane.getParameters();
			Element paramElement = document.createElement(XML.PARAMETERS.value());
			Element lenElement = document.createElement(XML.LENGTH.value());
			lenElement.setTextContent(String.valueOf(parameters.getLength()));
			paramElement.appendChild(lenElement);
			Element widElement = document.createElement(XML.WIDTH.value());
			widElement.setTextContent(String.valueOf(parameters.getWidth()));
			paramElement.appendChild(widElement);
			Element hElement = document.createElement(XML.HEIGHT.value());
			hElement.setTextContent(String.valueOf(parameters.getHeight()));
			paramElement.appendChild(hElement);
			pElement.appendChild(paramElement);

			// add price
			Element prElement =
					document.createElement(XML.PRICE.value());
			prElement.setTextContent(plane.getPrice());
			pElement.appendChild(prElement);
		}

		return document;		
	}
	
	/**
	 * Saves Planes object to XML file.
	 * 
	 * @param planes
	 *            Planes object to be saved.
	 * @param xmlFileName
	 *            Output XML file name.
	 */
	public static void saveToXML(Planes planes, String xmlFileName)
			throws ParserConfigurationException, TransformerException {		
		// Test -> DOM -> XML
		saveToXML(getDocument(planes), xmlFileName);
	}
	
	/**
	 * Save DOM to XML.
	 * 
	 * @param document
	 *            DOM to be saved.
	 * @param xmlFileName
	 *            Output XML file name.
	 */
	public static void saveToXML(Document document, String xmlFileName) 
			throws TransformerException {
		
		StreamResult result = new StreamResult(new File(xmlFileName));
		
		// set up transformation
		TransformerFactory tf = TransformerFactory.newInstance();
		javax.xml.transform.Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");			
		
		// run transformation
		t.transform(new DOMSource(document), result);
	}

	public static void main(String[] args) throws Exception {

		// try to parse NOT valid XML document with validation on (failed)
		DOMController domContr = new DOMController(Constants.INVALID_XML_FILE);
		try {
			// parse with validation (failed)
			domContr.parse(true);
		} catch (SAXException ex) {
			System.err.println("====================================");
			System.err.println("XML not valid");
			System.err.println("Planes object --> " + domContr.getPlanes());
			System.err.println("====================================");
		}

		// try to parse NOT valid XML document with validation off (success)
		domContr.parse(false);

		// we have Planes object at this point:
		System.out.println("====================================");
		System.out.print("Here is the planes: \n" + domContr.getPlanes());
		System.out.println("====================================");

		// save planes in XML file
		Planes planes = domContr.getPlanes();
		DOMController.saveToXML(planes, Constants.INVALID_XML_FILE + ".dom-result.xml");
	}
}

package ua.kharkiv.yeremenko.exXML.controller;

import org.xml.sax.helpers.DefaultHandler;
import ua.kharkiv.yeremenko.exXML.constants.Constants;
import ua.kharkiv.yeremenko.exXML.constants.XML;
import ua.kharkiv.yeremenko.exXML.entity.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

import java.io.IOException;


public class STAXController extends DefaultHandler {
    private String xmlFileName;

    // main container
    private Planes planes;

    public Planes getPlanes() {
        return planes;
    }

    public STAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    /**
     * Parses XML document with StAX (based on event reader). There is no validation during the
     * parsing.
     */
    public void parse() throws ParserConfigurationException, SAXException,
            IOException, XMLStreamException {

        Plane plane = null;
        PlaneChars planeChars = null;
        WarChars warChars = null;
        Parameters parameters = null;
        boolean check = false;


        // current element name holder
        String currentElement = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();

        factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

        XMLEventReader reader = factory.createXMLEventReader(
                new StreamSource(xmlFileName));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();

            // skip any empty content
            if (event.isCharacters() && event.asCharacters().isWhiteSpace()) {
                continue;
            }

            // handler for start tags
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                currentElement = startElement.getName().getLocalPart();

                if (XML.PLANES.equalsTo(currentElement)) {
                    planes = new Planes();
                    continue;
                }

                if (XML.PLANE.equalsTo(currentElement)) {
                    plane = new Plane();
                    planeChars = new PlaneChars();
                    continue;
                }

                if (XML.CHARS.equalsTo(currentElement)) {
                    planeChars = new PlaneChars();
                    warChars = new WarChars();
                    continue;
                }

                if (XML.PARAMETERS.equalsTo(currentElement)) {
                    parameters = new Parameters();
                    continue;
                }
            }

            // handler for contents
            if (event.isCharacters()) {
                Characters characters = event.asCharacters();

                if (XML.MODEL.equalsTo(currentElement)) {
                    plane.setModel(characters.getData());
                    continue;
                }

                if (XML.ORIGIN.equalsTo(currentElement)) {
                    plane.setOrigin(characters.getData());
                    continue;
                }

                if (XML.PLANE_TYPE.equalsTo(currentElement)) {
                    planeChars.setType(characters.getData());
                    warChars.setType(characters.getData());
                    continue;
                }

                if (XML.SIZE.equalsTo(currentElement)) {
                    planeChars.setSize(Integer.valueOf(characters.getData()));
                    warChars.setSize(Integer.valueOf(characters.getData()));
                    continue;
                }

                if (XML.AMMUNITION.equalsTo(currentElement)) {
                    planeChars.setAmmunition(Boolean.valueOf(characters.getData()));
                    warChars.setAmmunition(Boolean.valueOf(characters.getData()));
                    continue;
                }

                if (XML.RADAR.equalsTo(currentElement)) {
                    planeChars.setRadar(Boolean.valueOf(characters.getData()));
                    warChars.setRadar(Boolean.valueOf(characters.getData()));
                    continue;
                }

                if (XML.ROCKETS.equalsTo(currentElement)){
                    check = true;
                    warChars.setRockets(Integer.valueOf(characters.getData()));
                }

                if (XML.PRICE.equalsTo(currentElement)) {
                    plane.setPrice(characters.getData());
                    continue;
                }

                if (XML.LENGTH.equalsTo(currentElement)) {
                    parameters.setLength(Integer.valueOf(characters.getData()));
                    continue;
                }

                if (XML.WIDTH.equalsTo(currentElement)) {
                    parameters.setWidth(Integer.valueOf(characters.getData()));
                    continue;
                }

                if (XML.HEIGHT.equalsTo(currentElement)) {
                    parameters.setHeight(Integer.valueOf(characters.getData()));
                    continue;
                }
            }

            // handler for end tags
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                String localName = endElement.getName().getLocalPart();

                if (XML.CHARS.equalsTo(localName)) {
                    if (check) {
                        plane.setChars(warChars);
                        check = false;
                    }
                    else plane.setChars(planeChars);
                    continue;
                }

                if (XML.PARAMETERS.equalsTo(localName)) {
                    plane.setParameters(parameters);
                    continue;
                }

                if (XML.PLANE.equalsTo(localName)) {
                    planes.getPlanes().add(plane);
                    continue;
                }
            }
        }
        reader.close();
    }

    public static void main(String[] args) throws Exception {

        // try to parse (valid) XML file (success)
        STAXController staxContr = new STAXController(Constants.VALID_XML_FILE);
        staxContr.parse(); // <-- do parse (success)

        // obtain container
        Planes planes = staxContr.getPlanes();

        // we have Test object at this point:
        System.out.println("====================================");
        System.out.print("Here is the planes: \n" + planes);
        System.out.println("====================================");
    }


}

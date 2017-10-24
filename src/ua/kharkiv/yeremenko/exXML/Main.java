package ua.kharkiv.yeremenko.exXML;

import ua.kharkiv.yeremenko.exXML.controller.DOMController;
import ua.kharkiv.yeremenko.exXML.controller.SAXController;
import ua.kharkiv.yeremenko.exXML.controller.STAXController;
import ua.kharkiv.yeremenko.exXML.entity.Planes;
import ua.kharkiv.yeremenko.exXML.util.Sorter;

public class Main {

	public static void main(String[] args) throws Exception {
		String xmlFileName = args[0];
		System.out.println("Input ==> " + xmlFileName);
		
		////////////////////////////////////////////////////////
		// DOM
		////////////////////////////////////////////////////////
		
		// get
		DOMController domController = new DOMController(xmlFileName);
		domController.parse(true);
		Planes planes = domController.getPlanes();

		// sort (case 1)
		Sorter.setSortPlanesByModel(planes);
		
		// save
		String outputXmlFile = "output.dom.xml";
		DOMController.saveToXML(planes, outputXmlFile);
		System.out.println("Output ==> " + outputXmlFile);

		////////////////////////////////////////////////////////
		// SAX
		////////////////////////////////////////////////////////
		
		// get
		SAXController saxController = new SAXController(xmlFileName);
		saxController.parse(true);
		planes = saxController.getPlanes();
		
		// sort  (case 2)
		Sorter.setSortPlanesByOrigin(planes);
		
		// save
		outputXmlFile = "output.sax.xml";
		
		// other way: 
		DOMController.saveToXML(planes, outputXmlFile);
		System.out.println("Output ==> " + outputXmlFile);
		
		////////////////////////////////////////////////////////
		// StAX
		////////////////////////////////////////////////////////
		
		// get
		STAXController staxController = new STAXController(xmlFileName);
		staxController.parse();
		planes = staxController.getPlanes();
		
		// sort  (case 3)
		Sorter.setSortPlanesByPrice(planes);
		
		// save
		outputXmlFile = "output.stax.xml";
		DOMController.saveToXML(planes, outputXmlFile);
		System.out.println("Output ==> " + outputXmlFile);
	}

}
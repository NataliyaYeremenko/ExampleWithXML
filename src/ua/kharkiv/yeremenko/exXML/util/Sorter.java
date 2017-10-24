package ua.kharkiv.yeremenko.exXML.util;

import java.util.Collections;
import java.util.Comparator;

import ua.kharkiv.yeremenko.exXML.constants.Constants;
import ua.kharkiv.yeremenko.exXML.controller.DOMController;
import ua.kharkiv.yeremenko.exXML.entity.Plane;
import ua.kharkiv.yeremenko.exXML.entity.Planes;

/**
 * Contains static methods for sorting.
 */
public class Sorter {

	// //////////////////////////////////////////////////////////
	// these are comparators
	// //////////////////////////////////////////////////////////

	/**
	 * Sorts planes by model
	 */
	public static final Comparator<Plane> SORT_PLANES_BY_MODEL = new Comparator<Plane>() {
		@Override
		public int compare(Plane p1, Plane p2) {
			return p1.getModel().compareTo(p2.getModel());
		}
	};

	/**
	 * Sorts planes by origin.
	 */
	public static final Comparator<Plane> SORT_PLANES_BY_ORIGIN = new Comparator<Plane>() {
		@Override
		public int compare(Plane p1, Plane p2) {
			return p1.getOrigin().compareTo(p2.getOrigin());
		}
	};

	/**
	 * Sorts planes by price.
	 */
	public static final Comparator<Plane> SORT_PLANES_BY_PRICE = new Comparator<Plane>() {
		@Override
		public int compare(Plane p1, Plane p2) {
			return p1.getPrice().compareTo(p2.getPrice());
		}
	};

	/**
	 * Sorts planes by ammunition.
	 */
	public static final Comparator<Plane> SORT_PLANES_BY_AMMUNITION = new Comparator<Plane>() {
		@Override
		public int compare(Plane p1, Plane p2) {
			if (p1.getChars().isAmmunition() && !p2.getChars().isAmmunition()) {
				return -1;
			}
			if (p2.getChars().isAmmunition() && !p1.getChars().isAmmunition()) {
				return 1;
			}
			return 0;
		}
	};

	// //////////////////////////////////////////////////////////
	// these methods take Planes object and sort it
	// with according comparator
	// //////////////////////////////////////////////////////////

	public static final void setSortPlanesByModel(Planes planes) {
		Collections.sort(planes.getPlanes(), SORT_PLANES_BY_MODEL);
	}

	public static final void setSortPlanesByOrigin(Planes planes) {
		Collections.sort(planes.getPlanes(), SORT_PLANES_BY_ORIGIN);
	}

	public static final void setSortPlanesByPrice(Planes planes) {
		Collections.sort(planes.getPlanes(), SORT_PLANES_BY_PRICE);
	}

	public static final void setSortPlanesByAmmunition(Planes planes) {
		Collections.sort(planes.getPlanes(), SORT_PLANES_BY_AMMUNITION);
	}

	public static void main(String[] args) throws Exception {
		// Planes.xml --> Planes object
		DOMController domController = new DOMController(
				Constants.VALID_XML_FILE);
		domController.parse(false);
		Planes planes = domController.getPlanes();

		System.out.println("====================================");
		System.out.println(planes);
		System.out.println("====================================");

		System.out.println("====================================");
		Sorter.setSortPlanesByModel(planes);
		System.out.println(planes);
		System.out.println("====================================");

		System.out.println("====================================");
		Sorter.setSortPlanesByOrigin(planes);
		System.out.println(planes);
		System.out.println("====================================");

		System.out.println("====================================");
		Sorter.setSortPlanesByPrice(planes);
		System.out.println(planes);
		System.out.println("====================================");

		System.out.println("====================================");
		Sorter.setSortPlanesByAmmunition(planes);
		System.out.println(planes);
		System.out.println("====================================");
	}
}
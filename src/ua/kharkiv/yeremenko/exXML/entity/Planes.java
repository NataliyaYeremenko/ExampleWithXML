package ua.kharkiv.yeremenko.exXML.entity;

import java.util.ArrayList;
import java.util.List;

public class Planes {
    private List<Plane> planes;

    public List<Plane> getPlanes() {
        if (planes == null) {
            planes = new ArrayList<Plane>();
        }
        return planes;
    }

    @Override
    public String toString() {
        if (planes == null || planes.size() == 0) {
            return "Planes contains no planes";
        }
        StringBuilder result = new StringBuilder();
        for (Plane plane : planes) {
            result.append(plane).append('\n');
        }
        return result.toString();
    }

}

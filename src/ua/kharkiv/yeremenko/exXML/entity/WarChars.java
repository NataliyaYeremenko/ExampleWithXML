package ua.kharkiv.yeremenko.exXML.entity;

public class WarChars extends PlaneChars {
    private int rockets = 3;

    public WarChars(){

    }

    public WarChars(String type, int size, boolean ammunition, boolean radar, int rockets) {
        super(type, size, ammunition, radar);
        this.rockets = rockets;
    }

    public int getRockets() {
        return rockets;
    }

    public void setRockets(int rockets) {
        this.rockets = rockets;
    }
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString()).append(", ").
                append("rockets: ").append(rockets);
        return result.toString();
    }
}

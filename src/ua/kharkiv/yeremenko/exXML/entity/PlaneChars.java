package ua.kharkiv.yeremenko.exXML.entity;

public class PlaneChars {
    private String planeType;
    private int size;
    private boolean ammunition;
    private boolean radar;

    public PlaneChars(){

    }

    public PlaneChars(String planeType, int size, boolean ammunition, boolean radar) {
        this.planeType = planeType;
        this.size = size;
        this.ammunition = ammunition;
        this.radar = radar;
    }

    public String getType() {
        return planeType;
    }

    public void setType(String planeType) {
        this.planeType = planeType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAmmunition() {
        return ammunition;
    }

    public void setAmmunition(boolean ammunition) {
        this.ammunition = ammunition;
    }

    public boolean isRadar() {
        return radar;
    }

    public void setRadar(boolean radar) {
        this.radar = radar;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("plane type: ").append(planeType).append(", ").
                append("size: ").append(size).append(", ").
                append("ammunition: ").append(ammunition).append(", ").
                append("radar: ").append(radar);
        return result.toString();
    }


}

package ua.kharkiv.yeremenko.exXML.entity;

public class Parameters {
    private int length;
    private int width;
    private int height;


    public Parameters(int length, int width, int height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public Parameters() {
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("length: ").append(length).append(", ").
                append("width: ").append(width).append(", ").
                append("height: ").append(height);
        return result.toString();
    }
}

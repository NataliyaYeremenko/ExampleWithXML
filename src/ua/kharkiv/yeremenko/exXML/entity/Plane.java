package ua.kharkiv.yeremenko.exXML.entity;

public class Plane {
    private String model;
    private String origin;
    private PlaneChars chars;
    private Parameters parameters;
    private String price;

    public Plane(){

    }

    public Plane(String model, String origin, PlaneChars chars, Parameters parameters, String price) {
        this.model = model;
        this.origin = origin;
        this.chars = chars;
        this.parameters = parameters;
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public PlaneChars getChars() {
        return chars;
    }

    public void setChars(PlaneChars chars) {
        this.chars = chars;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("model: ").append(model).append('\n').
                append("origin: ").append(origin).append('\n').
                append("chars: ").append(chars).append('\n').
                append("parameters: ").append(parameters).append('\n').
                append("price: ").append(price).append('\n');
        return result.toString();
    }
}

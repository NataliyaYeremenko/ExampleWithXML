package ua.kharkiv.yeremenko.exXML.entity;

public enum PlaneType {
    САМОЛЕТ_ПОДДЕРЖКИ("самолет поддержки"), САМОЛЕТ_СОПРОВОЖДНИЯ("самолет сопровождения"),
    ИСТРЕБИТЕЛЬ("истребитель"), ПЕРЕХВАТЧИК("перехватчик"), РАЗВЕДЧИК("разведчик");
    private String value;

    PlaneType(String value) {
        this.value = value;
    }

    public boolean equalsTo(String name) {
        return value.equals(name);
    }

    public String value() {
        return value;
    }

}

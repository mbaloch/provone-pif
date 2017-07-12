package edu.kit.provone.provone2rdfmapper.model;

/**
 * Created by mukhtar on 18.10.16.
 */
public class Data {
    private String id;
    private String label;
    private String type;
    private String value;

    @Override
    public String toString() {
        return "Data{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

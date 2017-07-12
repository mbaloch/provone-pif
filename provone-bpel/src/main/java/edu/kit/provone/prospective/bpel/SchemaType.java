package edu.kit.provone.prospective.bpel;

/**
 * Created by mukhtar on 09.02.17.
 */
public class SchemaType {
    String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDataType() {
        return dataType;
    }

    public SchemaType(String typeName, String dataType) {
        this.typeName = typeName;
        this.dataType = dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    String dataType;
}

package edu.kit.provone.prospective.bpel.utils;

import com.predic8.schema.Schema;
import groovy.xml.QName;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by mukhtar on 10.05.17.
 */
public class SchemaParserHelper {


    private Map<String,Map<QName,List<SchemaType>>>  schemaTypes;

    public Map<String, Map<QName, List<SchemaType>>> parseSchemaTypes(List<String> wsdlFilePaths) {
            this.schemaTypes=new HashMap<>();
        for (String wsdlPath : wsdlFilePaths) {
           XSDschemaParser xsDschemaParser=new XSDschemaParser(wsdlPath);
            List<Schema> schemaList = xsDschemaParser.getDefinitions().getSchemas();
            for(Schema schema: schemaList){
                if (schema != null) {
                    xsDschemaParser.parseSchema(schema);
                    Map<QName, List<SchemaType>> complexTypes = schemaTypes.get("ComplexTypes");
                    Map<QName, List<SchemaType>> simpleTypes = schemaTypes.get("SimpleTypes");
                    Map<QName, List<SchemaType>> elementTypes = schemaTypes.get("ElementTypes");

                    if(complexTypes ==null){
                        schemaTypes.put("ComplexTypes", xsDschemaParser.getComplexTypes());
                    }else
                    {
                        complexTypes.putAll(xsDschemaParser.getComplexTypes());
                    }
                    // complexTypes == null ? schemaTypes.put("ComplexTypes", xsDschemaParser.getComplexTypes()): complexTypes.putAll(xsDschemaParser.getComplexTypes());
                    if(simpleTypes == null){
                        schemaTypes.put("SimpleTypes", xsDschemaParser.getSimpleTypes());
                    }else{
                        simpleTypes.putAll(xsDschemaParser.getSimpleTypes());
                    }
                    if(elementTypes == null){
                        schemaTypes.put("ElementTypes", xsDschemaParser.getElementTypes());
                    }else
                    {
                        elementTypes.putAll(xsDschemaParser.getElementTypes());
                    }

                }
            }
        }

        return schemaTypes;
    }

    public Map<String, Map<QName, List<SchemaType>>> getSchemaTypes() {
        return schemaTypes;
    }

    public  List<Map.Entry<QName, List<SchemaType>>>  resolveType(QName qName){
        Map<QName, List<SchemaType>> complexTypes = schemaTypes.get("ComplexTypes");
        Map<QName, List<SchemaType>> simpleTypes = schemaTypes.get("SimpleTypes");
        Map<QName, List<SchemaType>> elementTypes = schemaTypes.get("ElementTypes");

        final List<Map.Entry<QName, List<SchemaType>>> collect = Stream.of(complexTypes, simpleTypes, elementTypes).map(Map::entrySet)
                .flatMap(Collection::stream)
                .filter(qNameListEntry -> qNameListEntry.getKey().equals(qName))
                .collect(Collectors.toList());
        return collect;

    }

}

package edu.kit.provone.prospective.bpel.utils;

import com.predic8.schema.*;
import com.predic8.wsdl.Definitions;
import com.predic8.wsdl.WSDLParser;
import groovy.xml.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mukhtar on 10.05.17.
 */
public class XSDschemaParser {

    private HashMap<QName, List<SchemaType>> _elementTypes;
    private HashMap<QName, List<SchemaType>> _complexTypes;
    private HashMap<QName, List<SchemaType>> _simpleTypes;
    private static final Logger logger = LoggerFactory.getLogger(XSDschemaParser.class);
    private Definitions _definitions;

    public void resolveElement() {
        //return null;
    }

    public XSDschemaParser(String wsdlFilePath) {
        logger.info("-----------Parsing the WSDL File:" + wsdlFilePath);
        WSDLParser parser = new WSDLParser();
        this._definitions = parser.parse(wsdlFilePath);
        logger.info("-----------Finished Parsing the WSDL File:" + wsdlFilePath);
    }

    void parseSchema(Schema schema) {
        HashMap<QName, List<SchemaType>> elementTypes = new HashMap<>();
        HashMap<QName, List<SchemaType>> complexTypes = new HashMap<>();
        HashMap<QName, List<SchemaType>> simpleTypes = new HashMap<>();

        for (ComplexType ct : schema.getComplexTypes()) {
            if (ct.getModel() instanceof ModelGroup) {
                List<SchemaType> particles = new ArrayList<>();
                for (SchemaComponent sc : ((ModelGroup) ct.getModel()).getParticles()) {
                     particles.add(new SchemaType(sc.getName(), ((Element) sc).getType()));
                    //particles.add(new SchemaType(sc))
                }
                complexTypes.put(ct.getQname(), particles);
            }
        }
        if (schema.getAllElements().size() > 0) {
            schema.getAllElements().forEach(element -> {
                TypeDefinition def = element.getEmbeddedType();
                if (def != null) {
                    List<SchemaType> particles = new ArrayList<>();
                    for (SchemaComponent sc : ((ModelGroup) ((ComplexType) def).getModel()).getParticles()) {
                        // particles.add(new ElementParticle(sc.getName(), ((Element) sc).getType().getQualifiedName()));
                        particles.add(new SchemaType(sc.getName(), ((Element) sc).getType()));

                    }
                    elementTypes.put(element.getQname(), particles);
                }
            });
        }
        if (schema.getSimpleTypes().size() > 0) {

            for (SimpleType st : schema.getSimpleTypes()) {
                List<SchemaType> particles = new ArrayList<>();
                particles.add(new SchemaType(st.getName(), st.getRestriction().getBase()));
//                out("    SimpleType Name: " + st.getName());
//                out("    SimpleType Restriction: " + st.getRestriction());
//                out("    SimpleType Union: " + st.getUnion());
//                out("    SimpleType List: " + st.getList());
                simpleTypes.put(st.getQname(), particles);
            }

        }

        this._elementTypes=elementTypes;
        this._complexTypes=complexTypes;
        this._simpleTypes=simpleTypes;
    }

    public Definitions getDefinitions() throws IllegalStateException {
        if (this._definitions == null) {
            throw new IllegalStateException("Schema file not parsed. call parseWSDL first");
        }
        return this._definitions;
    }

    public HashMap<QName, List<SchemaType>> getElementTypes() throws IllegalStateException{
        if (this._elementTypes == null) {
            throw new IllegalStateException("Schema file not parsed. call parseWSDL first");
        }
        return this._elementTypes;
    }

    public HashMap<QName, List<SchemaType>> getComplexTypes() throws IllegalStateException{
        if (this._complexTypes == null) {
            throw new IllegalStateException("Schema file not parsed. call parseWSDL first");
        }
        return this._complexTypes;
    }

    public HashMap<QName, List<SchemaType>> getSimpleTypes() throws IllegalStateException{
        if (this._simpleTypes == null) {
            throw new IllegalStateException("Schema file not parsed. call parseWSDL first");
        }
        return _simpleTypes;
    }

}

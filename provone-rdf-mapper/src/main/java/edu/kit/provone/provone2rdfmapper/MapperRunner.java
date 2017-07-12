package edu.kit.provone.provone2rdfmapper;

import edu.kit.scufl.mainapp.SCUFLRetro2ProvONE;
import org.apache.jena.rdf.model.Model;

/**
 * Created by mukhtar on 12.07.17.
 */
public class MapperRunner {

    public static void main(){
        SCUFLRetro2ProvONE scuflRetro2ProvONE = new SCUFLRetro2ProvONE();
        try {
            Model retrospectiveRDFModel = scuflRetro2ProvONE.getRetrospectiveRDFModel("/home/mukhtar/IdeaProjects/provone-pif/provone-provenance/src/main/resources/workflowrun.prov.ttl");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

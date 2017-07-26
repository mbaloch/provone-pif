package edu.kit.provone.provone2rdfmapper.bpelprospective;

import edu.kit.provone.prospective.bpel.BPELProvONeGenerator;
import org.apache.jena.rdf.model.Model;

public class BPELProspectiveFactory {
    public String generateProspective() throws Exception {
        BPELProvONeGenerator bpelProvONeGenerator = new BPELProvONeGenerator();
        String prospectiveID = bpelProvONeGenerator.generateProspective();
        Model prospectiveModel = bpelProvONeGenerator.generateBPELRdf();
        return prospectiveID;
    }
}

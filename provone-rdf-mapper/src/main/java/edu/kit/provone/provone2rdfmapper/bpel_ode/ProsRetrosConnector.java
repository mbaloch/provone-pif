package edu.kit.provone.provone2rdfmapper.bpel_ode;

import edu.kit.provone.prospective.bpel.BPELProvONeGenerator;
import org.apache.jena.rdf.model.Model;

/**
 * Created by mukhtar on 18.07.17.
 */
public class ProsRetrosConnector {
    public static void main(String[] args) {
        BPELProvONeGenerator provONeGenerator=new BPELProvONeGenerator();
        Model model=null;
        try {
            model=provONeGenerator.generateBPELRdf();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

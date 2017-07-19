package kit.edu.mainapp;

import kit.edu.xpathmapper.RetrospectiveXpathMapper;
import org.apache.jena.rdf.model.Model;

public class TavernaRetrospective2ProvONE {

    public Model getRetrospectiveRDFModel(String ttlFilePath) {

        RetrospectiveXpathMapper retroMapper = new RetrospectiveXpathMapper();
        Model finalModel = retroMapper.xpathParser(ttlFilePath, retroMapper);
        return finalModel;
    }

}

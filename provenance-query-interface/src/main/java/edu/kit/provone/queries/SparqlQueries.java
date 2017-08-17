package edu.kit.provone.queries;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.resultset.ResultsFormat;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

public class SparqlQueries {
    public static void getProspective(String queryString,String endPointUrl){
        Model model = getResultSet(queryString, endPointUrl);
        model.write(System.out);

    }
    private static Model getResultSet(String queryString, String endPointUrl) {
        Query query = QueryFactory.create(queryString);
        Model model = null;
         QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointUrl, query);
            ((QueryEngineHTTP) qexec).addParam("timeout", "10000");
            model = qexec.execConstruct();
        return model;
    }

//    public static String getRetrospectiveQueryString(String graphUri){
//
//
//    }
//    public static String getInputAndOutputPortsQueryString(String graphUri){
//
//    }
//    public static String getInputAndOutputPortsOfGivenProcessQueryString(String processName, String graphUri){
//
//    }
//    public static String getOutputPortsOfProcessHavingValueQueryString(String value, String graphUri){
//
//    }
//    public static String getAllFailedRetrospectiveQueryString(String graphUri){
//
//    }
}

package edu.kit.provone.queries;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

public class SparqlQueries {
    public static Model getProspective(String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getProspectiveQueryString(graphUri);
        Model model = getResultSet(prospectiveQueryString, endPointUrl);
        return model;
    }

    private static Model getResultSet(String queryString, String endPointUrl) {
        Query query = QueryFactory.create(queryString);
        Model model = null;
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointUrl, query);
        ((QueryEngineHTTP) qexec).addParam("timeout", "10000");
        model = qexec.execConstruct();

        return model;
    }

    public static Model getRetrospective(String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getRetrospectiveQueryString(graphUri);
        Model model = getResultSet(prospectiveQueryString, endPointUrl);
        return model;

    }

    public static Model getInputAndOutputPorts(String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getInputAndOutputPortsQueryString(graphUri);
        Model model = getResultSet(prospectiveQueryString, endPointUrl);
        return model;
    }

    public static Model getInputAndOutputPortsOfGivenProcess(String processName, String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getInputAndOutputPortsOfGivenProcessQueryString(processName, graphUri);
        Model model = getResultSet(prospectiveQueryString, endPointUrl);
        return model;
    }

    public static Model getOutputPortsOfProcessHavingValue(String value, String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getOutputPortsOfProcessHavingValueQueryString(value, graphUri);
        Model model = getResultSet(prospectiveQueryString, endPointUrl);
        return model;
    }

    public static Model getAllFailedRetrospectiveQueryString(String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getAllFailedRetrospectiveQueryString(graphUri);
        Model model = getResultSet(prospectiveQueryString, endPointUrl);
        return model;
    }
}

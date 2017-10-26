package edu.kit.provone.queries;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

public class SparqlQueries {
    public static Model getProspective(String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getProspectiveQueryString(graphUri);
        Model model = getResultSetAsGraph(prospectiveQueryString, endPointUrl);
        return model;
    }

    private static Model getResultSetAsGraph(String queryString, String endPointUrl) {
        Query query = QueryFactory.create(queryString);
        Model model = null;
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointUrl, query);
        ((QueryEngineHTTP) qexec).addParam("timeout", "10000");
        model = qexec.execConstruct();

        return model;
    }

    private static ResultSet getResultSetAsSelect(String queryString, String endPointUrl) {
        Query query = QueryFactory.create(queryString);
        ResultSet resultSet = null;
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointUrl, query);
        ((QueryEngineHTTP) qexec).addParam("timeout", "10000");
        resultSet = qexec.execSelect();


        return resultSet;
    }

    public static Model getRetrospective(String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getRetrospectiveQueryString(graphUri);
        Model model = getResultSetAsGraph(prospectiveQueryString, endPointUrl);
        return model;

    }

    public static Model getInputAndOutputPorts(String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getInputAndOutputPortsQueryString(graphUri);
        Model model = getResultSetAsGraph(prospectiveQueryString, endPointUrl);
        return model;
    }

    public static Model getInputAndOutputPortsOfGivenProcess(String processName, String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getInputAndOutputPortsOfGivenProcessQueryString(processName, graphUri);
        Model model = getResultSetAsGraph(prospectiveQueryString, endPointUrl);
        return model;
    }

    public static ResultSet getOutputPortsOfProcessHavingValue(String value, String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getOutputPortsOfProcessHavingValueQueryString(value, graphUri);
        ResultSet resultSet = getResultSetAsSelect(prospectiveQueryString, endPointUrl);
        return resultSet;
    }

    public static ResultSet getRetrospectiveByStatus(boolean status, String graphUri, String endPointUrl) {
        String prospectiveQueryString = SparqlQueryStrings.getRetrospectiveByStatusQueryString(status, graphUri);
        ResultSet resultSet = getResultSetAsSelect(prospectiveQueryString, endPointUrl);
        return resultSet;
    }
}

package edu.kit.provone.queries;

public class SparqlQueryStrings {

    public static String getProspectiveQueryString(String graphUri){
        String NL = System.getProperty("line.separator");
        StringBuilder queryString=new StringBuilder();
        queryString.append("CONSTRUCT {?subject ?predicate ?object}").append(NL);
        queryString.append("WHERE").append(NL);
        queryString.append("{").append(NL);
        queryString.append("GRAPH <"+graphUri+">").append(NL);
        queryString.append("{").append(NL);
        queryString.append("?subject ?predicate ?object.").append(NL);
        queryString.append("FILTER regex(str(?subject), \"pp\")").append(NL);
        queryString.append("}}").append(NL);

        return queryString.toString();
    }
    public static String getRetrospectiveQueryString(String graphUri){
        String NL = System.getProperty("line.separator");
        StringBuilder queryString=new StringBuilder();
        queryString.append("PREFIX prov: <http://www.w3.org/ns/prov#>").append(NL);
        queryString.append("PREFIX provone: <http://purl.org/provone#>").append(NL);
        queryString.append("CONSTRUCT {?subject ?predicate ?object}").append(NL);
        queryString.append("WHERE").append(NL);
        queryString.append("{").append(NL);
        queryString.append("GRAPH <"+graphUri+">").append(NL);
        queryString.append("{").append(NL);
        queryString.append("?subject ?predicate ?object.").append(NL);
        queryString.append("FILTER regex(str(?subject), \"rp\")").append(NL);
        queryString.append("FILTER NOT EXISTS {?subject prov:wasAssociatedWith ?object}.").append(NL);
        queryString.append("FILTER NOT exists{?subject provone:dataOnLink ?object}.").append(NL);
        queryString.append("FILTER NOT exists{?subject provone:hasDefaultParam ?object}.").append(NL);
        queryString.append("}}").append(NL);

        return queryString.toString();
    }
    public static String getInputAndOutputPortsQueryString(String graphUri){
        String NL = System.getProperty("line.separator");
        StringBuilder queryString=new StringBuilder();
        queryString.append("PREFIX provone: <http://purl.org/provone#>").append(NL);
        queryString.append("CONSTRUCT {?object ?predicate ?value}").append(NL);
        queryString.append("WHERE").append(NL);
        queryString.append("{").append(NL);
        queryString.append("GRAPH <"+graphUri+">").append(NL);
        queryString.append("{").append(NL);
        queryString.append("{?subject provone:hasOutPort ?object.}").append(NL);
        queryString.append("UNION").append(NL);
        queryString.append("{?subject provone:hasInPort ?object }.").append(NL);
        queryString.append("?object ?predicate ?value").append(NL);
        queryString.append("}}").append(NL);
        return queryString.toString();
    }
    public static String getInputAndOutputPortsOfGivenProcessQueryString(String processName, String graphUri){
        String NL = System.getProperty("line.separator");
        StringBuilder queryString=new StringBuilder();
        queryString.append("PREFIX provone: <http://purl.org/provone#>").append(NL);
        queryString.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>").append(NL);
        queryString.append("PREFIX dc: <http://purl.org/dc/terms/>").append(NL);
        queryString.append("CONSTRUCT {?object ?predicate ?value}").append(NL);
        queryString.append("WHERE").append(NL);
        queryString.append("{").append(NL);
        queryString.append("GRAPH <"+graphUri+">").append(NL);
        queryString.append("{").append(NL);
        queryString.append("?subject rdf:type provone:Process.").append(NL);
        queryString.append("?subject dc:title ").append("\"").append(processName).append("\"").append(".").append(NL);
        queryString.append("{").append(NL);
        queryString.append("SELECT *").append(NL);
        queryString.append("WHERE").append(NL);
        queryString.append("{").append(NL);
        queryString.append("{?subject provone:hasOutPort ?object.}").append(NL);
        queryString.append("UNION").append(NL);
        queryString.append("{?subject provone:hasInPort ?object }.").append(NL);
        queryString.append("?object ?predicate ?value").append(NL);
        queryString.append("}").append(NL);
        queryString.append("}").append(NL);
        queryString.append("}}").append(NL);
        return queryString.toString();
    }
    public static String getOutputPortsOfProcessHavingValueQueryString(String value, String graphUri){
        String NL = System.getProperty("line.separator");
        StringBuilder queryString=new StringBuilder();
        queryString.append("PREFIX provone: <http://purl.org/provone#>").append(NL);
        queryString.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>").append(NL);
        queryString.append("PREFIX dc: <http://purl.org/dc/terms/>").append(NL);
        queryString.append("PREFIX prov: <http://www.w3.org/ns/prov#>").append(NL);
        queryString.append("SELECT ?processName ?outputPort ?value").append(NL);
        queryString.append("WHERE").append(NL);
        queryString.append("{").append(NL);
        queryString.append("GRAPH <"+graphUri+">").append(NL);
        queryString.append("{").append(NL);
        queryString.append("?subject a provone:Process .").append(NL);
        queryString.append("?subject provone:hasOutPort ?outport.").append(NL);
        queryString.append("?outport dc:title ?outputPort.").append(NL);
        queryString.append("?outport provone:outPortToDL ?DL.").append(NL);
        queryString.append("?subject dc:title ?processName.").append(NL);
        queryString.append("{").append(NL);
        queryString.append("SELECT *").append(NL);
        queryString.append("WHERE").append(NL);
        queryString.append("{").append(NL);
        queryString.append("?data a provone:Data.").append(NL);
        queryString.append("?data prov:value ?value.").append(NL);
        queryString.append("?data provone:dataOnLink ?DL.").append(NL);
        queryString.append("filter (?value  " + value + " )").append(NL);
        queryString.append("}").append(NL);
        queryString.append("}").append(NL);
        queryString.append("}}").append(NL);
        return queryString.toString();
    }

    public static String getRetrospectiveByStatusQueryString(boolean status, String graphUri) {
        String NL = System.getProperty("line.separator");
        StringBuilder queryString=new StringBuilder();
        queryString.append("PREFIX provone: <http://purl.org/provone#>").append(NL);
        queryString.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>").append(NL);
        queryString.append("PREFIX dc: <http://purl.org/dc/terms/>").append(NL);
        queryString.append("PREFIX prov: <http://www.w3.org/ns/prov#>").append(NL);
        queryString.append("PREFIX wfms:<http://www.wfms.org/registry.xsd#>").append(NL);

        queryString.append("SELECT ?workflow ?error").append(NL);
        queryString.append("WHERE").append(NL);
        queryString.append("{").append(NL);
        queryString.append("GRAPH <"+graphUri+">").append(NL);
        queryString.append("{").append(NL);
        queryString.append("?workflow a provone:ProcessExec.").append(NL);
        queryString.append("?workflow prov:wasAssociatedWith ?object.").append(NL);
        queryString.append("?object a provone:Workflow.").append(NL);
        queryString.append("?workflow wfms:completed ?completed.").append(NL);
        // queryString.append("?subject wfms:error ?error.").append(NL);
        queryString.append("filter(?completed =\"" + status + "\")").append(NL);
        queryString.append("}}").append(NL);
        return queryString.toString();
    }
}

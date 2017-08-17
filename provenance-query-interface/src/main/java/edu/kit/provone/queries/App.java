package edu.kit.provone.queries;

public class App {
    public static void main(String[] args) {
        String grapUri="http://kit.edu/bpel/5e50c89b-a841-4173-94c9-42153d0bc9dc";
        String prospectiveQueryString = SparqlQueryStrings.getProspectiveQueryString(grapUri);
        String endPointUrl = "http://localhost:3030/kit/sparql";
        SparqlQueries.getProspective(prospectiveQueryString,endPointUrl);
//        System.out.println(prospectiveQueryString);
//        System.out.println("retros");
//        System.out.println(SparqlQueryStrings.getRetrospectiveQueryString(grapUri));
//        System.out.println("All ports");
//        System.out.println(SparqlQueryStrings.getInputAndOutputPortsQueryString(grapUri));
//        System.out.println("All ports of given process");
//        System.out.println(SparqlQueryStrings.getInputAndOutputPortsOfGivenProcessQueryString("replyOutput",grapUri));
//        System.out.println("All ports of given value");
//        System.out.println(SparqlQueryStrings.getOutputPortsOfProcessHavingValueQueryString("20",grapUri));
//        System.out.println("All failed retro");
//        System.out.println(SparqlQueryStrings.getAllFailedRetrospectiveQueryString(grapUri));
    }
}

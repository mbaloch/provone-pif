//package edu.kit.provone.provone2rdfmapper;
//
//
//import edu.kit.provone.provone2rdfmapper.model.Data;
//import edu.kit.provone.provone2rdfmapper.model.ProcessExec;
//import edu.kit.provone.provone2rdfmapper.utility.RDFUtilityOld;
//import org.apache.jena.query.*;
//import org.apache.jena.rdf.model.Property;
//import org.apache.jena.rdf.model.RDFNode;
//import org.apache.jena.rdf.model.Resource;
//import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
//import org.apache.jena.update.UpdateExecutionFactory;
//import org.apache.jena.update.UpdateFactory;
//import org.apache.jena.update.UpdateProcessor;
//import org.apache.jena.update.UpdateRequest;
//
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
///**
// * Created by mukhtar on 06.03.17.
// */
//public class ProvenanceAPIRDFImpl implements IProvenanceAPI {
//    static final String NL = System.getProperty("line.separator");
//    static final String updateModelURI = "http://localhost:3030/tdb/";
//    static final String queryServiceURI = "http://localhost:3030/tdb/sparql";
//    static final String graphUri = "http://kit.demo/calculationProcess/1.0";
//    static final String wfms = "http://www.wfms.org/registry.xsd#";
//    static final String provone = "http://purl.org/provone#";
//    static final String prov = "http://www.w3.org/ns/prov#";
//    static final String rpns = "http://kit.edu/rp/";
//    static final String ppns = "http://kit.edu/pp/";
//
//    ProvenanceAPIRDFImpl() {
//     //   System.out.println("++++++++++++++++++++++I am called +---------------------");
//    }
//
//    public void createProcessExecInstance(ProcessExec processExec) {
//        String queryByTitle = queryByTitle(processExec.getTitle(), graphUri);
//        String queryById = queryById(processExec.getProcessInstanceId(), graphUri);
//        System.out.println("query by title:" + queryByTitle);
//        System.out.println("query by id:" + queryById);
//
//        Resource associatedWithProcessResource = getResource(queryByTitle, queryServiceURI);
//        Resource workflowExecResource = getResource(queryById, queryServiceURI);
//        Resource processExecResource = createProcessExecResource(associatedWithProcessResource, workflowExecResource, processExec);
//        insertResource(processExecResource);
//    }
//
//    public void createWorkflowExecInstance(ProcessExec processExec) {
//        String queryString = queryByTitle(processExec.getTitle(), graphUri);
//        System.out.println("query:" + queryString);
//        Resource result = getResource(queryString, queryServiceURI);
//        Resource processExecResource = createWorkflowExecResource(result, processExec);
//        insertResource(processExecResource);
//        /*
//        String workflowTitle = processExec.getTitle();
//        String prolog = "PREFIX dc: <http://purl.org/dc/terms/>";
//        String queryString = prolog + NL +
//                "SELECT ?subject ?predicate ?object" +
//                " WHERE {" +
//                "graph <http://kit.demo/calculationProcess> " +
//                "{"
//                //+"?subject  dc:title  \"CalculationProcess\""+ //+workflowTitle+
//                + "?subject  dc:title  \"" + workflowTitle + "\"" + //\"CalculationProcess\""+ //
//                //+"?subject ?predicate ?object"+
//                "}" +
//                "}";
//        System.out.println("query:" + queryString);
//
//        Resource result = getResource(queryString, queryServiceURI);
//        if (result != null) {
//            processExec.setCompleted(false);
//            RDFUtilityOld rdfUtility = new RDFUtilityOld();
//            Resource processExecResource = rdfUtility.createProcessExec(processExec.getProcessInstanceId(), processExec.getTitle()
//                    , processExec.getStartTime(), processExec.getEndTime(),
//                    processExec.getCompleted().toString());
//            //processExecResource.listProperties();
//
//            Property wasAssociatedWith = rdfUtility.wasAssociatedWith(processExecResource, result);
//
//            DatasetAccessor accessor = DatasetAccessorFactory
//                    .createHTTP(updateModelURI);
//            accessor.add(rdfUtility.getModel());
//        }
//*/
//    }
//
//
//    public void createChildProcessInstance(String parentProcessUUID, String currentProcess, ProcessExec processInfo) {
//
//    }
//
//
//    public void updateProcessStatus(ProcessExec processInfo) {
//        String querySubject ;
//        if (processInfo.getId().equals(processInfo.getProcessInstanceId())){
//            querySubject=queryById(processInfo.getId(), graphUri);
//        }else {
//            querySubject=queryActivityById(processInfo.getId(), graphUri);
//        }
//
//        System.out.println("query ActivityById:" + querySubject);
//        Resource activityResource = getResource(querySubject, queryServiceURI);
//
//        String delteQuery="PREFIX wfms: <"+wfms+">"+NL+
//                "Delete data {graph <"+graphUri+"> { <"+activityResource+ "> wfms:completed \"false\"}}";
//
//        String insertQuery="PREFIX wfms: <"+wfms+">"+NL+
//                "Insert data {graph <"+graphUri+"> { <"+activityResource+ "> wfms:completed \"true\"}}";
//
//        String insertEndTimeQuery="PREFIX wfms: <"+wfms+">"+NL+
//                "PREFIX prov: <"+prov+">"+NL+
//                "Insert data {graph <"+graphUri+"> { <"+activityResource+ "> prov:endTime \""+processInfo.getEndTime()+"\"}}";
//
//        //System.out.println("insert query"+insertQuery);
//        //System.out.println("delete query"+delteQuery);
//        UpdateRequest request = UpdateFactory.create();
//        request.add(delteQuery);
//        request.add(insertQuery);
//        request.add(insertEndTimeQuery);
//
//       // String ServiceURI="http://localhost:3030/tdb/";
//
//        //   UpdateRequest create = UpdateFactory.create(queryStringInsert);
//        UpdateProcessor createRemote = UpdateExecutionFactory.createRemote(request, updateModelURI);
//        createRemote.execute();
//    }
//
//
//    public void processProducedData(String workflowId, String processId, Data data) {
//
//    }
//
//
//    public void processConsumedData(String workflowId, String processId, Data data) {
//
//    }
//
//    private String queryByTitle(String title, String graphUri) {
//
//        String prolog = "PREFIX dc: <http://purl.org/dc/terms/>";
//        String queryString = prolog + NL +
//                "SELECT ?subject ?predicate ?object" +
//                " WHERE {" +
//                "graph <" + graphUri + "> " +
//                "{"
//                + "?subject  dc:title  \"" + title + "\"" +
//                "}" +
//                "}";
//        return queryString;
//    }
//
//    private String queryById(String id, String graphUri) {
//
//        String prolog = "PREFIX dc: <http://purl.org/dc/terms/>";
//        String queryString = prolog + NL +
//                "SELECT ?subject ?predicate ?object" +
//                " WHERE {" +
//                "graph <" + graphUri + "> " +
//                "{"
//                + "?subject  dc:identifier  \"" + id + "\"" +
//                "}" +
//                "}";
//        return queryString;
//    }
//
//    private String queryActivityById(String id, String graphUri) {
//
//        String prolog = "PREFIX wfms: <" + wfms + ">";
//        String queryString = prolog + NL +
//                "SELECT ?subject ?predicate ?object" +
//                " WHERE {" +
//                "graph <" + graphUri + "> " +
//                "{"
//                + "?subject  wfms:activityId  \"" + id + "\"" +
//                "}" +
//                "}";
//        return queryString;
//    }
//
//    private Resource getResource(String queryString, String endPointUrl) {
//        Query query = QueryFactory.create(queryString);
//        Resource result = null;
//
//        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointUrl, query)) {
//            ((QueryEngineHTTP) qexec).addParam("timeout", "10000");
//
//            // Execute.
//            ResultSet rs = qexec.execSelect();
//            //  ResultSetFormatter.out(System.out, rs, query);
//            for (; rs.hasNext(); ) {
//                QuerySolution rb = rs.nextSolution();
//
//                // Get title - variable names do not include the '?' (or '$')
//                RDFNode x = rb.get("subject");
//                if (x.isResource()) {
//                    return x.asResource();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    private Resource createProcessExecResource(Resource wasAssociatedWithResource, Resource workflowResource, ProcessExec processExec) {
//        //if (subject != null) {
//        if (processExec.getCompleted() == null)
//            processExec.setCompleted(false);
//        RDFUtilityOld rdfUtility = new RDFUtilityOld();
//        Resource processExecResource = rdfUtility.createProcessExec(
//                shortUUID(), processExec.getTitle(), processExec.getId()
//                , processExec.getStartTime(), processExec.getEndTime(),
//                processExec.getCompleted().toString());
//        Property wasAssociatedWith = rdfUtility.wasAssociatedWith(processExecResource, wasAssociatedWithResource);
//        Property isPartOf = rdfUtility.isPartOf(processExecResource, workflowResource);
//        return processExecResource;
//
//        //}
//
//    }
//
//    private Resource createWorkflowExecResource(Resource subject, ProcessExec processExec) {
//        //if (subject != null) {
//        if (processExec.getCompleted() == null)
//            processExec.setCompleted(false);
//        RDFUtilityOld rdfUtility = new RDFUtilityOld();
//        Resource processExecResource = rdfUtility.createProcessExec(
//                processExec.getProcessInstanceId(), processExec.getTitle(), processExec.getId()
//                , processExec.getStartTime(), processExec.getEndTime(),
//                processExec.getCompleted().toString());
//        Property wasAssociatedWith = rdfUtility.wasAssociatedWith(processExecResource, subject);
//        return processExecResource;
//
//        //}
//
//    }
//public List<String> getAllVariableNames(){
//    String prolog = "PREFIX wfms: <" + wfms + ">";
//    String queryString = prolog + NL +
//            "SELECT ?varNames" +
//            " WHERE {" +
//            "graph <" + graphUri + "> " +
//            "{"+
//    "{?subject <http://purl.org/provone#hasOutPort> ?object.} "+
//        "Union"+
//        " {?subject <http://purl.org/provone#hasInPort> ?object }. "+
//    " ?object <http://purl.org/dc/terms/title> ?varNames "+
//            "}" +
//            "}";
//    List<String> varResult=new ArrayList<>();
//    Query query = QueryFactory.create(queryString);
//    Resource result = null;
//
//    try (QueryExecution qexec = QueryExecutionFactory.sparqlService(queryServiceURI, query)) {
//        ((QueryEngineHTTP) qexec).addParam("timeout", "10000");
//
//        // Execute.
//        ResultSet rs = qexec.execSelect();
//        //  ResultSetFormatter.out(System.out, rs, query);
//        for (; rs.hasNext(); ) {
//            QuerySolution rb = rs.nextSolution();
//
//            // Get title - variable names do not include the '?' (or '$')
//            RDFNode x = rb.get("varNames");
//            if (x.isLiteral()) {
//               // return x.asResource();
//                varResult.add(x.asLiteral().toString());
//            }
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return varResult;
//
//}
//
//    private void insertResource(Resource processExecResource) {
//
//        DatasetAccessor accessor = DatasetAccessorFactory
//                .createHTTP(updateModelURI);
//        //accessor.add(rdfUtility.getModel());
//        //processExecResource.addProperty(processExecResource.getModel())
//        accessor.add(graphUri, processExecResource.getModel());
//    }
//
//    private static String shortUUID() {
//        UUID uuid = UUID.randomUUID();
//        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
//        return Long.toString(l, Character.MAX_RADIX);
//    }
//
//}

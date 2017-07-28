package edu.kit.provone.provone2rdfmapper.bpel_ode;

import edu.kit.provone.provone2rdfmapper.model.Data;
import edu.kit.provone.provone2rdfmapper.model.ProcessExec;
import edu.kit.provone.provone2rdfmapper.utility.RDFUtility;
import edu.kit.provone.provone2rdfmapper.utility.RDFUtilityOld;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mukhtar on 06.03.17.
 */
public class BPELRetroToProspAttachment {
    final String NL = System.getProperty("line.separator");
    final String updateModelURI = "http://localhost:3030/kit/";
    final String queryServiceURI = "http://localhost:3030/kit/sparql";
    String graphUri = "http://kit.edu/bpel/";
    final String wfms = "http://www.wfms.org/registry.xsd#";
    final String provone = "http://purl.org/provone#";
    final String prov = "http://www.w3.org/ns/prov#";
    final String rpns = "http://kit.edu/rp/";
    final String ppns = "http://kit.edu/pp/";

    BPELRetroToProspAttachment(String graphUri) {
        this.graphUri += graphUri;
    }

    RDFUtility rdfUtility;

    BPELRetroToProspAttachment(Model rdfModel, String prospectiveGraphId) {
        rdfUtility = new RDFUtility(rdfModel);
        this.graphUri += prospectiveGraphId;
    }

    public void updateJena(Model updatedRetrospectiveModel) {
        insertResource(updatedRetrospectiveModel);
    }

    public void createProcessExecInstance(Resource processExec, String title) {
        String queryByTitle = queryByTitle(title, graphUri);
        System.out.println("query by title:" + queryByTitle);
        Resource associatedWithProcessResource = getResource(queryByTitle, queryServiceURI);
        Resource processExecResource = createProcessExecResource(processExec, associatedWithProcessResource);

        //      insertResource(processExecResource);
    }

    public void createWorkflowExecInstance(Resource processExec, String workflowId) {
        String queryString = queryById(workflowId, graphUri);
        System.out.println("query:" + queryString);
        Resource workflowResource = getResource(queryString, queryServiceURI);
        Resource processExecResource = rdfUtility.wasAssociatedWith(processExec, workflowResource);

        //  insertResource(processExecResource);

    }

    public void updateProcessStatus(ProcessExec processInfo) {
        String querySubject;
        if (processInfo.getId().equals(processInfo.getProcessInstanceId())) {
            querySubject = queryById(processInfo.getId(), graphUri);
        } else {
            querySubject = queryActivityById(processInfo.getId(), graphUri);
        }

        System.out.println("query ActivityById:" + querySubject);
        Resource activityResource = getResource(querySubject, queryServiceURI);

        String delteQuery = "PREFIX wfms: <" + wfms + ">" + NL +
                "Delete data {graph <" + graphUri + "> { <" + activityResource + "> wfms:completed \"false\"}}";

        String insertQuery = "PREFIX wfms: <" + wfms + ">" + NL +
                "Insert data {graph <" + graphUri + "> { <" + activityResource + "> wfms:completed \"true\"}}";

        String insertEndTimeQuery = "PREFIX wfms: <" + wfms + ">" + NL +
                "PREFIX prov: <" + prov + ">" + NL +
                "Insert data {graph <" + graphUri + "> { <" + activityResource + "> prov:endTime \"" + processInfo.getEndTime() + "\"}}";

        //System.out.println("insert query"+insertQuery);
        //System.out.println("delete query"+delteQuery);
        UpdateRequest request = UpdateFactory.create();
        request.add(delteQuery);
        request.add(insertQuery);
        request.add(insertEndTimeQuery);

        // String ServiceURI="http://localhost:3030/tdb/";

        //   UpdateRequest create = UpdateFactory.create(queryStringInsert);
        UpdateProcessor createRemote = UpdateExecutionFactory.createRemote(request, updateModelURI);
        createRemote.execute();
    }


    public void processProducedData(Resource processResource, Resource dataResource) {
        rdfUtility.wasGeneratedBy(dataResource, processResource);
    }

    public void createDataOnLink(Resource dataLinkResource, Resource dataResource) {
        rdfUtility.dataOnLink(dataResource, dataLinkResource);
    }

    public Resource createData(String variableName, String particleName, String value) {
        UUID uuid = UUID.randomUUID();
        return rdfUtility.createData(uuid.toString(), variableName, particleName, value);
    }


    public void processConsumedData(Resource processResource, Resource dataResource) {
        rdfUtility.used(processResource, dataResource);
    }

    private String queryGetDLByVariable(String variableName, String particleName) {
        String prolog = "PREFIX dc: <http://purl.org/dc/terms/>";
        String queryString = prolog + NL +
                "SELECT ?subject ?predicate ?object" +
                " WHERE {" +
                "graph <" + graphUri + "> " +
                "{ "
                + " ?subject <http://www.wfms.org/registry.xsd#signature> \"" + particleName + "\" ." + NL
                + " ?subject  dc:title  \"" + variableName + "\" ." + NL
                + " ?object <http://purl.org/provone#DLToInPort> ?subject ." + NL
                + " }" +
                " }";

        return queryString;

    }

    private String queryByTitle(String title, String graphUri) {

        String prolog = "PREFIX dc: <http://purl.org/dc/terms/>";
        String queryString = prolog + NL +
                "SELECT ?subject ?predicate ?object" +
                " WHERE {" +
                "graph <" + graphUri + "> " +
                "{"
                + "?subject  dc:title  \"" + title + "\"" +
                "}" +
                "}";
        return queryString;
    }

    private String queryById(String id, String graphUri) {

        String prolog = "PREFIX dc: <http://purl.org/dc/terms/>";
        String queryString = prolog + NL +
                "SELECT ?subject ?predicate ?object" +
                " WHERE {" +
                "graph <" + graphUri + "> " +
                "{"
                + "?subject  dc:identifier  \"" + id + "\"" +
                "}" +
                "}";
        return queryString;
    }

    private String queryActivityById(String id, String graphUri) {

        String prolog = "PREFIX wfms: <" + wfms + ">";
        String queryString = prolog + NL +
                "SELECT ?subject ?predicate ?object" +
                " WHERE {" +
                "graph <" + graphUri + "> " +
                "{"
                + "?subject  wfms:activityId  \"" + id + "\"" +
                "}" +
                "}";
        return queryString;
    }

    private Resource getResource(String queryString, String endPointUrl) {
        Query query = QueryFactory.create(queryString);
        Resource result = null;

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointUrl, query)) {
            ((QueryEngineHTTP) qexec).addParam("timeout", "10000");

            // Execute.
            ResultSet rs = qexec.execSelect();
            //  ResultSetFormatter.out(System.out, rs, query);
            for (; rs.hasNext(); ) {
                QuerySolution rb = rs.nextSolution();

                // Get title - variable names do not include the '?' (or '$')
                RDFNode x = rb.get("subject");
                if (x.isResource()) {
                    return x.asResource();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Resource createProcessExecResource(Resource processExecResource, Resource workflowResource) {
        Property wasAssociatedWith = rdfUtility.wasAssociatedWith(processExecResource, workflowResource);
        return processExecResource;
    }

    private Resource createWorkflowExecResource(Resource subject, ProcessExec processExec) {
        //if (subject != null) {
        if (processExec.getCompleted() == null)
            processExec.setCompleted(false);
        RDFUtilityOld rdfUtility = new RDFUtilityOld();
        Resource processExecResource = rdfUtility.createProcessExec(
                processExec.getProcessInstanceId(), processExec.getTitle(), processExec.getId()
                , processExec.getStartTime(), processExec.getEndTime(),
                processExec.getCompleted().toString());
        Property wasAssociatedWith = rdfUtility.wasAssociatedWith(processExecResource, subject);
        return processExecResource;

        //}

    }

    public List<String> getAllVariableNames() {
        String prolog = "PREFIX wfms: <" + wfms + ">";
        String queryString = prolog + NL +
                "SELECT ?varNames" +
                " WHERE {" +
                "graph <" + graphUri + "> " +
                "{" +
                "{?subject <http://purl.org/provone#hasOutPort> ?object.} " +
                "Union" +
                " {?subject <http://purl.org/provone#hasInPort> ?object }. " +
                " ?object <http://purl.org/dc/terms/title> ?varNames " +
                "}" +
                "}";
        List<String> varResult = new ArrayList<>();
        Query query = QueryFactory.create(queryString);
        Resource result = null;

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(queryServiceURI, query)) {
            ((QueryEngineHTTP) qexec).addParam("timeout", "10000");

            // Execute.
            ResultSet rs = qexec.execSelect();
            //  ResultSetFormatter.out(System.out, rs, query);
            for (; rs.hasNext(); ) {
                QuerySolution rb = rs.nextSolution();

                // Get title - variable names do not include the '?' (or '$')
                RDFNode x = rb.get("varNames");
                if (x.isLiteral()) {
                    // return x.asResource();
                    varResult.add(x.asLiteral().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return varResult;

    }

    private void insertResource(Model updatedRetroModel) {

        DatasetAccessor accessor = DatasetAccessorFactory
                .createHTTP(updateModelURI);
        //accessor.add(rdfUtility.getModel());
        //processExecResource.addProperty(processExecResource.getModel())
        accessor.add(graphUri, updatedRetroModel);
    }

    private static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}

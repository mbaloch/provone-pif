package edu.kit.provone.provone2rdfmapper.bpel_ode;

import edu.kit.provone.provone2rdfmapper.SCUFLRetroToProspAttachment;
import edu.kit.provone.provone2rdfmapper.bpelprospective.BPELProspectiveFactory;
import edu.kit.provone.retrospective.ode.Ode2ProvONE;
import edu.kit.provone.retrospective.ode.RDFUtility;
import edu.kit.provone.retrospective.ode.model.Data;
import kit.edu.mainapp.TavernaRetrospective2ProvONE;
import org.apache.axis2.AxisFault;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.ode.bpel.pmapi.TEventInfo;
import org.apache.xmlbeans.XmlException;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mukhtar on 18.07.17.
 */
public class BPELProsRetrosConnector {


    public static void main(String[] args) throws Exception {
        BPELProspectiveFactory bpelProspectiveFactory = new BPELProspectiveFactory();
        String prospectiveGraphID = bpelProspectiveFactory.generateProspective();
        Ode2ProvONE ode2ProvONE = new Ode2ProvONE();
        try {
            List<TEventInfo> eventsByInstanceId = ode2ProvONE.getEventsByInstanceId("400");
            // eventsByInstanceId.forEach(System.out::println);
            Model retrospectiveRDFModel = ode2ProvONE.generateRetrospective(eventsByInstanceId);
            BPELRetroToProspAttachment prospAttachment = new BPELRetroToProspAttachment(retrospectiveRDFModel, prospectiveGraphID);

            retrospectiveRDFModel.write(System.out);
            Map<String, List<Data>> dataList = ode2ProvONE.getCommunicationbyInstanceId("400");
            //dataList.stream().forEach(System.out::println);
//            System.out.println("testing");
            attachProspectiveAndRetrospective(retrospectiveRDFModel, prospAttachment);
            attachDataToModel(retrospectiveRDFModel, prospAttachment, dataList, prospectiveGraphID);


        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }

    }

    static void attachDataToModel(Model retroModel, BPELRetroToProspAttachment bpelRetroToProspAttachment
            , Map<String, List<Data>> dataList, String graphiri) {
        String endPointUrl = "http://localhost:3030/kit/sparql";
        String NL = System.getProperty("line.separator");
        String graphiUri = "http://kit.edu/bpel/" + graphiri;
        List<SourceProcessToDestProcesses> sourceProcessToDestProcesses = new ArrayList<>();
        StringBuilder queryString = new StringBuilder();

        queryString.append("select ?sourceP ?outPort ?dataLink ?inPort ?destP ?sourcetitle ?desTtitle ?sourceOpTitle ?destOpTilte ?inputVarName ?inputParticleName ?outputVarName ?outputParticleName").append(NL);
        queryString.append("where {").append(NL);
        queryString.append("graph <" + graphiUri + "> ").append(NL);
        queryString.append("{").append(NL);
        queryString.append("?sourceP <http://purl.org/provone#hasOutPort> ?outPort.").append(NL);
        queryString.append("?outPort <http://purl.org/provone#outPortToDL> ?dataLink .").append(NL);
        queryString.append("?dataLink <http://purl.org/provone#DLToInPort> ?inPort .").append(NL);
        queryString.append("?destP <http://purl.org/provone#hasInPort> ?inPort .").append(NL);
        queryString.append("?sourceP <http://purl.org/dc/terms/title> ?sourcetitle.").append(NL);
        queryString.append("?destP <http://purl.org/dc/terms/title> ?desTtitle .").append(NL);
        queryString.append("?sourceP <http://www.wfms.org/registry.xsd#operation> ?sourceOpTitle .").append(NL);
        queryString.append("?destP <http://www.wfms.org/registry.xsd#operation> ?destOpTilte .").append(NL);
        queryString.append("?outPort <http://purl.org/dc/terms/title> ?outputVarName .").append(NL);
        queryString.append("?outPort <http://www.wfms.org/registry.xsd#signature> ?outputParticleName.").append(NL);
        queryString.append("?inPort <http://purl.org/dc/terms/title> ?inputVarName.").append(NL);
        queryString.append("?inPort <http://www.wfms.org/registry.xsd#signature> ?inputParticleName.").append(NL);

        queryString.append("} }");
        System.out.println(queryString);
        Query query = QueryFactory.create(queryString.toString());

        try (QueryExecution qexec = QueryExecutionFactory.sparqlService(endPointUrl, query)) {
            ((QueryEngineHTTP) qexec).addParam("timeout", "10000");

            // Execute.
            ResultSet rs = qexec.execSelect();
            //  ResultSetFormatter.out(System.out, rs, query);
            for (; rs.hasNext(); ) {
                QuerySolution rb = rs.nextSolution();

                // Get title - variable names do not include the '?' (or '$')
                RDFNode sourceP = rb.get("sourceP");
                RDFNode outPort = rb.get("outPort");
                RDFNode dataLink = rb.get("dataLink");
                RDFNode inPort = rb.get("inPort");
                RDFNode destP = rb.get("destP");
                RDFNode sourcetitle = rb.get("sourcetitle");
                RDFNode desTtitle = rb.get("desTtitle");
                RDFNode sourceOpTitle = rb.get("sourceOpTitle");
                RDFNode destOpTilte = rb.get("destOpTilte");

                RDFNode inputVarName = rb.get("inputVarName");
                RDFNode inputParticleName = rb.get("inputParticleName");
                RDFNode outputVarName = rb.get("outputVarName");
                RDFNode outputParticleName = rb.get("outputParticleName");

                SourceProcessToDestProcesses toDestProcesses = new SourceProcessToDestProcesses();

                if (sourceP.isResource()) {
                    toDestProcesses.setSourceProcess(sourceP.asResource());
                }
                if (outPort.isResource()) {
                    toDestProcesses.setOutputPort(outPort.asResource());
                }
                if (dataLink.isResource()) {
                    toDestProcesses.setDataLink(dataLink.asResource());
                }
                if (inPort.isResource()) {
                    toDestProcesses.setInputPort(inPort.asResource());
                }
                if (destP.isResource()) {
                    toDestProcesses.setDestinationProcess(destP.asResource());
                }
                if (sourcetitle.isLiteral()) {
                    toDestProcesses.setSourceProcessTitle(sourcetitle.asLiteral().getString());
                }
                if (desTtitle.isLiteral()) {
                    toDestProcesses.setDestinationProcessTitle(desTtitle.asLiteral().getString());
                }
                if (sourceOpTitle.isLiteral()) {
                    toDestProcesses.setSourceProcessOperationTitle(sourceOpTitle.asLiteral().getString());
                }
                if (destOpTilte.isLiteral()) {
                    toDestProcesses.setDestinationProcessOperationTitle(destOpTilte.asLiteral().getString());
                }
                if (inputVarName.isLiteral()) {
                    toDestProcesses.setInputPortVariableName(inputVarName.asLiteral().getString());
                }
                if (inputParticleName.isLiteral()) {
                    toDestProcesses.setInputVariableParticleName(inputParticleName.asLiteral().getString());
                }

                if (outputVarName.isLiteral()) {
                    toDestProcesses.setOutputPortVariableName(outputVarName.asLiteral().getString());
                }
                if (outputParticleName.isLiteral()) {
                    toDestProcesses.setOutputVariableParticleName(outputParticleName.asLiteral().getString());
                }


                sourceProcessToDestProcesses.add(toDestProcesses);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        sourceProcessToDestProcesses.forEach(System.out::println);
        List<Data> inDataList = dataList.get("inDataList");
        List<Data> outDataList = dataList.get("outDataList");
        List<Data> inDataListWorkflow = dataList.get("inDataListWorkflow");
        List<Data> outDataListWorkflow = dataList.get("outDataListWorkflow");
        inDataListWorkflow.forEach(data -> {
            String operationName = data.getOperationName();
            String variableName = data.getVariableName().toString();
            String variableParitcle = data.getLabel();
            String value = data.getValue();
            sourceProcessToDestProcesses.forEach(sourceProcessToDestP -> {
                String sourceProcessOperationTitle = sourceProcessToDestP.getSourceProcessOperationTitle();
                String inputPortVariableName = sourceProcessToDestP.getOutputPortVariableName();
                String inputVariableParticleName = sourceProcessToDestP.getOutputVariableParticleName();
                if (sourceProcessToDestP.getSourceProcessOperationTitle().equals(operationName) &&
                        sourceProcessToDestP.outputPortVariableName.equals(variableName) &&
                        sourceProcessToDestP.getOutputVariableParticleName().equals(variableParitcle)) {

                    Resource dataResouce = bpelRetroToProspAttachment.createData(variableName, variableParitcle, value);
                    bpelRetroToProspAttachment.createDataOnLink(sourceProcessToDestP.getDataLink(), dataResouce);
                    bpelRetroToProspAttachment.processProducedData(sourceProcessToDestP.getSourceProcess(), dataResouce);
                    bpelRetroToProspAttachment.processConsumedData(sourceProcessToDestP.getDestinationProcess(), dataResouce);
                    bpelRetroToProspAttachment.wasInfromedBy(sourceProcessToDestP.getDestinationProcess(), sourceProcessToDestP.getSourceProcess());

                }
            });
        });
        outDataList.forEach(data -> {
            String operationName = data.getOperationName();
            String variableName = data.getVariableName().toString();
            String variableParitcle = data.getLabel();
            String value = data.getValue();
            sourceProcessToDestProcesses.forEach(sourceProcessToDestP -> {
                String sourceProcessOperationTitle = sourceProcessToDestP.getSourceProcessOperationTitle();
                String inputPortVariableName = sourceProcessToDestP.getOutputPortVariableName();
                String inputVariableParticleName = sourceProcessToDestP.getOutputVariableParticleName();
                if (sourceProcessToDestP.getSourceProcessOperationTitle().equals(operationName) &&
                        sourceProcessToDestP.getOutputPortVariableName().equals(variableName) &&
                        sourceProcessToDestP.getOutputVariableParticleName().equals(variableParitcle)) {

                    Resource dataResouce = bpelRetroToProspAttachment.createData(variableName, variableParitcle, value);
                    bpelRetroToProspAttachment.createDataOnLink(sourceProcessToDestP.getDataLink(), dataResouce);
                    bpelRetroToProspAttachment.processProducedData(sourceProcessToDestP.getSourceProcess(), dataResouce);
                    bpelRetroToProspAttachment.processConsumedData(sourceProcessToDestP.getDestinationProcess(), dataResouce);
                    bpelRetroToProspAttachment.wasInfromedBy(sourceProcessToDestP.getDestinationProcess(), sourceProcessToDestP.getSourceProcess());

                }
            });
        });

        bpelRetroToProspAttachment.updateJena(retroModel);

    }

    static Model attachProspectiveAndRetrospective(Model retrospective, BPELRetroToProspAttachment prosRetroAttachment) {
        final String wfprov = "http://purl.org/wf4ever/wfprov#";
        final String provone = "http://purl.org/provone#";
        final String type = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";

        List<Resource> processExeList = new ArrayList<Resource>();
        List<Resource> dataList = new ArrayList<Resource>();
        StmtIterator iter = null;
        Resource processExecResource = null;
        Model retrospectiveRDFModel = retrospective;
        Resource workflowResource = null;
        BPELRetroToProspAttachment prospAttachment = prosRetroAttachment; //new BPELRetroToProspAttachment(retrospectiveRDFModel, prospecitveGraphId);
        try {
            iter = retrospectiveRDFModel.listStatements();
            while (iter.hasNext()) {
                Statement stmt = iter.next();
                Resource s = stmt.getSubject();
                Resource p = stmt.getPredicate();
                RDFNode o = stmt.getObject();
                Property typeProperty = retrospectiveRDFModel.createProperty(type);
                Property processExec = retrospectiveRDFModel.createProperty(provone + "ProcessExec");

                Property typePropertyData = retrospectiveRDFModel.createProperty(type);
                Property propertyData = retrospectiveRDFModel.createProperty(provone + "Data");

                Statement typeStatement = s.getProperty(typeProperty);
                Statement typeStatementData = s.getProperty(typePropertyData);

                if (typeStatement != null && processExec.toString().equals(o.toString())) {
                    processExeList.add(s);
                } else if (typeStatementData != null && propertyData.toString().equals(o.toString())) {
                    dataList.add(s);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (iter != null) iter.close();
        }
        for (Resource processExeResource : processExeList) {
            Property titleProperty = retrospectiveRDFModel.getProperty(DCTerms.title.toString());
            Statement titleStatement = processExeResource.getProperty(titleProperty);
            String processTitle = titleStatement.getObject().toString();
            System.out.println("Title :" + processTitle);
            if (processTitle.matches("^.+?\\d$")) {
                processTitle = processTitle.substring(0, processTitle.length() - 2);
            }
            prospAttachment.createProcessExecInstance(processExeResource, processTitle);
        }

        prospAttachment.updateJena(retrospectiveRDFModel);

        return retrospective;
    }

}


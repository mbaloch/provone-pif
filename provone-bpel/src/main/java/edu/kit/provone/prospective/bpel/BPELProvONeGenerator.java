package edu.kit.provone.prospective.bpel;

import edu.kit.provone.prospective.bpel.model.DataLink;
import edu.kit.provone.prospective.bpel.model.InputPort;
import edu.kit.provone.prospective.bpel.model.OutputPort;
import edu.kit.provone.prospective.bpel.model.ProvOneProcess;
import edu.kit.provone.prospective.bpel.utils.RDFUtility;
import org.apache.jena.query.DatasetAccessor;
import org.apache.jena.query.DatasetAccessorFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.ode.bpel.compiler.BpelCompiler20;
import org.apache.ode.bpel.compiler.DefaultResourceFinder;
import org.apache.ode.bpel.compiler.bom.BpelObjectFactory;
import org.apache.ode.bpel.compiler.bom.Process;
import org.apache.ode.bpel.o.OProcess;
import org.apache.ode.utils.StreamUtils;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.xml.sax.InputSource;

import javax.wsdl.Definition;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Created by mukhtar on 18.07.17.
 */
public class BPELProvONeGenerator {
    private Map<Object, Resource> objectResourceMap = new HashMap<>();
    String serviceURI = "http://localhost:3030/kit/";

    public String generateProspective() throws Exception {
        UUID workflowId = UUID.randomUUID();
        String workflowID = "http://kit.edu/bpel/" + workflowId.toString();

        Model prospectiveModel = null;

        prospectiveModel = generateBPELRdf();
        saveRDFtoJenaStore(prospectiveModel, serviceURI, workflowID);
        return workflowId.toString();
    }

    private static void saveRDFtoJenaStore(Model prosModel, String serviceURI, String workflowID) throws Exception {
        DatasetAccessor accessor = DatasetAccessorFactory.createHTTP(serviceURI);
        if (!accessor.containsModel(workflowID)) {
            Model workflowContainerModel = ModelFactory.createDefaultModel();
            accessor.putModel(workflowID, prosModel);
        } else {
            throw new Exception("Prospective graph for the givern workflow id already exists in store");
        }

    }

    public Model generateBPELRdf() throws Exception {

        BPELProvOne provOne;
        OProcess BpelProcessObject;

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL resource = classLoader.getResource("bpelAdd.bpel");
        String bpelFilePath = resource.getFile();
        Process process;
        File _bpelFile = new File(bpelFilePath);

        InputSource isrc = new InputSource(new ByteArrayInputStream(StreamUtils.read(_bpelFile.toURL())));
        isrc.setSystemId(_bpelFile.getAbsolutePath());
        process = BpelObjectFactory.getInstance().parse(isrc, _bpelFile.toURI());
        BpelCompiler20 BPEL2Compiler = new BpelCompiler20();
        String bpelPath = _bpelFile.getAbsolutePath();
        String cbpPath = bpelPath.substring(0, bpelPath.lastIndexOf(".")) + ".cbp";
        File suDir = _bpelFile.getParentFile();
        DefaultResourceFinder wf = new DefaultResourceFinder(_bpelFile.getAbsoluteFile().getParentFile(), suDir.getAbsoluteFile());
        long version = 0;

        BpelProcessObject = BPEL2Compiler.compile(process, wf, version);
        provOne = new BPELProvOne(BpelProcessObject);

        final Definition[] wsdlDefinitions = BPEL2Compiler.getWsdlDefinitions();
        List<String> wsdlFilesList = new ArrayList<>();
        for (Definition def : wsdlDefinitions) {
            String wsdlFilePath = def.getDocumentBaseURI().replaceFirst("file:/", "/");
            wsdlFilesList.add(wsdlFilePath);
        }
        provOne.setWsdlFilesList(wsdlFilesList);
        provOne.Prov2ONE(BpelProcessObject, Arrays.asList(BpelProcessObject.procesScope.activity));
        provOne.buildPorts();
        DirectedAcyclicGraph<Object, SequenceLink> dag = BPELProvOne.getDag();

        return constructRDF(dag, BpelProcessObject);

    }

    private Model constructRDF(DirectedAcyclicGraph<Object, SequenceLink> dag, OProcess bpelProcessObject) {
        RDFUtility rdfUtility = new RDFUtility();
        // recusive(dag.vertexSet(), rdfUtility, dag);
        final Set<Object> vertexSet = dag.vertexSet();
        vertexSet.stream().forEach(vertex -> {
            if (vertex instanceof DataLink) {
                DataLink dataLink = (DataLink) vertex;
                if (objectResourceMap.get(dataLink) == null) {
                    String uuid = UUID.randomUUID().toString();
                    Resource dataLinkResource = rdfUtility.createDataLink(uuid);
                    objectResourceMap.put(dataLink, dataLinkResource);
                }
            } else if (vertex instanceof InputPort) {
                InputPort inputPort = (InputPort) vertex;
                if (objectResourceMap.get(inputPort) == null) {
                    String uuid = UUID.randomUUID().toString();
                    Resource inputPortResource = rdfUtility.createInputPort(uuid, inputPort.getVariableQname().toString(), inputPort.getParticle().getParticleName());
                    objectResourceMap.put(inputPort, inputPortResource);
                }
            } else if (vertex instanceof OutputPort) {
                OutputPort outputPort = (OutputPort) vertex;
                if (objectResourceMap.get(outputPort) == null) {
                    String uuid = UUID.randomUUID().toString();
                    Resource outputPortResouce = rdfUtility.createOutputPort(uuid, outputPort.getVariableQname().toString(), outputPort.getParticle().getParticleName());
                    objectResourceMap.put(outputPort, outputPortResouce);
                }
            } else if (vertex instanceof ProvOneProcess) {
                ProvOneProcess provOneProcess = (ProvOneProcess) vertex;
                if (objectResourceMap.get(provOneProcess) == null) {
                    String uuid = UUID.randomUUID().toString();
                    Resource process = rdfUtility.createProcess(uuid, provOneProcess.getActivityName(), provOneProcess.getOperationName());
                    objectResourceMap.put(provOneProcess, process);
                    if (provOneProcess.getActivityType() != null && provOneProcess.getActivityType().equals("receive")) {
                        String uuid2 = UUID.randomUUID().toString();
                        Resource workflowResource = rdfUtility.createProcess(uuid2, bpelProcessObject.getQName().toString(), null);
                        String uuid3 = UUID.randomUUID().toString();
                        Resource scl = rdfUtility.createSeqCtrlLink(workflowResource, process, uuid3);
                        rdfUtility.CLtoDestP(process, scl);
                        rdfUtility.sourcePToCL(workflowResource, scl);
                    }
                }
            }
            final Set<SequenceLink> incomingEdgesOf = dag.incomingEdgesOf(vertex);

            incomingEdgesOf.forEach(sequenceLink -> {
                final Object edgeSource = dag.getEdgeSource(sequenceLink);
                if (vertex instanceof InputPort) {
                    InputPort inputPort = (InputPort) vertex;
                    if (sequenceLink.getTitle().equals("hasInPort")) {
                        final Resource sourceResource = objectResourceMap.get(edgeSource);

                        final Resource destResource = objectResourceMap.get(vertex);
                        String uuid2 = UUID.randomUUID().toString();
                        rdfUtility.hasInPort(sourceResource, destResource);
                    }
                    if (sequenceLink.getTitle().equals("DLToInPort")) {
                        Resource sourceResource = objectResourceMap.get(edgeSource);
                        final Resource destResource = objectResourceMap.get(vertex);

                        if (sourceResource == null) {
                            DataLink dataLink = (DataLink) edgeSource;
                            String uuid = UUID.randomUUID().toString();
                            Resource dataLinkResource = rdfUtility.createDataLink(uuid);
                            objectResourceMap.put(dataLink, dataLinkResource);
                        }
                        sourceResource = objectResourceMap.get(edgeSource);
                        rdfUtility.DLToInPort(sourceResource, destResource);
                    }

                } else if (vertex instanceof OutputPort) {
                    OutputPort outputPort = (OutputPort) vertex;
                    if (sequenceLink.getTitle().equals("hasOutPort")) {
                        final Resource sourceResource = objectResourceMap.get(edgeSource);
                        final Resource destResource = objectResourceMap.get(vertex);
                        String uuid2 = UUID.randomUUID().toString();
                        rdfUtility.hasOutPort(sourceResource, destResource);
                    }
                } else if (vertex instanceof DataLink) {
                    DataLink dataLink = (DataLink) vertex;
                    if (sequenceLink.getTitle().equals("outPortToDL")) {
                        Resource sourceResource = objectResourceMap.get(edgeSource);
                        final Resource destResource = objectResourceMap.get(vertex);
                        rdfUtility.outPortToDL(sourceResource, destResource);
                    }

                } else if (vertex instanceof ProvOneProcess) {
                    ProvOneProcess provOneProcess = (ProvOneProcess) vertex;
                    if (sequenceLink.getTitle().equals("SCL")) {
                        Resource sourceProcess = objectResourceMap.get(edgeSource);
                        if (sourceProcess == null) {
                            String uuid = UUID.randomUUID().toString();
                            Resource process = rdfUtility.createProcess(uuid, provOneProcess.getActivityName(), provOneProcess.getOperationName());
                            objectResourceMap.put(provOneProcess, process);
                        }
                        sourceProcess = objectResourceMap.get(edgeSource);
                        final Resource destProcess = objectResourceMap.get(vertex);
                        String uuid2 = UUID.randomUUID().toString();
                        Resource scl = rdfUtility.createSeqCtrlLink(sourceProcess, destProcess, uuid2);
                        rdfUtility.CLtoDestP(destProcess, scl);
                        rdfUtility.sourcePToCL(sourceProcess, scl);
                    }
                }

            });

        });

//        System.out.println("============================== writing rdf =============================");
//        rdfUtility.getModel().write(System.out);
        return rdfUtility.getModel();
    }
}

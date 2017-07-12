package edu.kit.provone.prospective.bpel;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import edu.kit.provone.prospective.bpel.model.DataLink;
import edu.kit.provone.prospective.bpel.model.InputPort;
import edu.kit.provone.prospective.bpel.model.OutputPort;
import edu.kit.provone.prospective.bpel.model.ProvOneProcess;
import edu.kit.provone.prospective.bpel.utils.RDFUtility;
import org.apache.jena.rdf.model.Resource;
import org.apache.ode.bpel.compiler.BpelCompiler20;
import org.apache.ode.bpel.compiler.DefaultResourceFinder;
import org.apache.ode.bpel.compiler.bom.BpelObjectFactory;
import org.apache.ode.bpel.compiler.bom.Process;
import org.apache.ode.bpel.o.OProcess;
import org.apache.ode.bpel.o.OScope;
import org.apache.ode.utils.StreamUtils;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import javax.swing.*;
import javax.wsdl.Definition;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by MUKHTAR on 7/1/2017.
 */
public class BPELProvOneTest {
    BPELProvOne provOne;
    OProcess BpelProcessObject;
    @Before
    public void setUp() throws Exception {

        //String bpelFilePath="/home/mukhtar/Desktop/workflows/test/BPEL_ADD/bpelAdd.bpel";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        URL resource = classLoader.getResource("bpelAdd.bpel");
        String bpelFilePath = resource.getFile();

        // String wsdlFilePath="/home/mukhtar/Desktop/workflows/test/BPEL_ADD/bpelAddArtifacts.wsdl";
        // String baseDir="/home/mukhtar/Desktop/workflows/test/BPEL_ADD/";
        Process process;
        File _bpelFile = new File(bpelFilePath);
        try {
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

//            Field field = BpelCompiler.class.getDeclaredField("_wsdlRegistry");
//            field.setAccessible(true);
//            Class<?> targetType = field.getType();
//            Object value = field.get(BPEL2Compiler);
//            demo demo=new demo();
//            demo.set(value);
//            final SchemaModel schemaModel = demo.get();
//            XSComplexTypeDecl decl=new XSComplexTypeDecl();


            //  Class c=Class.forName("WSDLRegistry");
            // ((c) value)._model;

            final Definition[] wsdlDefinitions = BPEL2Compiler.getWsdlDefinitions();
            List<String> wsdlFilesList = new ArrayList<>();
            for (Definition def : wsdlDefinitions) {
                System.out.println(def);
                String wsdlFilePath = def.getDocumentBaseURI().replaceFirst("file:/", "/");
                wsdlFilesList.add(wsdlFilePath);
            }
            provOne.setWsdlFilesList(wsdlFilesList);

//            SchemaParserHelper instance = new SchemaParserHelper();
//            Map<String, Map<QName, List<ElementParticle>>> result = instance.parseSchemaTypes(wsdlFilesList);
//            final HashMap<String, OScope.Variable> variables = BpelProcessObject.procesScope.variables;
//            String namespaceURI="http://kit.demo/";
//            String localPart="Add";
//            QName qname=new QName(namespaceURI,localPart);
//            final List<Map.Entry<QName, List<ElementParticle>>> entries = instance.resolveType(qname);

            //      final SchemaModel schemaModel = ((BpelCompiler20) BPEL2Compiler)._wsdlRegistry.getSchemaModel();
            System.out.println("hi");


//             List<OBase> children = oprocess.getChildren();
//             Definition[] wsdlDefinitions = compiler2.getWsdlDefinitions();
//             String namespaceURI="http://kit.demo/";
//             String localPart="Add";
//             QName qname=new QName(namespaceURI,localPart);
//             List<OScope.Variable> accessibleVariables = compiler2.getAccessibleVariables();
//            System.out.println("hi");
//
        } catch (Exception e) {
            //  CompilationMessage cmsg = __cmsgs.errBpelParseErr().setSource(new SourceLocationImpl(bpelFile.toURI()));
            System.out.println("ERRROR in compilation");
            throw e;
            //throw new CompilationException(cmsg, e);
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void prov2ONE() throws Exception {
        System.out.println("process name:" + BpelProcessObject.processName);
        final String name = BpelProcessObject.procesScope.name;
        OScope rootScope = BpelProcessObject.procesScope;
        String type = rootScope.activity.getType();
        String name2 = BpelProcessObject.procesScope.name;
        provOne.Prov2ONE(BpelProcessObject, Arrays.asList(BpelProcessObject.procesScope.activity));
        provOne.buildPorts();

        /*
        TProcess process = new TProcess();
        process.setName("workflow1");
        TSequence sequence = new TSequence();
        sequence.setName("Main Sequence");
        TFlow flow=new TFlow();
        flow.setName("Flow");
        TInvoke invoke = new TInvoke();
        invoke.setName("X");
        TInvoke invoke2 = new TInvoke();
        invoke2.setName("Y");

        TIf ifActivity=new TIf();
        //ifActivity

        //process.setSequence(f);
        //provOne.Prov2ONE(process, Arrays.asList(invoke,flow,invoke2));
      //  provOne.Prov2ONE(process, Arrays.asList(invoke,invoke2,sequence,flow));
        provOne.Prov2ONE(process, Arrays.asList(ifActivity,invoke,invoke2));
       */
        DirectedAcyclicGraph<Object, SequenceLink> dag = BPELProvOne.getDag();
        System.out.println("dag:" + dag);
        constructRDF(dag);

        // JGraphXAdapterDemo applet = new JGraphXAdapterDemo();
        JGraphXAdapter<Object, SequenceLink> jgxAdapter;
        jgxAdapter = new JGraphXAdapter<>(dag);

        // getContentPane().add(new mxGraphComponent(jgxAdapter));

        JFrame frame = new JFrame();
        frame.getContentPane().add(new mxGraphComponent(jgxAdapter));
        Dimension DEFAULT_SIZE = new Dimension(530, 320);
        frame.setSize(DEFAULT_SIZE);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
//        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
//        layout.execute(jgxAdapter.getDefaultParent());
        mxHierarchicalLayout layout = new mxHierarchicalLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());

        while (true) {
            Thread.sleep(2000);
        }

    }

    Map<Object, Resource> objectResourceMap = new HashMap<>();

    private void constructRDF(DirectedAcyclicGraph<Object, SequenceLink> dag) {
        RDFUtility rdfUtility = new RDFUtility();
        // recusive(dag.vertexSet(), rdfUtility, dag);
        final Set<Object> vertexSet = dag.vertexSet();
        vertexSet.stream().forEach(vertex -> {
            if (vertex instanceof DataLink) {
                DataLink dataLink = (DataLink) vertex;
                if(objectResourceMap.get(dataLink)== null) {
                    String uuid = UUID.randomUUID().toString();
                    Resource dataLinkResource = rdfUtility.createDataLink(uuid.substring(uuid.length() - 6));
                    objectResourceMap.put(dataLink, dataLinkResource);
                }
            } else if (vertex instanceof InputPort) {
                InputPort inputPort = (InputPort) vertex;
                if(objectResourceMap.get(inputPort)== null) {
                    String uuid = UUID.randomUUID().toString();
                    Resource inputPortResource = rdfUtility.createInputPort(uuid.substring(uuid.length() - 6), inputPort.getVariableQname().toString(), inputPort.getParticle().getParticleName());
                    objectResourceMap.put(inputPort, inputPortResource);
                }
            } else if (vertex instanceof OutputPort) {
                OutputPort outputPort = (OutputPort) vertex;
                if(objectResourceMap.get(outputPort)== null) {
                    String uuid = UUID.randomUUID().toString();
                    Resource outputPortResouce = rdfUtility.createOutputPort(uuid.substring(uuid.length() - 6), outputPort.getVariableQname().toString(), outputPort.getParticle().getParticleName());
                    objectResourceMap.put(outputPort, outputPortResouce);
                }
            } else if (vertex instanceof ProvOneProcess) {
                ProvOneProcess provOneProcess = (ProvOneProcess) vertex;
                if(objectResourceMap.get(provOneProcess)== null) {
                    String uuid = UUID.randomUUID().toString();
                    Resource process = rdfUtility.createProcess(uuid.substring(uuid.length() - 6), provOneProcess.getActivityName());
                    objectResourceMap.put(provOneProcess, process);
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
                        rdfUtility.hasInPort(sourceResource,destResource);
                    }
                    if (sequenceLink.getTitle().equals("DLToInPort")) {
                        Resource sourceResource = objectResourceMap.get(edgeSource);
                        final Resource destResource = objectResourceMap.get(vertex);

                        if (sourceResource==null){
                            DataLink dataLink = (DataLink) edgeSource;
                            String uuid = UUID.randomUUID().toString();
                            Resource dataLinkResource=rdfUtility.createDataLink(uuid.substring(uuid.length() - 6));
                            objectResourceMap.put(dataLink,dataLinkResource);
                        }
                        sourceResource = objectResourceMap.get(edgeSource);
                        rdfUtility.DLToInPort(sourceResource,destResource);
                    }

                } else if (vertex instanceof OutputPort) {
                    OutputPort outputPort = (OutputPort) vertex;
                    if (sequenceLink.getTitle().equals("hasOutPort")) {
                        final Resource sourceResource = objectResourceMap.get(edgeSource);
                        final Resource destResource = objectResourceMap.get(vertex);
                        String uuid2 = UUID.randomUUID().toString();
                        rdfUtility.hasOutPort(sourceResource,destResource);
                    }
                } else if (vertex instanceof DataLink) {
                    DataLink dataLink = (DataLink) vertex;
                    if (sequenceLink.getTitle().equals("outPortToDL")) {
                        Resource sourceResource = objectResourceMap.get(edgeSource);
                        final Resource destResource = objectResourceMap.get(vertex);
                        rdfUtility.outPortToDL(sourceResource,destResource);
                    }

                } else if (vertex instanceof ProvOneProcess) {
                    ProvOneProcess provOneProcess = (ProvOneProcess) vertex;
                    if (sequenceLink.getTitle().equals("SCL")) {
                        Resource sourceProcess = objectResourceMap.get(edgeSource);
                        if(sourceProcess== null) {
                            String uuid = UUID.randomUUID().toString();
                            Resource process = rdfUtility.createProcess(uuid.substring(uuid.length() - 6), provOneProcess.getActivityName());
                            objectResourceMap.put(provOneProcess, process);
                        }
                        sourceProcess = objectResourceMap.get(edgeSource);
                        final Resource destProcess = objectResourceMap.get(vertex);
                        String uuid2 = UUID.randomUUID().toString();
                        Resource scl = rdfUtility.createSeqCtrlLink(sourceProcess, destProcess, uuid2.substring(uuid2.length() - 6));
                        rdfUtility.CLtoDestP(destProcess, scl);
                        rdfUtility.sourcePToCL(sourceProcess, scl);
                    }
                }

            });

        });

        System.out.println("============================== writing rdf =============================");
        rdfUtility.getModel().write(System.out);
    }

    @Test
    public void children() throws Exception {
    }

    @Test
    public void getChildren() throws Exception {
    }

    @Test
    public void getDag() throws Exception {
    }

    @Test
    public void constructActivityElement() throws Exception {
    }

    @Test
    public void buildPorts() throws Exception {
    }

    @Test
    public void getWsdlFilesList() throws Exception {
    }

    @Test
    public void setWsdlFilesList() throws Exception {
    }

}
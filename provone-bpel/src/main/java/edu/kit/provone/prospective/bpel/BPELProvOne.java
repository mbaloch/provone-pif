package edu.kit.provone.prospective.bpel;

import edu.kit.provone.prospective.bpel.model.*;
import edu.kit.provone.prospective.bpel.utils.SchemaParserHelper;
import groovy.xml.QName;
import edu.kit.provone.prospective.bpel.utils.SchemaType;
import org.apache.jena.rdf.model.Resource;
import org.apache.ode.bpel.compiler.BpelCompiler20;
import org.apache.ode.bpel.elang.xpath10.o.OXPath10Expression;
import org.apache.ode.bpel.o.*;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Created by mukhtar on 02.02.17.
 */
public class BPELProvOne {


    ArrayList<Class> A = new ArrayList<Class>();
    ArrayList<Class> B = new ArrayList<Class>();
    ArrayList<Class> P = new ArrayList<Class>();
    Stack<ParserObject> S = new Stack<ParserObject>();
    //  RDFUtility rdfUtality = new RDFUtility();
    //Stack<OVariables> scopeVariables = new Stack<>();
    HashMap<OActivity, Resource> parserObjectRdfMap = new HashMap<OActivity, Resource>();
    Resource workflowRoot;
    private static final Logger logger = LoggerFactory.getLogger(BPELProvOne.class);
    final OProcess processRoot;
    private List<String> wsdlFilesList = new ArrayList<>();
    Map<String, Map<QName, List<SchemaType>>> schemaTypeDefinations;
    SchemaParserHelper schemaParserHelper = new SchemaParserHelper();
    Map<OScope.Variable, List<Port>> variableToPorts = new HashMap<>();
    Map<Port, Port> portRelations = new HashMap<>();

    public BPELProvOne(OProcess processRoot) {
        A.add(OSequence.class);
        A.add(OProcess.class);
        A.add(OWhile.class);
        A.add(OScope.class);
        A.add(OSwitch.OCase.class);

        B.add(OFlow.class);
        //B.add(OPick.class);
        //B.add(OIf.class);
        B.add(OSwitch.class);
        //  B.add(OAssign.class);

        P.add(OInvoke.class);
        P.add(OPickReceive.class);
        P.add(OReply.class);
        //P.add(OOnMessage.class);
        P.add(OAssign.class); //not as per the the algorithm
        //P.add(OSwitch.OCase.class);
        P.add(PCase.class);
        P.add(PWhile.class);
        P.add(null);

        //   this.bpelParser = bpelParser;
        ParserObject rootObject = new ParserObject();
        //OProcess process = new OProcess();
        //process.setName("Workflow");
        rootObject.setStructure(processRoot);

        S.add(rootObject);
        this.processRoot = processRoot;

    }

    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

    public void Prov2ONE(OProcess workflow, List<? extends OBase> C) {

        if (C == null || C.size() == 0) {
            return;
        }
        if (variablePartsList.size() == 0)
            processVariable(workflow);

        OBase activity;
        for (Object act : C) {
            if (act instanceof OActivity || act instanceof OSwitch.OCase) {

                if (act instanceof OSwitch.OCase) {
                    activity = (OSwitch.OCase) act;
                } else {
                    activity = (OActivity) act;
                }
                if (P.contains(activity.getClass())) {
                    //  GenerateProvONE(activity);
                    constructActivityElement(activity);

                }
                //if the activity is a structural element (sequential or parallel

                if (A.contains(activity.getClass()) || B.contains(activity.getClass())) {
                    ParserObject lastObject = S.peek();
                    ParserObject currentObject = new ParserObject();
                    currentObject.setStructure(activity);
                    // if last activity is a sequential element
                    if (A.contains(lastObject.getStructure().getClass())) {
                        if (lastObject.getOne().equals(lastObject.getTwo())) {
                            // if current activity is also a sequential element
                            if (A.contains(activity.getClass())) {
                                currentObject.setTwo((LinkedHashSet<OBase>) lastObject.getOne().clone());
                            }
                            lastObject.setOne(new LinkedHashSet<OBase>());
                        }
                        currentObject.getOne().clear();
                        currentObject.setOne((LinkedHashSet<OBase>) lastObject.getTwo().clone());
                        lastObject.setTwo(new LinkedHashSet<OBase>());
                    } else {
                        currentObject.setOne((LinkedHashSet<OBase>) lastObject.getOne().clone());
                        lastObject.setOne(new LinkedHashSet<OBase>());
                    }
                    if (A.contains(activity.getClass()) && currentObject.getTwo().size() == 0) {
                        currentObject.setTwo((LinkedHashSet<OBase>) currentObject.getOne().clone());
                    }
                    S.push(currentObject);
                    Prov2ONE(workflow, children(activity));
                    currentObject = S.pop();
                    if (!S.empty()) {
                        lastObject = S.peek();
                        if (lastObject.getOne().size() == 0) {
                            lastObject.setOne(currentObject.getOne());
                        }
                        if (A.contains(lastObject.getStructure().getClass())) {
                            lastObject.setTwo(currentObject.getTwo());
                        } else {
                            lastObject.getTwo().addAll(currentObject.getTwo());
                        }
                    }

                }
            }
        }
    }

    // Map<OScope.Variable,List<Port>> variableToPortsTempMap = new HashMap<>();
    List<List<Map.Entry<QName, List<SchemaType>>>> variablePartsList = new ArrayList<>();
    Map<OScope.Variable, List<Map.Entry<QName, List<SchemaType>>>> variableAndPartListMap = new HashMap<>();

    private void processVariable(OProcess workflow) {
        //final Set<OScope.Variable> variableRd = workflow.procesScope.variableRd;
        final HashMap<String, OScope.Variable> variables = workflow.procesScope.variables;
        variables.entrySet().stream().forEach(stringVariableEntry -> {
            final List<Map.Entry<QName, List<SchemaType>>> variableDetials = getVariableDetials(stringVariableEntry.getValue());
            variablePartsList.add(variableDetials);
            variableAndPartListMap.put(stringVariableEntry.getValue(), variableDetials);
        });
        //    List<Map.Entry<QName, List<SchemaType>>> inputVariableDetials = getVariableDetials(inputVar);
        //final List<Map.Entry<QName, List<SchemaType>>> outputVariableDetials = getVariableDetials(outputVar);
        //variables.

        System.out.println("finished processing variables");

    }

    List<? extends OBase> children(OBase activity) {

        if (activity.getClass() == OSequence.class) {
            final OSequence oSequence = (OSequence) activity;
            return oSequence.sequence;
        } else if (activity.getClass() == OScope.class) {
            final OScope oScope = (OScope) activity;
            return Arrays.asList(oScope.activity);
        } else if (activity.getClass() == OSwitch.class) {
            final OSwitch oSwitch = (OSwitch) activity;
            final List<OSwitch.OCase> oSwitchCases = oSwitch.getCases();
            return oSwitchCases;

        } else if (activity.getClass().getName().equals("org.apache.ode.bpel.o.OSwitch$OCase")) {
            final OSwitch.OCase oCase = (OSwitch.OCase) activity;
            PCase pCase = new PCase(oCase.getOwner());
            pCase.expression = oCase.expression;
            return Arrays.asList(pCase, oCase.activity);

        } else if (activity.getClass() == OWhile.class) {
            final OWhile oWhile = (OWhile) activity;
            PWhile pWhile = new PWhile(oWhile.getOwner(), oWhile.getParent());
            pWhile.whileCondition = oWhile.whileCondition;
            pWhile.name = oWhile.name;
            return Arrays.asList(pWhile, oWhile.activity);
        }

        return null;


    }

    List<Object> getChildren(OActivity activity) {
        return new ArrayList<Object>();
    }

    //  static DirectedAcyclicGraph<String, DefaultEdge> dag = new DirectedAcyclicGraph<String, DefaultEdge>(DefaultEdge.class);
    static DirectedAcyclicGraph<Object, SequenceLink> dag = new DirectedAcyclicGraph<Object, SequenceLink>(SequenceLink.class);

    public static DirectedAcyclicGraph<Object, SequenceLink> getDag() {
        return dag;
    }

    List<String> V = new ArrayList<>();

    void constructActivityElement(OBase activity) {

        ParserObject top = S.peek();
        if (activity instanceof OAssign) {
            handleAssign(activity);
        } else {

            if (A.contains(top.getStructure().getClass())) {
                LinkedHashSet<OBase> tail = top.getTwo();
                connect(tail, activity);
                top.getTwo().clear();
                top.getTwo().add(activity);
            } else {
                LinkedHashSet<OBase> head = top.getOne();
                connect(head, activity);
                top.getTwo().add(activity);
            }
            attachPorts(activity);
        }
    }

    public void buildPorts() {
        final Set<Object> vertexSet = dag.vertexSet();
//        vertexSet.forEach(o -> {
//            if(o instanceof InputPort){
//                InputPort inputPort=(InputPort)o;
//            }
//        });
        final Set<Map.Entry<Port, Port>> entrySet = fromToPorts.entrySet();
        fromToPorts.entrySet().forEach(portPortEntry -> {
            DataLink dataLink = new DataLink();
            dataLink.setTitle("DataLink");
            dag.addVertex(dataLink);
            SequenceLink seq = new SequenceLink();
            seq.setTitle("outPortToDL");
            SequenceLink seq2 = new SequenceLink();
            seq2.setTitle("DLToInPort");
            final Port fromPort = portPortEntry.getKey();
            final Port toPort = portPortEntry.getValue();
            final OutputPort outputPort = vertexSet.stream().filter(o -> o instanceof Port)
                    .filter(o -> o instanceof OutputPort).map(o -> (OutputPort) o)
                    .filter(outputPort1 -> {
                        return fromPort.getVariableQname().equals(outputPort1.getVariableQname()) &&
                                fromPort.getParticle().getParticleName().equals(outputPort1.getParticle().getParticleName());
                    }).findFirst().get();
            final InputPort inputPort = vertexSet.stream().filter(o -> o instanceof Port)
                    .filter(o -> o instanceof InputPort).map(o -> (InputPort) o)
                    .filter(inputPort1 -> {
                        return toPort.getVariableQname().equals(inputPort1.getVariableQname()) &&
                                toPort.getParticle().getParticleName().equals(inputPort1.getParticle().getParticleName());
                    })
                    .findFirst().get();

            try {
                dag.addDagEdge(outputPort, dataLink, seq);
                dag.addDagEdge(dataLink, inputPort, seq2);
            } catch (DirectedAcyclicGraph.CycleFoundException e) {
                e.printStackTrace();
            }

        });
    }

    private void handleAssign(OBase activity) {
        if (activity instanceof OAssign) {
            final OAssign oAssign = (OAssign) activity;
            final List<OAssign.Copy> copyList = oAssign.copy;
            for (OAssign.Copy copy : copyList) {
                Port fromPort = new OutputPort();
                Port toPort = new InputPort();
                final OAssign.RValue from = copy.from;
                final OAssign.LValue to = copy.to;

                if (from instanceof OAssign.VariableRef) {
                    final OAssign.VariableRef fromVariable = (OAssign.VariableRef) from;
                    final OMessageVarType.Part part = fromVariable.part;
                    if (part.type instanceof OElementVarType) {
                        final OElementVarType oElementVarType = (OElementVarType) part.type;
                        final OExpression location = fromVariable.location;
                        OXPath10Expression oxPath10Expression = (OXPath10Expression) location;
                        final String xpath = oxPath10Expression.xpath;
                        String elementName;
                        if (xpath.contains(":")) {
                            final String[] split = xpath.split(":");
                            elementName = split[1];
                        } else if (xpath.contains("/")) {
                            elementName = xpath.substring(xpath.lastIndexOf("/") + 1);
                        } else {
                            elementName = xpath;
                        }
                        System.out.println("ElementName in from:" + elementName);
                        QName element = new QName(oElementVarType.elementType.getNamespaceURI(), oElementVarType.elementType.getLocalPart());

                        variablePartsList.forEach((List<Map.Entry<QName, List<SchemaType>>> entries1) -> {
                            entries1.forEach(qNameListEntry -> {
                                if (qNameListEntry.getKey().equals(element)) {
                                    final List<SchemaType> value = qNameListEntry.getValue();
                                    value.forEach(schemaType -> {
                                        if (schemaType.getParticleName().equals(elementName)) {
                                            System.out.println("copy from schema type:" + schemaType);
                                            fromPort.setVariableQname(element);
                                            fromPort.setParticle(schemaType);
                                        }
                                    });

                                }

                            });
                        });

                    }

                    if (to instanceof OAssign.VariableRef) {
                        final OAssign.VariableRef toVariableRef = (OAssign.VariableRef) to;
                        final OMessageVarType.Part toPart = toVariableRef.part;
                        if (toPart.type instanceof OElementVarType) {
                            final OElementVarType oElementVarType = (OElementVarType) toPart.type;
                            OXPath10Expression oxPath10Expression = (OXPath10Expression) toVariableRef.location;
                            final String xpath = oxPath10Expression.xpath;
                            String elementName;
                            if (xpath.contains(":")) {
                                final String[] split = xpath.split(":");
                                elementName = split[1];
                            } else if (xpath.contains("/")) {
                                elementName = xpath.substring(xpath.lastIndexOf("/") + 1);
                            } else {
                                elementName = xpath;
                            }
                            System.out.println("ElementName in to:" + elementName);

                            QName element = new QName(oElementVarType.elementType.getNamespaceURI(), oElementVarType.elementType.getLocalPart());
                            variablePartsList.forEach((List<Map.Entry<QName, List<SchemaType>>> entries1) -> {
                                entries1.forEach(qNameListEntry -> {
                                    if (qNameListEntry.getKey().equals(element)) {
                                        final List<SchemaType> value = qNameListEntry.getValue();
                                        value.forEach(schemaType -> {
                                            if (schemaType.getParticleName().equals(elementName)) {
                                                System.out.println("Copy to schema type:" + schemaType);
                                                toPort.setVariableQname(element);
                                                toPort.setParticle(schemaType);
                                            }
                                        });
                                    }

                                });
                            });

                        }
                        if (fromPort.getParticle() != null && toPort.getParticle() != null) {
                            fromToPorts.put(fromPort, toPort);
                        }
                    }

                } else if (from instanceof OAssign.Literal) {
                    final OAssign.Literal fromLiteral = (OAssign.Literal) from;
                } else if (from instanceof OAssign.Expression) {
                    final OAssign.Expression fromExpression = (OAssign.Expression) from;
                }

            }

        }
    }

    Map<OBase, ProvOneProcess> activityProvOneProcessMap = new HashMap<>();
    Map<Port, Port> fromToPorts = new HashMap<>();

    private void connect(LinkedHashSet<OBase> headOrtail, OBase activity) {

        if (activity instanceof PCase) {
            final PCase pCase = (PCase) activity;
            ProvOneProcess p1 = new ProvOneProcess();
            p1.setTitle(pCase.expression.toString());
            dag.addVertex(p1);
            activityProvOneProcessMap.put(pCase, p1);
            headOrtail.forEach(lastElement -> {
                ProvOneProcess p2 = activityProvOneProcessMap.get(lastElement);
                if (p2 != null) {
                    SequenceLink seq = new SequenceLink();
                    try {
                        dag.addDagEdge(p2, p1, seq);
                    } catch (DirectedAcyclicGraph.CycleFoundException ex) {
                        java.util.logging.Logger.getLogger(BPELProvOne.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        } else if (activity instanceof OInvoke || activity instanceof OReply || activity instanceof OPickReceive) {
            OActivity oActivity = (OActivity) activity;
            ProvOneProcess primitiveProcess = new ProvOneProcess();
            if (oActivity instanceof OInvoke) {
                final OInvoke oInvoke = (OInvoke) oActivity;
                primitiveProcess.setOperationName(oInvoke.operation.getName());
                primitiveProcess.setActivityName(oInvoke.name);
            } else if (oActivity instanceof OReply) {
                final OReply oReply = (OReply) oActivity;
                primitiveProcess.setOperationName(oReply.operation.getName());
                primitiveProcess.setActivityName(oReply.name);

            } else if (oActivity instanceof OPickReceive) {
                final OPickReceive oPickReceive = (OPickReceive) oActivity;
                primitiveProcess.setOperationName(oPickReceive.onMessages.get(0).operation.getName());
                primitiveProcess.setActivityName(oPickReceive.name);
                primitiveProcess.setActivityType("receive");
            }

            dag.addVertex(primitiveProcess);
            activityProvOneProcessMap.put(oActivity, primitiveProcess);
            headOrtail.forEach(lastElement -> {
                ProvOneProcess lastActivity = activityProvOneProcessMap.get(lastElement);
                if (lastActivity != null) {
                    SequenceLink seq = new SequenceLink();
                    seq.setTitle("SCL");
                    try {
                        dag.addDagEdge(lastActivity, primitiveProcess, seq);
                    } catch (DirectedAcyclicGraph.CycleFoundException ex) {
                        java.util.logging.Logger.getLogger(BPELProvOne.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        }

    }

    private List<Port> constructOutputPorts(OBase activity, List<Map.Entry<QName, List<SchemaType>>> outputVariableDetials) {
        List<Port> ports = new ArrayList<>();
        outputVariableDetials.stream().forEach(qNameListEntry -> {
            qNameListEntry.getValue().forEach(schemaType -> {
                OutputPort outputPort = new OutputPort();
                outputPort.setVariableQname(qNameListEntry.getKey());
                outputPort.setParticle(schemaType);
                dag.addVertex(outputPort);
                ports.add(outputPort);
                ProvOneProcess currentProcessNode = activityProvOneProcessMap.get(activity);
                if (currentProcessNode != null) {
                    SequenceLink seq = new SequenceLink();
                    seq.setTitle("hasOutPort");
                    try {
                        dag.addDagEdge(currentProcessNode, outputPort, seq);
                    } catch (DirectedAcyclicGraph.CycleFoundException ex) {
                        java.util.logging.Logger.getLogger(BPELProvOne.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });
        return ports;
    }

    private List<Port> constructInputPorts(OBase activity, List<Map.Entry<QName, List<SchemaType>>> inputVariableDetials) {
        List<Port> ports = new ArrayList<>();
        inputVariableDetials.stream().forEach(qNameListEntry -> {
            qNameListEntry.getValue().forEach(schemaType -> {
                InputPort inputPort = new InputPort();
                inputPort.setVariableQname(qNameListEntry.getKey());
                inputPort.setParticle(schemaType);
                dag.addVertex(inputPort);
                ports.add(inputPort);
                ProvOneProcess currentProcessNode = activityProvOneProcessMap.get(activity);
                if (currentProcessNode != null) {
                    SequenceLink seq = new SequenceLink();
                    seq.setTitle("hasInPort");
                    try {
                        dag.addDagEdge(currentProcessNode, inputPort, seq);
                    } catch (DirectedAcyclicGraph.CycleFoundException ex) {
                        java.util.logging.Logger.getLogger(BPELProvOne.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        });
        return ports;
    }

    //Set<> variableSet= new HashSet<>();


    private void attachPorts(OBase activity) {

        ProvOneProcess provOneProcess = activityProvOneProcessMap.get(activity);

        if (activity instanceof OInvoke) {
            OInvoke oInvoke = (OInvoke) activity;
            final OScope.Variable inputVar = oInvoke.inputVar;
            final OScope.Variable outputVar = oInvoke.outputVar;

            final List<Map.Entry<QName, List<SchemaType>>> inputVariableDetials = getVariableDetials(inputVar);
            final List<Map.Entry<QName, List<SchemaType>>> outputVariableDetials = getVariableDetials(outputVar);
            final List<Port> inputPorts = constructInputPorts(activity, inputVariableDetials);
            final List<Port> outputPorts = constructOutputPorts(activity, outputVariableDetials);
            variableToPorts.put(inputVar, inputPorts);
            variableToPorts.put(outputVar, outputPorts);

        } else if (activity instanceof OReply) {
            OReply oReply = (OReply) activity;
            final OScope.Variable variable = oReply.variable;
            final List<Map.Entry<QName, List<SchemaType>>> inputVariableDetials = getVariableDetials(variable);
            final List<Port> ports = constructInputPorts(activity, inputVariableDetials);
            variableToPorts.put(variable, ports);


        } else if (activity instanceof OPickReceive) {
            final OPickReceive oPickReceive = (OPickReceive) activity;
            final List<OScope.Variable> variables = oPickReceive.onMessages.stream().map(onMessage -> onMessage.variable).collect(Collectors.toList());
            variables.stream().forEach(variable -> {
                final List<Map.Entry<QName, List<SchemaType>>> outputVariableDetials = getVariableDetials(variable);
                final List<Port> ports = constructOutputPorts(activity, outputVariableDetials);
                variableToPorts.put(variable, ports);
            });

        }

    }

    private List<Map.Entry<QName, List<SchemaType>>> getVariableDetials(OScope.Variable variable) {
        //OConstantVarType
        //OElementVarType
        //OMessageVarType
        //OXsdTypeVarType
        //SchemaParserHelper schemaParserHelper = new SchemaParserHelper();
        if (this.schemaTypeDefinations == null) {
            //get all schema definitions
            this.schemaTypeDefinations = schemaParserHelper.parseSchemaTypes(getWsdlFilesList());
        }

        final OVarType oVarType = variable.type;

        if (oVarType instanceof OMessageVarType) {
            final OMessageVarType oVarMessageType = (OMessageVarType) oVarType;
            final List<Map.Entry<String, OMessageVarType.Part>> messageParts = oVarMessageType.parts.entrySet().stream().collect(Collectors.toList());
//            collect.forEach(stringPartEntry -> {
//                        final OMessageVarType.Part value = stringPartEntry.getValue();
//                        if (value.type instanceof OElementVarType) {
//                            final OElementVarType oElementVarType = (OElementVarType) value.type;
//                            final javax.xml.namespace.QName elementType = oElementVarType.elementType;
//                            final List<Map.Entry<QName, List<lsdma.kit.utils.ElementParticle>>> entries = schemaParserHelper.resolveType(new QName(elementType.getNamespaceURI(), elementType.getLocalPart()));
//                            System.out.println("abc");
//                        }
//
//                    }
//            );
            final List<Map.Entry<QName, List<SchemaType>>> messagePartsParticles = messageParts.stream()
                    .filter(stringPartEntry -> stringPartEntry.getValue().type instanceof OElementVarType)
                    .map(stringPartEntry -> (OElementVarType) stringPartEntry.getValue().type)
                    .flatMap(oElementVarType -> {
                        final javax.xml.namespace.QName elementType = oElementVarType.elementType;
                        final List<Map.Entry<QName, List<SchemaType>>> entries = schemaParserHelper.resolveType(new QName(elementType.getNamespaceURI(), elementType.getLocalPart()));
//                       if (entries.size()==1) {
                        final Map.Entry<QName, List<SchemaType>> qNameListEntry = entries.get(0);
                        final List<Map.Entry<QName, List<SchemaType>>> entryList = schemaParserHelper.resolveType(qNameListEntry.getValue().get(0).getDataType());
                        System.out.println("resolve inside elements");

                        //                     }
                        if (entryList.size() > 0)
                            return entryList.stream();
                        else
                            return entries.stream();


//                        entries.forEach(qNameListEntry -> {
//                            qNameListEntry.getValue().forEach(schemaType -> {
//                                final QName dataType = schemaType.getDataType();
//                                final List<Map.Entry<QName, List<SchemaType>>> resolveType = schemaParserHelper.resolveType(dataType);
//                                System.out.println("resolve inside elements");
//
//                            });
//                        });

                    }).collect(Collectors.toList());
            return messagePartsParticles;
        } else if (oVarType instanceof OElementVarType) {

        } else if (oVarType instanceof OXsdTypeVarType) {

        } else if (oVarType instanceof OConstantVarType) {

        }


//        String namespaceURI = "http://kit.demo/";
//        String localPart = "Add";
//        QName qname = new QName(namespaceURI, localPart);
//        final List<Map.Entry<QName, List<lsdma.kit.utils.ElementParticle>>> entries = schemaParserHelper.resolveType(qname);
//        System.out.println("var type:" + variable);
        return null;
    }

    private static void prepareSchemaParser(BpelCompiler20 bpelCompiler20) {

    }

    public List<String> getWsdlFilesList() {
        return wsdlFilesList;
    }

    public void setWsdlFilesList(List<String> wsdlFilesList) {
        this.wsdlFilesList = wsdlFilesList;
    }
}

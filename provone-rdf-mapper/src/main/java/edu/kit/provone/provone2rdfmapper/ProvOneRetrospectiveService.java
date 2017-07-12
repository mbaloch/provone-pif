package edu.kit.provone.provone2rdfmapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by mukhtar on 03.03.17.
 */
@Path("/Retro")
public class ProvOneRetrospectiveService {
    private final Logger logger = LoggerFactory.getLogger(ProvenanceRESTapi.class);

    IProvenanceAPI provenanceAPI = new ProvenanceAPIRDFImpl();//ProvenanceAPIArangoImpl();

    @GET
    @Path("ping")
    @Produces(MediaType.TEXT_HTML)
    public String test() {
        System.out.println("RESTful Service 'MessageService' is running ==> ping");
        return "received ping on " + new Date().toString();

    }

    @POST
    @Path("ActivityExecInstance")
    @Consumes(MediaType.APPLICATION_JSON)
    public void newProcessInstance(@NotNull @Valid final ActivityExec newActivityExec) {

        provenanceAPI.createProcessExecInstance(newActivityExec);
        logger.info("New ProcessExec Operation completed successfully:" + newActivityExec);
    }

    @POST
    @Path("WorkflowExecInstance")
    @Consumes(MediaType.APPLICATION_JSON)
    public void newWorkflowInstance(@NotNull @Valid final ProcessExec workflowInfo) {
        provenanceAPI.createWorkflowExecInstance(workflowInfo);

        logger.info("New Workflow Exec Operation completed successfully:" + workflowInfo);
    }

    @POST
    @Path("ChildProcessInstance")
    @Consumes(MediaType.APPLICATION_JSON)
    public void childProcessInstance(String parentProcessId, String currentProcess,
                                     ProcessExec processInfo) {
        logger.info("Operation completed successfully:");
    }


    @POST
    @Path("UpdateProcessStatus")
    @Consumes(MediaType.APPLICATION_JSON)
    public void processStatusChanged(ProcessExec processInfo) {
        provenanceAPI.updateProcessStatus(processInfo);
        logger.info("Operation completed successfully:");
    }

    @POST
    @Path("producedData")
    @Consumes(MediaType.APPLICATION_JSON)
    public void producedData(DataModification dataModification) {
        String variableName = dataModification.getVariableName();
        String dataInJson = dataModification.getDataNode();
        logger.info("data:" + dataInJson);
        logger.info("Operation completed successfully:");

    }
    static List<String> originalVariables=new ArrayList<>( );
    @POST
    @Path("consumedData")
    @Consumes(MediaType.APPLICATION_JSON)
    public void consumedData(DataModification dataNode) {
        String variableName = dataNode.getVariableName();
        String dataInJson = dataNode.getDataNode();
        //List<String> originalVariables=new ArrayList<>( );
        if (originalVariables.isEmpty()){
            ProvenanceAPIRDFImpl obj=new ProvenanceAPIRDFImpl();
            List<String> allVariableNames = obj.getAllVariableNames();
            originalVariables.addAll(allVariableNames);
        }


        /*
        originalVariables.addAll(Arrays.asList("input:num2",
                "input:num1",
                "simpleCalculatorPLResponse:return",
                "simpleCalculatorPLResponse1:return",
                "output:result",
                "simpleCalculatorPLRequest1:number",
                "simpleCalculatorPLRequest:secondNum",
                "simpleCalculatorPLRequest:firstNum"
        ));
*/
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        try {

            map = mapper.readValue(dataInJson, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("===================");
        Map<String, String> variableAndValue = new HashMap<>();
        flatten("",map,variableAndValue);

        final List<String> collect = originalVariables.stream().filter(
                entry -> entry.startsWith(variableName+":")).collect(Collectors.toList());
        Map<String,String> finalValues=new HashMap<>();
     try {
         collect.forEach(s -> {
                 List<String> value2= variableAndValue.entrySet()
                         .stream()
                         .filter(entry -> entry.getKey().contains (s.split(":")[1]+".content"))
                         .map(Map.Entry::getValue)
                         .collect(Collectors.toList());
             if (value2.size()>0){
                finalValues.put(s,value2.get(0));
             }
             else
             {
                 List<String> value= variableAndValue.entrySet()
                         .stream()
                         .filter(entry -> entry.getKey().contains (s.split(":")[1]))
                         .map(Map.Entry::getValue)
                         .collect(Collectors.toList());
                 finalValues.put(s,value.get(0));
             }

         });

     }catch (Exception e){
         logger.info(e.getStackTrace().toString());
     }

        //logger.info("variableName:" + variableName);
        //logger.info("data:" + finalValues.toString());
        logger.info("===================");
        for (Map.Entry entry: finalValues.entrySet()){
            logger.info("key:"+entry.getKey() +" value:"+entry.getValue());
        }
        //   logger.info("Operation completed successfully:");

        //   logger.info("Process instance data:" + dataNode);

    }





    public static void flatten(String path, Object from, Map to) {
        if (to == null) {
            return;
        } else if (from instanceof Map && !((Map) from).isEmpty()) {
            ((Map) from)
                    .entrySet()
                    .stream()
                    .forEach(o -> {
                        String pathPrefix = path.isEmpty() ? "" : path + ".";
                        flatten(pathPrefix + ((Map.Entry) o).getKey(), ((Map.Entry) o).getValue(), to);
                    });

        } else if ((from instanceof Collection) && !((Collection) from).isEmpty()) { // Add into the map
            List<Object> collection = Arrays
                    .asList(((Collection) from).toArray());
            IntStream
                    .range(0, collection.size())
                    .forEach(i -> {
                        flatten(path + "[" + i + "]", collection.get(i), to);
                    });
        } else if (!StringUtils.isBlank(path)) {

            to.put(path, from);
        }
    }
}

package kit.edu.xpathmapper;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import kit.edu.core.HasPart;
import kit.edu.core.WorkflowRun;

/**
 * This class is used to create ProcessExec node for Retrospective Provenance. 
 * 
 * @author Vaibhav
 *
 */
public class GenerateProcessExe {

	private RetrospectiveXpathMapper mapper;
	private Property activityIdProperty;
	
	public GenerateProcessExe(RetrospectiveXpathMapper retrospectiveXpathMapper) {
		mapper = retrospectiveXpathMapper;
	}

	/**
	 * This function is used to create workflow ProcessExec Node in retorspective provenance.
	 * 
	 * @param workflowRun
	 * @return
	 */
	public Resource workflowProcExec(WorkflowRun workflowRun) {
		Resource workFlowResource = mapper.getUtil().createWorkResource(workflowRun);
		activityIdProperty= mapper.getRdfUtil().getIdentifirePropety();
		for (HasPart hasPart : workflowRun.getHasPart()) {
			if(null != hasPart.getProcessRun()){
				Resource processResource = mapper.getUtil().createProcessResource(hasPart.getProcessRun());
				mapper.getRdfUtil().isPartOf(processResource, workFlowResource);
				mapper.setProcessExeMap(processResource.getProperty(activityIdProperty).getObject().toString(), processResource);
				mapper.setProcessRunMap(processResource.getProperty(activityIdProperty).getObject().toString(), hasPart.getProcessRun());
			}else if(null != hasPart.getWorkflowRun()){
				Resource processResource = createSubWorkFlow(hasPart.getWorkflowRun());
				mapper.getRdfUtil().isPartOf(processResource, workFlowResource);
				mapper.setProcessExeMap(processResource.getProperty(activityIdProperty).getObject().toString(), processResource);
				mapper.setWorkFlowRunMap(processResource.getProperty(activityIdProperty).getObject().toString(), hasPart.getWorkflowRun());
			}
		}
		mapper.setProcessExeMap(workFlowResource.getProperty(activityIdProperty).getObject().toString(), workFlowResource);
		return workFlowResource;
	}


	/**
	 * This function is used to create ProcessExec for sub-workflow.
	 * 
	 * @param workflowRun
	 * @return
	 */
	public Resource createSubWorkFlow(WorkflowRun workflowRun) {
		Resource subWorkFlowResource = mapper.getUtil().createSubWrkFlwProcessResource(workflowRun);
		
		for (HasPart hasPart : workflowRun.getHasPart()) {
			if(null != hasPart.getProcessRun()){
				Resource processResource = mapper.getUtil().createProcessResource(hasPart.getProcessRun());
				mapper.getRdfUtil().isPartOf(processResource, subWorkFlowResource);
				mapper.setProcessExeMap(processResource.getProperty(activityIdProperty).getObject().toString(), processResource);
				mapper.setProcessRunMap(processResource.getProperty(activityIdProperty).getObject().toString(), hasPart.getProcessRun());
			}else if(null != hasPart.getWorkflowRun()){
				Resource processResource = createSubWorkFlow(hasPart.getWorkflowRun());
				mapper.getRdfUtil().isPartOf(processResource, subWorkFlowResource);
				mapper.setProcessExeMap(processResource.getProperty(activityIdProperty).getObject().toString(), processResource);
				mapper.setWorkFlowRunMap(processResource.getProperty(activityIdProperty).getObject().toString(), hasPart.getWorkflowRun());
			}
		}
		return subWorkFlowResource;
	}

	public Property getActivityIdProperty() {
		return activityIdProperty;
	}

	public void setActivityIdProperty(Property activityIdProperty) {
		this.activityIdProperty = activityIdProperty;
	}

}

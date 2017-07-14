package edu.kit.scufl.api;

public class Utility {

	/*public String getID(WorkflowRun workflowRun) {
		String[] about = workflowRun.getAbout().split("/");
		String id = about[about.length-1];
		return id;
	}

	public String getID(ProcessRun processRun) {
		String[] about = processRun.getAbout().split("/");
		String id = about[about.length-1];
		return id;
	}*/

	private String about;

	public String getID(String rdfabout) {
		about = rdfabout;
		String[] about = rdfabout.split("/");
		String id = about[about.length-1];
		return id;
	}


	public String getWorkFlowID(String describedByWorkflowID) {
		String[] about = describedByWorkflowID.split("/");
		for (String part : about) {
			if(part.contains("-")){
				return part;
			}
		}
		return null;
	}
	public String getPath(String resource) {
		String workflowid = getWorkFlowID(resource);
		String[] splitString = resource.split(workflowid);
		System.out.println(splitString[1]);
		return splitString[1].replaceAll("/workflow/", "");
	}
}

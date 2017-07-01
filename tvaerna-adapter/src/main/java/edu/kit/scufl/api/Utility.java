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

	public String getID(String rdfabout) {
		String[] about = rdfabout.split("/");
		String id = about[about.length-1];
		return id;
	}
	
	

}

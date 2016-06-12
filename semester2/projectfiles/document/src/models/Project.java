package models;

import java.util.ArrayList;

public class Project {
	private String projectID;
	private String projectName;
	private String Status;
	private String projectManager;
	private ArrayList<String> listProjectMember;
	
	public String getProjectID() {
		return projectID;
	}
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public ArrayList<String> getListProjectMember() {
		return listProjectMember;
	}
	public void setListProjectMember(ArrayList<String> listProjectMember) {
		this.listProjectMember = listProjectMember;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

}

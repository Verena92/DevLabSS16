package de.hdm.wim.events.model;

import java.util.ArrayList;
import java.util.List;

public class Project {

	private String projectID;
	private String projectName;
	private String projectType;
	private String projectStatus;
	private String projectManager;
	private List<String> projectOfCompanies;
	private List<String> projectMembers;
	
	public static Project createDummyProject() {
		List<String> projectOfCompanies = new ArrayList<String>();
		projectOfCompanies.add("C003");
		List<String> projectMembers = new ArrayList<String>();
		projectMembers.add( "M001");
		projectMembers.add( "M002");
		Project project = new Project("P001", "HighNet", "Einführungsprojekt", "Grün", "M001", projectOfCompanies, projectMembers);
		return project;
	}

	public Project() {
		
	}
	
	public Project(String projectID, String projectName, String projectType, String projectStatus, String projectManager, List<String> projectOfCompanies,
			List<String> projectMembers) {
		this.projectID = projectID;
		this.projectName = projectName;
		this.projectType = projectType;
		this.projectStatus = projectStatus;
		this.projectManager = projectManager;
		this.projectOfCompanies = projectOfCompanies;
		this.projectMembers = projectMembers;
	}

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

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public List<String> getProjectOfCompanies() {
		return projectOfCompanies;
	}

	public void setProjectOfCompanies(List<String> projectOfCompanies) {
		this.projectOfCompanies = projectOfCompanies;
	}

	public List<String> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(List<String> projectMembers) {
		this.projectMembers = projectMembers;
	}

}

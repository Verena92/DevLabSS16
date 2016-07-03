package de.hdm.wim.events.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Representation of a project as it is saved by the document representation.
 * The Lists 'projectMembers' and 'involvedCompanies' can be used to find connections
 * with other entities (such as 'Product', 'Employee' or 'Company').
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class Project {

	@Id
	private String projectID;
	private String projectName;
	private String projectManager;
	private String status;
	
	@ElementCollection
	private List<String> projectMembers;
	@ElementCollection
	private List<String> involvedCompanies;

	/**
	 * Default constructor needed for JSON Parsing
	 */
	public Project() {

	}

	/**
	 * Constructor with all available parameters
	 * 
	 * @param projectID a unique ID, i.e 'P0100'
	 * @param projectName the name of the project, i.e. 'HighNet'
	 * @param projectManager an employeeId refering to the responsible manager, i.e. 'M0001'
	 * @param projectMembers a list of employeeIds representing the employees, i.e. '{M0001, M0002}'
	 * @param involvedCompanies a list of companyIDs representing the involved companies, i.e. '{U0001, U0002}'
	 * @param status the status of the project, i.e. 'gruen'
	 */
	public Project(String projectID, String projectName, String projectManager, List<String> projectMembers, List<String> involvedCompanies, String status) {
		this.projectID = projectID;
		this.projectName = projectName;
		this.status = status;
		this.projectManager = projectManager;
		this.involvedCompanies = involvedCompanies;
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

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public List<String> getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(List<String> projectMembers) {
		this.projectMembers = projectMembers;
	}

	public List<String> getInvolvedCompanies() {
		return involvedCompanies;
	}

	public void setInvolvedCompanies(List<String> involvedCompanies) {
		this.involvedCompanies = involvedCompanies;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Project [projectID=" + projectID + ", projectName=" + projectName + ", projectManager=" + projectManager + ", projectMembers=" + projectMembers
				+ ", involvedCompanies=" + involvedCompanies + ", status=" + status + "]";
	}
	
	
}

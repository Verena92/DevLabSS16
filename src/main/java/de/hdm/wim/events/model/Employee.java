package de.hdm.wim.events.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Representation of an Employee as we receive it from the DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class Employee {
	
	@Id
	private String employeeID;
	private String employeeName;
	private String employeeSurname;
	private String jobTitle;
	private String employeeOf;
	private String hangoutUserID;
	private String driveUserID;

	@ElementCollection
	private List<String> projects;
	
	public Employee(String employeeID, String employeeName, String employeeSurname, String jobTitle, String employeeOf, String hangoutUserID, String driveUserID,
			List<String> projects) {
		this.employeeID = employeeID;
		this.employeeName = employeeName;
		this.employeeSurname = employeeSurname;
		this.jobTitle = jobTitle;
		this.employeeOf = employeeOf;
		this.hangoutUserID = hangoutUserID;
		this.driveUserID = driveUserID;
		this.projects = projects;
	}

	/**
	 * Default constructor needed for JSON Parsing
	 */
	public Employee() {

	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeSurname() {
		return employeeSurname;
	}

	public void setEmployeeSurname(String employeeSurname) {
		this.employeeSurname = employeeSurname;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmployeeOf() {
		return employeeOf;
	}

	public void setEmployeeOf(String employeeOf) {
		this.employeeOf = employeeOf;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	public String getHangoutUserID() {
		return hangoutUserID;
	}

	public void setHangoutUserID(String hangoutUserID) {
		this.hangoutUserID = hangoutUserID;
	}

	public String getDriveUserID() {
		return driveUserID;
	}

	public void setDriveUserID(String driveUserID) {
		this.driveUserID = driveUserID;
	}

	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", employeeName=" + employeeName + ", employeeSurname=" + employeeSurname + ", jobTitle=" + jobTitle + ", employeeOf="
				+ employeeOf + ", projects=" + projects + "]";
	}
}

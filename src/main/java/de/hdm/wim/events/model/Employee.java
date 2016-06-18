package de.hdm.wim.events.model;

import java.util.ArrayList;
import java.util.List;

public class Employee {
	
	private String employeeID;
	private String employeeName;
	private String employeeSurname;
	private String jobTitle;
	private String employeeOf;

	private List<String> projects;

	/**
	 * Helper method to create a dummy Employee
	 * 
	 * @return a dummy Employee with fix values
	 */
	public static Employee createDummyEmployee() {
		List<String> projects = new ArrayList<String>();
		projects.add( "P0001");
		projects.add("P0100");
		
		Employee employee = new Employee("M0001", "Maier", "Lisa", "Projektleiter", "U0002", projects);
		return employee;
	}
	
	public Employee(String employeeID, String employeeName, String employeeSurname, String jobTitle, String employeeOf, List<String> projects) {
		this.employeeID = employeeID;
		this.employeeName = employeeName;
		this.employeeSurname = employeeSurname;
		this.jobTitle = jobTitle;
		this.employeeOf = employeeOf;
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
}

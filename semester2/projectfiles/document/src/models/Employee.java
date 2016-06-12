package models;

import java.util.ArrayList;

public class Employee {
	private String employeeID;
	private String employeeName;
	private String employeeSurname;
	private ArrayList<String> listProjects;
	private String employeeOf;
	
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

	public ArrayList<String> getListProjects() {
		return listProjects;
	}
	public void setListProjects(ArrayList<String> listProjects) {
		this.listProjects = listProjects;
	}
	public String getEmployeeOf() {
		return employeeOf;
	}
	public void setEmployeeOf(String employeeOf) {
		this.employeeOf = employeeOf;
	}

}

package models;

import java.util.ArrayList;

public class Company {
	private String companyID;
	private String companyName;
	private int numberEmployee;
	private ArrayList<String> listDepartments;
	private ArrayList<String> listEmployees;
	private ArrayList<String> listProjects;

	public ArrayList<String> getListDepartments() {
		return listDepartments;
	}
	public void setListDepartments(ArrayList<String> listDepartments) {
		this.listDepartments = listDepartments;
	}
	public ArrayList<String> getListEmployees() {
		return listEmployees;
	}
	public void setListEmployees(ArrayList<String> listEmployees) {
		this.listEmployees = listEmployees;
	}
	public ArrayList<String> getListProjects() {
		return listProjects;
	}
	public void setListProjects(ArrayList<String> listProjects) {
		this.listProjects = listProjects;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getNumberEmployee() {
		return numberEmployee;
	}
	public void setNumberEmployee(int numberEmployee) {
		this.numberEmployee = numberEmployee;
	}
}

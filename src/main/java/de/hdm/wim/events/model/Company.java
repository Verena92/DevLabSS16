package de.hdm.wim.events.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a company as it is saved by the document representation.
 * The Lists 'employees', 'projects' and 'products' can be used to find connections
 * with other entities (such as 'Product', 'Employee' or 'Project').
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class Company {

	private String companyID;
	private String companyName;
	private String industrialSector;
	private String headquarter;
	private int numberEmployee;

	private List<String> employees;
	private List<String> projects;
	private List<String> products;
	
	public Company(String companyID, String companyName, String sector, String headquarter, List<String> employees, List<String> projects, List<String> products, int numberEmployee) {
		this.companyID = companyID;
		this.companyName = companyName;
		this.industrialSector = sector;
		this.headquarter = headquarter;
		this.employees = employees;
		this.projects = projects;
		this.products = products;
		this.numberEmployee = numberEmployee;
	}

    /**
	 * Default constructor needed for JSON Parsing
	 */
	public Company() {
		
	}
	
	public int getNumberEmployee() {
		return numberEmployee;
	}

	public void setNumberEmployee(int numberEmployee) {
		this.numberEmployee = numberEmployee;
	}

	public String getIndustrialSector() {
		return industrialSector;
	}

	public void setIndustrialSector(String industrialSector) {
		this.industrialSector = industrialSector;
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

	public String getHeadquarter() {
		return headquarter;
	}

	public void setHeadquarter(String headquarter) {
		this.headquarter = headquarter;
	}

	public List<String> getEmployees() {
		return employees;
	}

	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Company [companyID=" + companyID + ", companyName=" + companyName + ", industrialSector=" + industrialSector + ", headquarter=" + headquarter + ", numberEmployee="
				+ numberEmployee + ", employees=" + employees + ", projects=" + projects + ", products=" + products + "]";
	}
	
	
}

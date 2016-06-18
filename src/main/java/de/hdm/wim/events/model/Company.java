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

	private List<String> employees;
	private List<String> projects;
	private List<String> products;
	
	/**
	 * Helper method to create a dummy Company
	 * 
	 * @return a dummy Company with fix values
	 */
	public static Company createDummyCompany() {
		List<String> employees = new ArrayList<String>();
		employees.add( "M0101");
		employees.add("M0102");
		
		List<String> projects = new ArrayList<String>();
		projects.add( "P0001");
		projects.add("P0003");
		
		List<String> products = new ArrayList<String>();
		projects.add( "PR0001");
		projects.add("PR0003");
		
		Company company = new Company("U0001", "HighNet GmbH", "Finanzen", "Stuttgart", employees, projects, products);
		return company;		
	}
	
	public Company(String companyID, String companyName, String sector, String headquarter, List<String> employees, List<String> projects, List<String> products) {
		this.companyID = companyID;
		this.companyName = companyName;
		this.industrialSector = sector;
		this.headquarter = headquarter;
		this.employees = employees;
		this.projects = projects;
		this.products = products;
	}

    /**
	 * Default constructor needed for JSON Parsing
	 */
	public Company() {
		
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

	public String getSector() {
		return industrialSector;
	}

	public void setIndustrialSector(String industrialSector) {
		this.industrialSector = industrialSector;
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
}

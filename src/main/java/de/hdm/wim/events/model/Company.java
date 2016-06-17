package de.hdm.wim.events.model;

import java.util.ArrayList;
import java.util.List;

public class Company {

	private String companyID;
	private String companyName;
	private String sector;
	private String country;
	private String headQuarter;

	private List<String> employees;
	private List<String> projects;
	private List<String> products;
	
	public static Company createDummyCompany() {
		List<String> employees = new ArrayList<String>();
		employees.add( "M001");
		employees.add("M002");
		
		List<String> projects = new ArrayList<String>();
		projects.add( "P001");
		projects.add("P003");
		
		List<String> products = new ArrayList<String>();
		projects.add( "PR001");
		projects.add("PR003");
		
		Company company = new Company("C001", "HighNet GmbH", "Finanzen", "Germany", "Stuttgart", employees, projects, products);
		return company;		
	}
	
	public Company(String companyID, String companyName, String sector, String country, String headQuarter, List<String> employees, List<String> projects, List<String> products) {
		super();
		this.companyID = companyID;
		this.companyName = companyName;
		this.sector = sector;
		this.country = country;
		this.headQuarter = headQuarter;
		this.employees = employees;
		this.projects = projects;
		this.products = products;
	}

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
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadQuarter() {
		return headQuarter;
	}

	public void setHeadQuarter(String headQuarter) {
		this.headQuarter = headQuarter;
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

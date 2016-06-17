package de.hdm.wim.events.model;

import java.util.List;

public class KeywordInformation {

	private List<String> projects;
	private List<String> companies;
	private List<String> products;
	private List<String> employees;
	
	
	public KeywordInformation() {
		
	}
	
	public KeywordInformation(List<String> projects, List<String> companies, List<String> products, List<String> employees) {
		this.projects = projects;
		this.companies = companies;
		this.products = products;
		this.employees = employees;
	}

	public List<String> getProjects() {
		return projects;
	}
	
	public void setProjects(List<String> projects) {
		this.projects = projects;
	}
	
	public List<String> getCompanies() {
		return companies;
	}
	
	public void setCompanies(List<String> companies) {
		this.companies = companies;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public List<String> getEmployees() {
		return employees;
	}

	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}
}

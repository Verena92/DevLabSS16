package de.hdm.wim.events.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class KeywordInformation {

	@Id
	@GeneratedValue
	private long id;
	
	@ElementCollection
	private List<String> projects;
	
	@ElementCollection
	private List<String> companies;
	
	@ElementCollection
	private List<String> products;
	
	@ElementCollection
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

	@Override
	public String toString() {
		return "KeywordInformation [id=" + id + ", projects=" + projects + ", companies=" + companies + ", products=" + products + ", employees=" + employees + "]";
	}

	
}

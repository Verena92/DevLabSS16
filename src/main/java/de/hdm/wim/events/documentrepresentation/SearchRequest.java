package de.hdm.wim.events.documentrepresentation;

import java.util.ArrayList;
import java.util.List;

public class SearchRequest {

	private List<String> employees;
	private List<String> companies;
	private List<String> projects;

	public static SearchRequest createDummySearchRequest() {
		List<String> employees = new ArrayList<String>();
		employees.add("M001");
		employees.add("M002");
		List<String> companies = new ArrayList<String>();
		companies.add("C002");
		List<String> projects = new ArrayList<String>();
		projects.add("P002");
		projects.add("P004");

		SearchRequest searchRequest = new SearchRequest(employees, companies, projects);
		return searchRequest;
	}

	public SearchRequest(List<String> employees, List<String> companies, List<String> projects) {
		this.employees = employees;
		this.companies = companies;
		this.projects = projects;
	}

	public SearchRequest() {

	}

	public List<String> getEmployees() {
		return employees;
	}

	public void setEmployees(List<String> employees) {
		this.employees = employees;
	}

	public List<String> getCompanies() {
		return companies;
	}

	public void setCompanies(List<String> companies) {
		this.companies = companies;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

}
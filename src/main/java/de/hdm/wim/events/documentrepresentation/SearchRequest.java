package de.hdm.wim.events.documentrepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to ask for documents by {@link DocumentRepresentationRequester}.
 * It contains 0-N employees, companies, projects and products.
 * All of them need to be relevant for the SearchRequest to succeed (=return a document)
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class SearchRequest {

	private List<String> employees;
	private List<String> companies;
	private List<String> projects;
	private List<String> products;

	public static SearchRequest createDummySearchRequest() {
		List<String> employees = new ArrayList<String>();
//		employees.add("M001");
//		employees.add("M002");
		List<String> companies = new ArrayList<String>();
//		companies.add("C002");
		List<String> projects = new ArrayList<String>();
		projects.add("P001");
//		projects.add("P002");
		List<String> products = new ArrayList<String>();
//		products.add("PR001");

		SearchRequest searchRequest = new SearchRequest(employees, companies, projects, products);
		return searchRequest;
	}

	public SearchRequest(List<String> employees, List<String> companies, List<String> projects, List<String> products) {
		this.employees = employees;
		this.companies = companies;
		this.projects = projects;
		this.products = products;
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

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}
}
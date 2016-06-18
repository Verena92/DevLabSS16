package de.hdm.wim.events.model;

import java.util.ArrayList;
import java.util.List;


public class Product {

	private String productID;
	private String productName;
	private String categorie;

	private List<String> relatedProjects;
	private List<String> relatedCompanies;

	public static Product createDummyProduct() {
		List<String> relatedProjects = new ArrayList<String>();
		relatedProjects.add("P001");
		List<String> relatedCompanies = new ArrayList<String>();
		relatedCompanies.add( "C001");
		
		Product product = new Product("PR001", "Mercedes Benz", "Automobile", relatedProjects, relatedCompanies);
		return product;
	}
	
	public Product(String productID, String productName, String categorie, List<String> relatedProjects, List<String> relatedCompanies) {
		this.productID = productID;
		this.productName = productName;
		this.categorie = categorie;
		this.relatedProjects = relatedProjects;
		this.relatedCompanies = relatedCompanies;
	}

	/**
	 * Default constructor needed for JSON Parsing
	 */
	public Product() {

	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public List<String> getRelatedProjects() {
		return relatedProjects;
	}

	public void setRelatedProjects(List<String> relatedProjects) {
		this.relatedProjects = relatedProjects;
	}

	public List<String> getRelatedCompanies() {
		return relatedCompanies;
	}

	public void setRelatedCompanies(List<String> relatedCompanies) {
		this.relatedCompanies = relatedCompanies;
	}

}

package de.hdm.wim.events.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Representation of a Product as we receive it from the DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class Product {

	@Id
	private String productID;
	private String productName;
	private String categorie;

	@ElementCollection
	private List<String> relatedProjects;
	@ElementCollection
	private List<String> relatedCompanies;

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

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", productName=" + productName + ", categorie=" + categorie + ", relatedProjects=" + relatedProjects + ", relatedCompanies="
				+ relatedCompanies + "]";
	}
}

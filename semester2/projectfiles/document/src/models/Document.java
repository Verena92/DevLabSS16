package models;

import java.util.ArrayList;

public class Document {
	private String documentID;
	private String documentName;
	private String documentType;
	private String creationDate;
	private String drivePath;
	private String createdBy;
	private String status;
	private double version;
	private ArrayList<String> keywords;
	private ArrayList<String> projects;
	private String documentClass;
	
	
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}

	public String getDocumentID() {
		return documentID;
	}
	public void setDocumentID(String documentID) {
		this.documentID = documentID;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDrivePath() {
		return drivePath;
	}
	public void setDrivePath(String drivePath) {
		this.drivePath = drivePath;
	}
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	public ArrayList<String> getProjects() {
		return projects;
	}
	public void setProjects(ArrayList<String> projects) {
		this.projects = projects;
	}
	public String getDocumentClass() {
		return documentClass;
	}
	public void setDocumentClass(String documentClass) {
		this.documentClass = documentClass;
	}
}

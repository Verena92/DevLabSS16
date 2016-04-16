package models;

import java.util.ArrayList;

public class Document {
	private String documentName;
	private String documentPath;
	private String creationDate;
	private String createdBy;
	private String relatedProject;
	private String documentType;
	private ArrayList<String> keyword;

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

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

	public String getRelatedProject() {
		return relatedProject;
	}

	public void setRelatedProject(String relatedProject) {
		this.relatedProject = relatedProject;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public ArrayList<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(ArrayList<String> keyword) {
		this.keyword = keyword;
	}
}

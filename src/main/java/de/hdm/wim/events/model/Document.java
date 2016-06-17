package de.hdm.wim.events.model;

import java.util.Date;

public class Document {

	private String driveID;
	private String documentName;
	private Date creationDate;
	private Date editDate;
	private String drivePath;
	private String documentType;
	private String status;
	private String version;
	private String createdBy;
	private String lastEditBy;
	private String partOfProject;
	private String partOfDepartment;

	public static Document createDummyDocument() {
		Document document = new Document("sf", "Abschlussbericht 2015", new Date(), new Date(),
				"https://drive.google.com/open?id=1vJNvuPnCwg37yKZRsRuWvDn_LIwF5N4nHm_Xm1SIn8g12345", "Textdokument", "Entwurf", "2.3", "M001", "M003", "P0010", "DP001");
		return document;
	}

	public Document(String driveID, String documentName, Date creationDate, Date editDate, String drivePath, String documentType, String status, String version, String createdBy,
			String lastEditBy, String partOfProject, String partOfDepartment) {
		super();
		this.driveID = driveID;
		this.documentName = documentName;
		this.creationDate = creationDate;
		this.editDate = editDate;
		this.drivePath = drivePath;
		this.documentType = documentType;
		this.status = status;
		this.version = version;
		this.createdBy = createdBy;
		this.lastEditBy = lastEditBy;
		this.partOfProject = partOfProject;
		this.partOfDepartment = partOfDepartment;
	}

	public Document() {

	}

	public String getDriveID() {
		return driveID;
	}

	public void setDriveID(String driveID) {
		this.driveID = driveID;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getDrivePath() {
		return drivePath;
	}

	public void setDrivePath(String drivePath) {
		this.drivePath = drivePath;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastEditBy() {
		return lastEditBy;
	}

	public void setLastEditBy(String lastEditBy) {
		this.lastEditBy = lastEditBy;
	}

	public String getPartOfProject() {
		return partOfProject;
	}

	public void setPartOfProject(String partOfProject) {
		this.partOfProject = partOfProject;
	}

	public String getPartOfDepartment() {
		return partOfDepartment;
	}

	public void setPartOfDepartment(String partOfDepartment) {
		this.partOfDepartment = partOfDepartment;
	}
}
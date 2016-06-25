package de.hdm.wim.events.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Representation of a Document as we receive it from the DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class Document {

	@Id
	private String documentID;
	private String documentName;
	private String documentType;
	private Date creationDate;
	private String drivePath;
	private String createdBy;
	private String status;
	private String version;
	
	@ElementCollection
	private List<String> keywords;
	@ElementCollection
	private List<String> projects;
	private String driveID;
	
	/**
	 * 
	 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	

	public Document(String documentID, String documentName, String documentType, String creationDate, String drivePath, String createdBy, String status, String version,
			List<String> keywords, List<String> projects, String driveID) throws ParseException {
		this.documentID = documentID;
		this.documentName = documentName;
		this.documentType = documentType;
		this.creationDate = DATE_FORMAT.parse(creationDate);
		this.drivePath = drivePath;
		this.createdBy = createdBy;
		this.status = status;
		this.version = version;
		this.keywords = keywords;
		this.projects = projects;
		this.driveID = driveID;
	}

	public Document() {

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) throws ParseException {
		this.creationDate = DATE_FORMAT.parse(creationDate);
	}


	public String getDrivePath() {
		return drivePath;
	}

	public void setDrivePath(String drivePath) {
		this.drivePath = drivePath;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	public String getDriveID() {
		return driveID;
	}

	public void setDriveID(String driveID) {
		this.driveID = driveID;
	}

	public String getAuthor() {
		return createdBy;
	}

	public void setAuthor(String author) {
		this.createdBy = author;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGoogleDrivePath() {
		return drivePath;
	}

	public void setGoogleDrivePath(String googleDrivePath) {
		this.drivePath = googleDrivePath;
	}

	public String getGoogleDriveID() {
		return driveID;
	}

	public void setGoogleDriveID(String googleDriveID) {
		this.driveID = googleDriveID;
	}

	@Override
	public String toString() {
		return "Document [documentID=" + documentID + ", documentName=" + documentName + ", documentType=" + documentType + ", creationDate=" + creationDate + ", drivePath="
				+ drivePath + ", createdBy=" + createdBy + ", status=" + status + ", version=" + version + ", keywords=" + keywords + ", projects=" + projects + ", driveID="
				+ driveID + "]";
	}
}
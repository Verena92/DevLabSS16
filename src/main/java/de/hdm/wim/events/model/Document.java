package de.hdm.wim.events.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representation of a Document as we receive it from the DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class Document {

	private String type;
	private String documentID;
	private String documentName;
	private Date creationDate;
	private Date lastEditDate;
	private String project;
	private String author;
	private String documentType;
	private List<String> keywords;
	private String version;
	private String status;
	private String googleDrivePath;
	private String googleDriveID;
	
	/**
	 * 
	 */
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	

	public Document(String type, String documentID, String documentName, String creationDate, String lastEditDate, String project, String author, String documentType,
			List<String> keywords, String version, String status, String googleDrivePath, String googleDriveID) throws ParseException {
		this.type = type;
		this.documentID = documentID;
		this.documentName = documentName;
		this.creationDate = DATE_FORMAT.parse(creationDate);
		this.lastEditDate = DATE_FORMAT.parse(lastEditDate);
		this.project = project;
		this.author = author;
		this.documentType = documentType;
		this.keywords = keywords;
		this.version = version;
		this.status = status;
		this.googleDrivePath = googleDrivePath;
		this.googleDriveID = googleDriveID;
	}

	public Document() {

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Date getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(String lastEditDate) throws ParseException {
		this.lastEditDate = DATE_FORMAT.parse( lastEditDate);
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
		return googleDrivePath;
	}

	public void setGoogleDrivePath(String googleDrivePath) {
		this.googleDrivePath = googleDrivePath;
	}

	public String getGoogleDriveID() {
		return googleDriveID;
	}

	public void setGoogleDriveID(String googleDriveID) {
		this.googleDriveID = googleDriveID;
	}

	@Override
	public String toString() {
		return "Document [type=" + type + ", documentID=" + documentID + ", documentName=" + documentName + ", creationDate=" + creationDate + ", lastEditDate=" + lastEditDate
				+ ", project=" + project + ", author=" + author + ", documentType=" + documentType + ", keywords=" + keywords + ", version=" + version + ", status=" + status
				+ ", googleDrivePath=" + googleDrivePath + ", googleDriveID=" + googleDriveID + "]";
	}
	
	

}
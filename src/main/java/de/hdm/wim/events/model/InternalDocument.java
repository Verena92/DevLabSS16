package de.hdm.wim.events.model;

/**
 * This class models a Document with another structure. 
 * Different to the receive Document this InternalDocument
 * implements the name and the path.
 *   
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */

public class InternalDocument {
	private String documentName;
	private String drivePath;
	
	public InternalDocument(Document document){
		this.documentName = document.getDocumentName();
		this.drivePath = document.getDrivePath();
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDrivePath() {
		return drivePath;
	}

	public void setDrivePath(String drivePath) {
		this.drivePath = drivePath;
	}
}

package de.hdm.speechtomcat;


public class Document {

	private String userId;
	private String hangoutsId;
	private String documentName;
	private String drivePath;


	/*public static Document createDummyDocument() {
		Document document = new Document("sf", "Abschlussbericht 2015",
				"https://drive.google.com/open?id=1vJNvuPnCwg37yKZRsRuWvDn_LIwF5N4nHm_Xm1SIn8g12345", "Textdokument", "Entwurf", "2.3", "M001", "M003", "P0010", "DP001");
		return document;
	}*/

	public Document(String userId, String hangoutsId, String documentName, String drivePath) {
		super();
		this.userId = userId;
		this.hangoutsId = hangoutsId;
		this.documentName = documentName;
		this.drivePath = drivePath;
	}

	public Document() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getHangoutsId() {
		return hangoutsId;
	}

	public void setHangoutsId(String hangoutsId) {
		this.hangoutsId = hangoutsId;
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
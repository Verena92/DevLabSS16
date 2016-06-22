package de.hdm.wim.events.model;

import java.util.Date;

public class DocumentReceivedEvent implements Event {

	private Document document;
	
	public DocumentReceivedEvent(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Date getTimestamp() {
		return new Date(); // TODO: use meaningful date
	}

	@Override
	public String getId() {
		return ""; // TODO: use meaningful id
	}

}
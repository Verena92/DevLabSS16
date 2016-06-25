package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class DocumentReceivedEvent implements Event {
	
	@Id
	private String id;
	
	@OneToOne(cascade = {CascadeType.ALL})
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
		return this.id; // TODO: use meaningful id
	}

	public void setId(String id) {
		this.id = id;
	}
}
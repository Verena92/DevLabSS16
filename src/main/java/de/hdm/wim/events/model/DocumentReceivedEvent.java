package de.hdm.wim.events.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DocumentReceivedEvent implements Event {
	
	@Id
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Document document;
	
	public DocumentReceivedEvent() {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
	}
	
	public DocumentReceivedEvent(Document document) {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String getId() {
		return this.id;
	}
}
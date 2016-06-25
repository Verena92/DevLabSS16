package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class DocumentReceivedEvent implements Event {
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Document document;
	
	public DocumentReceivedEvent() {
		
	}
	
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
		return ""+this.id;
	}
}
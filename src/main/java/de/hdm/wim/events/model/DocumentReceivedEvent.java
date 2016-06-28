package de.hdm.wim.events.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Representation of an Event we create when receiving a document from the
 * DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class DocumentReceivedEvent extends EventEntity {

	@OneToOne(cascade = { CascadeType.ALL })
	private Document document;

	public DocumentReceivedEvent() {
		super();
	}

	public DocumentReceivedEvent(Document document) {
		super();
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	@Override
	public String toString() {
		return "DocumentReceivedEvent [document=" + document + ", getTimestamp()=" + getTimestamp() + ", getId()=" + getId() + ", toString()=" + super.toString() + "]";
	}
}
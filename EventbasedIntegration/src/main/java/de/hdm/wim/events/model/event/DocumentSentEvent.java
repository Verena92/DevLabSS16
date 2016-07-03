package de.hdm.wim.events.model.event;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import de.hdm.wim.events.model.DocumentForSpeechTokenizer;

/**
 * Representation of an Event we create when sending a document to the
 * SpeechTokenizer
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class DocumentSentEvent extends EventEntity {

	@OneToOne(cascade = { CascadeType.ALL })
	private DocumentForSpeechTokenizer documentForSpeechTokenizer;

	public DocumentSentEvent() {
		super();
	}

	public DocumentSentEvent(DocumentForSpeechTokenizer documentForSpeechTokenizer) {
		super();
		this.documentForSpeechTokenizer = documentForSpeechTokenizer;
	}

	public DocumentForSpeechTokenizer getDocumentForSpeechTokenizer() {
		return documentForSpeechTokenizer;
	}

	public void setDocumentForSpeechTokenizer(DocumentForSpeechTokenizer documentForSpeechTokenizer) {
		this.documentForSpeechTokenizer = documentForSpeechTokenizer;
	}

	@Override
	public String toString() {
		return "DocumentSentEvent [documentForSpeechTokenizer=" + documentForSpeechTokenizer + ", getTimestamp()=" + getTimestamp() + ", getId()=" + getId() + ", toString()="
				+ super.toString() + "]";
	}

}
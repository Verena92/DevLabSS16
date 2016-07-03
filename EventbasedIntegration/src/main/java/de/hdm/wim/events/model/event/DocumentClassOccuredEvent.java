package de.hdm.wim.events.model.event;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Representation of an Event we create when receiving a documentclass keyword
 * token from the SpeechTokenizer
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class DocumentClassOccuredEvent implements Event {

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	private String keyword;

	public DocumentClassOccuredEvent(String keyword) {
		this.keyword = keyword;
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
	}
	
	/**
	 * default constructor for JPA
	 */
	public DocumentClassOccuredEvent() {
		
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "DocumentClassOccuredEvent [id=" + id + ", timestamp=" + timestamp + ", keyword=" + keyword + "]";
	}

}
package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Token implements Event {

	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	private String keyword;
	
	
	public Token() {
	}
	
	public Token(String id, Date timestamp, String keyword) {
		this.id = id;
		this.timestamp = timestamp; 
		this.keyword = keyword;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", keyword=" + keyword + ", creator=" + "]";
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}

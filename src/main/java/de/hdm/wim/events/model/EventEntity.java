package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class EventEntity implements Event {
	
	private String id;
	private Date timestamp;
	
	public EventEntity(String id, Date timestamp) {
		this.id = id;
		this.timestamp = timestamp;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}
	@Override
	public String getId() {
		return this.id;
	}
}

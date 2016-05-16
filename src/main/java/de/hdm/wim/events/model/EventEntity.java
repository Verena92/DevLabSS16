package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Jens
 *
 */
@Entity
public class EventEntity implements Event {
	
	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	public EventEntity() {
		//no param constructor needed for jpa porpuses
	}
	
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

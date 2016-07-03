package de.hdm.wim.events.model.event;

import java.util.Date;
import java.util.UUID;

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
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
	}
	
	@Override
	public Date getTimestamp() {
		return timestamp;
	}
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "EventEntity [id=" + id + ", timestamp=" + timestamp + "]";
	}
}

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
public class ProjectReceivedEvent implements Event {

	@Id
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Project project;
	
	public ProjectReceivedEvent() {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
	}
	
	public ProjectReceivedEvent( Project project) {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
		this.project = project;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String getId() {
		return this.id;
	}
}
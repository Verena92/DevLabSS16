package de.hdm.wim.events.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Representation of an Event we create when receiving a project from the
 * DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class ProjectReceivedEvent extends EventEntity {

	@OneToOne(cascade = { CascadeType.ALL })
	private Project project;

	public ProjectReceivedEvent() {
		super();
	}

	public ProjectReceivedEvent(Project project) {
		this.project = project;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "ProjectReceivedEvent [project=" + project + ", getTimestamp()=" + getTimestamp() + ", getId()=" + getId() + ", toString()=" + super.toString() + "]";
	}
}
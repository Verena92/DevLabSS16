package de.hdm.wim.events.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Representation of an Event we create when receiving a company from the
 * DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class CompanyReceivedEvent implements Event {

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@OneToOne(cascade = { CascadeType.ALL })
	private Company company;

	public CompanyReceivedEvent() {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
	}

	public CompanyReceivedEvent(Company company) {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "CompanyReceivedEvent [id=" + id + ", timestamp=" + timestamp + ", company=" + company + "]";
	}

}
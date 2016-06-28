package de.hdm.wim.events.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Representation of an Event we create when receiving a company from the
 * DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class CompanyReceivedEvent extends EventEntity {

	@OneToOne(cascade = { CascadeType.ALL })
	private Company company;

	public CompanyReceivedEvent() {
		super();
	}

	public CompanyReceivedEvent(Company company) {
		super();
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "CompanyReceivedEvent [company=" + company + ", getTimestamp()=" + getTimestamp() + ", getId()=" + getId() + ", toString()=" + super.toString() + "]";
	}
	
}
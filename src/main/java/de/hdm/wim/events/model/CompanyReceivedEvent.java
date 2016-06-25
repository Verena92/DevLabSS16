package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CompanyReceivedEvent implements Event {

	@OneToOne(cascade = {CascadeType.ALL})
	private Company company;

	public CompanyReceivedEvent(Company company) {
		this.company = company;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getTimestamp() {
		return new Date(); // TODO: use meaningful date
	}

	@Override
	@Id
	public String getId() {
		return ""; // TODO: use meaningful id
	}

}
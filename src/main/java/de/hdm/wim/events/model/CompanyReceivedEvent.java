package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CompanyReceivedEvent implements Event {

	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Company company;
	
	public CompanyReceivedEvent() {
		
	}

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
	public String getId() {
		return ""+this.id;
	}
}
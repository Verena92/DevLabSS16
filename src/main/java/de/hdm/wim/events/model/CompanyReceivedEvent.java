package de.hdm.wim.events.model;

import java.util.Date;

public class CompanyReceivedEvent implements Event {

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
	public String getId() {
		return ""; // TODO: use meaningful id
	}

}
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
public class EmployeeReceivedEvent implements Event {

	@Id
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Employee employee;
	
	public EmployeeReceivedEvent() {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
	}

	public EmployeeReceivedEvent(Employee employee) {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	@Override
	public String getId() {
		return this.id;
	}
}
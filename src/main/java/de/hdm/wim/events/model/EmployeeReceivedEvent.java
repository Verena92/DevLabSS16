package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class EmployeeReceivedEvent implements Event {

	@Id
	private String id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Employee employee;

	public EmployeeReceivedEvent(Employee employee) {
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getTimestamp() {
		return new Date(); // TODO: use meaningful date
	}

	@Override
	@Id
	public String getId() {
		return this.id; // TODO: use meaningful id
	}

	public void setId(String id) {
		this.id = id;
	}
}
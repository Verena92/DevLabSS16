package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class EmployeeReceivedEvent implements Event {

	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Employee employee;
	
	public EmployeeReceivedEvent() {
		
	}

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
	public String getId() {
		return ""+this.id;
	}
}
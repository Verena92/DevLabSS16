package de.hdm.wim.events.model;

import java.util.Date;

public class EmployeeReceivedEvent implements Event {

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
	public String getId() {
		return ""; // TODO: use meaningful id
	}

}
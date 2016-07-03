package de.hdm.wim.events.model.event;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import de.hdm.wim.events.model.Employee;

/**
 * Representation of an Event we create when receiving an employee from the
 * DocumentRepresentation
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class EmployeeReceivedEvent extends EventEntity {

	@OneToOne(cascade = { CascadeType.ALL })
	private Employee employee;

	public EmployeeReceivedEvent() {
		super();
	}

	public EmployeeReceivedEvent(Employee employee) {
		super();
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "EmployeeReceivedEvent [employee=" + employee + ", getTimestamp()=" + getTimestamp() + ", getId()=" + getId() + ", toString()=" + super.toString() + "]";
	}
	
	
}
package de.hdm.wim.events.model;

/**
 * Base class for Persons
 * @author Jens
 *
 */
public class Person {

	private String firstName;
	private String lastName;

	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}

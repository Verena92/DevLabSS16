package de.hdm.wim.events.model;

public class Participant extends Person {

	private String hangoutsId;
	private String hangoutsSessionId;
	private String role;

	public Participant(String firstName, String lastName, String hangoutsId, String hangoutsSessionId, String role) {
		super(firstName, lastName);
		this.hangoutsId = hangoutsId;
		this.hangoutsSessionId = hangoutsSessionId;
		this.role = role;
	}

	public String getHangoutsId() {
		return hangoutsId;
	}

	public String getHangoutsSessionId() {
		return hangoutsSessionId;
	}

	public String getRole() {
		return role;
	}

}

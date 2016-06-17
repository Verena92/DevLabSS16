package de.hdm.wim.events.model;

public class User {
	private String firstname;
	private String lastname;
	private String google_id;
	private String hangouts_id;
	
	public User(String fn, String ln, String gid, String hid){
		this.firstname = fn;
		this.lastname = ln;
		this.google_id = gid;
		this.hangouts_id = hid;
	}
		
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getGoogle_id() {
		return google_id;
	}
	public void setGoogle_id(String google_id) {
		this.google_id = google_id;
	}
	public String getHangouts_id() {
		return hangouts_id;
	}
	public void setHangouts_id(String hangouts_id) {
		this.hangouts_id = hangouts_id;
	}
}

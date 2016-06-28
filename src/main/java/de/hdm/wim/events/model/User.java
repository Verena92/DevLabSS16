package de.hdm.wim.events.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Representation of an User as a part of the internal token from the SpeechTokenizer
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class User {
	private String firstname;
	private String lastname;
	@Id
	private String google_id;
	private String hangouts_id;

	/**
	 * Default constructor for JPA
	 */
	public User() {

	}
	
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

package de.hdm.wim.events.model.event;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Representation of an User as a part of the internal token from the
 * SpeechTokenizer
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

	public User(String gid, String hid) {
		this.google_id = gid;
		this.hangouts_id = hid;
	}

	public User(String fn, String ln, String gid, String hid) {
		this.firstname = fn;
		this.lastname = ln;
		this.google_id = gid;
		this.hangouts_id = hid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((google_id == null) ? 0 : google_id.hashCode());
		result = prime * result + ((hangouts_id == null) ? 0 : hangouts_id.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (google_id == null) {
			if (other.google_id != null)
				return false;
		} else if (!google_id.equals(other.google_id))
			return false;
		if (hangouts_id == null) {
			if (other.hangouts_id != null)
				return false;
		} else if (!hangouts_id.equals(other.hangouts_id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		return true;
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

	@Override
	public String toString() {
		return "User [firstname=" + firstname + ", lastname=" + lastname + ", google_id=" + google_id + ", hangouts_id=" + hangouts_id + "]";
	}

}

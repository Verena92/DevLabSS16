package de.hdm.wim.events.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class models a Token with another structure. Different to the sent Token
 * this InternalToken separates the User from the rest of the Token
 *   
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
@Entity
public class InternalToken implements Event {

	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private String keyword;
	@OneToOne(cascade = {CascadeType.ALL})
	private User user;
	@OneToOne(cascade = {CascadeType.ALL})
	private KeywordInformation keywordInformation;
	
	
	/**
	 * Default constructor for JPA
	 */
	public InternalToken() {
		
	}
	
	/**
	 * Constructor used to create an InternalToken out of a Token
	 */
	public InternalToken(Token token){
		this.user = new User(token.getCreatedByFirstName(),token.getCreatedByLastName(),token.getCreatedByUserId(),token.getHangoutsId());
		this.keywordInformation = token.getKeywordInformation();
		this.id = token.getId();
		this.timestamp = token.getTimestamp();
		this.keyword = token.getKeyword();
	}
	
	@Override
	public Date getTimestamp() {
		return timestamp;
	}
	@Override
	public String getId() {
		return id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public KeywordInformation getKeywordInformation() {
		return keywordInformation;
	}
	public void setKeywordInformation(KeywordInformation keywordInformation) {
		this.keywordInformation = keywordInformation;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "InternalToken [id=" + id + ", timestamp=" + timestamp + ", keyword=" + keyword + ", user=" + user + ", keywordInformation=" + keywordInformation + "]";
	}
}

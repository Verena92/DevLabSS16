package de.hdm.wim.events.model;

import java.util.Date;

/**
 * This class models a Token with another structure. It implements the User who has created the TokenEvent.
 */
public class InternalToken implements Event {

	private String id;
	private Date timestamp;
	private String keyword;
	private User user;
	private KeywordInformation keywordInformation;
	
	/**
	 * Use this mapper to map a Token to a TokenMapper.
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
}

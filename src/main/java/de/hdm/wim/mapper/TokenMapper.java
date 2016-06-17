package de.hdm.wim.mapper;

import de.hdm.wim.events.model.Event;
import de.hdm.wim.events.model.KeywordInformation;
import de.hdm.wim.events.model.Token;
import de.hdm.wim.events.model.User;

import java.util.Date;

/**
 * This class models a Token with another structure. TokenMapper implements the User who has created the TokenEvent.
 */
public class TokenMapper implements Event {

	private String id;
	private Date timestamp;
	private String keyword;
	private User user;
	private KeywordInformation keywordInformation;
	
	/**
	 * Use this mapper to map a Token to a TokenMapper.
	 */
	public TokenMapper map(Token token){
		User user = new User(token.getCreatedByFirstName(),token.getCreatedByLastName(),token.getCreatedByUserId(),token.getHangoutsId());
		KeywordInformation ki = token.getKeywordInformation();
		TokenMapper tm = new TokenMapper(token.getId(), token.getTimestamp(), token.getKeyword(), user, ki);
		return tm;
	}
	
	public TokenMapper(String id, Date timestamp, String keyword, User user, KeywordInformation keywordInformation) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.keyword = keyword;
		this.user = user;
		this.keywordInformation = keywordInformation;
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

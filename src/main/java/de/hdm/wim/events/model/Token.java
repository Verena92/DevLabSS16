package de.hdm.wim.events.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Token implements Event {

	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private String keyword;
	
	private String createdByFirstName;
	private String createdByLastName;
	private String createdByUserId;
	private String hangoutsId;
	
	private KeywordInformation keywordInformation;
	
	public static Token createDummyToken() {
		List<String> projects = new ArrayList<String>();
		projects.add("P001");
		projects.add("P003");
		
		List<String> companies = new ArrayList<String>();
		companies.add("C001");
		companies.add("C007");
		
		List<String> products = new ArrayList<String>();
		
		List<String> employees = new ArrayList<String>();
		
		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, products, employees);
		Token token = new Token("id", new Date(), "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		return token;
	}
	
	/**
	 * Default constructor needed for JSON Parsing and JPA
	 */
	public Token() {
		
	}
	
	public Token(String id, Date timestamp, String keyword, String createdByFirstName, String createdByLastName, String createdByUserId, String hangoutsId,
			KeywordInformation keywordInformation) {
		this.id = id;
		this.timestamp = timestamp;
		this.keyword = keyword;
		this.createdByFirstName = createdByFirstName;
		this.createdByLastName = createdByLastName;
		this.createdByUserId = createdByUserId;
		this.hangoutsId = hangoutsId;
		this.keywordInformation = keywordInformation;
	}
	
	public String getCreatedByFirstName() {
		return createdByFirstName;
	}

	public void setCreatedByFirstName(String createdByFirstName) {
		this.createdByFirstName = createdByFirstName;
	}

	public String getCreatedByLastName() {
		return createdByLastName;
	}

	public void setCreatedByLastName(String createdByLastName) {
		this.createdByLastName = createdByLastName;
	}

	public String getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(String createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public String getHangoutsId() {
		return hangoutsId;
	}

	public void setHangoutsId(String hangoutsId) {
		this.hangoutsId = hangoutsId;
	}

	public KeywordInformation getKeywordInformation() {
		return keywordInformation;
	}

	public void setKeywordInformation(KeywordInformation keywordInformation) {
		this.keywordInformation = keywordInformation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", keyword=" + keyword + ", creator=" + "]";
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
package de.hdm.wim.events.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Token implements Event {

	/**
	 * SimpleDateFormat used to format incoming Strings into java.util.Date.
	 * (2016 5 1 12 0 0 therefore represents the 1. May 2016 12 o'clock)
	 */
	public static final SimpleDateFormat DATE_FORMAT_yyyy_M_d_H_m_s = new SimpleDateFormat("yyyy M d H m s");

	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private String keyword;
	
	private String createdByFirstName;
	private String createdByLastName;
	private String createdByUserId;
	private String hangoutsId;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private KeywordInformation keywordInformation;
	
	/**
	 * Default constructor needed for JSON Parsing and JPA
	 */
	public Token() {
		
	}
	
	public Token(String timeString, String keyword, String createdByFirstName, String createdByLastName, String createdByUserId, String hangoutsId,
			KeywordInformation keywordInformation) throws ParseException {
		this.id = UUID.randomUUID().toString();
		this.timestamp = DATE_FORMAT_yyyy_M_d_H_m_s.parse(timeString);
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

	@Override
	public String getId() {
		return this.id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", timestamp=" + timestamp + ", keyword=" + keyword + ", createdByFirstName=" + createdByFirstName + ", createdByLastName=" + createdByLastName
				+ ", createdByUserId=" + createdByUserId + ", hangoutsId=" + hangoutsId + ", keywordInformation=" + keywordInformation + "]";
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestampString) throws ParseException {
		this.timestamp = DATE_FORMAT_yyyy_M_d_H_m_s.parse(timestampString);
	}
}
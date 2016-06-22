package de.hdm.wim.events.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	@OneToOne
	private KeywordInformation keywordInformation;
	
	public static Token createDummyTokenWithRelatedEmployeeM001() {
		List<String> projects = new ArrayList<String>();
		
		List<String> companies = new ArrayList<String>();
		
		List<String> products = new ArrayList<String>();
		
		List<String> employees = new ArrayList<String>();
		employees.add("M001");
		
		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, products, employees);
		
		Token token = null;
		try {
			token = new Token("id", "2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static Token createDummyTokenWithRelatedProductPR001() {
		List<String> projects = new ArrayList<String>();
		
		List<String> companies = new ArrayList<String>();
		
		List<String> products = new ArrayList<String>();
		products.add("PR001");
		
		List<String> employees = new ArrayList<String>();
		
		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, products, employees);
		
		Token token = null;
		try {
			token = new Token("id", "2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static Token createDummyTokenWithRelatedCompanyU001() {
		List<String> projects = new ArrayList<String>();
		
		List<String> companies = new ArrayList<String>();
		companies.add("U001");
		
		List<String> products = new ArrayList<String>();
		
		List<String> employees = new ArrayList<String>();
		
		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, products, employees);
		
		Token token = null;
		try {
			token = new Token("id", "2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static Token createDummyTokenWithRelatedProjectP004() {
		List<String> projects = new ArrayList<String>();
		projects.add("P004");
		
		List<String> companies = new ArrayList<String>();
		
		List<String> products = new ArrayList<String>();
		
		List<String> employees = new ArrayList<String>();
		
		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, products, employees);
		
		Token token = null;
		try {
			token = new Token("id", "2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static Token createDummyTokenWithRelatedProjectsP001andP002() {
		List<String> projects = new ArrayList<String>();
		projects.add("P001");
		projects.add("P002");
		
		List<String> companies = new ArrayList<String>();
		
		List<String> products = new ArrayList<String>();
		
		List<String> employees = new ArrayList<String>();
		
		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, products, employees);
		
		Token token = null;
		try {
			token = new Token("id", "2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static Token createDummyTokenWithExactly1RelatedProjectP001() {
		List<String> projects = new ArrayList<String>();
		projects.add("P001");
		
		List<String> companies = new ArrayList<String>();
		
		List<String> products = new ArrayList<String>();
		
		List<String> employees = new ArrayList<String>();
		
		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, products, employees);
		
		Token token = null;
		try {
			token = new Token("id", "2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static Token createDummyToken() {
		List<String> projects = new ArrayList<String>();
		projects.add("P002");
		projects.add("P003");
		
		List<String> companies = new ArrayList<String>();
		companies.add("C001");
		companies.add("C007");
		
		List<String> products = new ArrayList<String>();
		
		List<String> employees = new ArrayList<String>();
		
		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, products, employees);
		
		Token token = null;
		try {
			token = new Token("id", "2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	/**
	 * Default constructor needed for JSON Parsing and JPA
	 */
	public Token() {
		
	}
	
	public Token(String id, String timeString, String keyword, String createdByFirstName, String createdByLastName, String createdByUserId, String hangoutsId,
			KeywordInformation keywordInformation) throws ParseException {
		this.id = id;
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
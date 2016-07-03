package de.hdm.wim.events.model.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DocumentSuggestionReactionEvent implements Event {

	/**
	 * SimpleDateFormat used to format incoming Strings into java.util.Date.
	 * (2016 5 1 12 0 0 therefore represents the 1. May 2016 12 o'clock)
	 */
	public static final SimpleDateFormat DATE_FORMAT_yyyy_M_d_H_m_s = new SimpleDateFormat("yyyy M d H m s");

	@Id
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	private String userId;
	private String hangoutsId;
	private String documentName;
	private String drivePath;
	private boolean accepted;

	public DocumentSuggestionReactionEvent() {
		this.id = UUID.randomUUID().toString();
		this.timestamp = new Date();
	}

	public DocumentSuggestionReactionEvent(String timeString, String userId, String hangoutsId, String documentName, String drivePath, boolean accepted) throws ParseException {
		this.id = UUID.randomUUID().toString();
		this.timestamp = DATE_FORMAT_yyyy_M_d_H_m_s.parse(timeString);
		this.userId = userId;
		this.hangoutsId = hangoutsId;
		this.documentName = documentName;
		this.drivePath = drivePath;
		this.accepted = accepted;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestampString) throws ParseException {
		this.timestamp = DATE_FORMAT_yyyy_M_d_H_m_s.parse(timestampString);
	}

	@Override
	public String getId() {
		return this.id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHangoutsId() {
		return hangoutsId;
	}

	public void setHangoutsId(String hangoutsId) {
		this.hangoutsId = hangoutsId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDrivePath() {
		return drivePath;
	}

	public void setDrivePath(String drivePath) {
		this.drivePath = drivePath;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
}
package de.hdm.wim.events.model;

import java.util.Date;

/**
 * A SpeechTokenEvent represents a Request from a user.
 * 
 * @author Jens
 *
 */
public class SpeechTokenEvent extends EventEntity {

	private String userId;
	private SpeechToken token;

	public SpeechTokenEvent(String id, String userId, SpeechToken token, Date timestamp) {
		super(id, timestamp);
		this.userId = userId;
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public SpeechToken getToken() {
		return token;
	}

	@Override
	public String toString() {
		return "SpeechTokenEvent [id= " + super.getId() + ", userId=" + userId + ", timestamp=" + super.getTimestamp()
				+ ", token=" + token + "]";
	}
}
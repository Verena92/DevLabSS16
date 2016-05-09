package de.hdm.wim.events.model;

import java.util.List;

public class SpeechToken {

	private Participant user;
	private String keyword;
	private List<String> relatedKeywords;

	public SpeechToken(Participant user, String keyword, List<String> relatedKeywords) {
		this.user = user;
		this.keyword = keyword;
		this.relatedKeywords = relatedKeywords;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public Participant getUser() {
		return user;
	}

	public List<String> getRelatedKeywords() {
		return relatedKeywords;
	}

}

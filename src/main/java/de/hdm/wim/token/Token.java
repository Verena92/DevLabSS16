package de.hdm.wim.token;

import java.util.Date;


import de.hdm.wim.events.model.Event;

public class Token implements Event {

	private String id;
	private String keyword;
	private Creator creator;

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

	public Creator getCreator() {
		return creator;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", keyword=" + keyword + ", creator=" + creator + "]";
	}

	@Override
	public Date getTimestamp() {
		return new Date();
	}

}

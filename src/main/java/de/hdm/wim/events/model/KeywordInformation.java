package de.hdm.wim.events.model;

import java.util.List;

public class KeywordInformation {

	private List<String> projects;
	private List<String> companies;
	
	public KeywordInformation() {
		
	}
	
	public KeywordInformation(List<String> projects, List<String> companies) {
		super();
		this.projects = projects;
		this.companies = companies;
	}

	public List<String> getProjects() {
		return projects;
	}
	
	public void setProjects(List<String> projects) {
		this.projects = projects;
	}
	
	public List<String> getCompanies() {
		return companies;
	}
	
	public void setCompanies(List<String> companies) {
		this.companies = companies;
	}
}

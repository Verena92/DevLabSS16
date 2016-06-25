package models;

import java.util.ArrayList;

import org.apache.commons.collections.iterators.ArrayListIterator;

public class WordInformation {
	private ArrayList<String> projects;
	private ArrayList<String> persons;
	private ArrayList<String> companies;

	public ArrayList<String> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<String> projects) {
		this.projects = projects;
	}

	public ArrayList<String> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<String> persons) {
		this.persons = persons;
	}

	public ArrayList<String> getCompanies() {
		return companies;
	}

	public void setCompanies(ArrayList<String> companies) {
		this.companies = companies;
	}
}

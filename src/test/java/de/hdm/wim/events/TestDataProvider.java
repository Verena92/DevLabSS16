package de.hdm.wim.events;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import de.hdm.wim.events.documentrepresentation.SearchRequest;
import de.hdm.wim.events.model.Company;
import de.hdm.wim.events.model.Document;
import de.hdm.wim.events.model.DocumentForSpeechTokenizer;
import de.hdm.wim.events.model.Employee;
import de.hdm.wim.events.model.Project;
import de.hdm.wim.events.model.event.DocumentSuggestionReactionEvent;
import de.hdm.wim.events.model.event.KeywordInformation;
import de.hdm.wim.events.model.event.Token;
import de.hdm.wim.events.model.event.User;

public class TestDataProvider {

	public static Token createDummyTokenWithRelatedEmployeeM0001() {
		List<String> projects = new ArrayList<String>();

		List<String> companies = new ArrayList<String>();


		List<String> employees = new ArrayList<String>();
		employees.add("hangout12197171_ephemeral.id.google.com^b48de1652ed790");

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}

	public static Token createDummyTokenWithRelatedCompanyU0001() {
		List<String> projects = new ArrayList<String>();

		List<String> companies = new ArrayList<String>();
		companies.add("U0001");

		List<String> employees = new ArrayList<String>();

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}

	public static Token createDummyTokenWithRelatedProjectP0001() {
		List<String> projects = new ArrayList<String>();
		projects.add("P0001");

		List<String> companies = new ArrayList<String>();


		List<String> employees = new ArrayList<String>();

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2016 6 20 22 9 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}

	public static Token createDummyTokenWithRelatedProjectsP0001andP0002() {
		List<String> projects = new ArrayList<String>();
		projects.add("P0001");
		projects.add("P0002");

		List<String> companies = new ArrayList<String>();

		List<String> employees = new ArrayList<String>();

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2016 6 29 16 15 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static Token createDummyToken_2016_06_01_12_00_00() {
		List<String> projects = new ArrayList<String>();
		projects.add("P002");
		projects.add("P003");

		List<String> companies = new ArrayList<String>();
		companies.add("C001");
		companies.add("C007");

		List<String> employees = new ArrayList<String>();

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2016 6 1 12 0 0", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	public static Token createDummyToken_2016_06_01_12_00_06() {
		List<String> projects = new ArrayList<String>();
		projects.add("P002");
		projects.add("P003");

		List<String> companies = new ArrayList<String>();
		companies.add("C001");
		companies.add("C007");

		List<String> employees = new ArrayList<String>();

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2016 6 1 12 0 6", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * Helper method to create a dummy Company
	 * 
	 * @return a dummy Company with fix values
	 */
	public static Company createDummyCompany() {
		List<String> employees = new ArrayList<String>();
		employees.add("M0101");
		employees.add("M0102");

		List<String> projects = new ArrayList<String>();
		projects.add("P0001");
		projects.add("P0003");

		Company company = new Company("U0001", "HighNet GmbH", "Finanzen", "Stuttgart", employees, projects, 600);
		return company;
	}

	public static Document createDummyDocument() {
		List<String> keywords = new ArrayList<String>();
		keywords.add("Ideensammlung");
		keywords.add("Aufgabenverteilung");
		keywords.add("Vorgehensmodell");
		List<String> projects = new ArrayList<String>();
		projects.add("P001");
		Document document = null;
		try {
			document = new Document("D0002", "Besprechungsprotokoll_HighNet_15-01-2016", "Textdokument", "15-01-2016T12:00:00", "1vJNvuPnCwg37yKZRsRuWvDn_LIwF5N4nHm_Xm1SIn8g",
					"M0001", "Fertiggestellt", "1.0", keywords, projects, "1onKOGZFLOKReA1unsstajHHy6eBK-C6rn52i1Ra4A78", "Protokoll");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return document;
	}

	public static DocumentForSpeechTokenizer createDummyDocumentForSpeechTokenizer() {
		DocumentForSpeechTokenizer documentForSpeechTokenizer = new DocumentForSpeechTokenizer(new User("hangoutF2AA23F4_ephemeral.id.google.com^b005d30bf33378", "AP36tYfMptB_3_cJW6AOwyJAIgAdMt5jKh6lyqVUTFVSapjVT0fRNg"), createDummyDocument());
		return documentForSpeechTokenizer;
	}

	/**
	 * Helper method to create a dummy Employee
	 * 
	 * @return a dummy Employee with fix values
	 */
	public static Employee createDummyEmployee() {
		List<String> projects = new ArrayList<String>();
		projects.add("P0001");
		projects.add("P0100");

		Employee employee = new Employee("M0001", "Maier", "Lisa", "Projektleiter", "U0002", "hangoutsUserID", "driveUserID", projects);
		return employee;
	}


	/**
	 * Helper method to create a dummy Project
	 * 
	 * @return a dummy Project with fix values
	 */
	public static Project createDummyProject() {
		List<String> involvedCompanies = new ArrayList<String>();
		involvedCompanies.add("U0003");
		List<String> projectMembers = new ArrayList<String>();
		projectMembers.add("M0001");
		projectMembers.add("M0002");
		Project project = new Project("P0100", "HighNet", "M0001", projectMembers, involvedCompanies, "Gruen");
		return project;
	}
	
	public static SearchRequest createDummySearchRequest() {
		List<String> employees = new ArrayList<String>();
		List<String> companies = new ArrayList<String>();
		List<String> projects = new ArrayList<String>();
		projects.add("P0001");
		String documentClass = "Calculation";

		SearchRequest searchRequest = new SearchRequest(employees, companies, projects, documentClass);
		return searchRequest;
	}
	
	public static DocumentSuggestionReactionEvent createDummyDocumentSuggestionReactionAcceptedEvent() {
		DocumentSuggestionReactionEvent documentSuggestionReactionEvent = null;
		try {
			documentSuggestionReactionEvent = new DocumentSuggestionReactionEvent("1990 12 12 12 12 12", "user", "hangout", "documentNa", true);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return documentSuggestionReactionEvent;
	}

	
	public static DocumentSuggestionReactionEvent createDummyDocumentSuggestionReactionDeclinedEvent() {
		DocumentSuggestionReactionEvent documentSuggestionReactionEvent = null;
		try {
			documentSuggestionReactionEvent = new DocumentSuggestionReactionEvent("1990 12 12 12 12 12", "user", "hangout", "documentNa", false);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return documentSuggestionReactionEvent;
	}

	public static Token createDummyTokenWithDocumentClassKeyword() {
		List<String> projects = new ArrayList<String>();
		List<String> companies = new ArrayList<String>();
		List<String> employees = new ArrayList<String>();

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2016 6 20 22 9 10", "Abschlussbericht", "jens", "lindner", "userId", "hangoutId", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}

	public static Token createDummyTokenWithRelatedProjectsP0001andP0002_2017() {
		List<String> projects = new ArrayList<String>();
		projects.add("P0001");
		projects.add("P0002");

		List<String> companies = new ArrayList<String>();

		List<String> employees = new ArrayList<String>();

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2017 6 29 15 30 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	
	
	public static Token createDummyTokenWithRelatedProjectsP0001andP0100() {
		List<String> projects = new ArrayList<String>();
		projects.add("P0001");
		projects.add("P0100");

		List<String> companies = new ArrayList<String>();

		List<String> employees = new ArrayList<String>();

		KeywordInformation keywordInformation = new KeywordInformation(projects, companies, employees);

		Token token = null;
		try {
			token = new Token("2016 6 29 17 20 10", "amg", "jens", "lindner", "asdf", "fdasHangouts", keywordInformation);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return token;
	}
	
	

}

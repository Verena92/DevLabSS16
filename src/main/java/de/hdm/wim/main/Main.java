package de.hdm.wim.main;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hdm.wim.events.TestDataProvider;
import de.hdm.wim.events.documentrepresentation.DocumentRepresentationRequester;
import de.hdm.wim.events.documentrepresentation.SearchRequest;
import de.hdm.wim.events.model.Company;
import de.hdm.wim.events.model.Document;
import de.hdm.wim.events.model.DocumentForSpeechTokenizer;
import de.hdm.wim.events.model.Employee;
import de.hdm.wim.events.model.Product;
import de.hdm.wim.events.model.Project;

public class Main {

	public static void main(String[] args) {
		doRestCalls();
//		printJSONStuff();
	}

	private static void doRestCalls() {
//		SpeechTokenSender speechTokenSender = new SpeechTokenSender();
//		Token token = Token.createDummyToken();
//		speechTokenSender.sendDocument(token);

		DocumentRepresentationRequester documentRepresentationRequester = new DocumentRepresentationRequester();
//		SearchRequest searchRequest = new SearchRequest();
//		List<String> foundDocuments = documentRepresentationRequester.getDocuments(searchRequest);
//		System.out.println("found Documents: " + foundDocuments);
		
		Company company = documentRepresentationRequester.getCompany("U0001");
		System.out.println( company);
		
		Project project = documentRepresentationRequester.getProject("P0001");
		System.out.println( project);
		
		Employee employee = documentRepresentationRequester.getEmployee("M0001");
		System.out.println( employee);
		
		Product product = documentRepresentationRequester.getProduct("PR0001");
		System.out.println( product);
		
		Document document = documentRepresentationRequester.getDocument(null);
		System.out.println( document);
	}

	private static void printJSONStuff() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			String tokenString = mapper.writeValueAsString(TestDataProvider.createDummyToken());
			System.out.println(tokenString);
			
			String companyString = mapper.writeValueAsString(TestDataProvider.createDummyCompany());
			System.out.println(companyString);
			
			String productString = mapper.writeValueAsString(TestDataProvider.createDummyProduct());
			System.out.println(productString);
			
			String projectString = mapper.writeValueAsString(TestDataProvider.createDummyProject());
			System.out.println(projectString);
			
			String documentString = mapper.writeValueAsString(TestDataProvider.createDummyDocument());
			System.out.println(documentString);
			
			String searchRequestString = mapper.writeValueAsString(TestDataProvider.createDummySearchRequest());
			System.out.println( searchRequestString);
			
			String documentForSpeechString = mapper.writeValueAsString(TestDataProvider.createDummyDocumentForSpeechTokenizer());
			System.out.println( documentForSpeechString);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

package de.hdm.wim.main;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hdm.wim.events.documentrepresentation.DocumentRepresentationRequester;
import de.hdm.wim.events.model.Company;
import de.hdm.wim.events.model.Document;
import de.hdm.wim.events.model.Employee;
import de.hdm.wim.events.model.Product;
import de.hdm.wim.events.model.Project;
import de.hdm.wim.events.model.Token;

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
	}

	private static void printJSONStuff() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			String tokenString = mapper.writeValueAsString(Token.createDummyToken());
			System.out.println(tokenString);
			
			String companyString = mapper.writeValueAsString(Company.createDummyCompany());
			System.out.println(companyString);
			
			String productString = mapper.writeValueAsString(Product.createDummyProduct());
			System.out.println(productString);
			
			String projectString = mapper.writeValueAsString(Project.createDummyProject());
			System.out.println(projectString);
			
			String documentString = mapper.writeValueAsString(Document.createDummyDocument());
			System.out.println(documentString);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

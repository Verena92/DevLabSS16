package de.hdm.wim.events;

import org.junit.Before;
import org.junit.Test;

import de.hdm.wim.events.documentrepresentation.DocumentClassesWrapper;
import de.hdm.wim.events.documentrepresentation.DocumentIDsWrapper;
import de.hdm.wim.events.documentrepresentation.DocumentRepresentationRequester;
import de.hdm.wim.events.documentrepresentation.SearchRequest;
import de.hdm.wim.events.model.Company;
import de.hdm.wim.events.model.Document;
import de.hdm.wim.events.model.DocumentForSpeechTokenizer;
import de.hdm.wim.events.model.Employee;
import de.hdm.wim.events.model.Project;
import de.hdm.wim.events.speechtokenizer.SpeechTokenSender;

public class InterfaceTest {

	DocumentRepresentationRequester requester;

	@Before
	public void setup() {
		requester = new DocumentRepresentationRequester();
	}

	@Test
	public void test_documentIDs_request() {
		SearchRequest searchRequest = TestDataProvider.createDummySearchRequest();
		System.out.println(searchRequest);
		DocumentIDsWrapper wrapper = requester.getDocumentIDs(searchRequest);
		System.out.println(wrapper);
	}

	@Test
	public void test_project_request() {
		Project project = requester.getProject("P0001");
		System.out.println(project);
	}

	@Test
	public void test_company_request() {
		Company company = requester.getCompany("U0001");
		System.out.println(company);
	}

	@Test
	public void test_employee_request() {
		Employee employee = requester.getEmployee("hangout12197171_ephemeral.id.google.com^b48de1652ed790");
		System.out.println(employee);
	}

	@Test
	public void test_document_request() {
		Document document = requester.getDocument("D0001");
		System.out.println(document);
	}

	@Test
	public void test_document_classes_request() {
		DocumentClassesWrapper wrapper = requester.getDocumentClasses();
		System.out.println(wrapper);
	}

	@Test
	public void test_send_document_to_speechtoken() {
		SpeechTokenSender speechTokenSender = new SpeechTokenSender();
		DocumentForSpeechTokenizer document = TestDataProvider.createDummyDocumentForSpeechTokenizer();
		speechTokenSender.sendDocument( document);
	}

}

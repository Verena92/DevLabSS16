package de.hdm.wim.events;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TokenTest {
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void test_token_with_only_one_related_project_occured() throws Exception {
		EventService eventService = new EventService();
		eventService.insertToken(TestDataProvider.createDummyTokenWithRelatedProjectP0001());
        int amountOfRulesFired = EventService.getKieSession().fireAllRules();
        
        assertEquals( 3, amountOfRulesFired);
        assertEquals( "A Token with exactly 1 related project occured", EventService.getResultList().get(0));
        assertEquals( "Request a document for a project", EventService.getResultList().get(1));
        assertEquals( "send Document to SpeechToken", EventService.getResultList().get(2));
	}
	
	@Test
	public void test_token_with_only_one_related_company_occured() throws Exception {
		EventService eventService = new EventService();
		eventService.insertToken(TestDataProvider.createDummyTokenWithRelatedCompanyU0001());
        int amountOfRulesFired = EventService.getKieSession().fireAllRules();
        
        assertEquals( 3, amountOfRulesFired);
        assertEquals( "A Token with exactly 1 related company occured", EventService.getResultList().get(0));
        assertEquals( "Request a document for a company", EventService.getResultList().get(1));
        assertEquals( "send Document to SpeechToken", EventService.getResultList().get(2));
	}
	
	@Test
	public void test_token_with_only_one_related_employee_occured() throws Exception {
		EventService eventService = new EventService();
		eventService.insertToken(TestDataProvider.createDummyTokenWithRelatedEmployeeM0001());
        int amountOfRulesFired = EventService.getKieSession().fireAllRules();
        
        assertEquals( 3, amountOfRulesFired);
        assertEquals( "A Token with exactly 1 related employee occured", EventService.getResultList().get(0));
        assertEquals( "Request a document for an employee", EventService.getResultList().get(1));
        assertEquals( "send Document to SpeechToken", EventService.getResultList().get(2));
	}
	
	@Test
	public void test_document_suggestion_accepted() throws Exception {
		new EventService().insertCustom(TestDataProvider.createDummyDocumentSuggestionReactionAcceptedEvent());
        int amountOfRulesFired = EventService.getKieSession().fireAllRules();
        
        assertEquals( 1, amountOfRulesFired);
        assertEquals( "A DocumentSuggestion was accepted", EventService.getResultList().get(0));
	}
	
	@Test
	public void test_document_suggestion_declined() throws Exception {
		new EventService().insertCustom(TestDataProvider.createDummyDocumentSuggestionReactionDeclinedEvent());
        int amountOfRulesFired = EventService.getKieSession().fireAllRules();
        
        assertEquals( 1, amountOfRulesFired);
        assertEquals( "A DocumentSuggestion was declined", EventService.getResultList().get(0));
	}
	
//	@Test 
//	public void test_token_with_document_class_occured() throws Exception {
//		entryPoint.insert(new InternalToken(TestDataProvider.createDummyTokenWithDocumentClassKeyword()));
//        int amountOfRulesFired = EventService.getKieSession().fireAllRules();
//        
//        assertEquals( 1, amountOfRulesFired);
//        assertEquals( "A Token with a documentClass occured", EventService.getResultList().get(0));
//	}	
	
	@Test
	public void test_token_with_two_related_projects_and_token_with_document_class_occured() throws Exception {
		EventService eventService = new EventService();
		eventService.insertToken(TestDataProvider.createDummyTokenWithDocumentClassKeyword());
		eventService.insertToken(TestDataProvider.createDummyTokenWithRelatedProjectsP0001andP0100());
		
		int amountOfRulesFired = EventService.getKieSession().fireAllRules();
		
		assertEquals( 2, amountOfRulesFired);
        assertEquals( "A Token with a documentClass occured", EventService.getResultList().get(0));
        assertEquals( "A Token with a documentClass occured and Token(s) with several related projects occured", EventService.getResultList().get(1));
	}	
	
	@Test
	public void test_token_with_two_related_projects_and_token_with_document_class_occured_but_after_10min_which_is_too_late() throws Exception {
		EventService eventService = new EventService();
		eventService.insertToken(TestDataProvider.createDummyTokenWithRelatedProjectsP0001andP0002_2017());
		eventService.insertToken(TestDataProvider.createDummyTokenWithDocumentClassKeyword());
        int amountOfRulesFired = EventService.getKieSession().fireAllRules();
        
        assertEquals( 1, amountOfRulesFired);
        assertEquals( "A Token with a documentClass occured", EventService.getResultList().get(0));
	}	
}

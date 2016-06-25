package de.hdm.wim.events;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import de.hdm.wim.events.interceptor.EventStorageInterceptor;
import de.hdm.wim.events.model.Token;

public class TokenTest {
	
	private EntryPoint entryPoint;
	private KieSession kieSession;
	private List<String> resultList;

	@Before
	public void setUp() {
		KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
    	kieSession = kContainer.newKieSession();
    	resultList = new ArrayList<>();
    	kieSession.setGlobal( "resultList", resultList);
    	entryPoint = kieSession.getEntryPoint("SpeechTokenEventStream");
    	kieSession.addEventListener( new EventStorageInterceptor());
	}
	
	@After
	public void tearDown() {
		kieSession.dispose();
	}
	
	@Test
	public void test_no_token() throws Exception {
		//no rule should fire, as no token has occured
        
		int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 0, amountOfRulesFired);
	}
	
	@Test
	public void test_one_token_occured() throws Exception {
		//ohne token has fired, so the 'A Token has fired' rule should fire
		entryPoint.insert(TestDataProvider.createDummyToken());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 1, amountOfRulesFired);
        assertEquals( "A Token occured", resultList.get(0));
	}
	
	@Test
	public void test_token_with_only_one_related_project_occured() throws Exception {
		entryPoint.insert(TestDataProvider.createDummyTokenWithRelatedProjectP0001());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 4, amountOfRulesFired);
        assertEquals( "A Token occured", resultList.get(0));
        assertEquals( "A Token with exactly 1 related project occured", resultList.get(1));
        assertEquals( "Request a document for a project", resultList.get(2));
        assertEquals( "send Document to SpeechToken", resultList.get(3));
	}
	
	@Test
	public void test_token_with_only_one_related_company_occured() throws Exception {
		entryPoint.insert(TestDataProvider.createDummyTokenWithRelatedCompanyU0001());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 4, amountOfRulesFired);
        assertEquals( "A Token occured", resultList.get(0));
        assertEquals( "A Token with exactly 1 related company occured", resultList.get(1));
        assertEquals( "Request a document for a company", resultList.get(2));
        assertEquals( "send Document to SpeechToken", resultList.get(3));
	}
	
	@Test
	@Ignore //products might not be searched
	public void test_token_with_only_one_related_product_occured() throws Exception {
		entryPoint.insert(TestDataProvider.createDummyTokenWithRelatedProductPR001());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 4, amountOfRulesFired);
        assertEquals( "A Token occured", resultList.get(0));
        assertEquals( "A Token with exactly 1 related product occured", resultList.get(1));
        assertEquals( "Request a document for a product", resultList.get(2));
        assertEquals( "send Document to SpeechToken", resultList.get(3));
	}
	
	@Test
	public void test_token_with_only_one_related_employee_occured() throws Exception {
		entryPoint.insert(TestDataProvider.createDummyTokenWithRelatedEmployeeM0001());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 4, amountOfRulesFired);
        assertEquals( "A Token occured", resultList.get(0));
        assertEquals( "A Token with exactly 1 related employee occured", resultList.get(1));
        assertEquals( "Request a document for an employee", resultList.get(2));
        assertEquals( "send Document to SpeechToken", resultList.get(3));
	}
	
	@Test
	public void test_document_suggestion_accepted() throws Exception {
		entryPoint.insert(TestDataProvider.createDummyDocumentSuggestionReactionAcceptedEvent());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 1, amountOfRulesFired);
        assertEquals( "A DocumentSuggestion was accepted", resultList.get(0));
	}
	
	@Test
	public void test_document_suggestion_declined() throws Exception {
		entryPoint.insert(TestDataProvider.createDummyDocumentSuggestionReactionDeclinedEvent());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 1, amountOfRulesFired);
        assertEquals( "A DocumentSuggestion was declined", resultList.get(0));
	}
}

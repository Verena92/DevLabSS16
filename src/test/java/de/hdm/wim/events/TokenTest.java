package de.hdm.wim.events;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

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
		entryPoint.insert(Token.createDummyToken());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 1, amountOfRulesFired);
        assertEquals( "A Token occured", resultList.get(0));
	}
	
	@Test
	public void test_token_with_related_project_P001_occured() throws Exception {
		entryPoint.insert(Token.createDummyTokenWithRelatedProjectsP001andP002());
        int amountOfRulesFired = kieSession.fireAllRules();
        
        assertEquals( 2, amountOfRulesFired);
        assertEquals( "A Token occured", resultList.get(0));
        assertEquals( "A Token with related project P001 occured", resultList.get(1));
	}
}

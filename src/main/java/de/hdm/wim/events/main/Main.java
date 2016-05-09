package de.hdm.wim.events.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.drools.compiler.lang.ParseException;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import de.hdm.wim.events.interceptor.EventStorageInterceptor;
import de.hdm.wim.events.mocks.UserInterfaceMock;
import de.hdm.wim.events.model.Event;
import de.hdm.wim.events.model.Participant;
import de.hdm.wim.events.model.SpeechToken;

public class Main {

	
	public static void main( String[] args) throws Exception {
		//set everything up
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession();
		
		kieSession.addEventListener( new EventStorageInterceptor());
		
		//insert events
		try {
			List<Event> events = new ArrayList<>();
			Participant user = new Participant("jens", "lindner", "jens@gmail.com", "hangoutssessionId-asdf-1234", "it architect");
			List<String> related = new ArrayList<>();
			related.add("mercedes");
			related.add("tuning");
			SpeechToken speechToken = new SpeechToken(user, "amg", related);
			events.add( UserInterfaceMock.createSpeechTokenEvent("1", "jens", speechToken, "01-01-2016:12:00:00"));
			events.add( UserInterfaceMock.createSpeechTokenEvent("2", "max", speechToken, "01-01-2016:12:00:30"));
			events.add( UserInterfaceMock.createSpeechTokenEvent("3", "sebastian", speechToken, "01-01-2016:12:01:30"));
			events.add( UserInterfaceMock.createSpeechTokenEvent("4", "stefan", speechToken, "01-01-2016:12:02:45"));

			for (Event event : events) {
				System.out.println( "insert");
				insert(kieSession, "SpeechTokenEventStream", event);
				kieSession.fireAllRules(); //this should run in a separate thread or something, so we check for correlation every X seconds. or after Y events got inserted.
			}
		} catch (ParseException e) {
			//never happens in this example
			System.out.println("A ParseException happened during creation of SpeechTokenEvents: " + e.getMessage());
		} finally {
			kieSession.dispose();
		}
	}

	private static void insert(KieSession kieSession, String stream, Event event) {
		//insert event
		SessionPseudoClock pseudoClock = kieSession.getSessionClock();
		EntryPoint ep = kieSession.getEntryPoint(stream);
		ep.insert(event);
		
		//advance pseudoClock(System) time to event time
		long advanceTime = ((Event) event).getTimestamp().getTime() - pseudoClock.getCurrentTime();
		pseudoClock.advanceTime(advanceTime, TimeUnit.MILLISECONDS);
	}
}

package de.hdm.wim.events.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.drools.compiler.lang.ParseException;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import de.hdm.wim.events.model.Event;
import de.hdm.wim.events.model.EventEntity;
import de.hdm.wim.token.Token;

/**
 * Test class for JPA testing within JEE environment
 * @author Jens
 *
 */
@Stateless
@WebService
public class Test {

	private static EntityManager em;
	private static EntityManagerFactory emFactory;

	public void doRealStuff() {
		//set everything up
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession();
		
		//kieSession.addEventListener( new EventStorageInterceptor());
		
		//insert events
		try {
			List<Event> events = new ArrayList<>();
//			Participant user = new Participant("jens", "lindner", "jens@gmail.com", "hangoutssessionId-asdf-1234", "it architect");
//			List<String> related = new ArrayList<>();
//			related.add("mercedes");
//			related.add("tuning");
//			SpeechToken speechToken = new SpeechToken(user, "amg", related);
//			events.add( UserInterfaceMock.createSpeechTokenEvent("1", "jens", speechToken, "01-01-2016:12:00:00"));
//			events.add( UserInterfaceMock.createSpeechTokenEvent("2", "max", speechToken, "01-01-2016:12:00:30"));
//			events.add( UserInterfaceMock.createSpeechTokenEvent("3", "sebastian", speechToken, "01-01-2016:12:01:30"));
//			events.add( UserInterfaceMock.createSpeechTokenEvent("4", "stefan", speechToken, "01-01-2016:12:02:45"));

			events.add( new Token());
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
		
		emFactory = Persistence.createEntityManagerFactory("test");
		em = emFactory.createEntityManager();
		System.out.println( "em: " + em);

		em.getTransaction().begin();
		EventEntity eventEntity = new EventEntity("someString", new Date());
		em.persist(eventEntity);
		em.getTransaction().commit();
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

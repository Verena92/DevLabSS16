package de.hdm.wim.smarpet;

import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.drools.compiler.lang.ParseException;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.hdm.wim.events.model.Event;
import de.hdm.wim.events.model.Token;

/**
 * Test class for JPA testing within JEE environment
 * @author Jens
 *
 */
@Path("/events")
public class TestService {

	private static EntityManager em;
	private static EntityManagerFactory emFactory;
	
	public static void main( String[] args) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Token t = new Token();
			t.setId("1");
			t.setKeyword("amg");
			
//			EventEntity e = new EventEntity("asdf", new Date());
			String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(t);
			System.out.println(jsonInString);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
	}

	@POST
	@Path("/insert")
	@Consumes("application/json")
	public Response doRealStuff(Token token) {
		//set everything up
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession();
		
		//kieSession.addEventListener( new EventStorageInterceptor());
		try {
			insert(kieSession, "SpeechTokenEventStream", token);
			kieSession.fireAllRules(); //this should run in a separate thread or something, so we check for correlation every X seconds. or after Y events got inserted.
		} catch (ParseException e) {
			//never happens in this example
			System.out.println("A ParseException happened during creation of SpeechTokenEvents: " + e.getMessage());
		} finally {
			kieSession.dispose();
		}
		
		emFactory = Persistence.createEntityManagerFactory("test");
		em = emFactory.createEntityManager();

		em.getTransaction().begin();
		//EventEntity eventEntity = new EventEntity("someString", new Date());
		em.persist(token);
		em.getTransaction().commit();
		
		return Response.status(200).entity("persisted: " + token.getId()).build();
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

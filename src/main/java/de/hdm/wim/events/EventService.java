package de.hdm.wim.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.drools.compiler.lang.ParseException;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import de.hdm.wim.events.documentrepresentation.DocumentClassesWrapper;
import de.hdm.wim.events.documentrepresentation.DocumentRepresentationRequester;
import de.hdm.wim.events.model.event.DocumentSuggestionReactionEvent;
import de.hdm.wim.events.model.event.Event;
import de.hdm.wim.events.model.event.InternalToken;
import de.hdm.wim.events.model.event.KeywordInformation;
import de.hdm.wim.events.model.event.Token;
import de.hdm.wim.events.model.event.User;

/**
 *
 */
@Path("/events")
public class EventService {
	private static KieServices kieServices;
	private static KieContainer kieContainer;
	private static KieSession kieSession;
	static private List<String> resultList;

	private static EntityManager em;
	private static EntityManagerFactory emFactory;

	private static List<String> documentClasses;

	public static KieSession getKieSession() {
		return kieSession;
	}

	public static List<String> getResultList() {
		return resultList;
	}

	static {
		kieServices = KieServices.Factory.get();
		kieContainer = kieServices.getKieClasspathContainer();
		kieSession = kieContainer.newKieSession();
		resultList = new ArrayList<String>();
		kieSession.setGlobal("resultList", resultList);

		emFactory = Persistence.createEntityManagerFactory("test");
		em = emFactory.createEntityManager();

		DocumentRepresentationRequester documentRepresentationRequester = new DocumentRepresentationRequester();
		DocumentClassesWrapper documentClassesWrapper = documentRepresentationRequester.getDocumentClasses();
		documentClasses = documentClassesWrapper.getDocumentClasses();
	}

	private static List<User> activeUsers = new ArrayList<User>();

	public static List<User> getActiveUsers() {
		return activeUsers;
	}

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public Response test() {
		return Response.status(200).entity("test successful").build();
	}

	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response insertToken(Token token) {
		System.out.println("Received Token: " + token);
		InternalToken internalToken = new InternalToken(token);
		
		// add User to activeUsers if he isn't present yet
		if (!activeUsers.contains(internalToken.getUser())) {
			activeUsers.add(internalToken.getUser());
		}

		// if the token is no relevant information (no associated projects,
		// companies, employees
		// and is no keyword, it won't get processed
		if (hasNoFurtherRelevance(internalToken)) {
			return Response.status(200).build();
		}

		try {
			insert(kieSession, "SpeechTokenEventStream", internalToken);
			kieSession.fireAllRules();
		} catch (ParseException e) {
			System.out.println("A ParseException happened during creation of SpeechTokenEvents: " + e.getMessage());
			return Response.status(400).entity(e).build();
		}
		return Response.status(200).build();
	}

	@POST
	@Path("/react")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/json")
	public Response insertReaction(DocumentSuggestionReactionEvent documentSuggestionReactionEvent) {
		try {
			insert(kieSession, "SpeechTokenEventStream", documentSuggestionReactionEvent);
		} catch (ParseException e) {
			System.out.println("A ParseException happened during creation of DocmentSuggestionReactionEvent: " + e.getMessage());
			return Response.status(400).entity(e).build();
		}
		return Response.status(200).build();
	}

	public void insertCustom(Event event) {
		insert(kieSession, "SpeechTokenEventStream", event);
	}

	private void insert(KieSession kieSession, String stream, Event event) {
		System.out.println("inserted object: " + event);
		SessionPseudoClock clock = kieSession.getSessionClock();
		EntryPoint ep = kieSession.getEntryPoint(stream);
		ep.insert(event);

		merge(event);

		long advanceTime = ((Event) event).getTimestamp().getTime() - clock.getCurrentTime();
		clock.advanceTime(advanceTime, TimeUnit.MILLISECONDS);
	}

	private void merge(Event event) {
		System.out.println( "try to merge: " + event);
		em.getTransaction().begin();
		em.merge(event);
		em.getTransaction().commit();
		System.out.println( "merged: " + event);
	}

	private static boolean hasNoRelatedEntities(KeywordInformation keywordInformation) {
		return keywordInformation.getCompanies().isEmpty() && keywordInformation.getEmployees().isEmpty() && keywordInformation.getProjects().isEmpty();
	}

	private static boolean hasNoFurtherRelevance(InternalToken internalToken) {
		return hasNoRelatedEntities(internalToken.getKeywordInformation()) && !isADocumentClass(internalToken.getKeyword());
	}

	private static boolean isADocumentClass(String keyword) {
		return documentClasses.contains(keyword);
	}
}
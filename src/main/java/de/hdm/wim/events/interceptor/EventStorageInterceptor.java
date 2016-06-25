package de.hdm.wim.events.interceptor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

public class EventStorageInterceptor implements RuleRuntimeEventListener {

	private static EntityManager em;
	private static EntityManagerFactory emFactory;

	static {
		emFactory = Persistence.createEntityManagerFactory("test");
		em = emFactory.createEntityManager();
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		System.out.println("EventStorageInterceptor: try to persist event: " + event);

//		em.getTransaction().begin();
//		em.merge(event.getObject());
//		em.getTransaction().commit();

	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
	}
}

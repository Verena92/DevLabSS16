package de.hdm.wim.events.interceptor;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

import de.hdm.wim.events.model.EventEntity;

public class EventStorageInterceptor implements RuleRuntimeEventListener {
	
	private static EntityManager em;
	private static EntityManagerFactory emFactory;

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		System.out.println("inserted: " + event);
		emFactory = Persistence.createEntityManagerFactory("test");
		em = emFactory.createEntityManager();
		System.out.println( "em: " + em);

		em.getTransaction().begin();
		EventEntity eventEntity = new EventEntity("someString", new Date());
		em.persist(eventEntity);
		em.getTransaction().commit();
	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		// TODO Auto-generated method stub
	}
}

package de.hdm.wim.events.interceptor;

import javax.persistence.EntityManager;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

public class EventStorageInterceptor implements RuleRuntimeEventListener {
	
	private EntityManager em;

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		System.out.println("inserted: " + event);
	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		// TODO Auto-generated method stub
	}
}

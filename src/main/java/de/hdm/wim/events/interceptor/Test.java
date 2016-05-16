package de.hdm.wim.events.interceptor;

import java.util.Date;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.hdm.wim.events.model.EventEntity;

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
		emFactory = Persistence.createEntityManagerFactory("test");
		em = emFactory.createEntityManager();
		System.out.println( "em: " + em);

		em.getTransaction().begin();
		EventEntity eventEntity = new EventEntity("someString", new Date());
		em.persist(eventEntity);
		em.getTransaction().commit();
	}
}

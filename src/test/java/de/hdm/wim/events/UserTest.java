package de.hdm.wim.events;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hdm.wim.events.model.event.User;

public class UserTest {

	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void test_equals() throws Exception {
		//Same GoogleID && same HangoutsID mean Equality.
		User user1 = new User("googleId1", "hangoutId1");
		User user2 = new User("googleId1", "hangoutId1");
		
		assertEquals(true, user1.equals(user2));
	}
	
	@Test
	public void test_equals_false() throws Exception {
		//Same HangoutsID but different GoogleID --> not equal
		User user1 = new User("googleId1", "hangoutId1");
		User user2 = new User("googleId2", "hangoutId1");
		
		assertEquals(false, user1.equals(user2));
	}
	
	@Test
	public void test_equals_false2() throws Exception {
		//Same GoogleID but different HangoutsID --> not equal
		User user1 = new User("googleId1", "hangoutId2");
		User user2 = new User("googleId1", "hangoutId1");
		
		assertEquals(false, user1.equals(user2));
	}
	
	@Test
	public void testListe() throws Exception {
		//Same GoogleID but different HangoutsID --> not equal
		User user1 = new User("googleId1", "hangoutId2");
		User user2 = new User("googleId1", "hangoutId1");
		User user3 = new User("googleId1", "hangoutId1");
		
		Set<User> users = new HashSet<User>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		System.out.println( users);
		
		assertEquals(false, user1.equals(user2));
	}
}

package de.hdm.wim.events;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import de.hdm.wim.events.model.User;

public class UserTest {

	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void test_equals() throws Exception {
		//Same GoogleID means Equality.
		User user1 = new User("googleId1", "hangoutId1");
		User user2 = new User("googleId1", "hangoutId2");
		
		assertEquals(true, user1.equals(user2));
	}
	
	@Test
	public void test_equals_false() throws Exception {
		//Same GoogleID means Equality. HangoutId is not so relevant, therefore user1 and user2 are not equal.
		User user1 = new User("googleId1", "hangoutId1");
		User user2 = new User("googleId2", "hangoutId1");
		
		assertEquals(false, user1.equals(user2));
	}
}

package de.hdm.wim.smarpet;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Bootstrapping Class to register REST Services
 * @author Jens
 *
 */
public class SmarpetApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();

	public SmarpetApplication() {
		//all services need to be added here
		singletons.add(new TestService());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
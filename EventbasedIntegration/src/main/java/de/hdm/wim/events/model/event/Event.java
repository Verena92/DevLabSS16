package de.hdm.wim.events.model.event;

import java.util.Date;

/**
 * Base Interface used for all Events
 * @author Jens
 *
 */
public interface Event {

	public abstract Date getTimestamp();

	public abstract String getId();

}

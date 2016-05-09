package de.hdm.wim.events.mocks;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdm.wim.events.model.SpeechToken;
import de.hdm.wim.events.model.SpeechTokenEvent;

/**
 * Dummy Mock implementation of the UserInterface that consumes the SpeechTokenEvents. 
 * Can be used to receive SpeechTokenEvents and send the DBResult to the SpeechTokenInterface.
 * 
 * @author Jens
 *
 */
public class UserInterfaceMock {

	/**
	 * Helper method to create SpeechTokenEvents
	 * 
	 * @param id
	 *            the unique! id of the SpeechTokenEvent
	 * @param userId
	 *            the user the SpeechTokenEvent comes from
	 * @param token
	 *            a relevant word
	 * @param timestampString
	 *            Format dd-MM-yyyy:hh:mm:ss, i.e. 01-01-2016:12:00:00
	 * @return a SpeechTokenEvent
	 * @throws ParseException
	 *             if the timestampString doesn't fit the pattern
	 */
	public static SpeechTokenEvent createSpeechTokenEvent(String id, String userId, SpeechToken token,
			String timestampString) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:hh:mm:ss");
		Date inputDate = dateFormat.parse(timestampString);

		return new SpeechTokenEvent(id, userId, token, inputDate);
	}
}

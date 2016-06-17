package de.hdm.wim.events.speechtokenizer;

import de.hdm.wim.events.model.Token;
import de.hdm.wim.events.restclient.BaseRestClient;

public class SpeechTokenSender {

	public static final String SPEECH_TOKENIZER_URL = "http://localhost:8080/EventbaseIntegration-4/rest/events/";
	
	//FIXME: needs to send Document - not token
	public void sendDocument(Token token) {
		BaseRestClient restClient = new BaseRestClient(SPEECH_TOKENIZER_URL);
		restClient.doPost(token);
	}
}

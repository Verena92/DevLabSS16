package de.hdm.wim.events.speechtokenizer;

import de.hdm.wim.events.model.DocumentForSpeechTokenizer;
import de.hdm.wim.events.restclient.BaseRestClient;
import de.hdm.wim.helper.PropertiesHelper;

/**
 * 
 * Class used to post Documents to the SpeechToken component
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class SpeechTokenSender {
	
	//FIXME: needs to send actual Document instead of just printing it out
	public void sendDocument(DocumentForSpeechTokenizer document) {
		String url = PropertiesHelper.getProperties("url.speech_tokenizer");
		//BaseRestClient restClient = new BaseRestClient(url);
		//restClient.doPost("document", document);
		System.out.println("SpeechTokenSende sending the following Document: " + document);
	}
}

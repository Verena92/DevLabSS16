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
	
	public void sendDocument(DocumentForSpeechTokenizer document) {
		String url = PropertiesHelper.getProperties("url.speech_tokenizer");
		BaseRestClient restClient = new BaseRestClient(url);
		restClient.doPostDocument("PostDocuments", document);
		System.out.println("SpeechTokenSender sending the following Document: " + document);
	}
}

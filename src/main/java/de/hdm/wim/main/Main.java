package de.hdm.wim.main;

import java.util.Date;
import java.util.List;

import de.hdm.wim.events.documentrepresentation.DocumentRepresentationRequester;
import de.hdm.wim.events.documentrepresentation.SearchRequest;
import de.hdm.wim.events.model.Token;
import de.hdm.wim.events.speechtokenizer.SpeechTokenSender;

public class Main {

	public static void main( String[] args) {
		SpeechTokenSender speechTokenSender = new SpeechTokenSender();
		Token token =new Token("iidd2", new Date(), "amg");
		speechTokenSender.sendDocument(token);
		
		DocumentRepresentationRequester documentRepresentationRequester = new DocumentRepresentationRequester();
		SearchRequest searchRequest = new SearchRequest();
		List<String> foundDocuments = documentRepresentationRequester.getDocuments(searchRequest);
		System.out.println( "found Documents: " + foundDocuments);
	}
}

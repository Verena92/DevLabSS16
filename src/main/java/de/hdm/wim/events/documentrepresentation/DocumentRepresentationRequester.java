package de.hdm.wim.events.documentrepresentation;

import java.util.ArrayList;
import java.util.List;
import de.hdm.wim.events.restclient.BaseRestClient;

public class DocumentRepresentationRequester {

	public static final String DOCUMENT_REPRESENTATION_URL = "http://localhost:8080/EventbaseIntegration-4/rest/events/";
	
	//FIXME: use reasonable SearchRequest and act upon it
	public List<String> getDocuments(SearchRequest searchRequest) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL);
		String result = restClient.doGet("test");
		List<String> list = new ArrayList<String>();
		list.add(result);
		return list;
	}	
}
package de.hdm.wim.events.restclient;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import de.hdm.wim.events.documentrepresentation.SearchRequest;
import de.hdm.wim.events.model.DocumentForSpeechTokenizer;

/**
 * Abstraction class to encapsulate the technical REST handling
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class BaseRestClient {
	
	private Client client;
	private URI uri;
	private WebTarget target;
	
	public BaseRestClient( String baseUrl) {
		client = ClientBuilder.newClient(new ClientConfig());
		uri =  UriBuilder.fromUri(baseUrl).build();
		target = client.target(uri);
	}

	public Response doGet(String thing) {
		return target.path(thing).request().accept(MediaType.APPLICATION_JSON).get(Response.class);
	}
	
	public Response doPostSearchRequest(String path, SearchRequest searchRequest) {
	    return target.path(path).request().post(Entity.entity(searchRequest,MediaType.APPLICATION_JSON),Response.class);
	}
	
	public Response doPostDocument(String path, DocumentForSpeechTokenizer document) {
	    return target.path(path).request().post(Entity.entity(document,MediaType.APPLICATION_JSON),Response.class);
	}
}

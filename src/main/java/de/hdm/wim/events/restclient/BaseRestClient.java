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

import de.hdm.wim.events.model.Token;

public class BaseRestClient {
	
	private Client client;
	private URI uri;
	private WebTarget target;
	
	public BaseRestClient( String baseUrl) {
		client = ClientBuilder.newClient(new ClientConfig());
		uri =  UriBuilder.fromUri(baseUrl).build();
		target = client.target(uri);
	}
	
	public Response doPost(Token token) {
	    return target.path("insert").request().post(Entity.entity(token,MediaType.APPLICATION_JSON),Response.class);
	}

	public String doGet(String thing) {
		return target.path(thing).request().accept(MediaType.APPLICATION_JSON).get(Response.class).toString();
	}
}

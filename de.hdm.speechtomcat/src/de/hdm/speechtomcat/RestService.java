package de.hdm.speechtomcat;

	/**
	 * Diese Klasse stellt eine POST Schnittstelle zur Verfügung über die Dokumenten URLs mit Zuordnung zu User und Hangouts Session entgegengenommen werden können. 
	 * Diese Daten können über eine GET Schnittstelle wieder abgerufen werden.
	 * @author Verena Hofmann
	 * @author Maren Graeff
	 */

	import java.sql.*;	


import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.jersey.core.util.Base64;
import org.json.simple.parser.JSONParser;


@Path("/rest") 
public class RestService {
		
		
	/**
	 * Testklasse ob der Server angersprochen werden kann und das war korrekt deployed wurde
	 * @return String
	 */
	@GET
	@Path("/hello") 
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey";
	}
	
	
	private static JSONObject Object;	 
	private static Logger log = Logger.getLogger(RestService.class.getName());
		  
	/**
	 * Testmethode um die Ip des zugreifenden Clients auszulesen
	 * @return IP
	 */
	
	@GET
	@Path("/register") 
	@Produces("application/x-www-form-urlencoded")
		  
	public String registerClient() { 
		try { 
			return InetAddress.getLocalHost().getHostAddress(); 
		} catch (UnknownHostException e) { 
			e.printStackTrace(); 
			} 
		return null; 
	} 

	
	
	/**
	 * @POST um die Daten userId, hangoutsId, documentName und drivePath entgegenzunehmen und im Dateisystem usr/local/postdocument/document.json abzuspeichern
	 * wird von der Eventgruppe aufgerufen
	 * @Consumes JSON
	 * @param objDocument
	 * @throws JSONException
	 * @throws JsonProcessingException
	 * @throws org.codehaus.jettison.json.JSONException
	 * @throws FileNotFoundException
	 */
		
	
	@POST
	@Path("/PostDocuments")
	@Consumes("application/json")
	@Produces("application/json")
	public static void postDocuments(Object objDocument) throws JSONException, JsonProcessingException, org.codehaus.jettison.json.JSONException, FileNotFoundException  {
		
		/**
		 * Auslesen der Datei documents.json und initialisieren des Object Mappers
		 */
				
		org.codehaus.jettison.json.JSONObject obj = null;
		
		obj = new org.codehaus.jettison.json.JSONObject(objDocument.toString());
		
		ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		InputStream is = new FileInputStream("/usr/local/postdocument/documents.json");
 			 
 	        try {
 	        	
 	        	/** 
 	        	 * Aktueller Inhalt der documents.json Datei wird ausgelesen, das neu erhaltene Objekt angehängt und wieder in documents.json abgespeichert
 	        	 */
 	        	
 	        	JsonParser jp = new JsonFactory().createParser(is);
 	        	

 	        	Object old = mapper.readTree(jp);
 	        	
 	        	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
 	        	String json = ow.writeValueAsString(old);
 	        	
 	            
 	            JSONObject jsonObject = new JSONObject(json);
 	            
 	            jsonObject.append("documents", obj);
 	       
 	            FileWriter file = new FileWriter("/usr/local/postdocument/documents.json", false);
 	 			file.write(jsonObject.toString());

 	 			file.flush();
 	 			file.close();
 	 			
 	 			/**
 	 			 * Abfangen von Fehlern
 	 			 */
 	            

			} catch (FileNotFoundException e) {
				e.printStackTrace();
 	        
     		} catch (Exception ex) {
     			ex.printStackTrace();}

 		 }
		  
		  
/**
 * @GET um die Daten userId, hangoutsId, documentName und drivePath aus dem Dateisystem usr/local/postdocument/document.json auszulesen 
 * wird vom hangoutsclient aufgerufen
 * @param getDocument
 * @return content
 * @throws JSONException
 * @throws JsonProcessingException
 * @throws org.codehaus.jettison.json.JSONException
 * @throws FileNotFoundException
 */
	

		@GET
		@Path("/GetDocuments")
		@Consumes("application/json")
		@Produces("application/json")
		public static Response getDocuments(Object getDocument) throws JSONException, JsonProcessingException, org.codehaus.jettison.json.JSONException, FileNotFoundException {		
	
			/**
			 * Initialisieren des ObjectMappers und einlesen der Datei documents.json
			 */
			ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

			InputStream is = new FileInputStream("/usr/local/postdocument/documents.json");
			
			String line ="";
			BufferedReader br = null;
			
			String json = "";
			
			try {
				
				/**
				 * Inhalt der Datei parsen und abspeichern
				 */
 	        	
				JsonParser jp = new JsonFactory().createParser(is);
 	        	
 	        	Object old = mapper.readTree(jp);
 	        	
 	        	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
 	        	json = ow.writeValueAsString(old);
				
 	        	/**
 	        	 * Abfangen von Fehlern
 	        	 */
 	        	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
	
     		} catch (Exception ex) {
     			ex.printStackTrace();
			}finally{
				try{
					if (br !=null)
						br.close();
				} catch (IOException ex){
					ex.printStackTrace();}
			}
			
			/**
			 * Inhalt der Datei documents.json zurückgeben
			 */
							
			return Response.status(200).entity(json).build();
			
 			} 
		
		}	
package de.hdm.speechtomcat;

	/**
	 * @author Verena Hofmann
	 * @author Maren Graeff
	 */

	import java.sql.*;	


//import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
//import org.codehaus.jackson.annotate.JsonMethod;
//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONObject;


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
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.jersey.core.util.Base64;
import org.json.simple.parser.JSONParser;


@Path("/rest") 
public class RestService {
		
		
	// This method is called if TEXT_PLAIN is requested
	@GET
	@Path("/hello") 
	@Produces(MediaType.TEXT_PLAIN)
	public String sayPlainTextHello() {
		return "Hello Jersey";
	}
	
	
	private static JSONObject Object;	 
	private static Logger log = Logger.getLogger(RestService.class.getName());
		  
	@GET
	@Path("/register") 
	@Produces("application/x-www-form-urlencoded")
		  
	//Methode um die IP Adresse des zugreifenden Clients auszulesen
	public String registerClient() { 
		try { 
			return InetAddress.getLocalHost().getHostAddress(); 
		} catch (UnknownHostException e) { 
			e.printStackTrace(); 
			} 
		return null; 
	} 

	
	
	// Interface to be called from Event Group
		  
	/*
	Wir nehmen entgegen:
	userId
	hangoutsId
	documentName
	drivePath*/

		  			
	// POST Statements to post relevant tokens and save them in filestream
		
	
	@POST
	@Path("/PostDocuments")
	@Consumes("application/json")
	@Produces("application/json")
	public static void postDocuments(Object objDocument) throws JSONException  {
				
		//JsonProcessingException
		//org.codehaus.jettison.json.JSONException
		
		//org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject(objDocument.toString());
		
		JSONObject obj = (JSONObject) objDocument;
		
//		String userId = obj.getString("userId");
//		String hangoutsId = obj.getString("hangoutsId");
//		String documentName = obj.getString("documentName");
//		String drivePath = obj.getString("drivePath");
//
//		System.out.println(userId+ " "+hangoutsId+" "+documentName+" "+drivePath);
		
		JSONParser parser = new JSONParser();

 		/*try {
 			String path = "/usr/local/postdocument/document";
 			String json = ".json";
 			FileWriter file = new FileWriter(path+documentName+json);
 			file.write(obj.toString());
 			file.flush();
 			file.close();
 			
 			
 			JSONParser parser = new JSONParser();
 			
 			 		
 		} catch (IOException e) {
 			e.printStackTrace();
 			log.info("Error");
 		}

 		System.out.print(obj);*/
 			 
 	        try {
 	 
 	            Object old = parser.parse(new FileReader("/usr/local/postdocument/documents.json"));
 	 
 	            JSONObject jsonObject = (JSONObject) old;
 	 
 	            JSONArray documents = (JSONArray) jsonObject.get("documents");
 	 
 	            documents.add(obj);
 	            
 	            FileWriter file = new FileWriter("/usr/local/postdocument/documents.json");
 	 			file.write(documents.toString()); //toJSONString()
 	 			file.flush();
 	 			file.close();
 	            
 	        } catch (Exception e) {
 	            e.printStackTrace();
 	        }
 			
 				  

			
 		/////////////////ENDE VERSION 2///////////////////

 		 }//Ende POST Methode
		  
		  

		/////////////////VERSUCH GET-Methode zum Auslesen der .json Datei///////////////////
	

		@GET
		@Path("/GetDocuments")
		@Consumes("application/json")
		@Produces("application/json")
		//public Response getDocuments(@PathParam("hangoutsId") String hangoutsId) throws JSONException {
		public static Response getDocuments(Object getDocument) throws JSONException, JsonProcessingException, org.codehaus.jettison.json.JSONException {		

			/////////////////START VERSION 1///////////////////
			
			//org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject(getDocument.toString());
			/*
			Object obj = null;

			try{
			
				java.io.FileInputStream fis = new java.io.FileInputStream("/usr/local/postdocument/documentBesprechungsprotokoll_HighNet_15-01-2016.json");
				java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);

				obj = (org.codehaus.jettison.json.JSONObject) ois.readObject();
				fis.close();
				
				}
				catch(Exception e){}
			return Response.status(200).entity(obj).build();
		}*/
			
			/////////////////ENDE VERSION 1///////////////////
			
			//documentBesprechungsprotokoll_HighNet_15-01-2016.json
			
			/////////////////START VERSION 2///////////////////
			
			
			
			String jsonData = "";
			String line ="";
			BufferedReader br = null;
	

			
			try{
				
				InputStream is = new FileInputStream("/usr/local/postdocument/documents.json");
				InputStreamReader isr = new InputStreamReader (is);
				br = new BufferedReader (isr);
			
			
				while ((line = br.readLine())  != null){
					jsonData += line + "\n";
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}finally{
				try{
					if (br !=null)
						br.close();
				} catch (IOException ex){
					ex.printStackTrace();
				}
			}
			
		
				
			return Response.status(200).entity(jsonData).build();

			
			/////////////////ENDE VERSION 1///////////////////
			  
			/////////////////START VERSION 3///////////////////
			
			/*
			//String hangoutsId = getDocument.getString("hangoutsId");
			
			JSONParser parser = new JSONParser();
			Object object = null;  
			JSONObject jsonObject = new JSONObject();
			
			try {

				object = new FileReader("/usr/local/postdocument/documentBesprechungsprotokoll_HighNet_15-01-2016.json");
				//jsonObject = (JSONObject) object;

//				String userId = (String) jsonObject.get("userId");
//				System.out.println(userId);
//				String hangoutsId = (String) jsonObject.get("hangoutsId");
//				System.out.println(hangoutsId);
//				String documentName = (String) jsonObject.get("documentName");
//				System.out.println(documentName);
//				String drivePath = (String) jsonObject.get("drivePath");
//				System.out.println(drivePath);
				
				


			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch(Exception e){}
			return Response.status(200).entity(jsonObject).build();*/
		
			/////////////////ENDE VERSION 3///////////////////

 		} //Ende GET Methode
		
		
		}//Ende RestService Methode


		
		
			
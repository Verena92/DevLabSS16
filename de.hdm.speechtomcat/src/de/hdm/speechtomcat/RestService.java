package de.hdm.speechtomcat;

	/**
	 * @author Verena Hofmann
	 * @author Maren Graeff
	 */

	import java.sql.*;	

	import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jettison.json.JSONArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
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
import org.json.JSONObject;

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
	//public static void postDocuments(Object objDocument) throws JSONException, JsonProcessingException, org.codehaus.jettison.json.JSONException {
				
	public void postDocuments(@QueryParam("userId") String userId, @QueryParam("hangoutsId") String hangoutsId, @QueryParam("documentName") String documentName, @QueryParam ("drivePath") String drivePath) throws JSONException, JsonProcessingException, org.codehaus.jettison.json.JSONException {
		   			
		log.info(userId+" "+hangoutsId+" "+documentName+" "+drivePath);
		
		
		org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject();
		String userId = obj.getString("userId");
		String hangoutsId = obj.getString("hangoutsId");
		String documentName = obj.getString("documentName");
		String drivePath = obj.getString("drivePath");

		System.out.println(userId+ " "+hangoutsId+" "+documentName+" "+drivePath);

		/////////////////START VERSION 1///////////////////
					
		/*try{
			java.io.FileOutputStream fos = new java.io.FileOutputStream("usr/local/postdocument/document.json");
		   	java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);

		   	oos.writeObject(obj);
		   	oos.flush();
		   	fos.close();
		} catch(Exception e){}*/
		
		/////////////////ENDE VERSION 1///////////////////

		  
		/////////////////START VERSION 2///////////////////
		 
		JSONObject jsonobj = new JSONObject();
		jsonobj.put(obj);

 		try {
 			FileWriter file = new FileWriter("/usr/local/postdocument/document.json");
 			file.write(jsonobj.toString());
 			file.flush();
 			file.close();
 		
 		} catch (IOException e) {
 			e.printStackTrace();
 		}

 		System.out.print(jsonobj);
			
 		/////////////////ENDE VERSION 2///////////////////

 		 }//Ende POST Methode
		  
		  
		  
		  
		/////////////////VERSUCH GET-Methode zum Auslesen der .json Datei///////////////////
	/*

		@GET
		@Path("/GetDocuments")
		@Produces("application/json")
		//public Response getDocuments(@PathParam("hangoutsId") String hangoutsId) throws JSONException {
		public Response getDocuments(Object getDocument) throws JSONException, JsonProcessingException, org.codehaus.jettison.json.JSONException {		

			/////////////////START VERSION 1///////////////////
			org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject(getDocument.toString());

			try{
				java.io.FileInputStream fis = new java.io.FileInputStream("/usr/local/postdocument/document.json");
				java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);

				obj = (org.codehaus.jettison.json.JSONObject) ois.readObject();
				fis.close();
				return Response.status(200).entity(jsonInString).build();
				}
				catch(Exception e){}
				*/
			
			/////////////////ENDE VERSION 1///////////////////

			  
			/////////////////START VERSION 2///////////////////
			/*
			JSONParser parser = new JSONParser();
			  
			try {

				Object obj = parser.parse(new FileReader("/usr/local/postdocument/document.json"));
				JSONObject jsonObject = (JSONObject) obj;

				String userId = (String) jsonObject.get("userId");
				System.out.println(userId);
				String hangoutsId = (String) jsonObject.get("hangoutsId");
				System.out.println(hangoutsId);
				String documentName = (String) jsonObject.get("documentName");
				System.out.println(documentName);
				String drivePath = (String) jsonObject.get("drivePath");
				System.out.println(drivePath);


			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			*/
			/////////////////ENDE VERSION 2///////////////////

 	//	} Ende GET Methode
		
		
}//Ende RestService Methode
		
		
		
		  
		   		/*@POST
		   		@Path("/PostDocuments")
		   		@Consumes(MediaType.APPLICATION_JSON)
		   		public void postDocuments(@QueryParam("userId") String userId, @QueryParam("hangoutsId") String hangoutsId, 
		   				@QueryParam("documentName") String documentName, @QueryParam ("drivePath") String drivePath) {
		   			
		   			log.info(userId+" "+hangoutsId+" "+documentName+" "+drivePath);
		   			
//		   			JSONObject obj = new JSONObject();
//		   			obj.put("userId", userId);
//		   			obj.put("hangoutsId", hangoutsId);
//		   			obj.put("documentName", documentName);
//		   			obj.put("drivePath", drivePath);
//		   			log.info(obj);
		   			
		   			org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject(obj.toString());
		   			String userId2 = obj.getString("userId");
		   			String hangoutsId2 = obj.getString("hangoutsId");
		   			String documentName2 = obj.getString("documentName");
		   			String drivePath2 = obj.getString("drivePath");

		   			System.out.println(userId+ " "+hangoutsId+" "+documentName+" "+drivePath);		   			
		   			
		   			
			   			
		   				JSONParser parser = new JSONParser();
		  			  
		   				try {

		   					Object obj = parser.parse(new FileReader("/usr/local/postdocument/document.json"));
		   					JSONObject jsonObject = (JSONObject) obj;

		   					String userId2 = (String) jsonObject.get("userId");
		   					System.out.println(userId);
		   					String hangoutsId2 = (String) jsonObject.get("hangoutsId");
		   					System.out.println(hangoutsId);
		   					String documentName2 = (String) jsonObject.get("documentName");
		   					System.out.println(documentName);
		   					String drivePath2 = (String) jsonObject.get("drivePath");
		   					System.out.println(drivePath);


		   				} catch (FileNotFoundException e) {
		   					e.printStackTrace();
		   			
		   		}
	}
		   		}
}
		  
	

		   			
		   			
		   	
		   			
		   			/*ArrayList documentArray = new ArrayList();
		   			
		   			documentArray.add("1234");
		   			documentArray.add("23456");
		   			documentArray.add("Beispiel");
		   			documentArray.add("www.google.de");
		   			
		   			String uploadFileLocation = "usr/local/postdocuments/json.txt";
		   			InputStream is = new FileInputStream(documentArray);
		   			saveData(is, uploadFileLocation);

		            String output = "File uploaded to : " + uploadFileLocation; 
		            private void saveData(InputStream is, String uploadFileLocation) {
		            	
		            }
				        try {
				                OutputStream out = new FileOutputStream(new File(uploadFileLocation));
				                int read = 0;
				                byte[] bytes = new byte[1024];

				                out = new FileOutputStream(new File(uploadFileLocation));
				               
				                while ((read = is.read(bytes)) != -1) 
				                {
				                  out.write(bytes, 0, read);
				                }
				                out.flush();
				                out.close();
				                log.info( "Document  posted");
		   				
		   			} catch (Exception e){
						log.error( "Document not posted"+e);
					}
		            }}
		   				
				        
				        /*java.io.FileOutputStream fos = new java.io.FileOutputStream("usr/local/postdocument/document.txt");
		   				java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);

		   				oos.writeObject(documentArray);
		   				oos.flush();
		   				fos.close();
		   				}
		   				catch(Exception e){}
		   		}*/
		   			
		   			/*ArrayList documentArray = new ArrayList();
		   			
		   			documentArray.add(userId);
		   			documentArray.add(hangoutsId);
		   			documentArray.add(documentName);
		   			documentArray.add(drivePath);
		   			
		   	        arrayInDatei(documentArray, new File("usr/local/postdocument/document.txt")); 
		   	        log.info("Funktion aufgerufen");
	   		}

		   	     private static void arrayInDatei(ArrayList documentArray, File file) {
		 			// TODO Auto-generated method stub
		 			PrintWriter printWriter = null;
		 				try {
		 					printWriter = new PrintWriter(new FileWriter(file));
		 		            Iterator iter = documentArray.iterator(); 
		 		            while(iter.hasNext()) { 
		 		                Object o = iter.next(); 
		 		                printWriter.println(o); 
		 		            }
		 		           log.info("try block ausgeführt");
		 				} catch (IOException e) { 
		 		            e.printStackTrace(); 
		 		           log.info("Catch block ausgeführt");
		 		        } finally { 
		 		            //try { 
		 		                if(printWriter != null) printWriter.close(); 
		 		               log.info("Finally block ausgeführt");
		 		               log.info(file);
		 		           // } catch (IOException ioe) { ioe.printStackTrace();}
		 		        }
		 			
		 		}*/
		   	  
	
		   		
		   					
		   			//String Data = new JSONObject{userId, hangoutsId, documentName, drivePath};
		   			
		   			
		   			
		   			
		   			/*JSONObject obj = new JSONObject();
		   			obj.put("userId", userId);
		   			obj.put("hangoutsId", hangoutsId);
		   			obj.put("documentName", documentName);
		   			obj.put("drivePath", drivePath);*/
		   			
		   		// try-with-resources statement based on post comment below
		   		/*
		   			try (FileWriter file = new FileWriter("usr/local/postdocuments/json.txt")) {
		   				file.write(obj.toJSONString());
		   				System.out.println("Successfully Copied JSON Object to File...");
		   				System.out.println("\nJSON Object: " + obj);
		   				log.info(obj);
		   			}*/
		   			
		   			/*String uploadFileLocation = "usr/local/postdocuments/json.txt";
		   			InputStream is = new FileInputStream(DocumentArray);
		   			saveData(is, uploadFileLocation);

		            String output = "File uploaded to : " + uploadFileLocation;
		           

		           return Response.status(200).entity(output).build();
		            }
		   			
		   		
		            private void saveData(InputStream is, String uploadFileLocation) {
				        try {
				                OutputStream out = new FileOutputStream(new File(uploadFileLocation));
				                int read = 0;
				                byte[] bytes = new byte[1024];

				                out = new FileOutputStream(new File(uploadFileLocation));
				               
				                while ((read = is.read(bytes)) != -1) 
				                {
				                  out.write(bytes, 0, read);
				                }
				                out.flush();
				                out.close();
				                log.info( "Document  posted");
		   				
		   			} catch (Exception e){
						log.error( "Document not posted"+e);
					}
		   			
		   		}*/
		  
		   		
		   	

		  	/*@POST
		    @Path("/uploadDocuments")
		    @Consumes(MediaType.MULTIPART_FORM_DATA)
		    public Response uploadFile( @FormDataParam("file") InputStream uploadedInputStream,
		        @FormDataParam("file") FormDataContentDisposition fileDetail) {

		        System.out.println("ok");
		        String uploadedFileLocation = "d://upload/" + "abc.pdf";

		            writeToFile(uploadedInputStream, uploadedFileLocation);

		            String output = "File uploaded to : " + uploadedFileLocation;

		            return Response.status(200).entity(output).build();

		            }

		    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		        try {
		                OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
		                int read = 0;
		                byte[] bytes = new byte[1024];

		                out = new FileOutputStream(new File(uploadedFileLocation));
		                while ((read = uploadedInputStream.read(bytes)) != -1) 
		                {
		                  out.write(bytes, 0, read);
		                }
		                out.flush();
		                out.close();
		            } catch (IOException e) {

		               e.printStackTrace();
		            }
		    }*/
		  
		    
		    // End of POST Statement

		    
		    
		    

		    // Interface to be called from InterfaceEvent.js
			// GET Statements to get the relevant entries of mysql database with drivePath for a hangpoutsId
					
					/* @GET
					 //@Path("/GetDocuments/{hangoutsId}")
					 @Path("/GetDocuments")
					 @Produces("application/json")
					 //public Response getDocuments(@PathParam("hangoutsId") String hangoutsId) throws JSONException {
					 public Response getDocuments() {		
					 jsonObject = new JSONObject();
							

							
							try {
							      // SQL SELECT query
							      //String query = "SELECT * FROM reference";

							       
							      // iterate through the java resultset
							      while (rs.next())
							      {
							    	System.out.println("Id="+rs.getInt("id")
							      }
							      st.close();
							      jsonObject.put("data", rs);
							} catch (SQLException e){
								e.printStackTrace();
								//log.error( "GetWordInformation: Can't get word information "+e);
							}
							
							return Response.status(200).entity(jsonObject.toString()).build();
					 	}
					
					}	 
					 
			//End of Get Statement
	
	*/
			
	
	
	
	
	
	
	
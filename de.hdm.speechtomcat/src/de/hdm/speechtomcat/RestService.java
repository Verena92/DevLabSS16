package de.hdm.speechtomcat;

	/**
	 * @author Verena Hofmann
	 * @author Maren Graeff
	 */

	import java.sql.*;	

	import org.apache.log4j.Logger;
	import org.codehaus.jettison.json.JSONArray;

	import java.io.File;
	import java.io.IOException;
	import java.sql.ResultSet;
	import java.text.ParseException;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Date;
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
	import javax.ws.rs.core.MediaType;
	import javax.ws.rs.core.Response;

	import org.json.JSONException;
	import org.json.JSONObject;

	import com.fasterxml.jackson.core.JsonGenerationException;
	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.databind.JsonMappingException;
	import com.fasterxml.jackson.databind.ObjectMapper;
	//import com.mysql.jdbc.Connection;
	//import com.mysql.jdbc.Statement;



	@Path("/rest") 
	public class RestService {
		
		
		// This method is called if TEXT_PLAIN is request
		  @GET
		  @Path("/hello") 
		  @Produces(MediaType.TEXT_PLAIN)
		  public String sayPlainTextHello() {
		    return "Hello Jersey";
		  }

		  // This method is called if XML is request
		 /* @GET
		  @Path("/GetDocuments") 
		  @Produces(MediaType.TEXT_XML)
		  public String sayXMLHello() {
		    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
		  }

		  // This method is called if HTML is request
		  @GET
		  @Path("/GetDocuments") 
		  @Produces(MediaType.TEXT_HTML)
		  public String sayHtmlHello() {
		    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
		        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
		  }*/
		  
	
		 
		  private static JSONObject jsonObject;	 
		  private static Logger log = Logger.getLogger(RestService.class.getName());

			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// Speech Token Interface
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						 		
					// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					// GET Statements
					// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					 @GET
					 //@Path("/GetDocuments/{hangoutsId}")
					 @Path("/GetDocuments/45678")
					 @Produces("application/json")
					//public Response getDocuments(@PathParam("hangoutsId") String hangoutsId) throws JSONException {
					public Response getDocuments() {		
					 jsonObject = new JSONObject();
							
							DataSource ds = null;

							
							Connection con = null;
							Statement st = null;
							ResultSet rs = null;
							
							try {
							    // create our mysql database connection
							    /* String myDriver = "org.gjt.mm.mysql.Driver";
							    String myUrl = "jdbc:mysql://146.148.67.230/documentreference";
							    Class.forName(myDriver).newInstance();
								Connection con = DriverManager.getConnection(myUrl, "speechtokenizer", "password");*/
								con = ds.getConnection();
								st = con.createStatement();
								rs = st.executeQuery("SELECT * FROM reference");
							       
							      // our SQL SELECT query. 
							      // if you only need a few columns, specify them by name instead of using "*"
							     // String query = "SELECT * FROM reference";
							 
							      // create the java statement
							      //Statement st = con.createStatement();
							       
							      // execute the query, and get a java resultset
							      //ResultSet rs = st.executeQuery(query);
							       
							      // iterate through the java resultset
							      while (rs.next())
							      {
							    	System.out.println("Id="+rs.getInt("id")+
							    						", UserId="+rs.getString("userId")+
							    						", HangoutsId="+rs.getString("hangoutsId")+
							    						", DocumentName="+rs.getString("documentName")+
							    						", drivePath="+rs.getString("drivePath")+
							    						", timestamp="+rs.getDate("timestamp"));
							    	/*int id = rs.getInt("id");
							        String userId = rs.getString("userId");
							        String hangoutId = rs.getString("hangoutsId");
							        String documentName = rs.getString("documentName");
							        String drivePath = rs.getString("drivePath");
							        Date timestamp = rs.getDate("timestamp");*/
							        
							         
							        // print the results
							       // System.out.format("%s, %s, %s, %s, %s, %s\n", id, userId, hangoutId, documentName, drivePath, timestamp);
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
					 
					 
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// END-----------------------Speech Token Interface
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			
		  
		  
		  
	// Event Interface

			 
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// INSERT Statements
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		  
		  /*
			@POST
			@Path( "/AddDocumentInformation" )
			@Consumes("application/json")
		    
			public void addDocumentInformation(@FormParam("userId") String userId, @FormParam("hangoutsId") String hangoutsId , 
			@FormParam("documentName") String documentName, @FormParam("drivePath") String drivePath) 
							throws IOException, ParseException, org.codehaus.jettison.json.JSONException {
				
					log.info(userId+" "+hangoutsId+" "+documentName+" "+drivePath);
					try{
						String UPDATE_TEMPLATE =  "prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#> "
				 		+ "INSERT DATA"
				 		+ "{ "
				 		+ "<http://www.semanticweb.org/alinasiebert/ontologies/2016/0/A-BOX_Cloud_Dokumente#"+name+"> "
				 		+ "Cloud_Dokumente:Name '"+name+"';"
				 		+ "Cloud_Dokumente:DriveDocumentID '"+driveDocumentID+"';"
				 		+ "Cloud_Dokumente:Schlagwort "+keyword+";"
				 		+ "Cloud_Dokumente:Dokumenttyp '"+documentType+"';"
				 		+ "Cloud_Dokumente:Speicherort '"+documentPath+"';"
				 		+ "Cloud_Dokumente:Status '"+status+"';"
				 		+ "Cloud_Dokumente:Version '"+version+"^^http://www.w3.org/2001/XMLSchema#int';"
				 		+ "Cloud_Dokumente:Erstellungsdatum '"+creationDate+"^^http://www.w3.org/2001/XMLSchema#dateTime';"
				 		+ "Cloud_Dokumente:Dokument_gehoert_zu_Projekt <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/A-BOX_Cloud_Dokumente#"+project+">;"
				 		+ "Cloud_Dokumente:Dokument_hat_Verfasser  <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#Lisa_Maier> ."
				 		+ "}";
						
						String id = UUID.randomUUID().toString();
						UpdateProcessor upp = UpdateExecutionFactory.createRemote(
			            UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), 
			            "http://localhost:3030/ds/update");
						upp.execute();
					} catch (Exception e){
						log.error( "AddDocumentInformation: Can��t add document information "+e);
					}
			}
			*/
			
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// EDIT Statements
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
/*
		  
		  				@POST
						@Path( "/EditDocumentMetadata" )
						@Consumes("application/x-www-form-urlencoded")
						
						public void editDocumentMetadata(@FormParam("name") String name, @FormParam("documentType") String documentType , 
						@FormParam("status") String status, @FormParam("documentPath") String documentPath, @FormParam("keyword") String keyword) 
						throws IOException, ParseException, org.codehaus.jettison.json.JSONException 
						{
							try{		
								String UPDATE_TEMPLATE =  "PREFIX foaf:  <http://xmlns.com/foaf/0.1/>"
									+ "prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#> "
									+ "DELETE { "
									+ "		?Document Cloud_Dokumente:Name         	'Besprechungsprotokoll_HighNet_15-01-2016' ."
									+ "		?Document Cloud_Dokumente:Status 		'Fertiggestellt' ."
									+ "		?Document Cloud_Dokumente:Schlagwort    'Ideensammlung' , 'Aufgabenverteilung' ."
									+ "		?Document Cloud_Dokumente:Dokumenttyp 	'Textdokument' . "
									+ "		?Document Cloud_Dokumente:Speicherort  	'https://drive.google.com/open?id=1vJNvuPnCwg37yKZRsRuWvDn_LIwF5N4nHm_Xm1SIn8k' ;}"
									+ "}"
									+ "INSERT { "
									+ "		?Document Cloud_Dokumente:Name         	'"+name+"' ."
									+ "		?Document Cloud_Dokumente:Status 		'"+status+"' ."
									+ "		?Document Cloud_Dokumente:Schlagwort    '"+keyword+"' ."
									+ "		?Document Cloud_Dokumente:Dokumenttyp 	'"+documentType+"' ."
									+ "		?Document Cloud_Dokumente:Speicherort  	'"+documentPath+"' ;"
									+ "WHERE"
									+ "{ "
									+ "		?Document Cloud_Dokumente:DriveDocumentID '1K4_pQgxm9dEx4HK5s5ghw740hkcOu8IrbpMFZ4RNuX0'"
									+ "}"; 
						
								String id = UUID.randomUUID().toString();
								UpdateProcessor upp = UpdateExecutionFactory.createRemote(
						        UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), 
						        "http://localhost:3030/ds/update");
								upp.execute();
							}catch (Exception e){
								log.error( "EditDocumentMetadata: Can��t edit document metadata "+e);
							}
						}}

			
		   */
			 

	
	


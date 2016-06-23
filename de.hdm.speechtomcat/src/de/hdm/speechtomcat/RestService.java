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
	
		  private static JSONObject jsonObject;	 
		  private static Logger log = Logger.getLogger(RestService.class.getName());
		  
		  @GET
		  @Path("/register") 
		  @Produces("application/x-www-form-urlencoded")
		  public String registerClient(){
			  String ip="10.10.10.10";
			  return ip;
			  
		  }
		  
		  // Interface to be called from Event Group
			
		  // POST Statements to post relevant tokens and save them in mysql database
		  /*
		  
		   		@POST
		   		@Path("/PostDocuments/{data}")
		   		@Consumes("application/x-www-form-urlencoded")
		   public void postDocuments(){
		   			
		   			
		   			
		   			
		   			
		   		}
		  */
		  
		  // End of POST Statement


			// Interface to be called from Interface.js
			
			// GET Statements to get the relevant entries of mysql database with drivePath for a hangpoutsId
					
					 @GET
					 //@Path("/GetDocuments/{hangoutsId}")
					 @Path("/GetDocuments")
					 @Produces("application/json")
					//public Response getDocuments(@PathParam("hangoutsId") String hangoutsId) throws JSONException {
					public Response getDocuments() {		
					 jsonObject = new JSONObject();
							
							//DataSource ds = "jdbc/documentreference";
							
							//Connection con = null;
							//Statement st = null;
							//ResultSet rs = null;
							
							try {
							    // create our mysql database connection
							    String myDriver = "com.mysql.jdbc.Driver";
							    String myUrl = "jdbc:mysql://146.148.67.230/documentreference";
							    //Class.forName(myDriver).newInstance();
								Connection con = DriverManager.getConnection(myUrl, "speechtokenizer", "password");
								//con = ds.getConnection();
								//st = con.createStatement();
								//rs = st.executeQuery("SELECT * FROM reference");
							       
							 
							      // create the java statement
							      Statement st = con.createStatement();
							      
							      // SQL SELECT query
							      //String query = "SELECT * FROM reference";
							       
							      // execute the query, and get a java resultset
							      ResultSet rs = st.executeQuery("SELECT * FROM reference");
							       
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
					 
			//End of Get Statement
	
	
	
			
	
	
	
	
	
	
	
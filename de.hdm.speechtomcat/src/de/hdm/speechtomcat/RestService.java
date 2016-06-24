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
	
			
	
	
	
	
	
	
	
package de.hdm.speechtomcat;

	/**
	 * @author Verena Hofmann
	 * @author Maren Graeff
	 */

	import java.sql.*;	


	import org.apache.log4j.Logger;
	import org.codehaus.jettison.json.JSONArray;
	

	import java.io.File;
	import java.io.FileWriter;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.net.InetAddress;
	import java.net.UnknownHostException;
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
	import javax.ws.rs.QueryParam;
	import javax.ws.rs.core.MediaType;
	import javax.ws.rs.core.Response;

	import org.json.JSONException;
	import org.json.JSONObject;

	import com.fasterxml.jackson.core.JsonGenerationException;
	import com.fasterxml.jackson.core.JsonProcessingException;
	import com.fasterxml.jackson.databind.JsonMappingException;
	import com.fasterxml.jackson.databind.ObjectMapper;
	import com.sun.jersey.core.util.Base64;


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
		  
		  /* Gescheiterter versuch
		  @POST
		    @Path("/PostDocuments")

		    @Consumes(MediaType.APPLICATION_JSON)
		    @Produces(MediaType.APPLICATION_JSON)

		    public JSONObject receiveJSON(JSONObject json) throws JSONException, IOException {
		        convertFile(json.getString("file"), json.getString("file_name"));
		        //Prints my json object
		        return json;
		    }

		    //Convert a Base64 string and create a file
		    private void convertFile(String file_string, String file_name) throws IOException{
		        byte[] bytes = Base64.decode(file_string);
		        File file = new File("usr/local/postdocuments"+file_name);
		        log.info("File saved");
		        FileOutputStream fop = new FileOutputStream(file);
		        fop.write(bytes);
		        fop.flush();
		        fop.close();
		    }*/
		  
		  
		  
		  
			
		  // POST Statements to post relevant tokens and save them in filestream
		  //Alternativ @PathParam?
		  
		   		@POST
		   		@Path("/PostDocuments")
		   		@Consumes(MediaType.APPLICATION_JSON)
		   		public Response postDocuments(@QueryParam("userId") String userId, @QueryParam("hangoutsId") String hangoutsId, 
		   				@QueryParam("documentName") String documentName, @QueryParam ("drivePath") String drivePath) {
		   			
		   			log.info(userId+" "+hangoutsId+" "+documentName+" "+drivePath);
		   			
		   			
		   					
		   			//String Data = new JSONObject{userId, hangoutsId, documentName, drivePath};
		   			
		   			JSONObject obj = new JSONObject();
		   			obj.put("userId", userId);
		   			obj.put("hangoutsId", hangoutsId);
		   			obj.put("documentName", documentName);
		   			obj.put("drivePath", drivePath);
		   			
		   		// try-with-resources statement based on post comment below
		   			try (FileWriter file = new FileWriter("usr/local/postdocuments/json.txt")) {
		   				file.write(obj.toJSONString());
		   				System.out.println("Successfully Copied JSON Object to File...");
		   				System.out.println("\nJSON Object: " + obj);
		   				console.log(obj);
		   			}
		   			
		   			/*String uploadFileLocation = "usr/local/postdocuments/json.txt";
		   			InputStream is = new FileInputStream(Data.toJSONString());
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
	
			
	
		  }
	
	
	
	
	
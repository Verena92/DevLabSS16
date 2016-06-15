package de.hdm.speechtomcat;

	/**
	 * @author Verena Hofmann
	 * @author Maren Gräff
	 */

	import org.apache.log4j.Logger;
	import org.codehaus.jettison.json.JSONArray;

	import java.io.File;
	import java.io.IOException;
	import java.text.ParseException;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.List;
	import java.util.UUID;

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



	@Path("/rest") 
	public class RestService {
		  private static JSONObject jsonObject;	 
		  private static Logger log = Logger.getLogger( RestService.class.getName() );
		  

	// Event Interface

			 
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// INSERT Statements
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		  
			@POST
			@Path( "/AddDocumentMetadata" )
			@Consumes("application/x-www-form-urlencoded")
		    
			public void addDocumentMetadata(@FormParam("name") String name, @FormParam("documentType") String documentType , 
			@FormParam("status") String status, @FormParam("documentPath") String documentPath, @FormParam("keyword") String keyword, 
					@FormParam("driveDocumetID") String driveDocumentID, @FormParam("version") String version, 
					@FormParam("creationDate") String creationDate, @FormParam("project") String project ) 
							throws IOException, ParseException, org.codehaus.jettison.json.JSONException {
				
					log.info(name+" "+documentType+" "+status+" "+documentPath+" "+keyword+" "+driveDocumentID);
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
						log.error( "AddDocumentMetadata: Can¥t add document metadata "+e);
					}
			}
			*/
			
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// EDIT Statements
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
								log.error( "EditDocumentMetadata: Can¥t edit document metadata "+e);
							}
						}}

			
		   
			 
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Speech Token Interface
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				 		
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// GET Statements
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			 
			 @GET
			 @Path("/GetWordinformation/{word}")
			 @Produces("application/json")
			 public Response getWordinformation(@PathParam("word") String word) throws JSONException {
					jsonObject = new JSONObject();
					Word wordInformation;
					ArrayList<Word> listWord = new ArrayList<Word>();				
					try {
						String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
								+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
								+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
								+ " SELECT  * "
								+ " WHERE   { ?Dokument ?Typ ?Ausgabe ."
								+ " FILTER regex(?Ausgabe, '"+word+"') . "
								+ "?Typ <http://www.w3.org/2000/01/rdf-schema#domain> ?Klassentyp ."
								+ "?Typ <http://www.w3.org/2000/01/rdf-schema#range> ?Datentyp}";
						
						QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

				        ResultSet results = qe.execSelect();
				        List var = results.getResultVars();
				        String documentMetadata="";
				        String documentName="";
				        while (results.hasNext()){
				        	wordInformation = new Word();
							QuerySolution qs = results.nextSolution();
							for(int i=0; i<var.size();i++){			
								String va = var.get(i).toString();
								RDFNode node = qs.get(va);
								String value = node.toString().substring(node.toString().indexOf("#")+1, node.toString().length());
								switch(i){
									case 0: wordInformation.setObjectRelation(getGraphInformatoin("<"+node.toString()+">"));
										documentName = node.toString().substring(node.toString().indexOf("#")+1, node.toString().length());
										break;
									case 1: wordInformation.setValueType(value);
										documentMetadata=value;
									break;
										case 2: wordInformation.setValue(node.toString());
									break;
										case 3: wordInformation.setClassName(value);
									
									if(value.equals("Dokument")){
										System.out.println(documentMetadata+"_von: "+documentName);
									}
									break;
										case 4: wordInformation.setDataType(value);
									break;
								}
							}
							listWord.add(wordInformation);
						}
				        qe.close();
					} catch(Exception e){
						log.error( "GetWordInformation: Can¥t get word information "+e);
					}
					jsonObject.put("data", listWord);
					return Response.status(200).entity(jsonObject.toString()).build();
			 	}
			 
			 public ArrayList<ObjectRelation>  getGraphInformatoin(String GrapfInformation) {
					jsonObject = new JSONObject();
					ObjectRelation objectRelation;
					ArrayList<ObjectRelation> listObjectRelation = new ArrayList<ObjectRelation>();
					
					try {
						String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
								+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
								+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
								+ " SELECT  * "
								+ " WHERE   { "+GrapfInformation+" ?Typ ?Ausgabe }";
						
						QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

				        ResultSet results = qe.execSelect();
				        List var = results.getResultVars();
				        while (results.hasNext()){
							QuerySolution qs = results.nextSolution();
							for(int i=0; i<var.size();i++){
								String va = var.get(i).toString();
								RDFNode node = qs.get(va);
								System.out.println(node.toString());
								if(va.equals("Typ")&&node.toString().contains("#")){
									RDFNode ausgabe = qs.get(var.get(i+1).toString());
									String type = node.toString().substring(node.toString().indexOf("#")+1, node.toString().length());
									String value = ausgabe.toString().substring(ausgabe.toString().indexOf("#")+1, ausgabe.toString().length());
									if(type.contains("_")){
										if(ausgabe.toString().contains("#")){
											objectRelation = new ObjectRelation();
											objectRelation.setType(type);
											objectRelation.setValue(value);
											listObjectRelation.add(objectRelation);
										}
									}
								}
							}
						}
				        qe.close();
						
					} catch(Exception e){
						log.error( "GetGraphInformation: Can¥t get Graph information document metadata "+e);
					}
					return listObjectRelation;
			}
			 
			 
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// END-----------------------Speech Token Interface
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	
}

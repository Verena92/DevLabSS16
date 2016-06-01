/**
 * @author Waldemar Jaufmann
 */

package controller;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.codehaus.jettison.json.JSONArray;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
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
import models.Document;
import models.ObjectRelation;
import models.Word;
import org.json.JSONException;
import org.json.JSONObject;



@Path("/rest") 
public class OIS {
	  private static JSONObject jsonObject;	  

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
					e.printStackTrace();
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
					e.printStackTrace();
				}
				return listObjectRelation;
		}
		 
		 
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// END-----------------------Speech Token Interface
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// GOOGLE Apps Script Interface
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 
		 @GET
		 @Path("/GetDocumentByTitle/{documentTitle}")
		 @Produces("application/json")
		 public Response getDocumentByTitle(@PathParam("documentTitle") String documentTitle) throws JSONException {
				jsonObject = new JSONObject();
				Document document;
				ArrayList<Document> listDocument = new ArrayList<Document>();
				ArrayList<String> listKeywort = new ArrayList<String>();				
				try {
					String sparQuery = "prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ "	SELECT ?Name (group_concat(?Schlagwort;separator=' , ') as ?Schlagworte) ?Dokumenttyp ?Erstellungsdatum ?Speicherort ?Status ?Version ?Verfasser"
									+ " WHERE {"
									+ " Filter regex (?Name, '"+documentTitle+"')"
									+ "	  ?x Cloud_Dokumente:Name ?Name ."
									+ "	  ?x Cloud_Dokumente:Schlagwort ?Schlagwort ."
									+ "	  ?x Cloud_Dokumente:Dokumenttyp ?Dokumenttyp ."
									+ "	  ?x Cloud_Dokumente:Erstellungsdatum ?Erstellungsdatum ."
									+ "	  ?x Cloud_Dokumente:Speicherort ?Speicherort ."
									+ "	  ?x Cloud_Dokumente:Status ?Status ."
									+ "	  ?x Cloud_Dokumente:Version ?Version ."
									+ "   ?x Cloud_Dokumente:Dokument_hat_Verfasser ?Verfasser}"
									+ "	group by ?Name ?Dokumenttyp ?Erstellungsdatum ?Speicherort ?Status ?Version ?Verfasser";

					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        String documentMetadata="";
			        String documentName="";
			        int countIteration =0;
			        
			        while (results.hasNext()){
			        	document = new Document();
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							countIteration++;
							switch(countIteration){
							case 1: 
								document.setName(node.toString());
								break;
							case 2: 					
								listKeywort.add(node.toString().replace(" ", "\""));
								document.setListKeyword(listKeywort);
								break;
							case 3: 
								document.setType(node.toString());				
								break;
							case 4: 
								document.setCreationDate(node.toString().substring(0, node.toString().indexOf("^^")));
								break;
							case 5: 
								document.setPath(node.toString());
								break;
							case 6: 
								document.setStatus(node.toString());
								break;
							case 7: 
								document.setVersion(Double.parseDouble(node.toString().substring(0, node.toString().indexOf("^^"))));
								break;
							case 8: 
								document.setCreatedBy(node.toString());
								break;
						}
						if(countIteration==8){
							countIteration=0;
						}
					}
						listDocument.add(document);
					}
			        qe.close();
				} catch(Exception e){
					e.printStackTrace();
				}
				jsonObject.put("data", listDocument);
				return Response.status(200).entity(jsonObject.toString()).build();
		 	}
		 
		
	  	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// DELETE Statements
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			 @GET
			 @Path("/DeleteDocument/{googleDriveID}")
			 @Produces("application/json")
			 public String deleteDocument(@PathParam("googleDriveID") String googleDriveID) throws JSONException {
				 String UPDATE_TEMPLATE = 
						 "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
						 + "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
						 + "PREFIX Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						 + "DELETE { ?Cloud_Dokumente ?p ?v }"
						 + "WHERE"
						 + "{ ?Cloud_Dokumente Cloud_Dokumente:Name ?Name ."
						 + "FILTER regex(?Name,'Besprechungsprotokoll_HighNet_16-01-2016')"
						 + "?Cloud_Dokumente ?p ?v}";
						 
					//Add a new book to the collection
			     String id = UUID.randomUUID().toString();
			     UpdateProcessor upp = UpdateExecutionFactory.createRemote(
			                UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), 
			                "http://localhost:3030/ds/update");
			     upp.execute();
				 
				 return "hi";
			 }
			 
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// INSERT Statements
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			@POST
			@Path( "/AddDocumentMetadata" )
			@Consumes("application/x-www-form-urlencoded")
			
			public void addDocumentMetadata(@FormParam("name") String name, @FormParam("documenttyp") String documenttyp , 
					@FormParam("status") String status, @FormParam("speicherort") String speicherort, @FormParam("version") double version) 
							throws IOException, ParseException, org.codehaus.jettison.json.JSONException {
							
			String UPDATE_TEMPLATE =  "prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
				 		+ " INSERT DATA"
				 		+ "{ "
				 		+ "<http://www.semanticweb.org/alinasiebert/ontologies/2016/0/A-BOX_Cloud_Dokumente#"+name+"> "
				 		+ "Cloud_Dokumente:Name '"+name+"';"
				 		+ "Cloud_Dokumente:Schlagwort 'Modelle';"
				 		+ "Cloud_Dokumente:Dokumenttyp '"+documenttyp+"';"
				 		+ "Cloud_Dokumente:Speicherort '"+speicherort+"';"
				 		+ "Cloud_Dokumente:Status '"+status+"';"
				 		+ "Cloud_Dokumente:Version "+version+" ;"
				 		+ "Cloud_Dokumente:Erstellungsdatum '19-5-2016^^http://www.w3.org/2001/XMLSchema#dateTime';"
				 		+ "Cloud_Dokumente:Dokument_hat_Verfasser  <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#Lisa_Maier> ."
				 		+ "}";
					//Add a new book to the collection
				String id = UUID.randomUUID().toString();
				UpdateProcessor upp = UpdateExecutionFactory.createRemote(
			                UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), 
			                "http://localhost:3030/ds/update");
				upp.execute();
			}
			
			
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// EDIT Statements
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
						@POST
						@Path( "/EditDocumentMetadata" )
						@Consumes("application/x-www-form-urlencoded")
						
						public void editDocumentMetadata(@FormParam("name") String name, @FormParam("documenttype") String documenttype , 
								@FormParam("status") String status, @FormParam("documentPath") String documentPath, @FormParam("version") double version) 
										throws IOException, ParseException, org.codehaus.jettison.json.JSONException {
										
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
								+ "		?Document Cloud_Dokumente:Schlagwort    'Nichtfertiggestellt' , 'Vorgehensmodell' ."
								+ "		?Document Cloud_Dokumente:Dokumenttyp 	'"+documenttype+"' ."
								+ "		?Document Cloud_Dokumente:Speicherort  	'"+documentPath+"' ;"
								+ "WHERE"
								+ "{ "
								+ "		?Document Cloud_Dokumente:DriveDocumentID '1K4_pQgxm9dEx4HK5s5ghw740hkcOu8IrbpMFZ4RNuX0'"
								+ "}"; 
						
								//Add a new book to the collection
							String id = UUID.randomUUID().toString();
							UpdateProcessor upp = UpdateExecutionFactory.createRemote(
						                UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), 
						                "http://localhost:3030/ds/update");
							upp.execute();
						}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// END ------------- DELETE Statements
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	 
}
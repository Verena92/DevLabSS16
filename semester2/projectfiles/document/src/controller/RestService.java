/**
 * @author Waldemar Jaufmann, @author Kristina Baketaric, @author Alina Siebert, @author Tugce Yazici
 */

package controller;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.jena.atlas.lib.cache.Cache0;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.function.library.leviathan.log;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.eclipse.jetty.io.NetworkTrafficListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.soap.Node;

import models.Company;
import models.Document;
import models.Employee;
import models.ObjectRelation;
import models.Project;
import models.WordInformation;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hp.hpl.jena.tdb.store.Hash;
import com.sun.jersey.api.client.ClientResponse.Status;



@Path("/rest") 
public class RestService {
	  private static JSONObject jsonObject;	 
	  private static Logger log = Logger.getLogger( RestService.class.getName() );
	  
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Event Interface
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// GET Statements
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 /* @GET
		 @Path("/GetDocumentMetadata2/")
		 @Produces("application/json")
		 public Response getDocumentMetadata2() throws JSONException, JsonProcessingException {
			String jsonData = "";
			BufferedReader br = null;
			try {
				String line;
				br = new BufferedReader(new FileReader("data/Crunchify_JSON"));
				while ((line = br.readLine()) != null) {
					jsonData += line + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("File Content: \n" + jsonData);
			JSONObject obj = new JSONObject(jsonData);
		
			
		  
		  

		  ArrayList<String> listDocuments = new ArrayList<>();
	      ArrayList<String> newArrayList = new ArrayList<>();
	      ArrayList<String> listProjectURIS = new ArrayList<>();
	      ArrayList<String> listProjectDocuments = new ArrayList<>();
	      
	      ArrayList<String> listEmployees = new ArrayList<>();
	      
		  JSONObject jsonObject = new JSONObject();
	      int countIteration = 0;
		  boolean boolProjectFound = false;
		  boolean boolEmployeeFound = false;
		  String projectURI = getIDByURI(obj.getJSONArray("projects").get(0).toString());
		  String employeeURI = getIDByURI(obj.getJSONArray("employees").get(0).toString());
		  
		  HashMap<String, ArrayList<String>> mapProjects = new HashMap<>();
		  HashMap<String, ArrayList<String>> mapEmployees = new HashMap<>();
		  
		  for(int i=0; i<obj.getJSONArray("projects").length();i++){
			  listProjectURIS.add(getIDByURI((String) obj.getJSONArray("projects").get(i)));
		  }
		  
		  System.out.println("lll"+listProjectURIS);

			try {
				String sparQuery = "prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " SELECT DISTINCT ?Projekt ?Verfasser  ?Klasse"
						+ " WHERE {"
						+ " ?Klasse ?y ?z ."
						+ " ?Klasse Cloud_Dokumente:Dokument_gehoert_zu_Projekt ?Projekt ."
						+ " ?Klasse Cloud_Dokumente:Dokument_hat_Verfasser ?Verfasser ."
						+ " }";
						  
	
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);
		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars(); 
		        
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					for(int i=0; i<var.size();i++){
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						
						if(countIteration==0){
							if(node.toString().equals(projectURI) && projectURI!=""){
								boolProjectFound = true;
							}
						}
						if(countIteration==1){
							if(node.toString().equals(employeeURI) && employeeURI!=""){
								boolEmployeeFound =true;
							}
						}
						if(countIteration==2){
							if(boolEmployeeFound==true){
								boolEmployeeFound = false;
								listEmployees.add(getDocumentByURI(node.toString()));
								listDocuments.add(getDocumentByURI(node.toString()));
							}
							if(boolProjectFound==true){
								boolProjectFound = false;
								listProjectDocuments.add(getDocumentByURI(node.toString()));
								listDocuments.add(getDocumentByURI(node.toString()));
							}
						}
						
						countIteration++;
						if(countIteration>2){
							countIteration=0;
						} 
					}
					for(String projectURIs : listProjectURIS){
						mapProjects.put(projectURIs, listProjectDocuments);
					}
			        
			        mapEmployees.put(employeeURI, listEmployees);
				}

		        
		        System.out.println(mapProjects);
		        System.out.println(mapEmployees);
		        if(projectURI != "" && employeeURI!=""){
			        Set<String> uniqueSet = new HashSet<String>(listDocuments);
					 for (String temp : uniqueSet) {
					   		if(Collections.frequency(listDocuments, temp)>1){
					   			newArrayList.add(temp);
					   		}
					}
		        } else {
		        	newArrayList = new ArrayList<>(listDocuments);
		        }
		        
		        qe.close();
			} catch(Exception e){
				log.error( "GetDocumentMetadata: Can´t get document metadata"+e);
			}
			jsonObject.put("documents", newArrayList);
			return Response.status(200).entity(jsonObject.toString()).build();
		 }*/
	  
	  
	  	 @POST
		 @Path("/GetDocumentMetadata/")
		 @Consumes("application/json")
		 @Produces("application/json")
		 public Response getDocumentMetadata(Object objKeyword) throws JSONException, JsonProcessingException, org.codehaus.jettison.json.JSONException {
			/*String jsonData = "";
			BufferedReader br = null;
			try {
				String line;
				br = new BufferedReader(new FileReader("data/Crunchify_JSON"));
				while ((line = br.readLine()) != null) {
					jsonData += line + "\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			System.out.println("File Content: \n" + jsonData);
			*/
			org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject(objKeyword.toString());
			/*JSONObject obj = new JSONObject(jsonData);
		*/
			JSONObject jsonObject = new JSONObject();
			
			ArrayList<String> listProjectURIs = new ArrayList<>();
			ArrayList<String> listEmployeeURIs = new ArrayList<>();
			ArrayList<String> listProjectDocumentIDs = new ArrayList<>();
			ArrayList<String> listEmployeeDocumentIDs = new ArrayList<>();
			ArrayList<String> listFoundDocumentIDs = new ArrayList<>();
			ArrayList<ArrayList<String>> listCompanies = new ArrayList<>();
			ArrayList<String> listCompanyIDs = new ArrayList<>();
			
			HashMap<String, String> mapProjectDocuments = new HashMap<>();
			HashMap<String, String> mapEmployeeDocuments = new HashMap<>();
			
			String ProjectURI = "";
			String EmployeeURI = "";
			
			
			
			//Transfer the ids from JSON-Array to a ArrayList with the ids
			for(int i=0; i<obj.getJSONArray("companies").length();i++){
				listCompanies.add(getProjectOfCompany((String) obj.getJSONArray("companies").get(i)));
			}
			
			for(int i=0; i<obj.getJSONArray("projects").length();i++){
				listProjectURIs.add(getIDByURI((String) obj.getJSONArray("projects").get(i)));
			}
			
			for(int i=0; i<obj.getJSONArray("employees").length();i++){
				listEmployeeURIs.add(getIDByURI((String) obj.getJSONArray("employees").get(i)));
			}
			
			try {
				String sparQuery = "prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " SELECT DISTINCT ?Projekt ?Verfasser  ?Klasse"
						+ " WHERE {"
						+ " ?Klasse ?y ?z ."
						+ " ?Klasse Cloud_Dokumente:Dokument_gehoert_zu_Projekt ?Projekt ."
						+ " ?Klasse Cloud_Dokumente:Dokument_hat_Verfasser ?Verfasser ."
						+ " }";
						  
	
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);
		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars(); 
		        
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					for(int i=0; i<var.size();i++){
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						switch(va){
							case "Projekt" :
								ProjectURI= node.toString();
								break;
							case "Verfasser" :
								EmployeeURI = node.toString();
								break;
							case "Klasse" :
								mapProjectDocuments.put(node.toString(), ProjectURI);
								mapEmployeeDocuments.put(node.toString(), EmployeeURI);
								break;
						}
					}
		        }
		        for (Entry<String, String> value : mapProjectDocuments.entrySet()) {
		        	for(int i=0; i<listProjectURIs.size();i++){
		        		if(listProjectURIs.get(i).equals(value.getValue())){
		        			listProjectDocumentIDs.add(getDocumentByURI(value.getKey()));
		        		}
		        	}
		        }
		        for (Entry<String, String> value : mapProjectDocuments.entrySet()) {
		        	for(int i=0;i<listCompanies.size();i++){
						for(int k=0;k<listCompanies.get(i).size();k++){
							if(listCompanies.get(i).get(k).equals(value.getValue())){
								listCompanyIDs.add(getDocumentByURI(value.getKey()));
							}	
						}
					}
		        }
		        
		        for (Entry<String, String> value : mapEmployeeDocuments.entrySet()) {
		        	for(int i=0; i<listEmployeeURIs.size();i++){
		        		if(listEmployeeURIs.get(i).equals(value.getValue())){
		        			listEmployeeDocumentIDs.add(getDocumentByURI(value.getKey()));
		        		}
		        	}
		        }
		        if(!listCompanyIDs.isEmpty()){
		        	for(int i=0;i<listCompanyIDs.size();i++){
		        		if(!listFoundDocumentIDs.contains(listCompanyIDs.get(i))){
		        			listFoundDocumentIDs.add(listCompanyIDs.get(i));
		        		}
					}
		        } else if(!listProjectDocumentIDs.isEmpty() && !listEmployeeDocumentIDs.isEmpty()){
			        for(int i=0;i<listEmployeeDocumentIDs.size();i++){
			        	for(int k=0;k<listProjectDocumentIDs.size();k++){
			        		if(listProjectDocumentIDs.get(k).contains(listEmployeeDocumentIDs.get(i))){
				        		listFoundDocumentIDs.add(listEmployeeDocumentIDs.get(i));
			        		}		        		
			        	}
			        }
		        } else if(!listProjectDocumentIDs.isEmpty() && listEmployeeDocumentIDs.isEmpty()){
		        	for(int k=0;k<listProjectDocumentIDs.size();k++){
			        	listFoundDocumentIDs.add(listProjectDocumentIDs.get(k));
		        	}
		        } else if(listProjectDocumentIDs.isEmpty() && !listEmployeeDocumentIDs.isEmpty()){
		        	for(int k=0;k<listEmployeeDocumentIDs.size();k++){
			        	listFoundDocumentIDs.add(listEmployeeDocumentIDs.get(k));
		        	}
		        }
		        qe.close();
			} catch(Exception e){
				log.error( "GetDocumentMetadata: Can´t get document metadata"+e);
			}
			jsonObject.put("documents", listFoundDocumentIDs);
			return Response.status(200).entity(jsonObject.toString()).build();
		 }
	  
	  /*
	  	 @GET
		 @Path("/GetDocumentMetadata/")
		 @Produces("application/json")
		 public Response getDocumentMetadata() throws JSONException, JsonProcessingException {
	  		Document document = null;
	  		ObjectMapper mapper = new ObjectMapper();
	  		
	  		String jsonInString = "";

			try {
				String sparQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
						+ " PREFIX Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
						+ " PREFIX Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " SELECT ?DokumentenID ?Dokumentenname ?Status ?Dokumenttyp ?Speicherort ?Version ?Erstellungsdatum ?Verfasser "
						+ " (group_concat(?Schlagwort;separator=',')  as ?Schlagwörter) (group_concat(?Projekt;separator=',')  as ?Projekte) WHERE {"
						+ " ?x ?y ?DokumentenID "
						+ " Filter (?DokumentenID = 'D0002') "
						+ " ?x Cloud_Dokumente:Dokumentenname ?Dokumentenname ."
						+ " ?x Cloud_Dokumente_old:Status ?Status ."
						+ " ?x Cloud_Dokumente_old:Erstellungsdatum ?Erstellungsdatum ."
						+ " ?x Cloud_Dokumente_old:Dokumenttyp ?Dokumenttyp ."
						+ " ?x Cloud_Dokumente_old:Speicherort ?Speicherort ."
						+ " ?x Cloud_Dokumente_old:Version ?Version ."
						+ " ?x Cloud_Dokumente_old:Dokument_hat_Verfasser ?Verfasser ."
						+ " ?x Cloud_Dokumente_old:Schlagwort ?Schlagwort . "
						+ " ?x Cloud_Dokumente_old:Dokument_gehoert_zu_Projekt ?Projekt ."
						+ " } group by ?DokumentenID ?Dokumentenname ?Status ?Dokumenttyp ?Speicherort ?Version ?Erstellungsdatum ?Verfasser ";
	
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);
		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars();    
		        
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					document = new Document();
					for(int i=0; i<var.size();i++){
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						document.setDriveID("1onKOGZFLOKReA1unsstajHHy6eBK-C6rn52i1Ra4A78");
						switch(va){
							case "DokumentenID" : 
								document.setDocumentID(node.toString());
								break;
							case "Dokumentenname" : 
								document.setDocumentName(node.toString());
								break;
							case "Dokumenttyp" :
								document.setDocumentType(node.toString());
								break;
							case "Speicherort" :
								document.setDrivePath(node.toString());
								break;
							case "Erstellungsdatum" :
								String creationDate = node.toString().substring(0, node.toString().indexOf("^^"));
								document.setCreationDate(creationDate);
								break;
							case "Version" :
								Double version = Double.parseDouble(node.toString().substring(0, node.toString().indexOf("^^")));
								document.setVersion(version);
								break;
							case "Status" :
								document.setStatus(node.toString());
								break;
							case "Verfasser" :
								document.setCreatedBy(getEmployeeByURI(node.toString()));
								break;
							case "Schlagwörter" :
								ArrayList<String> listKeywords = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
								document.setKeywords(listKeywords);
								break;
							case "Projekte" :
								ArrayList<String> listProjects = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
								ArrayList<String> newListProjects = new ArrayList<>();
								int count = 0;
								for(String getProjects : listProjects){
									if(count==0){
										newListProjects.add(getProjectByURI(getProjects));
									}
									count++;	
								}
								document.setProjects(newListProjects);
								break;
						}
					
					}
				}
		        qe.close();
			} catch(Exception e){
				log.error( "GetDocumentMetadata: Can´t get document metadata"+e);
			}
			
			if(document!=null){
				jsonInString = mapper.writeValueAsString(document);
			}

			return Response.status(200).entity(jsonInString).build();
		 }
	  	 
	  	*/ 
	  	 
	  	@GET
		 @Path("/GetDocumentByID/{documentID}")
		 @Produces("application/json")
		 public Response getDocumentByDocumentID(@PathParam("documentID") String documentID) throws JSONException, JsonProcessingException {
	  		Document document = null;
	  		ObjectMapper mapper = new ObjectMapper();
	  		
	  		String jsonInString = "";

			try {
				String sparQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
						+ " PREFIX Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
						+ " PREFIX Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " SELECT ?DokumentenID ?Dokumentenklasse ?Dokumentenname ?Status ?Dokumenttyp ?Speicherort ?Version ?Erstellungsdatum ?Verfasser "
						+ " (group_concat(?Schlagwort;separator=',')  as ?Schlagwörter) (group_concat(?Projekt;separator=',')  as ?Projekte) WHERE {"
						+ " ?x ?y ?DokumentenID "
						+ " Filter (?DokumentenID = '"+documentID+"') "
						+ " ?x Cloud_Dokumente:Dokumentenname ?Dokumentenname ."
						+ " ?x Cloud_Dokumente_old:Status ?Status ."
						+ " ?x Cloud_Dokumente_old:Erstellungsdatum ?Erstellungsdatum ."
						+ " ?x Cloud_Dokumente_old:Dokumenttyp ?Dokumenttyp ."
						+ " ?x Cloud_Dokumente_old:Speicherort ?Speicherort ."
						+ " ?x Cloud_Dokumente_old:Version ?Version ."
						+ " ?x Cloud_Dokumente_old:Dokument_hat_Verfasser ?Verfasser ."
						+ " ?x Cloud_Dokumente_old:Schlagwort ?Schlagwort . "
						+ " ?x Cloud_Dokumente_old:Dokument_gehoert_zu_Projekt ?Projekt ."
						+ "	?x rdf:type ?Dokumentenklasse ."
						+ " } group by ?DokumentenID ?Dokumentenklasse ?Dokumentenname ?Status ?Dokumenttyp ?Speicherort ?Version ?Erstellungsdatum ?Verfasser ";
	
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);
		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars();    
		        
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					document = new Document();
					for(int i=0; i<var.size();i++){
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						document.setDriveID("1onKOGZFLOKReA1unsstajHHy6eBK-C6rn52i1Ra4A78");
						switch(va){
							case "DokumentenID" : 
								document.setDocumentID(node.toString());
								break;
							case "Dokumentenname" : 
								document.setDocumentName(node.toString());
								break;
							case "Dokumenttyp" :
								document.setDocumentType(node.toString());
								break;
							case "Speicherort" :
								document.setDrivePath(node.toString());
								break;
							case "Erstellungsdatum" :
								String creationDate = node.toString().substring(0, node.toString().indexOf("^^"));
								document.setCreationDate(creationDate);
								break;
							case "Version" :
								Double version = Double.parseDouble(node.toString().substring(0, node.toString().indexOf("^^")));
								document.setVersion(version);
								break;
							case "Status" :
								document.setStatus(node.toString());
								break;
							case "Verfasser" :
								document.setCreatedBy(getEmployeeByURI(node.toString()));
								break;
							case "Schlagwörter" :
								ArrayList<String> listKeywords = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
								document.setKeywords(listKeywords);
								break;
							case "Projekte" :
								ArrayList<String> listProjects = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
								ArrayList<String> newListProjects = new ArrayList<>();
								int count = 0;
								for(String getProjects : listProjects){
									if(count==0){
										newListProjects.add(getProjectByURI(getProjects));
									}
									count++;
								}
								document.setProjects(newListProjects);
								break;
							case "Dokumentenklasse" :
								document.setDocumentClass(node.asResource().getLocalName());
								break;
						}
					
					}
				}
		        qe.close();
			} catch(Exception e){
				log.error( "GetDocumentMetadata: Can´t get document metadata"+e);
			}
			
			if(document!=null){
				jsonInString = mapper.writeValueAsString(document);
			}

			return Response.status(200).entity(jsonInString).build();
		 }
	  
	  
	  
	  /**
	     * 
	     * GetProjectByID
	     * 
	     * Diese Methode wird als Schnittstelle extrahiert, sodass nach den Projekten mit der jeweiligen ProjektID
	     * gesucht werden kann
	     * 
	     * @param productName
		 *        Die ProjektID, die verwendet wird, um nach zusätzlichen Projektinformationen zu suchen.
	     * 
	     * @return Ein Response-Objekt, welches alle Informationen zu dem Projekt enthält
	     * @throws IOException 
	     * @throws JsonMappingException 
	     * @throws JsonGenerationException 
	     */
	  	 @GET
		 @Path("/GetProjectByID/{projectID}")
		 @Produces("application/json")
		 public Response getProjectByID(@PathParam("projectID") String projectID) throws JSONException, JsonProcessingException {
	  		Project project = null;
	  		Employee employee = null;
	  		Project newProject = null;
	  		
	  		ArrayList<String> listEmployees = new ArrayList<>();
	  		ArrayList<Project> listProjects = new ArrayList<>();
	  		ArrayList<String> listCompany = new ArrayList<>();
	  		
	  		
	  		ObjectMapper mapper = new ObjectMapper();
	  		
	  		
			try {
				String sparQuery = "prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
						+ " prefix Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " Select ?ProjektID ?ProjektName ?Projektleiter ?Status (group_concat(?Unternehmen;separator=',')  as ?GroupUnternehmen) (group_concat(?Projektmitglied;separator=',') as ?Projektmitglieder) where"
						+ " {"
						+ " ?x ?y ?ProjektID ."
						+ " Filter (?ProjektID='"+projectID+"') ."
						+ " ?x Cloud_Dokumente:ProjektID ?ProjektID ."
						+ " ?x Cloud_Dokumente:Projektname ?ProjektName ."
						+ " ?x Cloud_Dokumente_old:Status ?Status ."
						+ " ?x Cloud_Dokumente_old:Projekt_hat_Projektmitglied ?Projektmitglied ."
						+ " ?x Cloud_Dokumente_old:Projekt_hat_Projektleiter ?Projektleiter ."
						+ " ?x Cloud_Dokumente:Projekt_gehoert_zu_Unternehmen ?Unternehmen ."
						+ " } group by ?ProjektID ?ProjektName ?Status ?Projektleiter ?Unternehmen";
	
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);
		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars();    
		        
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					project = new Project();
					employee = new Employee();
					
					for(int i=0; i<var.size();i++){
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						switch(va){
						  case "ProjektID" : project.setProjectID(node.toString()); break;
						  case "ProjektName" : project.setProjectName(node.toString()); break;
						  case "Projektmitglieder" :

							  ArrayList<String> listProjectMember = new ArrayList<String>(Arrays.asList(node.toString().split(",")));

							  for(String projectMember : listProjectMember){
									  listEmployees.add(getEmployeeByURI(projectMember));
							  }
								  project.setProjectMembers(listEmployees);

						  	  
						  	  break;
						  case "Status" : project.setStatus(node.toString()); break;
						  case "Projektleiter" : project.setProjectManager(getEmployeeByURI(node.toString())); break;
						  case "GroupUnternehmen" : 
							  if(node.toString().contains("#")){
								  ArrayList<String> listCompanies = new ArrayList<>(Arrays.asList(node.toString().split(",")));
								  for(String companies : listCompanies){
									  listCompany.add(getCompanyByURI(companies));
								  }
								  project.setInvolvedCompanies(listCompany);
							  } 
						}
					}
				}
		        qe.close();
				newProject = new Project();
				newProject.setProjectID(project.getProjectID());
				newProject.setProjectName(project.getProjectName());
				newProject.setProjectManager(project.getProjectManager());
				newProject.setStatus(project.getStatus());
				List<String> newProjectMembers = project.getProjectMembers().stream().distinct().collect(Collectors.toList());
				List<String> newCompanies = project.getInvolvedCompanies().stream().distinct().collect(Collectors.toList());
				newProject.setProjectMembers((ArrayList) newProjectMembers);
				newProject.setInvolvedCompanies((ArrayList)newCompanies);
			} catch(Exception e){
				log.error( "GetProject: Can´t get project information "+e);
			}

			String jsonInString = mapper.writeValueAsString(newProject);
	  		
			if(!jsonInString.contains("P")){
				return Response.status(Response.Status.NOT_FOUND).entity("Project not found for ID: "+projectID).build(); 
			}
			
			return Response.status(200).entity(jsonInString).build();
		 }
	  	 
	  	 @GET
		 @Path("/GetDocumentClasses/")
		 @Produces("application/json")
		 public Response getDocumentClasses() throws JSONException, JsonProcessingException {
	  		JSONObject jsonObject = new JSONObject();
	  		ArrayList<String> listDocumentClasses = new ArrayList<>();
			try {
				String sparQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
						+ " prefix Clou_Dokumente_Old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " SELECT  ?Dokumentenklasse"
						+ " WHERE   { "
						+ " ?x ?y ?z ."
						+ " Filter regex (?z, 'D') ."
						+ " ?x rdf:type ?Dokumentenklasse }";
	
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);
		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars();    
		        
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					
					for(int i=0; i<var.size();i++){
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						if(node.toString().contains("Cloud_Dokumente")){
							if(!listDocumentClasses.contains(node.asResource().getLocalName())){
								listDocumentClasses.add(node.asResource().getLocalName());
							}
							
						}
					}
				}
		        qe.close();

			} catch(Exception e){
				log.error( "GetProject: Can´t get project information "+e);
			}
			jsonObject.put("documentClasses", listDocumentClasses);
			return Response.status(200).entity(jsonObject.toString()).build();
		 }	  	 
	  	 
		 /**
		 * 
		 * GetCompanyByID
		 * 
		 * Diese Methode wird als Schnittstelle extrahiert, sodass nach den Unternehmen mit der jeweiligen UnternehmensID
		 * gesucht werden kann
		 * 
		 * @param companyID
		 *         Die comapanyID wird verwendet, um nach zusätzlichen Unternehmensinformationen zu suchen.
		 * 
		 * @return Ein Response-Objekt, welches alle Informationen zu dem Unternehmen enthält
		 * @throws JsonProcessingException 
		 */
	  	 	   
	  	 @GET
		 @Path("/GetCompanyByID/{companyID}")
		 @Produces("application/json")
		 public Response getCompanyID(@PathParam("companyID") String companyID) throws JsonProcessingException  {
	  		Company company = null;
	  		Company newCompany = null;
	  		ObjectMapper mapper = new ObjectMapper();
	  		
	  		ArrayList<String> newListProjects = new ArrayList<>();
	  		ArrayList<String> newListEmployees = new ArrayList<>();
			try {
				String sparQuery = "prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
						+ " prefix Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " SELECT  ?UnternehmensID ?Mitarbeiteranzahl ?Hauptsitz ?Unternehmensname ?Branche (group_concat(?Projekt;separator=',') as ?Projekte) (group_concat(?Mitarbeiter;separator=',') as ?GroupMitarbeiter) "
						+ " WHERE   { "
						+ " ?x ?y ?UnternehmensID ."
						+ " Filter (?UnternehmensID='"+companyID+"') ."
						+ " ?x Cloud_Dokumente:UnternehmensID ?UnternehmensID ."
						+ " ?x Cloud_Dokumente_old:Mitarbeiteranzahl ?Mitarbeiteranzahl ."
						+ " ?x Cloud_Dokumente_old:Unternehmen_hat_Mitarbeiter ?Mitarbeiter ."
						+ " ?x Cloud_Dokumente_old:Unternehmen_hat_Projekt ?Projekt ."
						+ " ?x Cloud_Dokumente_old:Unternehmen_hat_Hauptsitz_in ?Hauptsitz ."
						+ " ?x Cloud_Dokumente:Branche ?Branche ."
						+ " ?x Cloud_Dokumente:Unternehmensname ?Unternehmensname ."
						+ " } group by ?UnternehmensID ?Mitarbeiteranzahl ?Hauptsitz ?Branche ?Unternehmensname"; 
				
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars();
		        
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					company = new Company();
					for(int i=0; i<var.size();i++){			
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						
						switch(va){
						  case "UnternehmensID" :  company.setCompanyID(node.toString()); break;
						  case "Unternehmensname" : company.setCompanyName(node.toString()); break;
						  case "Mitarbeiteranzahl" : 
							  int numberEmployee = Integer.parseInt(node.toString().substring(0, node.toString().indexOf("^^")));
							  company.setNumberEmployee(numberEmployee); break;
						  case "GroupMitarbeiter" : 
							  ArrayList<String> listEmployee = new ArrayList<String>(Arrays.asList(node.toString().split(",")));

							  for(String employee : listEmployee){
									  newListEmployees.add(getEmployeeByURI(employee));
							  }
							  
							  company.setEmployees(newListEmployees);
							  break;
						  case "Branche" : company.setIndustrialSector(node.toString());break;
						  
						  case "Projekte" : 
							  ArrayList<String> listProjects = new ArrayList<String>(Arrays.asList(node.toString().split(",")));

							  for(String projects : listProjects){
									  newListProjects.add(getProjectByURI(projects));
							  }

							  company.setProjects(newListProjects);
							  break;
						  case "Hauptsitz": company.setHeadquarter(node.asResource().getLocalName());
						}
					}
				}
		        qe.close();
				newCompany = new Company();
				newCompany.setCompanyID(company.getCompanyID());
				newCompany.setNumberEmployee(company.getNumberEmployee());
				
				List<String> newProjects = company.getProjects().stream().distinct().collect(Collectors.toList());
				newCompany.setProjects((ArrayList) newProjects);
				
				List<String> newEmployees = company.getEmployees().stream().distinct().collect(Collectors.toList());
				newCompany.setEmployees((ArrayList) newEmployees);
				newCompany.setHeadquarter(company.getHeadquarter());
				newCompany.setIndustrialSector(company.getIndustrialSector());
				newCompany.setCompanyName(company.getCompanyName());
			} catch(Exception e){
				log.error( "GetCompanyByID: Can´t get company information "+e);
			}
			
			
			String jsonInString = mapper.writeValueAsString(newCompany);
			if(!jsonInString.contains("U")){
				return Response.status(Response.Status.NOT_FOUND).entity("Company not found for ID: "+companyID).build(); 
			}
			return Response.status(200).entity(jsonInString).build();
		 }
	  	 
	  	 
	  	/**
			 * 
			 * GetCompanyByID
			 * 
			 * Diese Methode wird als Schnittstelle extrahiert, sodass nach den Unternehmen mit der jeweiligen UnternehmensID
			 * gesucht werden kann
			 * 
			 * @param companyID
			 *         Die comapanyID wird verwendet, um nach zusätzlichen Unternehmensinformationen zu suchen.
			 * 
			 * @return Ein Response-Objekt, welches alle Informationen zu dem Unternehmen enthält
			 * @throws JsonProcessingException 
			 */
		  	 	   
		  	 @GET
			 @Path("/GetEmployeeByID/{employeeID}")
			 @Produces("application/json")
			 public Response getEmployeeByID(@PathParam("employeeID") String employeeID) throws JsonProcessingException  {
		  		Employee employee = null;
		  		ObjectMapper mapper = new ObjectMapper();
		  		
		  		ArrayList<String> listProjects = new ArrayList<>();
		  		
				try {
					String sparQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
							+ " prefix Clou_Dokumente_Old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ " SELECT  ?MitarbeiterID ?DriveUserID ?Personenname ?Vorname ?Unternehmen ?HangoutUserID (group_concat(?Projekt;separator=',') as ?Projekte) ?Jobtitel"
							+ " WHERE   { "
							+ " ?x ?y ?MitarbeiterID ."
							+ " Filter (?MitarbeiterID='"+employeeID+"') ."
							+ " ?x Clou_Dokumente_Old:Vorname ?Vorname ."
							+ " ?x Cloud_Dokumente:Personenname ?Personenname ."
							+ " ?x Cloud_Dokumente:Jobtitel ?Jobtitel ."
							+ " ?x Clou_Dokumente_Old:ist_Mitarbeiter_von_Unternehmen ?Unternehmen ."
							+ " ?x Clou_Dokumente_Old:Mitarbeiter_ist_Projektmitglied_von ?Projekt ."
							+ " ?x Cloud_Dokumente:HangoutUserID ?HangoutUserID ."
							+ " ?x Cloud_Dokumente:DriveUserID ?DriveUserID ."
							+ "}group by ?MitarbeiterID ?Personenname ?Vorname ?Unternehmen ?Jobtitel ?HangoutUserID ?DriveUserID"; 
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						employee = new Employee();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							switch(va){
								case "MitarbeiterID" :  
									employee.setEmployeeID(node.toString());
									break;
								case "Jobtitel" :employee.setJobTitle(node.toString());
								case "Unternehmen" :  
									if(node.toString().contains("#")){
										employee.setEmployeeOf(getCompanyByURI("http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#StarCars"));
										
									}
									break;
								case "Personenname" : 
									employee.setEmployeeName(node.toString());
									break;
								case "Vorname" :
									employee.setEmployeeSurname(node.toString());
									break;
								case "Projekte" :
									ArrayList<String> newListProjecs = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
									
									for(String projects : newListProjecs){
										listProjects.add(getProjectByURI(projects));
									}
									employee.setProjects(listProjects);
									break;
								case "HangoutUserID" : 
									employee.setHangoutUserID(node.toString());
									break;
								case "DriveUserID":
									employee.setDriveUserID(node.toString());
									break;
							}
						}
					}
			        qe.close();
				} catch(Exception e){
					log.error( "GetEmpoloyeeByID: Can´t get the employee information "+e);
				}
				
				String jsonInString = mapper.writeValueAsString(employee);
				if(!jsonInString.contains("M")){
					return Response.status(Response.Status.NOT_FOUND).entity("Employee not found for ID: "+employeeID).build(); 
				}
				return Response.status(200).entity(jsonInString).build();
			 }
	  	 
	  	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Methods to get further information
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		  	 public static String getCompanyByURI(String companyURI) {	
				String companyID = null;
				try {
					String sparQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#> "
							+ "	Select ?Unternehmen where "
							+ " {<"+companyURI+"> Cloud_Dokumente:UnternehmensID ?Unternehmen }";
						
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							companyID = node.toString();
						}
					}
			        qe.close();
				} catch(Exception e){
					log.error( "getCompanyByURI: Can´t get company by uri "+e);
				}
					return companyID;
			}
		  	 
		 public static String getEmployeeByURI(String employeeURI) {	
			String employeeID = null;
			try {
				String sparQuery = " prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>select ?HangoutUserID where {"
						+ " <"+employeeURI+"> Cloud_Dokumente:HangoutUserID ?HangoutUserID }";  
				
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars();
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					for(int i=0; i<var.size();i++){			
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						employeeID = node.toString();
					}
				}
		        qe.close();
			} catch(Exception e){
				log.error( "getEmployeeByURI: Can´t get employee by uri "+e);
			}
				return employeeID;
		 }
		 
		 

		 public static String getProjectURIByID(String projectID) {	
				String projectURI = null;
				try {
					String sparQuery = "prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
							+ " SELECT ?x"
							+ " WHERE {"
							+ " ?x Cloud_Dokumente:ProjektID '"+projectID+"' }";
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							projectURI = node.toString();
						}
					}
			        qe.close();
				} catch(Exception e){
					log.error( "getEmployeeByURI: Can´t get employee by uri "+e);
				}
					return projectURI;
			 }
		 
		 
		 public static ArrayList<String> getProjectOfCompany(String companyID) {	
			 ArrayList<String> listProjects = null;	
			 try {
					String sparQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#> "
							+ " prefix Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ " select (group_concat(?Projekt;separator=',')  as ?Projekte) where {"
							+ " ?x ?y ?z ."
							+ " Filter (?z = '"+companyID+"')"
							+ " ?x Cloud_Dokumente_old:Unternehmen_hat_Projekt ?Projekt}";
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							listProjects = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
						}
					}
			        qe.close();
				} catch(Exception e){
					log.error( "getEmployeeByURI: Can´t get employee by uri "+e);
				}
					return listProjects;
			 }
		 
		 
		 public static void main(String[] args) {
		}
		 public static String getIDByURI(String uri) {	
				String id = null;
				try {
					String sparQuery = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#> "
							+ " select ?x where {"
							+ " ?x ?y ?z ."
							+ " Filter (?z = '"+uri+"')}";
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							id = node.toString();
						}
					}
			        qe.close();
				} catch(Exception e){
					log.error( "getEmployeeByURI: Can´t get employee by uri "+e);
				}
					return id;
			 }
		 
		 
		 public static String getProjectByURI(String projectURI) {	
				String projectID = null;
				try {
						String sparQuery = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+	" prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#> "
							+   " select ?ProjektID where {"
							+   " <"+projectURI+"> Cloud_Dokumente:ProjektID ?ProjektID }"; 
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							projectID = node.toString();
						}
					}
			        qe.close();
				} catch(Exception e){
					log.error( "getProjectByURI: Can´t get project by uri "+e);
				}
					return projectID;
			 }
		 
		 public static String getDocumentByURI(String documentURI) {	
				String documentID = null;
				try {
						String sparQuery = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+	" prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#> "
							+   " select ?DokumentenID where {"
							+   " <"+documentURI+"> Cloud_Dokumente:DokumentenID ?DokumentenID }"; 
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							documentID = node.toString();
						}
					}
			        qe.close();
				} catch(Exception e){
					log.error( "getDocumentID: Can´t get document by uri "+e);
				}
					return documentID;
			 }
		 
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Speech Token Interface
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			 		
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// GET Statements
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*
		 public static void main(String[] args) throws FileNotFoundException, JSONException, JsonProcessingException {
				String jsonData = "";
				BufferedReader br = null;
				try {
					String line;
					br = new BufferedReader(new FileReader("data/Crunchify_JSON"));
					while ((line = br.readLine()) != null) {
						jsonData += line + "\n";
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null)
							br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				// System.out.println("File Content: \n" + jsonData);
				JSONObject obj = new JSONObject(jsonData);

				
				getWordinformation(jsonData);
			}
		 */
		 
		 @POST
		 @Path("/GetWordinformation/")
		 @Consumes("application/json")
		 @Produces("application/json")
		 public static Response getWordinformation(Object objKeyword) throws JSONException, JsonProcessingException, org.codehaus.jettison.json.JSONException {
			org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject(objKeyword.toString());
			
			
			/*
				String jsonData = "";
				BufferedReader br = null;
				try {
					String line;
					br = new BufferedReader(new FileReader("data/Crunchify_JSON"));
					while ((line = br.readLine()) != null) {
						jsonData += line + "\n";
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null)
							br.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				System.out.println("File Content: \n" + jsonData);
				JSONObject obj = new JSONObject(jsonData);*/
				String keyword = obj.getString("keyword");
				String priviousKeyword = obj.getString("previousKeyword");
				String nextKeyword = obj.getString("nextKeyword");
				System.out.println(keyword+ " "+priviousKeyword+" "+nextKeyword);

			 	ArrayList<WordInformation> listWordInformation = new ArrayList<>();
			 	System.out.println(nextKeyword.replace(" ", "").trim());
			 	for(int i=0; i<5; i++){
			 		switch(i){
			 			case 0 : 
			 				if(parseKeywords(priviousKeyword)!=null){
			 					
			 					listWordInformation.add(parseKeywords(priviousKeyword));
			 				}
			 				break;
			 			case 1 :
			 				if(parseKeywords(keyword)!=null){
			 					System.out.println(parseKeywords(keyword));
			 					listWordInformation.add(parseKeywords(keyword));
			 				}
			 				break;
			 			case 2 :
			 				if(parseKeywords(nextKeyword)!=null){
			 					listWordInformation.add(parseKeywords(nextKeyword));
			 				}
			 				break;
			 			case 3 : 
			 				if(parseKeywords(priviousKeyword+" "+keyword)!=null){
			 					listWordInformation.add(parseKeywords(priviousKeyword+" "+keyword));
			 				}
			 				break;
			 			case 4:
			 				if(parseKeywords(keyword+" "+nextKeyword)!=null){
			 					listWordInformation.add(parseKeywords(keyword+" "+nextKeyword));
			 				}
			 				break;
			 			case 5:
			 				if(parseKeywords(priviousKeyword+" "+keyword+" "+nextKeyword)!=null){
			 					listWordInformation.add(parseKeywords(priviousKeyword+" "+keyword+" "+nextKeyword));
			 				}
			 				break;
			 		}
			 	}
			 	
			 	WordInformation newWordInformation = new WordInformation();
			 	ArrayList<String> newListCompanies = new ArrayList<>();
			 	ArrayList<String> newListProjects = new ArrayList<>();
			 	ArrayList<String> newListPerseons = new ArrayList<>();
			 	
	
			 		for(WordInformation ausgabe : listWordInformation){
			 			newListPerseons = new ArrayList<>(ausgabe.getPersons());
			 			newListProjects = new ArrayList<>(ausgabe.getProjects());
			 			newListCompanies = new ArrayList<>(ausgabe.getCompanies());
			 			newWordInformation.setCompanies(newListCompanies);
			 			newWordInformation.setPersons(newListPerseons);
			 			newWordInformation.setProjects(newListProjects);
			 		}
			 
			 	
			 	for(WordInformation ausgabe : listWordInformation){
			 		System.out.println("f "+ausgabe.getPersons()+" "+ausgabe.getProjects() + " "+ausgabe.getCompanies());
			 	}
			 	ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(newWordInformation);
				
				return Response.status(200).entity(jsonInString).build();
					
		 	}
		 
		 /*
		 @GET
		 @Path("/GetWordinformation/")
		 @Produces("application/json")
		 public Response getDocume() throws JSONException {
				jsonObject = new JSONObject();
				Document document;
				ArrayList<Document> listDocument = new ArrayList<Document>();
				ArrayList<String> listKeywort = new ArrayList<String>();				
				try {
						
					
				} catch(Exception e){
					log.error( "GetDocumentByTitle: Can´t get document by title "+e);
				}
				return Response.status(200).entity("halloo").build();
		 	}*/
		 
		
		 public static WordInformation parseKeywords(String keyword) throws JsonProcessingException {
				ArrayList<String> listProjects = new ArrayList<>();
				ArrayList<String> listEmployees = new ArrayList<>();
				ArrayList<String> listCompanies = new ArrayList<>();
				ObjectRelation objectRelation;
				
				ArrayList<ObjectRelation> listObjectRelation = new ArrayList<>();
				try {
					String sparQuery = "prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#> "
							+ " SELECT  ?Klassentyp ?Wert"
							+ " WHERE   { ?Wert ?Typ ?Ausgabe ."
							+ " FILTER (?Ausgabe = '"+keyword+"') . "
							+ " ?Typ <http://www.w3.org/2000/01/rdf-schema#domain> ?Klassentyp "
							+ " } group by ?Klassentyp ?Wert";
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        String classType = "";
			        int countIteration = -1;
			        while (results.hasNext()){

			        	objectRelation = new ObjectRelation();
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							
							switch(va){
								case "Klassentyp" :
									objectRelation.setType(node.asResource().getLocalName());
									break;
								case "Wert" : 
									objectRelation.setValue(node.toString());
									break;
							}
						}
						listObjectRelation.add(objectRelation);
					}
			        qe.close();
				} catch(Exception e){
					log.error( "GetWordInformation: Can´t get word information "+e);
				}
				WordInformation wordInformation = null;
				ArrayList<WordInformation> listWordInformation = new ArrayList<WordInformation>();
				for(ObjectRelation ausgabe : listObjectRelation){
					wordInformation = new WordInformation();
					
					if(ausgabe.getType().equals("Projekt")){
						String projectID = getProjectByURI(ausgabe.getValue());
						if(projectID != null){
							listProjects.add(projectID);
						}
					}
					if(ausgabe.getType().equals("Person")){
						String employeeID = getEmployeeByURI(ausgabe.getValue());
						if(employeeID!=null){
							listEmployees.add(employeeID);
						}
					}	
					if(ausgabe.getType().equals("Unternehmen")){
						String companyID = getCompanyByURI(ausgabe.getValue());
						if(companyID!=null){
							listCompanies.add(companyID);
						}
					}	
				}	
				
				if(wordInformation!=null){
					wordInformation.setProjects(listProjects);
					wordInformation.setPersons(listEmployees);
					wordInformation.setCompanies(listCompanies);
				}
				
				return wordInformation;
		 	}
		 
		 /*
		 @GET
		 @Path("/GetWordinformation/")
		 @Produces("application/json")
		 public static Response getWordinformation(Object objKeyword) throws JSONException, JsonProcessingException {
			 	JSONObject jsonObjectKeywords = new JSONObject(objKeyword.toString());
			 	
			 	String keyword = jsonObjectKeywords.getString("keyword");
			 	String priviousKeyword = jsonObjectKeywords.getString("priviousKeyword");
			 	String nextKeyword = jsonObjectKeywords.getString("nextKeyword");
			 	System.out.println(priviousKeyword);
				jsonObject = new JSONObject();
		        String Klassentyp = "";
				ArrayList<String> listProjects = new ArrayList<>();
				ArrayList<String> listEmployees = new ArrayList<>();
				ObjectRelation objectRelation;
				
				ArrayList<ObjectRelation> listObjectRelation = new ArrayList<>();
				try {
					String sparQuery = "prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#> "
							+ " SELECT  ?Klassentyp ?Wert"
							+ " WHERE   { ?Wert ?Typ ?Ausgabe ."
							+ " FILTER regex (?Ausgabe, '"+nextKeyword+"') . "
							+ " ?Typ <http://www.w3.org/2000/01/rdf-schema#domain> ?Klassentyp "
							+ " } group by ?Klassentyp ?Wert";
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        String classType = "";
			        int countIteration = -1;
			        while (results.hasNext()){

			        	objectRelation = new ObjectRelation();
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){			
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							
							switch(va){
								case "Klassentyp" :
									objectRelation.setType(node.asResource().getLocalName());
									System.out.println(node.toString());
									break;
								case "Wert" : 
									objectRelation.setValue(node.toString());
									break;
							}
						}
						listObjectRelation.add(objectRelation);
					}
			        qe.close();
				} catch(Exception e){
					log.error( "GetWordInformation: Can´t get word information "+e);
				}
				WordInformation wordInformation = null;
				ArrayList<WordInformation> listWordInformation = new ArrayList<WordInformation>();
				for(ObjectRelation ausgabe : listObjectRelation){
					wordInformation = new WordInformation();
					
					if(ausgabe.getType().equals("Projekt")){
						String projectID = getProjectByURI(ausgabe.getValue());
						if(projectID != null){
							listProjects.add(projectID);
						}
					}
					if(ausgabe.getType().equals("Person")){
						String employeeID = getEmployeeByURI(ausgabe.getValue());
						if(employeeID!=null){
							listEmployees.add(employeeID);
						}
					}
					
				}
				String jsonInString = "";
				ObjectMapper mapper = new ObjectMapper();
				if(wordInformation!=null){
					wordInformation.setProjects(listProjects);
					wordInformation.setPersons(listEmployees);
					jsonInString = mapper.writeValueAsString(wordInformation);
				}
				
				
				
				return Response.status(200).entity(jsonInString).build();
		 	}
		 
		 */
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
							+ "	SELECT ?Name (group_concat(?Schlagwort;separator=',') as ?Schlagworte) ?Dokumenttyp ?Erstellungsdatum ?Speicherort ?Status ?Version ?Verfasser"
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
								document.setDocumentName(node.toString());
								break;
							case 2: 					
								ArrayList<String> listKeywords = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
								document.setKeywords(listKeywords);
								break;
							case 3: 
								document.setDocumentType(node.toString());				
								break;
							case 4: 
								document.setCreationDate(node.toString().substring(0, node.toString().indexOf("^^")));
								break;
							case 5: 
								document.setDrivePath(node.toString());
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
					log.error( "GetDocumentByTitle: Can´t get document by title "+e);
				}
				jsonObject.put("data", listDocument);
				return Response.status(200).entity(jsonObject.toString()).build();
		 	}
		 
		 @GET
		 @Path("/GetDocumentByDriveID/{driveDocumentID}")
		 @Produces("application/json")
		 public Response getDocumentByDriveID(@PathParam("driveDocumentID") String driveDocumentID) throws JSONException {
				jsonObject = new JSONObject();
				Document document;
				ArrayList<Document> listDocument = new ArrayList<Document>();
				ArrayList<String> listKeywort = new ArrayList<String>();				
				try {
					String sparQuery = "prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"+
								"SELECT ?Name (group_concat(?Schlagwort;separator=',') as ?Schlagworte) ?Dokumenttyp ?Erstellungsdatum ?Speicherort ?Status ?Version ?Verfasser"+
								" WHERE{"+
								"  ?x ?y ?DriveDocumentID ."+
								"  Filter (?DriveDocumentID = '"+driveDocumentID+"') ."+
								"  ?x Cloud_Dokumente:Name ?Name ."+
								"  ?x Cloud_Dokumente:Dokumenttyp ?Dokumenttyp ."+
								"  ?x Cloud_Dokumente:Schlagwort ?Schlagwort ."+
								"  ?x Cloud_Dokumente:Erstellungsdatum ?Erstellungsdatum ."+
								"  ?x Cloud_Dokumente:Speicherort ?Speicherort ."+
								"  ?x Cloud_Dokumente:Status ?Status ."+
								"  ?x Cloud_Dokumente:Version ?Version ."+
								"  ?x Cloud_Dokumente:Dokument_hat_Verfasser ?Verfasser"+
								"}"+
								"group by ?Name ?Dokumenttyp ?Erstellungsdatum ?Speicherort ?Status ?Version ?Verfasser";

					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
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
								document.setDocumentName(node.toString());
								break;
							case 2: 					
								ArrayList<String> listKeywords = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
								document.setKeywords(listKeywords);
								break;
							case 3: 
								document.setDocumentType(node.toString());				
								break;
							case 4: 
								
								document.setCreationDate(node.toString().substring(0, node.toString().indexOf("^^")));
								break;
							case 5: 
								document.setDocumentID(node.toString());
								break;
							case 6: 
								document.setStatus(node.toString());
								break;
							case 7: 
								document.setVersion(Double.parseDouble(node.toString().substring(0, node.toString().indexOf("^^"))));
								break;
							case 8: 
								/*document.setVersion(Double.parseDouble(node.toString().substring(0, node.toString().indexOf("^^"))));
								*/break;
						}
						if(countIteration==8){
							countIteration=0;
						}
					}
						listDocument.add(document);
					}
			        qe.close();
				} catch(Exception e){
					log.error( "GetDocumentByDriveID: Error by accessing the GetDocumentByID Interface "+e);
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
						 
			     String id = UUID.randomUUID().toString();
			     UpdateProcessor upp = UpdateExecutionFactory.createRemote(
			                UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), 
			                "http://localhost:3030/ds/update");
			     upp.execute();
				 
				 return "Deleted";
			 }
			 
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
						log.error( "AddDocumentMetadata: Can´t add document metadata "+e);
					}
			}
			
			
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
								log.error( "EditDocumentMetadata: Can´t edit document metadata "+e);
							}
						}}
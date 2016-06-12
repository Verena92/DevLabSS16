/**
 * @author Waldemar Jaufmann
 * @author Kristina Baketaric
 * @author Alina Siebert
 * @author Tugce Yazici
 */

package controller;

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

import models.Company;
import models.Document;
import models.Employee;
import models.ObjectRelation;
import models.Project;
import models.Word;
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
	  
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Event Interface
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// GET Statements
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	  		ArrayList<String> listEmployees = new ArrayList<>();
	  		
	  		ObjectMapper mapper = new ObjectMapper();
	  		
	  		ArrayList<Project> listProjects = new ArrayList<>();
			try {
				String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#> "
						+ " SELECT  ?ProjektID ?ProjektName ?Status ?Projektmitglied ?Projektleiter"
						+ " WHERE   { "
						+ " ?x ?y ?ProjektID ."
						+ " Filter (?ProjektID='P003') ."
						+ " ?x Cloud_Dokumente:ProjektName ?ProjektName ."
						+ " ?x Cloud_Dokumente:Status ?Status ."
						+ " ?x Cloud_Dokumente:Projekt_hat_Projektmitglied ?Projektmitglied ."
						+ " ?x Cloud_Dokumente:Projekt_hat_Projektleiter ?Projektleiter}"; 
	
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
						  case "Projektmitglied" :
						  		listEmployees.add(getEmployee(node.toString()));
						  		project.setListProjectMember(listEmployees);
						  		break;
						  case "Status" : project.setStatus(node.toString()); break;
						  case "Projektleiter" : project.setProjectManager(getEmployee(node.toString()));
						}
					}
					
				}
		        qe.close();
			} catch(Exception e){
				log.error( "GetWordInformation: Can´t get word information "+e);
			}
			String jsonInString = mapper.writeValueAsString(project);
	  		
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
		 @Path("/GetCompanyByID/{companyID}")
		 @Produces("application/json")
		 public Response getCompanyID(@PathParam("companyID") String companyID) throws JsonProcessingException  {
	  		Company company = null;
	  		ObjectMapper mapper = new ObjectMapper();
	  		
	  		ArrayList<String> listProjects = new ArrayList<>();
	  		ArrayList<String> listEmployees = new ArrayList<>();
			try {
				String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#> "
						+ " SELECT  ?UnternehmensID ?Unternehmensname ?Mitarbeiteranzahl ?Projekt ?Mitarbeiter"
						+ " WHERE   { "
						+ " ?x ?y ?UnternehmensID ."
						+ " Filter (?UnternehmensID='"+companyID+"') ."
						+ " ?x Cloud_Dokumente:Unternehmensname ?Unternehmensname ."
						+ " ?x Cloud_Dokumente:Mitarbeiteranzahl ?Mitarbeiteranzahl ."
						+ " ?x Cloud_Dokumente:Unternehmen_hat_Mitarbeiter ?Mitarbeiter ."
						+ " ?x Cloud_Dokumente:Unternehmen_hat_Projekt ?Projekt} "; 
				
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
						  case "Mitarbeiter" : 
							  listEmployees.add(getEmployee(node.toString()));
							  company.setListEmployees(listEmployees);
							  break;
						  case "Projekt" : 
							  listProjects.add(getProject(node.toString()));
							  company.setListProjects(listProjects); 
							  break;
						}
					}
				}
		        qe.close();
			} catch(Exception e){
				log.error( "GetCompanyByID: Can´t get company information "+e);
			}
			
			String jsonInString = mapper.writeValueAsString(company);
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
					String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ " prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#> "
							+ " SELECT  ?MitarbeiterID ?Unternehmen ?Name ?Vorname ?Projekt"
							+ " WHERE   { "
							+ " ?x ?y ?MitarbeiterID ."
							+ " Filter (?MitarbeiterID='"+employeeID+"') ."
							+ " ?x Cloud_Dokumente:ist_Mitarbeiter_von_Unternehmen ?Unternehmen ."
							+ " ?x Cloud_Dokumente:Vorname ?Vorname ."
							+ " ?x Cloud_Dokumente:Name ?Name ."
							+ " ?x Cloud_Dokumente:Mitarbeiter_ist_Projektmitglied_von ?Projekt} "; 
					
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
								case "Unternehmen" :  
									employee.setEmployeeOf(getCompanyByURI(node.toString()));
									break;
								case "Name" : 
									employee.setEmployeeName(node.toString());
									break;
								case "Vorname" :
									employee.setEmployeeSurname(node.toString());
									break;
								case "Projekt" :
									listProjects.add(getProject(node.toString()));
									employee.setListProjects(listProjects);
									break;
							}
						}
					}
			        qe.close();
				} catch(Exception e){
					log.error( "GetEmpoloyeeByID: Can´t get employee information "+e);
				}
				
				String jsonInString = mapper.writeValueAsString(employee);
				return Response.status(200).entity(jsonInString).build();
			 }
	  	 
	  	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Methods to get further information
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		  	public String getCompanyByURI(String companyURI) {	
				String companyID = null;
				try {
					String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ " prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#> "
							+ " select ?UnternehmensID where {"
							+ " <"+companyURI+"> Cloud_Dokumente:UnternehmensID ?UnternehmensID ."
							+ " }"; 
						
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
					log.error( "GetWordInformation: Can´t get word information "+e);
				}
					return companyID;
			}
		 
		 public String getEmployee(String employeeURI) {	
			String employeeID = null;
			try {
				String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#> "
						+ " select ?MitarbeiterID where {"
						+ " <"+employeeURI+"> Cloud_Dokumente:ID ?MitarbeiterID ."
						+ " }"; 
				
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
				log.error( "GetWordInformation: Can´t get word information "+e);
			}
				return employeeID;
		 }
		 
		 public String getProject(String projectURI) {	
				String projectID = null;
				try {
					String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ " prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#> "
							+ " select ?ProjektID where {"
							+ " <"+projectURI+"> Cloud_Dokumente:ProjktID ?ProjektID ."
							+ " }"; 
					
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
					log.error( "GetWordInformation: Can´t get word information "+e);
				}
					return projectID;
			 }
		 
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
					log.error( "GetWordInformation: Can´t get word information "+e);
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
					log.error( "GetGraphInformation: Can´t get Graph information document metadata "+e);
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
								document.setName(node.toString());
								break;
							case 2: 					
								ArrayList<String> listKeywords = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
								document.setListKeyword(listKeywords);
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
								document.setName(node.toString());
								break;
							case 2: 					
								ArrayList<String> listKeywords = new ArrayList<String>(Arrays.asList(node.toString().split(",")));
								document.setListKeyword(listKeywords);
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
								System.out.println("Version"+node.toString());
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
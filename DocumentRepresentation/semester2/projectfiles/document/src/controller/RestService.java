/**
 * @author Waldemar Jaufmann, @author Kristina Baketaric, @author Alina Siebert, @author Tugce Yazici
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
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import models.Company;
import models.Document;
import models.Employee;
import models.ObjectRelation;
import models.Project;
import models.WordInformation;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
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
		 * The method GetDocumentMetadata is used to request the document id´s of the
		 * given project id, company id, employee id or document class
		 * 
		 * @param objKeyword
		 * 		The given keywords from the speech client
		 * 
		 * @return A Response-Object with all document id´s found for a given 
		 * 		lookup key
		 * 
		 * @throws org.codehaus.jettison.json.JSONException 
		 * 		throws a exception, if the json object could not be parsed
		 * 
		 */
	  	 @POST
		 @Path("/GetDocumentMetadata/")
		 @Consumes("application/json")
		 @Produces("application/json")
		 public static Response getDocumentMetadata(Object objKeyword) throws org.codehaus.jettison.json.JSONException {
			org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject(objKeyword.toString());
			JSONObject jsonObject = new JSONObject();
					
			ArrayList<String> listProjectURIs = new ArrayList<>();
			ArrayList<String> listEmployeeURIs = new ArrayList<>();
			ArrayList<String> listProjectDocumentIDs = new ArrayList<>();
			ArrayList<String> listEmployeeDocumentIDs = new ArrayList<>();
			ArrayList<String> listFoundDocumentIDs = new ArrayList<>();
			ArrayList<ArrayList<String>> listCompanies = new ArrayList<>();
			ArrayList<String> listCompanyIDs = new ArrayList<>();
			ArrayList<String> listDocumentClassIDs = new ArrayList<>();
			
			HashMap<String, String> mapProjectDocuments = new HashMap<>();
			HashMap<String, String> mapEmployeeDocuments = new HashMap<>();
			HashMap<String, String> mapDocumentClassDocuments = new HashMap<>();
			
			String ProjectURI = "";
			String EmployeeURI = "";
			String DocumentURI = "";
			
			//Transfer the ids from JSON-Array to a ArrayList with the ids
			for(int i=0; i<obj.getJSONArray("companies").length();i++){
				listCompanies.add(getProjectOfCompany((String) obj.getJSONArray("companies").get(i)));
			}
			
			for(int i=0; i<obj.getJSONArray("projects").length();i++){
				listProjectURIs.add(getIDByURI((String) obj.getJSONArray("projects").get(i)));
			}
			String documentClass="";
			for(int i=0; i<obj.getJSONArray("employees").length();i++){
				listEmployeeURIs.add(getIDByURI((String) obj.getJSONArray("employees").get(i)));
			}
			
			if(obj.getString("documentClass")!=null && obj.getString("documentClass")!=""){
				documentClass = obj.getString("documentClass");
			}
			
			String documentURI = "";
			String documenClassDocument = "";
			
			try {
				String sparQuery = "prefix Cloud_Dokumente: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " SELECT DISTINCT ?Dokumentenklasse ?Projekt ?Verfasser  ?x ?DokumentenID"
						+ " WHERE {"
						+ " ?x ?y ?z ."
						+ " ?x Cloud_Dokumente:Dokument_gehoert_zu_Projekt ?Projekt ."
						+ " ?x Cloud_Dokumente:Dokument_hat_Verfasser ?Verfasser ."
						+ " ?x <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#DokumentenID> ?DokumentenID ."
						+ " ?x rdf:type ?Dokumentenklasse}";
						  
	
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
							case "Dokumentenklasse" :
								if(!node.toString().contains("NamedIndividual")){
									documentURI = node.toString();
								}				
							case "x" :
								if(!mapDocumentClassDocuments.containsKey(node.toString())){
									mapDocumentClassDocuments.put(node.toString(), documentURI);
								}
								if(!mapProjectDocuments.containsKey(node.toString())){
									mapProjectDocuments.put(node.toString(), ProjectURI);
								}
								
								if(!mapEmployeeDocuments.containsKey(node.toString())){
									mapEmployeeDocuments.put(node.toString(), EmployeeURI);
								}								
								break;
						}
					}
		        }
		        for (Entry<String, String> value : mapProjectDocuments.entrySet()) {
		        	for(int i=0; i<listProjectURIs.size();i++){
		        		if(listProjectURIs.get(i).equals(value.getValue())&&getDocumentByURI(value.getKey())!=null){
		        			listProjectDocumentIDs.add(getDocumentByURI(value.getKey()));
		        		}
		        	}
		        }
		        
		        for (Entry<String, String> value : mapDocumentClassDocuments.entrySet()) {
		        	if(value.getKey().contains("documentrepresentation") && value.getKey().contains(documentClass) && documentClass!=""){
		        		if(getDocumentByURI(value.getKey())!=null){
		        			listDocumentClassIDs.add(getDocumentByURI(value.getKey()));
		        		}
		        	}
		        }
		        
		        for (Entry<String, String> value : mapProjectDocuments.entrySet()) {
		        	for(int i=0;i<listCompanies.size();i++){
						for(int k=0;k<listCompanies.get(i).size();k++){
							if(listCompanies.get(i).get(k).equals(value.getValue())
									&&value.getKey().contains("documentrepresentation")&&getDocumentByURI(value.getKey())!=null){
								listCompanyIDs.add(getDocumentByURI(value.getKey()));
							}	
						}
					}
		        }
		        for (Entry<String, String> value : mapEmployeeDocuments.entrySet()) {
		        	for(int i=0; i<listEmployeeURIs.size();i++){
		        		if(listEmployeeURIs.get(i).equals(value.getValue())){
		        			if(getDocumentByURI(value.getKey())!=null){
		        				listEmployeeDocumentIDs.add(getDocumentByURI(value.getKey()));
		        			}
		        		}
		        	}
		        }

		        if(!listProjectDocumentIDs.isEmpty() && !listEmployeeDocumentIDs.isEmpty() && 
		        		!listDocumentClassIDs.isEmpty() && !listCompanyIDs.isEmpty()){	
			        for(int i=0;i<listEmployeeDocumentIDs.size();i++){
			        	for(int k=0;k<listProjectDocumentIDs.size();k++){
			        		for(int l=0;l<listDocumentClassIDs.size();l++){
			        			for(int m=0;m<listCompanyIDs.size();m++){
			        				if(listEmployeeDocumentIDs.get(i)!=null && listProjectDocumentIDs.get(k)!=null 
			        						&& listCompanyIDs!=null && listDocumentClassIDs.get(l).contains(listEmployeeDocumentIDs.get(i))
			        						&& listDocumentClassIDs.get(l).contains(listProjectDocumentIDs.get(k))
			        						&& listDocumentClassIDs.get(l).contains(listCompanyIDs.get(m))){
				        				if(!listFoundDocumentIDs.contains(listDocumentClassIDs.get(l))){
				        					listFoundDocumentIDs.add(listDocumentClassIDs.get(l));
				        				}
					        		}	
								}
			        		}     	
			        	}
			        }
		        } else if(!listProjectDocumentIDs.isEmpty() && !listEmployeeDocumentIDs.isEmpty() && 
		        		!listDocumentClassIDs.isEmpty()){
		        	for(int i=0;i<listEmployeeDocumentIDs.size();i++){
			        	for(int k=0;k<listProjectDocumentIDs.size();k++){
			        		for(int l=0;l<listDocumentClassIDs.size();l++){
			        				if(listEmployeeDocumentIDs.get(i)!=null && listProjectDocumentIDs.get(k)!=null 
			        						&& listDocumentClassIDs.get(l).contains(listEmployeeDocumentIDs.get(i))
			        						&& listDocumentClassIDs.get(l).contains(listProjectDocumentIDs.get(k))){
				        				if(!listFoundDocumentIDs.contains(listDocumentClassIDs.get(l))){
				        					listFoundDocumentIDs.add(listDocumentClassIDs.get(l));
				        				}
					        		}	
			        		}     	
			        	}
			        }
		        }
		        else if(!listProjectDocumentIDs.isEmpty() && !listCompanyIDs.isEmpty() && 
		        		!listDocumentClassIDs.isEmpty()){
		        	for(int i=0;i<listCompanyIDs.size();i++){
			        	for(int k=0;k<listProjectDocumentIDs.size();k++){
			        		for(int l=0;l<listDocumentClassIDs.size();l++){
			        				if(listCompanyIDs.get(i)!=null && listProjectDocumentIDs.get(k)!=null 
			        						&& listDocumentClassIDs.get(l).contains(listCompanyIDs.get(i))
			        						&& listDocumentClassIDs.get(l).contains(listProjectDocumentIDs.get(k))){
				        				if(!listFoundDocumentIDs.contains(listDocumentClassIDs.get(l))){
				        					listFoundDocumentIDs.add(listDocumentClassIDs.get(l));
				        				}
					        		}	
			        		}     	
			        	}
			        }
		        }
		        else if(!listEmployeeDocumentIDs.isEmpty() && !listCompanyIDs.isEmpty() && 
		        		!listDocumentClassIDs.isEmpty()){
		        	for(int i=0;i<listCompanyIDs.size();i++){
			        	for(int k=0;k<listEmployeeDocumentIDs.size();k++){
			        		for(int l=0;l<listDocumentClassIDs.size();l++){
			        				if(listCompanyIDs.get(i)!=null && listEmployeeDocumentIDs.get(k)!=null 
			        						&& listDocumentClassIDs.get(l).contains(listCompanyIDs.get(i))
			        						&& listDocumentClassIDs.get(l).contains(listEmployeeDocumentIDs.get(k))){
				        				if(!listFoundDocumentIDs.contains(listDocumentClassIDs.get(l))){
				        					listFoundDocumentIDs.add(listDocumentClassIDs.get(l));
				        				}
					        		}	
			        		}     	
			        	}
			        }
		        }
		        else if(!listDocumentClassIDs.isEmpty() && !listCompanyIDs.isEmpty()){
		        	for(int k=0;k<listCompanyIDs.size();k++){
		        		for(int l=0;l<listDocumentClassIDs.size();l++){
		        			if(listCompanyIDs.get(k)!=null && listDocumentClassIDs.get(l).contains(listCompanyIDs.get(k))){
	        					listFoundDocumentIDs.add(listDocumentClassIDs.get(l));
	        				}
		        		}
		        	}
		        }
		        else if(!listDocumentClassIDs.isEmpty() && !listProjectDocumentIDs.isEmpty()){
		        	for(int k=0;k<listProjectDocumentIDs.size();k++){
		        		for(int l=0;l<listDocumentClassIDs.size();l++){
		        			if(listProjectDocumentIDs.get(k)!=null && listDocumentClassIDs.get(l).contains(listProjectDocumentIDs.get(k))){
	        					listFoundDocumentIDs.add(listDocumentClassIDs.get(l));
	        				}
		        		}
		        	}
		        }
		        else if(!listDocumentClassIDs.isEmpty() && !listEmployeeDocumentIDs.isEmpty()){
		        	for(int k=0;k<listEmployeeDocumentIDs.size();k++){
		        		for(int l=0;l<listDocumentClassIDs.size();l++){
		        			if(listEmployeeDocumentIDs.get(k)!=null && listDocumentClassIDs.get(l).contains(listEmployeeDocumentIDs.get(k))){
	        					listFoundDocumentIDs.add(listDocumentClassIDs.get(l));
	        				}
		        		}
		        	}
		        }
		        else if(!listCompanyIDs.isEmpty() && !listEmployeeDocumentIDs.isEmpty()){
		        	for(int k=0;k<listEmployeeDocumentIDs.size();k++){
		        		for(int l=0;l<listCompanyIDs.size();l++){
		        			if(listEmployeeDocumentIDs.get(k)!=null && listCompanyIDs.get(l).contains(listEmployeeDocumentIDs.get(k))){
	        					listFoundDocumentIDs.add(listCompanyIDs.get(l));
	        				}
		        		}
		        	}
		        } else if(!listProjectDocumentIDs.isEmpty() && !listEmployeeDocumentIDs.isEmpty()){
		        	for(int k=0;k<listEmployeeDocumentIDs.size();k++){
			        	listFoundDocumentIDs.add(listEmployeeDocumentIDs.get(k));
		        	}
		        } else if(!listDocumentClassIDs.isEmpty()){
		        	for(int l=0;l<listDocumentClassIDs.size();l++){
		        		listFoundDocumentIDs.add(listDocumentClassIDs.get(l));
	        		}
		        }else if(!listProjectDocumentIDs.isEmpty()){
		        	for(int k=0;k<listProjectDocumentIDs.size();k++){
			        	listFoundDocumentIDs.add(listProjectDocumentIDs.get(k));
		        	}
		        } else if(!listEmployeeDocumentIDs.isEmpty()){
		        	for(int k=0;k<listEmployeeDocumentIDs.size();k++){
			        	listFoundDocumentIDs.add(listEmployeeDocumentIDs.get(k));
		        	}
		        } else if(!listCompanyIDs.isEmpty()){
		        	for(int k=0;k<listCompanyIDs.size();k++){
			        	listFoundDocumentIDs.add(listCompanyIDs.get(k));
		        	}
		        } else if(!listCompanyIDs.isEmpty()){
		        	for(int i=0;i<listCompanyIDs.size();i++){
		        		if(!listFoundDocumentIDs.contains(listCompanyIDs.get(i))){
		        			listFoundDocumentIDs.add(listCompanyIDs.get(i));
		        		}
					}
		        }
		        qe.close();
			} catch(Exception e){
				log.error( "GetDocumentMetadata: Can´t get document metadata"+e);
			}
			jsonObject.put("documents", listFoundDocumentIDs);
			return Response.status(200).entity(jsonObject.toString()).build();
		 }

		 /**
		 * The method GetDocumentByID is used to request additional document information by document id
		 * 
		 * @param documentID
		 *      The documentID, which will be used to look for further document informations
		 * 
		 * @return A Response-Object with all project informations
		 * 
		 * @throws JsonProcessingException 
		 * 		Intermediate base class for all problems encountered when processing 
		 * 		(parsing, generating) JSON content that are not pure I/O problems
		 */
	  	 @GET
	  	 @Path("/GetDocumentByID/{documentID}")
		 @Produces("application/json")
		 public Response GetDocumentByID(@PathParam("documentID") String documentID) throws JSONException, JsonProcessingException {
	  		Document document = null;
	  		ObjectMapper mapper = new ObjectMapper();
	  		ArrayList<String> listKeywords = new ArrayList<>();
	  		String jsonInString = "";

			try {
				String sparQuery = 
						"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
						+ " PREFIX Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
						+ " PREFIX Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
						+ " SELECT Distinct ?DocumentName ?DriveDocumentDrive ?Status ?Schlagwort ?Speicherort ?Version ?Projekt ?Dokumentenklasse ?Dokumenttyp"
						+ " ?Erstellungsdatum ?Verfasser"
						+ " where {"
						+ " ?x ?y ?DriveDocumentDrive ."
						+ " Filter (?DriveDocumentDrive = '"+documentID+"') ."
						+ " ?x Cloud_Dokumente_old:Status ?Status ."
						+ " ?x Cloud_Dokumente:Dokumentenname ?DocumentName ."
						+ " ?x Cloud_Dokumente_old:Schlagwort ?Schlagwort . "
						+ " ?x Cloud_Dokumente_old:Speicherort ?Speicherort ."
						+ " ?x Cloud_Dokumente_old:Version ?Version ."
						+ " ?x Cloud_Dokumente_old:Erstellungsdatum ?Erstellungsdatum ."
						+ " ?x Cloud_Dokumente_old:Dokument_hat_Verfasser ?Verfasser ."
						+ " ?x Cloud_Dokumente_old:Dokument_gehoert_zu_Projekt ?Projekt ."
						+ " optional{?x Cloud_Dokumente_old:Dokumenttyp ?Dokumenttyp} ."
						+ " ?x rdf:type ?Dokumentenklasse ."
						+ "}";
				
				QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);
		        ResultSet results = qe.execSelect();
		        List var = results.getResultVars();    
		        
		        while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					document = new Document();
					for(int i=0; i<var.size();i++){
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						switch(va){
							case "DocumentName" : 
								document.setDocumentName(node.toString());
								break;
							case "Status": 
								document.setStatus(node.toString());
								break;
							case "Schlagwort" : 
									listKeywords.add(node.toString());
									document.setKeywords(listKeywords);	
								break;
							case "DriveDocumentDrive" : 
								document.setDocumentID(node.toString());
								break;
							case "Speicherort" :
								log.info(node.toString());
								document.setDrivePath(node.toString());
								break;
							case "Version" :
								String version = node.toString().substring(0, node.toString().indexOf("^^"));
								document.setVersion(Double.parseDouble(version));
								break;
							case "Erstellungsdatum" :
								String creationDate = node.toString().substring(0, node.toString().indexOf("^^"));
								document.setCreationDate(creationDate);
								break;
							case "Verfasser" :
								document.setCreatedBy(getEmployeeByURI(node.toString()));
								break;
							case "Projekt" :
								ArrayList<String> listProjects = new ArrayList<>();
								listProjects.add(getProjectByURI(node.toString()));
								document.setProjects(listProjects);
								break;
							case "Dokumentenklasse" :
								document.setDocumentClass(node.asResource().getLocalName());
								break;
							case "Dokumenttyp" :
								document.setDocumentType(node.toString());
								break;		
						}
					}
				}
		        qe.close();
			} catch(Exception e){
				log.error( "GetDocumentByID: Can´t get document metadata by id"+e);
			}
		
			jsonInString = mapper.writeValueAsString(document);
			return Response.status(200).entity(jsonInString).build();
		 }

	  	/**
	     * The method GetProjectByID is used to request additional project information by project id
	     * 
	     * @param projectID
		 *      The projectID, which will be used to look for further project informations
	     * 
	     * @return A Response-Object with all project informations
	     * 
	     * @throws JsonProcessingException 
	     * 		Intermediate base class for all problems encountered when processing 
	     * 		(parsing, generating) JSON content that are not pure I/O problems
	     */
	  	 @GET
		 @Path("/GetProjectByID/{projectID}")
		 @Produces("application/json")
		 public Response getProjectByID(@PathParam("projectID") String projectID) throws JsonProcessingException {
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
						  case "Status" : project.setStatus(node.toString());
						 
						  break;
						  
						  case "Projektleiter" :  project.setProjectManager(getEmployeeByURI(node.toString())); break;
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
				log.error( "GetProjectByID: Can´t get project information "+e);
			}
			String jsonInString = mapper.writeValueAsString(newProject);
	  	
			if(!jsonInString.contains("P")){
				return Response.status(Response.Status.NOT_FOUND).entity("Project not found for ID: "+projectID).build(); 
			}
			
			return Response.status(200).entity(jsonInString).build();
		 }
	  	 
		 /**
		 * The method GetDocumentClasses is used to request all available document classes
		 * 
		 * @return A Response-Object with all document class names
		 * 
		 */
	  	 	
	  	 @GET
		 @Path("/GetDocumentClasses/")
		 @Produces("application/json")
		 public Response getDocumentClasses(){
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
				log.error( "GetDocumentClasses: Can´t get document class information "+e);
			}
			jsonObject.put("documentClasses", listDocumentClasses);
			return Response.status(200).entity(jsonObject.toString()).build();
		 }	  	 
	  	 
		 /**
		 * The method GetCompanyByID is used to request additional company informations by company id.
		 * 
		 * @param companyID
		 *      The companyID, which will be used as a lookup value for further company informations.
		 * 
		 * @return A Response-Object with all company informations.
		 * 
		 * @throws JsonProcessingException 
		 * 		Intermediate base class for all problems encountered when processing 
		 * 		(parsing, generating) JSON content that are not pure I/O problems.
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
		 * The method GetEmployeeByID is used to request additional employee informations by employee id.
		 * 
		 * @param employeeID
		 *      The employeeID, which will be used as a lookup value for further employee informations.
		 * 
		 * @return A Response-Object with all employee informations.
		 * 
		 * @throws JsonProcessingException 
		 * 		Intermediate base class for all problems encountered when processing 
		 * 		(parsing, generating) JSON content that are not pure I/O problems.
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
						+ " ?x Cloud_Dokumente:hangoutUserID ?HangoutUserID ."
						+ " ?x Cloud_Dokumente:driveUserID ?DriveUserID ."
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
		  	 
	  	/**
	     * The method getCompanyByURI is used to request the company id by company uri
	     * 
	     * @param companyURI
		 *      The companyURI, which will be used to look for the companyID
	     * 
	     * @return The company id
	     * 
	     */
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
		  	 
	  	/**
	     * The method getEmployeeByURI is used to request the employee id by employee uri
	     * 
	     * @param employeeURI
		 *      The employeeURI, which will be used to look for the employeeID
	     * 
	     * @return The employee id
	     * 
	     */		  	
		 public static String getEmployeeByURI(String employeeURI) {	
			String employeeID = null;
			try {
				String sparQuery = " prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>select ?HangoutUserID where {"
						+ " <"+employeeURI+"> Cloud_Dokumente:hangoutUserID ?HangoutUserID }";  
				
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
		 
		 
	  	/**
	     * The method getProjectByURI is used to request the project id by project uri
	     * 
	     * @param projectID
		 *      The projectID, which will be used to look for the project uri
		 *       
	     * @return The project uri
	     * 
	     */		
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
					log.error( "getProjectByURI: Can´t get project by uri "+e);
				}
					return projectURI;
			 }
		 
	  	/**
	     * The method getProjectOfCompany is used to request all projects of a company
	     * 
	     * @param companyID
		 *      The companyID, which will be used as a lookup value for the associated projects
	     * 
	     * @return A list with all projects of the company
	     * 
	     */		
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
					log.error( "getProjectOfCompany: Can´t get projects of the company "+e);
				}
					return listProjects;
			 }

		 /**
		 * A generic class to get the id of by a uri
		 * 
		 * @param uri
		 *      The uri, which will be used as a lookup value for further informations.
		 * 
		 * @return A Response-Object with all informations found for the.
		 * 
		 */
		 public static String getIDByURI(String uri) {	
				String id = null;
				try {
					String sparQuery ="PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
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
					log.error( "getIDByURI: Can´t get id by uri "+e);
				}
					return id;
			 }
		 
	  	/**
	     * The method getProjectByURI is used to request further project informations by project uri
	     * 
	     * @param projectURI
		 *      The projectURI, which will be used as a lookup value for the associated projects
	     * 
	     * @return The project id
	     * 
	     */	
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
		 
	  	/**
	     * The method getDocumentByURI is used to request further document informations by document uri
	     * 
	     * @param documentURI
		 *      The documentURI, which will be used as a lookup value for the associated document id
	     * 
	     * @return The document id
	     * 
	     */			 
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
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// POST Statements
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  	/**
	     * The method getWordinformation is used by the speech client to request further 
	     * informations about a keyword
	     * 
	     * @param objKeyword
		 *      A JSON-Object with the keywords.
	     * 
	     * @return Keyword informations like the associated project id, employee id or company id
	  	 * @throws org.codehaus.jettison.json.JSONException 
	  	 * 		throws a exception, if the json object could not be parsed
	  	 * 
	  	 * @throws JsonProcessingException 
	  	 * 		Intermediate base class for all problems encountered when processing 
		 * 		(parsing, generating) JSON content that are not pure I/O problems
	     * 
	     */	
		 
		 @POST
		 @Path("/GetWordinformation/")
		 @Consumes("application/json")
		 @Produces("application/json")
		 public static Response getWordinformation(Object objKeyword) throws org.codehaus.jettison.json.JSONException, JsonProcessingException  {
			org.codehaus.jettison.json.JSONObject obj = new org.codehaus.jettison.json.JSONObject(objKeyword.toString());

				String keyword=""; 
				String nextKeyword="";
				String priviousKeyword = "";
				if(obj.getString("keyword")!=null){
					String ikeyword= obj.getString("keyword");
					keyword = ikeyword.substring(0, 1).toUpperCase() + ikeyword.substring(1);
				}
				if(obj.getString("nextKeyword")!=null){
					String inextKeyword= obj.getString("nextKeyword");
					nextKeyword = inextKeyword.substring(0, 1).toUpperCase() + inextKeyword.substring(1);
				}
				if(obj.getString("previousKeyword")!=null){
					String ipriviousKeyword= obj.getString("previousKeyword");
					priviousKeyword = ipriviousKeyword.substring(0, 1).toUpperCase() + ipriviousKeyword.substring(1);
				}
				
			 	ArrayList<WordInformation> listWordInformation = new ArrayList<>();
			 	for(int i=0; i<5; i++){
			 		switch(i){
			 			case 0 : 
			 				if(parseKeywords(priviousKeyword)!=null){
			 					listWordInformation.add(parseKeywords(priviousKeyword));
			 				}
			 				break;
			 			case 1 :
			 				if(parseKeywords(keyword)!=null){
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
			 			newListPerseons = new ArrayList<>(ausgabe.getEmployees());
			 			newListProjects = new ArrayList<>(ausgabe.getProjects());
			 			newListCompanies = new ArrayList<>(ausgabe.getCompanies());
			 			
			 			if(!newListPerseons.isEmpty()){
			 				newWordInformation.setEmployees(newListPerseons);
			 			} else {
			 				ArrayList<String> listEmployeess = new ArrayList<>();
			 			
			 				newWordInformation.setEmployees(listEmployeess);
			 			}
			 			
			 			if(!newListProjects.isEmpty()){
			 				newWordInformation.setProjects(newListProjects);
			 			} else {
			 				ArrayList<String> listProjects = new ArrayList<>();

			 				newWordInformation.setProjects(listProjects);
			 			}
			 			
			 			if(!newListCompanies.isEmpty()){
			 				newWordInformation.setCompanies(newListCompanies);
			 			} else {
			 				ArrayList<String> listCompanies = new ArrayList<>();
			 				newWordInformation.setCompanies(listCompanies);
			 			}
			 		}

			 	WordInformation newnewWordInformation = new WordInformation();
			 	
			 	if(newWordInformation.getCompanies()==null){
			 		ArrayList<String> listCompanies = new ArrayList<>();
		
			 		newnewWordInformation.setCompanies(listCompanies);
			 	} else {
			 		newnewWordInformation.setCompanies(newWordInformation.getCompanies());
			 	}
			 	
			 	if(newWordInformation.getProjects()==null){
			 		ArrayList<String> listProjects = new ArrayList<>();

			 		newnewWordInformation.setProjects(listProjects);
			 	} else {
			 		newnewWordInformation.setProjects(newWordInformation.getProjects());
			 	}
			 	
			 	if(newWordInformation.getEmployees()==null){
			 		ArrayList<String> listEmployees = new ArrayList<>();
		
			 		newnewWordInformation.setEmployees(listEmployees);
			 	} else {
			 		newnewWordInformation.setEmployees(newWordInformation.getEmployees());
			 	}
			 	ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(newnewWordInformation);
				
				return Response.status(200).entity(jsonInString).build();	
		 	}
	  	/**
	     * The method is used to search for word informations in the abox 
	     * 
	     * @param keyword
		 *      The given keyword
	     * @return The informations about the word
	     */
		 public static WordInformation parseKeywords(String keyword) {
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
					wordInformation.setEmployees(listEmployees);
					wordInformation.setCompanies(listCompanies);
				}
				
				return wordInformation;
		 	} 
		
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// END-----------------------Speech Token Interface
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// GOOGLE Apps Script Interface
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// GET Statements
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	/**
     * The method GetEmployeeByDriveUserID is used by the apps script client to request google drive user id  
     * 
     * @param driveUserID
	 *      The driveUserID (e.g. wjaufmann166@gmail.com).
     * 
     * @return A Response object with the informations of the drive user
     * 
     */
		 
	@GET
	@Path("/GetEmployeeByDriveUserID/{driveUserID}")
	@Produces("application/json")
	public Response getEmployeeByDriveUserID(@PathParam("driveUserID") String driveUserID) {
		String employeeURI = "";

		try {
			String sparQuery = "prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
					+ " select ?x where {" + " ?x ?y ?DriveUserID ."
					+ " Filter (?DriveUserID = 'haruki.murakami@gmail.com') }";

			QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			ResultSet results = qe.execSelect();
			List var = results.getResultVars();

			while (results.hasNext()) {
				QuerySolution qs = results.nextSolution();
				for (int i = 0; i < var.size(); i++) {
					String va = var.get(i).toString();
					RDFNode node = qs.get(va);
					employeeURI = node.toString();
				}
			}
			qe.close();
		} catch (Exception e) {
			log.error("GetEmployeeByDriveUserID: Can´t get the employee information " + e);
		}
		return Response.status(200).entity(employeeURI).build();
	}
		 
	
  	/**
     * The method GetDocumentByDriveID is used by the apps script client to request document informations by 
     * drive document id.  
     * 
     * @param driveDocumentID
	 *      The driveDocumentID.
     * 
     * @return A Response object with the informations of the document
     * 
  	 * @throws JsonProcessingException 
	 * 		Intermediate base class for all problems encountered when processing 
	 * 		(parsing, generating) JSON content that are not pure I/O problems
     */
	@GET
	@Path("/GetDocumentByDriveID/{driveDocumentID}")
	@Produces("application/json")
	public Response getDocumentByDriveID(@PathParam("driveDocumentID") String driveDocumentID) throws JsonProcessingException {
		jsonObject = new JSONObject();
		Document document = null;
		ArrayList<Document> listDocument = new ArrayList<Document>();
		ArrayList<String> listKeywort = new ArrayList<String>();
		try {
			String sparQuery = "prefix Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
					+ "	prefix Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
					+ "	SELECT ?DriveDocumentID ?Status ?Dokumentenname (group_concat(?Schlagwort;separator=',') as ?Schlagworte)"
					+ "	WHERE{" + "	?x ?y ?DriveDocumentID ."
					+ " Filter (?DriveDocumentID = '1fauar5Y4MrJ0jfN8WKfo111O-Rj6QDSWxWQjcbia--I') ."
					+ " ?x <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#Status> ?Status ."
					+ " ?x Cloud_Dokumente:Dokumentenname ?Dokumentenname ."
					+ " ?x Cloud_Dokumente_old:Schlagwort ?Schlagwort"
					+ " } group by ?DriveDocumentID ?Status ?Dokumentenname";

			QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			ResultSet results = qe.execSelect();
			List var = results.getResultVars();
			int countIteration = 0;

			while (results.hasNext()) {
				document = new Document();
				QuerySolution qs = results.nextSolution();
				for (int i = 0; i < var.size(); i++) {
					String va = var.get(i).toString();
					RDFNode node = qs.get(va);
					countIteration++;
					switch (va) {
					case "Dokumentenname":
						document.setDocumentName(node.toString());
						break;
					case "Schlagworte":
						ArrayList<String> listKeywords = new ArrayList<String>(
								Arrays.asList(node.toString().split(",")));
						document.setKeywords(listKeywords);
						break;
					case "Status":
						document.setStatus(node.toString());
						break;
					}
				}
			}
			qe.close();
		} catch (Exception e) {
			log.error("GetDocumentByDriveID: Error by accessing the GetDocumentByID Interface " + e);
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(document);

		return Response.status(200).entity(jsonInString).build();
	}
		 
			 
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// INSERT Statements
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			 
	/**
	 * 
	 * AddDocumentMetadata
	 * 
	 * This Method is responsible for adding the document metadata to the abox.
	 * The document metadata will be send by the google apps script client.
	 * 
	 * @param name
	 * 		The name of the document
	 *   
	 * @param driveID
	 * 		The google drive id of the document
	 * 
	 * @param status
	 * 		The actual status of the document
	 * 
	 * @param drivePath 
	 * 		The drivePath of the document
	 * 
	 * @param version 
	 * 		The version of the document
	 * 
	 * @param creationDate
	 * 		The creationdate of the document in a ISO 8601 format
	 * 
	 * @param createdBy
	 * 		The creater name of the document
	 *  
	 * @param project
	 * 		The associated project
	 * 
	 * @param documentClass
	 * 		The document class (e.g. Costplan)
	 * 
	 * @param keywords
	 * 		The associated keywords of the document
	 * 	
	 * @param type
	 * 		The associated documenttype
	 * 
	 */
	@POST
	@Path("/AddDocumentMetadata")
	@Consumes("application/x-www-form-urlencoded")
	public static void addDocumentMetadata(@FormParam("name") String name, @FormParam("driveID") String driveID, @FormParam("status") String status, 
			@FormParam("keywords") String keywords, @FormParam("drivePath") String drivePath, @FormParam("version") String version, 
			@FormParam("creationDate") String creationDate, @FormParam("createdBy") String createdBy, 
			@FormParam("project") String project, @FormParam("type") String type, @FormParam("documentClass") String documentClass) {
		try {
			String UPDATE_TEMPLATE = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					+ " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ " PREFIX Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
					+ " PREFIX Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
					+ " INSERT DATA { "
					+ " <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/A-BOX_Cloud_Dokumente#" + name + "> "
					+ " Cloud_Dokumente:Dokumentenname '" + name + "' ;" 
					+ " Cloud_Dokumente_old:Status '"+status+"' ;" 
					+ " Cloud_Dokumente_old:DriveDocumentID '"+driveID+"' ;"
					+ " Cloud_Dokumente_old:Schlagwort "+keywords+" ;" 
					+ " Cloud_Dokumente_old:Speicherort '"+ drivePath + "' ;" 
					+ " Cloud_Dokumente_old:Version '" + version + "^^xsd:double' ;"
					+ " Cloud_Dokumente_old:Erstellungsdatum '" + creationDate + "^^xsd:dateTime' ;"
					+ " Cloud_Dokumente_old:Dokument_hat_Verfasser  <"+createdBy+"> ;"
					+ " Cloud_Dokumente_old:Dokument_gehoert_zu_Projekt <"+project+"> ;"
					+ " Cloud_Dokumente_old:Dokumenttyp '"+type+"' ; "
					+ " rdf:type <"+documentClass+"> ;"
					+ " }";

			String id = UUID.randomUUID().toString();
			UpdateProcessor upp = UpdateExecutionFactory.createRemote(
					UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), "http://localhost:3030/ds/update");
			upp.execute();
		} catch (Exception e) {
			log.error("AddDocumentMetadata: Can´t add document metadata " + e);
		}
	}
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// EDIT Statements
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  	/**
     * The method EditDocumentMetadata is used by the apps script client to edit document metadata   
     * 
     * @param driveID
	 *      The driveDocumentID.
	 *      
	 * @param oldName
	 * 		The old name of the document
	 *      
	 * @param newName
	 * 		The new given name of the document
	 *      
	 * @param oldStatus
	 *      The old status of the document
	 *      
	 * @param newStatus
	 *      The new given status of the document
     * 
     * 
     */
	@POST
	@Path("/EditDocumentMetadata")
	@Consumes("application/x-www-form-urlencoded")
	public static void editDocumentMetadata(@FormParam("driveID") String driveID, 
			@FormParam("oldName") String oldName, @FormParam("newName") String newName,
			@FormParam("oldStatus") String oldStatus, @FormParam("newStatus") String newStatus) {
		try {
			String UPDATE_TEMPLATE = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					+ " PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ " PREFIX Cloud_Dokumente: <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#>"
					+ " PREFIX Cloud_Dokumente_old: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
					+ " DELETE { "
					+ " ?x <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#Dokumentenname> '"+oldName+"' ."
					+ " ?x Cloud_Dokumente_old:Status '"+oldStatus+"' ."
					+ " }"
					+ " INSERT { "
					+ " ?x <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#Dokumentenname> '"+newName+"' . "
					+ " ?x Cloud_Dokumente_old:Status '"+newStatus+"' ."
					+ " }"
					+ " WHERE  "
					+ " { "
					+ " ?x <http://www.documentrepresentation.org/ontologies/Cloud_Dokumente#DokumentenID> '"+driveID+"' "
					+ " }";

			String id = UUID.randomUUID().toString();
			UpdateProcessor upp = UpdateExecutionFactory.createRemote(
					UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), "http://localhost:3030/ds/update");
			upp.execute();
		} catch (Exception e) {
			log.error("EditDocumentMetadata: Can´t edit document metadata " + e);
		}
	}
}
/**
 * @author Waldemar Jaufmann
 * @author Thomas Seewald
 */

package controller;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import java.io.File;
import java.io.FileReader;
import java.io.Writer;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import models.Document;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import org.codehaus.jettison.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import jena_test.MyOutputStream;
import jena_test.graph;

@Path("/rest")
public class OIS {
	  private static JSONObject jsonObject;

	  private static String object;
	  private static String fileName = "data/A-Box_Cloud_Dokumente.owl";
	  private static OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// METHODS FOR THE Simulator
// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
		// GET Statements
		// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		 
		 @GET
		 @Path("/GetDocumentByKeyword/{keyword}")
		 @Produces("application/json")
		 public Response getDocumentByKeyword(@PathParam("keyword") String keyword) throws JSONException {
				jsonObject = new JSONObject();
				Document document = new Document();
				ArrayList<Document> listDocument = new ArrayList<Document>();
				
				try {
					
					String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ " SELECT ?Schlagwort ?Name ?Speicherort ?Erstellungsdatum ?Dokument_hat_Verfasser ?Dokument_gehoert_zu_Projekt "
							+ " WHERE  { ?Dokument foaf:Schlagwort ?Schlagwort ."
							+ "			 ?Dokument foaf:Name ?Name . "
							+ "			 ?Dokument foaf:Speicherort ?Speicherort . "
							+ "			 ?Dokument foaf:Erstellungsdatum ?Erstellungsdatum . "
							+ "			 ?Dokument foaf:Dokument_hat_Verfasser ?Dokument_hat_Verfasser . "
							+ "			 ?Dokument foaf:Dokument_gehoert_zu_Projekt ?Dokument_gehoert_zu_Projekt . "
							+ "			  FILTER regex(?Schlagwort,'"+keyword+"') }";
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							if(i==1){
								document.setDocumentName(node.toString());
							} else if(i==2){
								document.setDocumentPath(node.toString());
							} else if(i==3){
								document.setCreationDate(node.toString().substring(0, 19));
							} else if(i==4){
								document.setCreatedBy(getAttributeOfGraph(node.toString()));
							} else if(i==5){
								document.setRelatedProject(getAttributeOfGraph(node.toString()));
							}
						}
					}
			        
			        qe.close();
					
				} catch(Exception e){
					e.printStackTrace();
				}
				listDocument.add(document);
				jsonObject.put("data", listDocument);
				return Response.status(200).entity(jsonObject.toString()).build();
		 	}
		 
		 @GET
		 @Path("/GetAllKeywords")
		 @Produces("application/json")
		 public Response getAllKeywords() throws JSONException {
				jsonObject = new JSONObject();
				Document document = new Document();
				ArrayList<Document> listDocument = new ArrayList<Document>();
				ArrayList<String> listKeywords = new ArrayList<String>();
				
				try {
					
					String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
							+ " SELECT DISTINCT ?Schlagwort "
							+ " WHERE  { ?Dokument foaf:Schlagwort ?Schlagwort }";
					
					QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query", sparQuery);

			        ResultSet results = qe.execSelect();
			        List var = results.getResultVars();
			        while (results.hasNext()){
						QuerySolution qs = results.nextSolution();
						for(int i=0; i<var.size();i++){
							String va = var.get(i).toString();
							RDFNode node = qs.get(va);
							listKeywords.add(node.toString());
							System.out.println(node.toString());
						}
					}
			        
			        qe.close();
					document.setKeyword(listKeywords);
					listDocument.add(document);
					
				} catch(Exception e){
					e.printStackTrace();
				}
				
				jsonObject.put("data", listDocument);
				return Response.status(200).entity(jsonObject.toString()).build();
		 	}
	  

		 	/*
			 @GET
			 @Path("/GetDocumentByName/{documentName}")
			 @Produces("application/json")
			 public Response getDocumentByName(@PathParam("documentName") String documentName) throws JSONException {
					jsonObject = new JSONObject();
					Document document = new Document();
					ArrayList<Document> listDocument = new ArrayList<Document>();
					String fileName = "data/A-Box_Cloud_Dokumente.owl";
					
					OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
					try {
						File file = new File(fileName);
						FileReader reader = new FileReader(file);
						model.read(reader,null);
						
						String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
								+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
								+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
								+ " SELECT ?Name ?Speicherort ?Erstellungsdatum ?Dokument_hat_Verfasser ?Dokument_gehoert_zu_Projekt"
								+ " WHERE  { ?Dokument foaf:Name ?Name ."
								+ "			 ?Dokument foaf:Speicherort ?Speicherort . "
								+ "			 ?Dokument foaf:Erstellungsdatum ?Erstellungsdatum . "
								+ "			 ?Dokument foaf:Dokument_hat_Verfasser ?Dokument_hat_Verfasser . "
								+ "			 ?Dokument foaf:Dokument_gehoert_zu_Projekt ?Dokument_gehoert_zu_Projekt . "
								+ "			 FILTER regex(?Name,'"+documentName+"')}";
									
						Query query = QueryFactory.create(sparQuery);
						QueryExecution qe = QueryExecutionFactory.create(query, model);
						ResultSet results = qe.execSelect();
						List var = results.getResultVars();
						
						while (results.hasNext()){
							QuerySolution qs = results.nextSolution();
							for(int i=0; i<var.size();i++){
								String va = var.get(i).toString();
								RDFNode node = qs.get(va);
								if(i==0){
									document.setDocumentName(node.toString());
								} else if(i==1){
									document.setDocumentPath(node.toString());
								} else if(i==2){
									document.setCreationDate(node.toString().substring(0, 19));
								} else if(i==3){
									document.setCreatedBy(getAttributeOfGraph(node.toString()));
								} else if(i==4){
									document.setRelatedProject(getAttributeOfGraph(node.toString()));
								}
								System.out.println(node.toString());
							}
						}
						MyOutputStream myOutput = new MyOutputStream();
						ResultSetFormatter.out(myOutput, results, query);
						
					} catch(Exception e){
						e.printStackTrace();
					}
					listDocument.add(document);
					jsonObject.put("data", listDocument);
					return Response.status(200).entity(jsonObject.toString()).build();
			}*/
			 
			 
		public String getAttributeOfGraph(String URI){
			Model schema = FileManager.get().loadModel("data/Cloud_Dokumente.owl");
			Model data = FileManager.get().loadModel("data/A-Box_Cloud_Dokumente.owl");
			Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
			reasoner = reasoner.bindSchema(schema);
			InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
			Resource nForce = infmodel.getResource(URI);
			return printStatements(infmodel, nForce, null, null);
		}
		
		public String printStatements(Model m, Resource s, Property p, Resource o) {
		    for (StmtIterator i = m.listStatements(s,p,o); i.hasNext(); ) {
		        Statement stmt = i.nextStatement();
		        if(stmt.getPredicate().getLocalName().toString().equals("Name")){
		        	 object=stmt.getObject().toString();
		        	 break;
		        }
		    }
		    return object;
		}
		
		

		  /**
		   * 
		   * @return Response
		   * @throws JSONException
		   */
			/* @GET
			 @Path("/GetAllKeywords")
			 @Produces("application/json")
			 public Response getAllKeywords() throws JSONException {
					jsonObject = new JSONObject();
					Document document = new Document();
					ArrayList<Document> listDocument = new ArrayList<Document>();
					ArrayList<String> listKeywords = new ArrayList<String>();
					
					OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
					try {
						File file = new File(fileName);
						FileReader reader = new FileReader(file);
						model.read(reader,null);
						
						String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
								+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
								+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
								+ " SELECT DISTINCT ?Schlagwort "
								+ " WHERE  { ?Dokument foaf:Schlagwort ?Schlagwort }";
						
						Query query = QueryFactory.create(sparQuery);
						QueryExecution qe = QueryExecutionFactory.create(query, model);
						ResultSet results = qe.execSelect();
						List var = results.getResultVars();
						
						while (results.hasNext()){
							QuerySolution qs = results.nextSolution();
							for(int i=0; i<var.size();i++){
								String va = var.get(i).toString();
								RDFNode node = qs.get(va);
								listKeywords.add(node.toString());
								System.out.println(node.toString());
							}
						}
						document.setKeyword(listKeywords);
						listDocument.add(document);
						MyOutputStream myOutput = new MyOutputStream();
						ResultSetFormatter.out(myOutput, results, query);
						
					} catch(Exception e){
						e.printStackTrace();
					}
					
					
					jsonObject.put("data", listDocument);
					return Response.status(200).entity(jsonObject.toString()).build();
			}*/
			 
}
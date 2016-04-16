package jena_test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

public class Szenario1 {
	
	public static void main(String[] args) {
		String fileName = "data/A-Box_Cloud_Dokumente.owl";
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		try {
			File file = new File(fileName);
			FileInputStream reader = new FileInputStream(file);
			model.read(reader,null);
			
			/*Aulesen von Speicherort aller Dateien zu einem bestimmtem Datum*/
			/*String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
					+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
					+ " SELECT ?Name ?Speicherort ?Erstellungsdatum ?Dokument_hat_Verfasser ?Dokument_gehoert_zu_Projekt"
					+ " WHERE  { ?Dokument foaf:Name ?Name ."
					+ "			 ?Dokument foaf:Speicherort ?Speicherort . "
					+ "			 ?Dokument foaf:Erstellungsdatum ?Erstellungsdatum . "
					+ "			 ?Dokument foaf:Dokument_hat_Verfasser ?Dokument_hat_Verfasser . "
					+ "			 ?Dokument foaf:Dokument_gehoert_zu_Projekt ?Dokument_gehoert_zu_Projekt . "
					+ "			 FILTER regex(?Name,'Abschlussbericht_HighNet_19-01-2016')}";*/
			
			String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
					+ "SELECT  ?Name"
					+ "WHERE   { <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/A-BOX_Cloud_Dokumente#Lisa_Maier> foaf:Name ?Name }";
			
			Query query = QueryFactory.create(sparQuery);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			List var = results.getResultVars();
			
			while (results.hasNext()){
				QuerySolution qs = results.nextSolution();
				for(int i=0; i<var.size();i++){
					String va = var.get(i).toString();
					RDFNode node = qs.get(va);
					System.out.println(node.toString());
				}
			}
			MyOutputStream myOutput = new MyOutputStream();
			ResultSetFormatter.out(myOutput, results, query);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
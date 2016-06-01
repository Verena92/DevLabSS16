package controller;

import java.util.List;
import java.util.UUID;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;


/**
 * Example connection to Fuseki. For this to work, you need to start a local
 * Fuseki server like this: ./fuseki-server --update --mem /ds
 */
public class fUS {
    /** A template for creating a nice SPARUL query */
    private static final String UPDATE_TEMPLATE = 
            "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
            + "DELETE DATA"
            + "{ <http://example/87495123-87be-471a-aeee-e30893e41c379>    dc:title    \"A new book\" ;"
            + "                         dc:creator  \"A.N.Other\" ." + "}   ";
 
    public static void main(String[] args) {
        //Add a new book to the collection
        String id = UUID.randomUUID().toString();
        System.out.println(String.format("Adding %s", id));
        UpdateProcessor upp = UpdateExecutionFactory.createRemote(
                UpdateFactory.create(String.format(UPDATE_TEMPLATE, id)), 
                "http://localhost:3030/ds/update");
        upp.execute();
        //Query the collection, dump output
        QueryExecution qe = QueryExecutionFactory.sparqlService(
                "http://localhost:3030/ds/query", "SELECT * WHERE {?x ?r ?y}");
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
        
        ResultSetFormatter.out(System.out, results);
        qe.close();
    }
 
}
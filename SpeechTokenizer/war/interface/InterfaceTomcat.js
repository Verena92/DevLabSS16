/**
 * Diese Klasse erhält die GET Methode auf den eigenen Server.
 * Die Get-Methode wird alle 5 Sekunden ausgeführt.
 * @author Verena Hofmann, Benjamin Mateja
 */

function doPoll(){
	$.ajax({ type:'GET', 
		url: 'https://146.148.67.230/de.hdm.speechtomcat/rest/GetDocuments',  
		contentType: 'application/json',
		dataType: 'json',

		//Fehler loggen 
	    error: function(){
	        console.log("Error");
	    },

		//Bei erfolgreichem Request Objekt in der Console ausgeben und GetResults aufrufen
	    success: function(responsedata){
	    	console.log("success");
	    	GetResults(responsedata);
	    	console.log(responsedata);
	    	/**
		     * setInterval ruft die Methode <code>doPoll()</code> nach 5000ms erneut auf.
		     */	    
	    	setTimeout(doPoll,5000);
	    	} 
	    
	});
}
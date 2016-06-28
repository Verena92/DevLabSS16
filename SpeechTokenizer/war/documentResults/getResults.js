/**
 * Autor: Verena Hofmann
 */


function getResults(){
//Asynchroner Post Request auf die Event Gruppe
$.ajax({ type:'GET', 
	url: 'https://146.148.67.230/de.hdm.speechtomcat/rest/GetDocuments/',  
	contentType: 'application/json',
	dataType: 'json',

	//Fehler loggen 
    error: function( ){
        console.log("Error");
    },

	//Bei erfolgreichem Request Objekt in der Console ausgeben
    success: function(responsedata){
    	console.log("success");
    	console.log(reponsedata);
    		
    	}
});

}





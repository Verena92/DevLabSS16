/**
 * Autor Mona Brunner
 */


function SendTokenToEvent(TokenNew){
//Asynchroner Post Request auf die Event Gruppe
$.ajax({ type:'POST', 
	url: 'http:130.211.110.54/EventbaseIntegration-5/rest/events/insert',  
	data: JSON.stringify(TokenNew),
	contentType: 'application/json',
	datatyp: 'json',

	//Fehler loggen 
    error: function( ){
        console.log("Error");
    },

	//Bei erfolgreichem Request Objekt in der Console ausgeben
    success: function(responsedata){
    	console.log("success")
        // hier response_data 
  

    	    //    new_keys = response_data["new_keys"]
    		
    	}
});

}


/*$.ajax({ method: "POST", url: "IP-Adresse-zur-Schnittstelle/nameSchnittstelle", data: { name: "John", location: "Boston" }

    error: function( ){

        console.log("Error");

    },

    success: function( response_data ){

        // hier response_data 
  

    	        new_keys = response_data["new_keys"]
    	}
});*/


/**
 * Hier wird der Token zur eventbasierten Integration gesendet
 * @author Mona Brunner
 */

/**
 *  Ajax Aufruf zur EventGruppe. Das stringify macht aus dem Objekt ein JSON.
 *  Über die URL wird die Schnittstelle aufgerufen.
 *  contentType und dataType signalisieren JSON.
 */
function SendTokenToEvent(TokenNew){
//Asynchroner Post Request auf die Event Gruppe

	$.ajax({ type:'POST', 
	url: 'https://130.211.110.54/EventbaseIntegration-5/rest/events/insert',  
	data: JSON.stringify(TokenNew),
	contentType: 'application/json',
	datatyp: 'json',

	//Fehler loggen 
    error: function( ){
        console.log("Error from event");
    },

	//Bei erfolgreichem Request Objekt in der Console ausgeben
    success: function(responsedata){
    	console.log("success from event")    		
    	}
});

}





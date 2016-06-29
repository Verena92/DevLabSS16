/**
 * In dieser Klasse wird die DokumentenRepräsentationsGruppe nach Informationen zu den Token gefragt.
 * Diese werden anschließend im Token abgespeichert.
 * @author Maren
 */

//Funktionierende POST Methode mit JQuery auf RepDok
/**
 * Die Function <code>GetKeywordInformation</code> holt die Informationen von der DokRepGruppe.
 * @param TokenNew 
 */
function GetKeywordInformation(TokenNew){
	 
	var keyword = TokenNew.keyword;
	
	/**
	 * Das Objekt queryWords beinhaltet das gesagte Keyword und das davor/danacha gesagte Wort. Außerdem beinhaltet es die UserId.
	 */
	var queryWords = new Object();
	if (TokenNew.previousKeyword == undefined) {
		queryWords.previousKeyword = null;
	} else {
		queryWords.previousKeyword = TokenNew.previousKeyword;
	}
	queryWords.keyword = TokenNew.keyword;
	if (TokenNew.nextKeyword == undefined) {
		queryWords.nextKeyword = null;
	} else {
		queryWords.nextKeyword = TokenNew.nextKeyword;
	}
	queryWords.createdByUserId = TokenNew.createdByUserId;

//Asynchroner POST Request auf die Schnittstelle der RepDok Gruppe
	/**
	 * Ajax Aufruf zur DokRepGruppe. Dafür wird das queryWord Objekt als JSON übergeben.
	 * Über die URL wird die Schnittstelle aufgerufen.
	 * Data übergibt das queryWord als JSON.
	 * contentType und dataType signalisieren JSON.
	 */
	  $.ajax({type:'POST', 
		  url:'https://104.154.103.216/document/rest/GetWordinformation',
		  data: JSON.stringify(queryWords),
		  contentType: 'application/json',
		  dataType: 'json',
		 
		//Fehler loggen 
		error: function(){
	    	console.log("not found");
		},
		//Bei erfolgreichem Request Objekt in der Console ausgeben
		success: function(responseData){
	    	
			delete TokenNew.previousKeyword;
			delete TokenNew.nextKeyword;
			
			//reicht keyword mit gefundenen informationen an
			TokenNew.keywordInformation = responseData;
						
			//ruft die funtkion auf, um den Token an die Event Gruppe zu enden
			SendTokenToEvent(TokenNew);		
		}
			
	    	
	})
			
		
}
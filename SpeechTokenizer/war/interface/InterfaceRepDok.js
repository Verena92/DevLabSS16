/**
 * Autor: Maren
 */


//Funktionierende POST Methode mit JQuery auf RepDok

function GetKeywordInformation(TokenNew){
	
	//keyword wird noch ersetzt durch die komplexere Objekt Abfrage previousKeyword, keyword und nextKeyword. 
	var keyword = TokenNew.keyword;
	
	/**
	 * size, sizeInside, i und j werden nur f��r die komplexere, alte Frage ben��tigt. bitte noch nicht l��schen.
	 */
	//var size;
	//var sizeInside;
	//var i;
	//var j;
	
	var queryWords = new Object();
	if (TokenNew.previousKeyword == undefined) {
		queryWords.previousKeyword = "";
	} else {
		queryWords.previousKeyword = TokenNew.previousKeyword;
	}
	queryWords.keyword = TokenNew.keyword;
	if (TokenNew.nextKeyword == undefined) {
		queryWords.nextKeyword = "";
	} else {
		queryWords.nextKeyword = TokenNew.nextKeyword;
	}
	queryWords.createdByUserID = TokenNew.createdByUserId;
	
	console.log(queryWords);

//Asynchroner POST Request auf die Schnittstelle der RepDok Gruppe
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
	    	
	    	//console.log("Success",responseData);
	    	
			delete TokenNew.previousKeyword;
			delete TokenNew.nextKeyword;
			TokenNew.keywordInformation = responseData;
			
			console.log(TokenNew);
			
			SendTokenToEvent(TokenNew);
				
			Stringify(TokenNew);
				
		}
			
	    	
	}
			
		
}
	  
	  
	  /*
	  //Funktionierende  GET Methode ohne JQuery
	  var request = new XMLHttpRequest();
	  var keyword = "Haruki";
	  request.open('GET', "http://104.197.87.226:8080/document/rest/GetWordinformation/"+ keyword, true);
	  request.setRequestHeader("Content-type", "application/json");
	  request.send();
	  request.addEventListener("readystatechange", processRequest, false);
	  
	  request.onreadystatechange = processRequest;
	  
	  function processRequest(e) {
		  if (request.readyState == 4 && request.status == 200) {
			        //var response = JSON.parse(request.responseText);
			        console.log("Sucess",JSON.parse(request.responseText));
		    }
	  };*/
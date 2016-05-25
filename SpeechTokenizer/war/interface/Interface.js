/**
 * Autor: Maren
 */


//Funktionierende GET Methode mit JQuery

//ToDO: Anstelle von des festen Keyword muss hier das Keyword aus dem PreToken übergeben werden TokenNew.keyword
//$.getScript("../tokenization/Token.js", function (){
	//Token();
	//var keyword = TokenNew.keyword;	
//});

var keyword = "Lisa";	

//Asynchroner Get Request auf die Schnittstelle der RepDok Gruppe
	  $.ajax({type:'GET', 
		  url:'http://104.197.87.226:8080/document/rest/GetWordinformation/'+keyword,
		  dataType: 'json',
		 
		//Fehler loggen 
		error: function(){
	    	console.log("Error");
		},
		//Bei erfolgreichem Request Objekt in der Console ausgeben
		success: function(responseData){
	    	//data = responseData["data"];
	    	console.log("Success",responseData);
	    	
		}
	  }); 
	  
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
	  };
	 
	  
	  
	  function UpdateToken(){ //ToDo
		  
	  };*/
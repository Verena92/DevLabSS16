/**
 * Autor: Maren
 */


//Funktionierende GET Methode mit JQuery

//ToDO: Anstelle von des festen Keyword muss hier das Keyword aus dem PreToken übergeben werden TokenNew.keyword
//$.getScript("../tokenization/Token.js", function (){
	//Token();
	//var keyword = TokenNew.keyword;	
//});

function GetKeywordInformation(TokenNew){
	
	var keyword = TokenNew.keyword;
	var size;
	var sizeInside;
	var i;

//Asynchroner Get Request auf die Schnittstelle der RepDok Gruppe
	  $.ajax({type:'GET', 
		  url:'http://104.197.87.226:8080/document/rest/GetWordinformation/'+keyword,
		  dataType: 'jsonp',
		  //added crossDomain and contentType.
		  //Also changed data Typ from json to jsonp
		  crossDomain: true,
		  contentType: "application/json; charset=utf-8",
		  
		 
		//Fehler loggen 
		error: function(){
	    	console.log("Error");
		},
		//Bei erfolgreichem Request Objekt in der Console ausgebenc
		success: function(responseData){
	    	//data = responseData["data"];
	    	//console.log("Success",responseData);
	    	size = Object.keys(responseData.data).length;
	    	
	    	if (size == 0) {
	    		//console.log(TokenNew);
				Stringify(TokenNew);
			} else {
				//console.log(responseData);
				//console.log(responseData.data[0].className);
				
				TokenNew.keywordClassName = responseData.data[0].className;
				TokenNew.keywordDataType = responseData.data[0].dataType;
				TokenNew.keywordValueType = responseData.data[0].valueType;
								
				sizeInside = Object.keys(responseData.data[0].objectRelation).length;
				
				TokenNew.keywordInformation = {
					
				}
				
				for (i = 0; i < sizeInside; i++){
					TokenNew.keywordInformation[responseData.data[0].objectRelation[i].type] = responseData.data[0].objectRelation[i].value;										
				}
				
				//console.log(TokenNew);
				
				Stringify(TokenNew);
				
			}
			
	    	
		}
			
		
	  });
	  
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
	  };
	 
	  
	  
	  function UpdateToken(){ //ToDo
		  
	  };*/
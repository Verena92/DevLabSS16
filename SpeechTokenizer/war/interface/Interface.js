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
	var j;

//Asynchroner Get Request auf die Schnittstelle der RepDok Gruppe
	  $.ajax({type:'GET', 
		  url:'https://104.155.140.18/document/rest/GetWordinformation/Lisa',
		  dataType: 'json',
		 
		//Fehler loggen 
		error: function(){
	    	console.log("Error");
		},
		//Bei erfolgreichem Request Objekt in der Console ausgeben
		success: function(responseData){
	    	//data = responseData["data"];
	    	//console.log("Success",responseData);
	    	size = Object.keys(responseData.data).length;
	    	//console.log(size);
	    			
	    	if (size == 0) {
	    		//console.log(TokenNew);
				Stringify(TokenNew);
			} else {
				//console.log(responseData);
				//console.log(responseData.data[0].className);
				
				for (i = 0; i < size; i++) {
					
					var sizestring = String(i+1);
					var keywordattribute = sizestring;
					
						TokenNew[keywordattribute] = {
					}
				
							TokenNew[keywordattribute].keywordClassName = responseData.data[i].className,
							TokenNew[keywordattribute].keywordDataType = responseData.data[i].dataType,
							TokenNew[keywordattribute].keywordValueType = responseData.data[i].valueType,
							TokenNew[keywordattribute].keywordValue = responseData.data[i].value,
			
							sizeInside = Object.keys(responseData.data[i].objectRelation).length;
									
							TokenNew[keywordattribute].keywordRelation = {		
							}
				
							for (j = 0; j < sizeInside; j++){
								TokenNew[keywordattribute].keywordRelation[responseData.data[i].objectRelation[j].type] = responseData.data[i].objectRelation[j].value;										
							}
							TokenNew.keywordInformation[i] = TokenNew[keywordattribute];
							delete TokenNew[keywordattribute];
				}
				console.log(TokenNew);
				
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
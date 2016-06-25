/**
 * Autor: Maren
 */


//Funktionierende POST Methode mit JQuery auf RepDok

function GetKeywordInformation(TokenNew){
	
	//keyword wird noch ersetzt durch die komplexere Objekt Abfrage previousKeyword, keyword und nextKeyword. 
	var keyword = TokenNew.keyword;
	
	/**
	 * size, sizeInside, i und j werden nur für die komplexere, alte Frage benötigt. bitte noch nicht löschen.
	 */
	//var size;
	//var sizeInside;
	//var i;
	//var j;
	
	var queryWords = new Object();
	queryWords.previousKeyword = TokenNew.previousKeyword;
	queryWords.keyword = TokenNew.keyword;
	queryWords.nextKeyword = TokenNew.nextKeyword;
	
	//console.log(queryWords);

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
	    	//data = responseData["data"];
	    	//console.log("Success",responseData);
	    	size = Object.keys(responseData.data).length;
	    	//console.log(size);
	    			
	    	if (size == 0) {
	    		//console.log(TokenNew);
				//Stringify(TokenNew);
	    		delete TokenNew;
			} else {
				//console.log(responseData);
				//console.log(responseData.data[0].className);
				
				delete TokenNew.previousKeyword;
				delete TokenNew.nextKeyword;
				//auskommentiert für testen POST Schnittstelle
				//TokenNew.keywordInformation = responseData.data;
				console.log(TokenNew);
				SendTokenToEvent(TokenNew);
				
				/**for (i = 0; i < size; i++) {
					
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
				*/
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
	  };*/
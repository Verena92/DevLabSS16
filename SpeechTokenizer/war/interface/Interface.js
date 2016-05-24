/**
 * 
 */
/*
//Funktionierende statische GET Methode für "Lisa" mit JQuery
	  $.ajax({type:'GET', 
		  url:'http://104.197.87.226:8080/document/rest/GetWordinformation/Lisa',
		  dataType: 'json',
		 
		 
		error: function(){
	    	console.log("Error");
		},
		success: function(responseData){
	    	//data = responseData["data"];
	    	console.log("Success",responseData);
	    	
		}
	  }); */
	  
	  //Funktionierende statische GET Methode für "Haruki" ohne JQuery
	  var request = new XMLHttpRequest();
	  request.open('GET', "http://104.197.87.226:8080/document/rest/GetWordinformation/Haruki", true);
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
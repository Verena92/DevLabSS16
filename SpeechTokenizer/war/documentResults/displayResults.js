/**
 * In dieser Klasse werden die Dokumente angezeigt.
 * Diese Klasse wird von der Funktion <code>doPoll()</code> aufgerufen.
 * Autor: Benjamin Mateja, Verena Hofmann
 */

var displayText = "<ul>";

function GetResults(responsedata){
	//create documentarray for documents
	var DocumentTempArray = new Array();
	
	//Variable i für die Schleife
	var i;
	var b;
	var googlePath = "https://drive.google.com/openid=";
	var userHangoutId = getHangoutId();
	//var documentObj = new Object();

	
	/**
	 *code here: if document.hangoutsid = user.hangoutsid then push into document array, else not
	 */
	
	//var documentObj = JSON.parse(responsedata);
	//DocumentTempArray.push(documentObj);
	
	console.log(responsedata);
	
	for (b = 0; b <responsedata.length; b++){
		
		if (responsedata[i].documents.hangoutsId == userHangoutId){
			responsedata[i].documents.show = googlePath + responsedata[i].drivePath;
			DocumentTempArray.push(responsedata[i]);
		} else {
		}
	}
	
	if (DocumentTempArray.length == 0){
		displayText = "";
		displayText = "Bisher wurden leider keine Dokumente gefunden ..."
		//displayText += "<li>" + "Bisher wurden keine Dokumente gefunden ..." + "</li>";
		//displayText += "</ul>";
		displayText = capitalize(displayText);
		showdocument.innerHTML = linebreak(displayText);
	} else {
		for (i = 0; i < DocumentTempArray.length; i++){
			displayText += "<li>" + DocumentTempArray[i].show + "</li>";
		}
		displayText += "</ul>";
		displayText = capitalize(displayText);
		showdocument.innerHTML = linebreak(displayText);
	}
	
	
	console.log(DocumentTempArray.length);
	
	
	
	//push document in documentarray
	//Hier die einzelnen Objekte reinpushen
	//DocumentArray.push(document1);
	//DocumentArray.push(document2);
	
	//console.log(DocumentArray);
	
	
	/*
	if (DocumentArray.length == 0){
		displayText += "<li>" + "Bisher wurden keine Dokumente gefunden ..." + "</li>";
		displayText += "</ul>";
		displayText = capitalize(displayText);
		showdocument.innerHTML = linebreak(displayText);
	} else {
		for (i = 0; i < DocumentArray.length; i++){
			displayText += "<li>" + DocumentArray[i].window + "</li>";
		}
		displayText += "</ul>";
		displayText = capitalize(displayText);
		showdocument.innerHTML = linebreak(displayText);
	}*/
	
}

/* Durch linebreak und capatilize wird Text eingeblendet */

function linebreak(displayText) {
	return displayText;
}

function capitalize(displayText) {
	return displayText;
}

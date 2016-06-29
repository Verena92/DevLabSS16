/**
 * In dieser Klasse werden die Dokumente angezeigt.
 * Diese Klasse wird von der Funktion <code>doPoll()</code> aufgerufen.
 * Autor: Benjamin Mateja, Verena Hofmann
 */

var displayText = "<ul>";

function GetResults(responsedata){
	//create documentarray for documents
	var DocumentArray = new Array();
	var DocumentTempArray = new Array();
	
	//Variable i für die Schleife
	var i;
	var b;
	var googlePath = "https://drive.google.com/openid=";
	var documentObj = new Object();

	
	//create first samle document
	var document1 = new Object();
	document1.id = 1;
	document1.UserId = "123@456";
	document1.hangoutsId = "abcdef";
	document1.title = "BMW Projektbericht 2016";
	document1.path = "https://www.google.de";
	document1.show = document1.title.link(document1.path);
	document1.window = window.open("https://www.google.de";);
	
	//create second sample document
	var document2 = new Object();
	document2.id = 2;
	document2.UserId = "123@456";
	document2.hangoutsId = "abcdef";
	document2.title = "Mercedes Projektbericht 2016";
	document2.path = "https://docs.google.com/document/d/18CkOl89zv9zT9VKI0xZJpO_Gl-g1HCXYdeyFwO7z5vA/edit?usp=drive_web";
	document2.show = document2.title.link(document2.path);
	document2.window = window.open("https://docs.google.com/document/d/18CkOl89zv9zT9VKI0xZJpO_Gl-g1HCXYdeyFwO7z5vA/edit?usp=drive_web");
	
	console.log(document2.show);
	
	/**
	 *code here: if document.hangoutsid = user.hangoutsid then push into document array, else not
	 */
	
	//var documentObj = JSON.parse(responsedata);
	//DocumentTempArray.push(documentObj);
	/*
	for (b = 0; b <DocumentTempArray.length; b++){
		
	}*/
	
	console.log(responsedata);
	
	//push document in documentarray
	//Hier die einzelnen Objekte reinpushen
	DocumentArray.push(document1);
	DocumentArray.push(document2);
	
	//console.log(DocumentArray);
	console.log(DocumentArray.length);
	
	
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
	}
	
}

/* Durch linebreak und capatilize wird Text eingeblendet */

function linebreak(displayText) {
	return displayText;
}

function capitalize(displayText) {
	return displayText;
}

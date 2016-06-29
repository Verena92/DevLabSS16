/**
 * In dieser Klasse werden die Dokumente angezeigt.
 * Diese Klasse wird von der Funktion <code>doPoll()</code> aufgerufen.
 * Autor: Benjamin Mateja, Verena Hofmann
 */

var displayText = "<ul>";

function GetResults(){
	//create documentarray for documents
	var DocumentArray = new Array();
	
	//Variable i für die Schleife
	var i;

	//create first samle document
	var document1 = new Object();
	document1.id = 1;
	document1.UserId = "123@456";
	document1.hangoutsId = "abcdef";
	document1.title = "BMW Projektbericht 2016";
	document1.path = "www.google.de";
	document1.show = document1.title.link(document1.path);
	
	//create second sample document
	var document2 = new Object();
	document2.id = 2;
	document2.UserId = "123@456";
	document2.hangoutsId = "abcdef";
	document2.title = "Mercedes Projektbericht 2016";
	document2.path = "www.spox.com";
	document2.show = document2.title.link(document2.path);
	
	/**
	 *code here: if document.hangoutsid = user.hangoutsid then push into document array, else not
	 */
	
	
	
	//push document in documentarray
	//Hier die einzelnen Objekte reinpushen
	//DocumentArray.push(document1);
	//DocumentArray.push(document2);
	
	if (Document.Array.length == 0){
		"Bisher wurden keine Dokumente gefunden"
	} else {
		for (i = 0; i < DocumentArray.length; i++){
			displayText += "<li>" + DocumentArray[i].show + "</li>";
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

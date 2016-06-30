/**
 * In dieser Klasse werden die Dokumente angezeigt.
 * Diese Klasse wird von der Funktion <code>doPoll()</code> aufgerufen.
 * @author Benjamin Mateja, Verena Hofmann
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
	var userUniqueId = getUniqueId();
	//var documentObj = new Object();
	
	//var documentObj = JSON.parse(responsedata);
	//DocumentTempArray.push(documentObj);
	
	//console.log(responsedata);
	
	for (b = 0; b <responsedata.documents.length; b++){
		
		//userHangoutId
		//documents.show
		//documents.drivePath
		
		//console.log(responsedata.documents[4]);
		
		/**
		 * Hier wird überprüft, ob das Dokument zur Hangouts Sitzung passt.
		 * Ist dies der Fall, wird das Objekt erweitert und in das DocumentTempArray gepushed.
		 */
		if (responsedata.documents[b].userId == userUniqueId){
			responsedata.documents[b].stringlink = googlePath + responsedata.documents[b].drivePath;
			var documentName = responsedata.documents[b].documentName
			responsedata.documents[b].show = documentName.link(responsedata.documents[b].stringlink);
			DocumentTempArray.push(responsedata.documents[b]);
		} else {
		}
	}
	/**
	 * Das DocumentTempArray zeigt wenn es leer ist einen DummyText an.
	 * Ist das Array befüllt, werden die Dokzumente angezeigt.
	 */
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

	
}

/* Durch linebreak und capatilize wird Text eingeblendet */

function linebreak(displayText) {
	return displayText;
}

function capitalize(displayText) {
	return displayText;
}

/**
 * In dieser Klasse wird der Token erstellt.
 * @author Benjamin Mateja, Mona Brunner
 */

var TokenNew = new Object();
var TokenArray = new Array();
var oldIdentifier = 0;

function Token(){
	
	/**
	 * Call Tokenize JS, um mit Tokenisierung zu starten.
	 */
	Tokenize();
	
	//vorname = getUserData('firstName');
	//console.log(vorname);
	
	/**
	 * Hier wird das Datum für den Timestamp erstellt.
	 */
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth();
	var modifiedMonth = month + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	var dateCombined = year + " " + modifiedMonth + " " + day + " " + hour + " " + minute + " " + second;
		
	/**
	 * Erstellen des Tokens mit verschiedenen Eigenschaften wie den keywords oder den User-Daten von Hangouts.
	 */
	for (i = oldIdentifier; i < uncommonArr.length; i++){
		
		TokenNew = {
				//id: i,
				keyword: uncommonArr[i],
				previousKeyword: uncommonArr[i-1],
				nextKeyword: uncommonArr[i+1],
				timestamp: dateCombined,
				
				keywordInformation: [],
								
				//Geht nur in Hangouts
				createdByFirstName: getUserData('firstName'),
				createdByLastName: getUserData('lastName'),
				//UserId: getUserData('Id'),
				createdByUserId: getUniqueId(),
				hangoutsId: getHangoutId(),
								
		};
		
		//sample data for test event 
		var array = ["12", "1223"];
		var data = {projects:array, companies:array, products:array, employees:array};

		TokenNew.keywordInformation = data;

		TokenArray.push(TokenNew);
		//GetKeywordInformation ruft interface.js auf und reichert ggf. das Keyword an.
		GetKeywordInformation(TokenNew);
		
		//stringify ruft direkt JSON.js auf, und wandelt das objekt um, ohne vorher nach dem keyword zufragen.
		//Stringify();
		
		oldIdentifier = i+1;	
	}
	
	//console.log(TokenArray);
	
	//console.log(TokenArray[2]);

};
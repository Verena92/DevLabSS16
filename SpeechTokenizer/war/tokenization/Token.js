/**
 * 
 * Autor: Benjamin Mateja
 * 
 */

/**
 * This function handles Tokens.
 * Input Keywords / User Data -> Token 
 */

var TokenNew = new Object();
var TokenArray = new Array();
var oldIdentifier = 0;

function Token(){
	
	/**
	 * Call Tokenize JS
	 */
	Tokenize();
	
	//vorname = getUserData('firstName');
	//console.log(vorname);
	
	date = new Date();
	hour = date.getHours();
	minutes = date.getMinutes();
	seconds = date.getSeconds();
	dateCombined = hour + " " + minutes + " " + seconds;
		
	/**
	 * Create Tokens with the keywords
	 */
	for (i = oldIdentifier; i < uncommonArr.length; i++){
		
		TokenNew = {
				id: i,
				keyword: uncommonArr[i],
				timestamp: dateCombined,
				
				//Muss noch mit den Hangouts Sachen ersetzt werden. Geht nur im Localhost Mode logischerweise nicht ;)
				createdByFirstName: getUserData('firstName'),
				createdByLastName: getUserData('lastName'),
				createdByUserId: getUserData('id'),
				hangoutsId: getHangoutId(),
				
				keywordInformation: [],
				
		};
		
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
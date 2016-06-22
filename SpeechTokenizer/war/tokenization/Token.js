/**
 * 
 * Autor: Benjamin Mateja, Mona Brunner
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
	 * Create Tokens with the keywords
	 */
	for (i = oldIdentifier; i < uncommonArr.length; i++){
		
		TokenNew = {
				id: i,
				keyword: uncommonArr[i],
				previousKeyword: uncommonArr[i-1],
				nextKeyword: uncommonArr[i+1],
				timestamp: dateCombined,
				
				keywordInformation: [projects:"", companies:"", employees:"Jens Lindner", products:""],
				
				//Geht nur in Hangouts
				createdByFirstName: getUserData('firstName'),
				createdByLastName: getUserData('lastName'),
				createdByUserId: getUserData('id'),
				hangoutsId: getHangoutId(),
								
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
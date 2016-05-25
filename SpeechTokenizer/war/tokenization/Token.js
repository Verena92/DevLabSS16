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
				createdByFirstName:"Benjamin",
				createdByLastName:"Mateja",
		};
				
		//console.log(TokenNew);
		
		TokenArray.push(TokenNew);
		oldIdentifier = i+1;
		
		GetKeywordInformation();
		
		Stringify();
		
		//console.log(TokenNew);
		
	}
	
	//console.log(TokenArray);
	
	//console.log(TokenArray[2]);

};
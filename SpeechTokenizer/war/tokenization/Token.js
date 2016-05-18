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
		
	/**
	 * Create Tokens with the keywords
	 */
	for (i = oldIdentifier; i < uncommonArr.length; i++){
		TokenNew = {
				id: i,
				keyword: uncommonArr[i],
				createdByFirstName:"Benjamin",
				createdByLastName:"Mateja",
				profession:"Student",
				TokenInformation: function() {
					return this.createdByFirstName + " " + this.createdByLastName + " is a " +
					this.profession + ". Keyword: " +this.keyword + " with ID: " +this.id;
				}
		};
				
		console.log(TokenNew);
		
		TokenArray.push(TokenNew);
		
		oldIdentifier = i+1;
		
		
		
		
		
		//console.log(TokenNew);
		
	}
	
	//console.log(TokenArray);
	
	//console.log(TokenArray[2]);

};
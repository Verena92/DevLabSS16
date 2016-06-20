/**
 * Autor: Benjamin Mateja
 */

var displayText = "<ul>";

function GetDocuments(){
	
	//create documentarray for documents
	var DocumentArray = new Array();
	
	
	var i;
	
	//create first samle document
	var Document1 = new Object();
	Document1.id = 1;
	Document1.UserId = "123@456";
	Document1.hangoutsId = "abcdef";
	Document1.title = "BMW Projektbericht 2016";
	Document1.path = "www.google.de";
	Document1.show = Document1.title.link(Document1.path);
	
	//create second sample document
	var Document2 = new Object();
	Document2.id = 2;
	Document2.UserId = "123@456";
	Document2.hangoutsId = "abcdef";
	Document2.title = "Mercedes Projektbericht 2016";
	Document2.path = "www.spox.com";
	Document2.show = Document2.title.link(Document2.path);
	
	/**
	 *code here: if document.hangoutsid = user.hangoutsid then push into document array, else not
	 */
	
	//push document in documentarray
	DocumentArray.push(Document1);
	DocumentArray.push(Document2);
	

	
	for (i = 0; i < DocumentArray.length; i++){
		displayText += "<li>" + DocumentArray[i].show + "</li>";
	}
	displayText += "</ul>";
	
	
	
}
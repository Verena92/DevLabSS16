/**
 * Autor: Benjamin Mateja
 */

function GetDocuments(){
	
	//create documentarray for documents
	var DocumentArray = new Array();
	
	//create first samle document
	var Document = new Object();
	Document.id = 1;
	Document.UserId = "123@456";
	Document.hangoutsId = "abcdef";
	Document.title = "BMW Projektbericht 2016";
	Document.path = "www.google.de";
	Document.show = title.link(path);
	
	//create second sample document
	var Document2 = new Object();
	Document2.id = 2;
	Document2.UserId = "123@456";
	Document2.hangoutsId = "abcdef";
	Document2.title = "Mercedes Projektbericht 2016";
	Document2.path = "www.spox.com";
	Document2.show = title.link(path);
	
	/**
	 *code here: if document.hangoutsid = user.hangoutsid then push into document array, else not
	 */
	
	//push document in documentarray
	DocumentArray.push(Document);
	DocumentArray.push(Document2);
	
	var Results = document.show;
	
	console.log(DocumentArray);
	
}
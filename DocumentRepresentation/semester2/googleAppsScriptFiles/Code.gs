function time(){
    ScriptApp.newTrigger("createSpreadsheetEditTrigger")
   .timeBased()
   .everyMinutes(1)
   .create();   
}


function createSpreadsheetEditTrigger() {
  var files = DriveApp.getFoldersByName("Development Lab").next().getFoldersByName("AppsScriptProgramm").next().getFoldersByName("Excel").next().getFiles();
  
  while (files.hasNext()){
    file = files.next();  
    ScriptApp.newTrigger('onOpen')
      .forSpreadsheet(SpreadsheetApp.openById(file.getId()))
      .onOpen()
      .create();
  }
}

function onOpen() {
  SpreadsheetApp.getUi() 
      .createMenu('Metadata')
      .addItem('Open', 'openDialog')
      .addToUi();
}

function openDialog() {
  var html = HtmlService.createHtmlOutputFromFile('Index')
      .setSandboxMode(HtmlService.SandboxMode.IFRAME)
      .setWidth(900)
      .setHeight(400);
  SpreadsheetApp.getUi()
  .showModalDialog(html, 'Metadaten zum Dokument: '+ SpreadsheetApp.getActive().getName());
}

function getFileName() { 
  var fileName = SpreadsheetApp.getActive().getName();
  PropertiesService.getScriptProperties().setProperty('fileName', fileName);
  return fileName;
}

function getOwner() {
  return SpreadsheetApp.getActive().getOwner().getEmail();
}

function getURL() {
  var documentPath = SpreadsheetApp.getActive().getUrl();    
  PropertiesService.getScriptProperties().setProperty('documentPath', documentPath);
  return documentPath;
}

function getID() {
  var driveDocumentID = SpreadsheetApp.getActive().getId();
  PropertiesService.getScriptProperties().setProperty('driveDocumentID', driveDocumentID);
  return driveDocumentID;
}

function getDateCreated() {
 var files = DriveApp.getFilesByName(PropertiesService.getScriptProperties().getProperty('fileName'));
 while (files.hasNext()) {
   var file = files.next();
   var creationDate = file.getDateCreated();
   PropertiesService.getScriptProperties().setProperty('creationDate', creationDate);
   return ""+creationDate;
 }
}

function getLastUpdated() {
 var files = DriveApp.getFilesByName(PropertiesService.getScriptProperties().getProperty('fileName'));
 while (files.hasNext()) {
   var file = files.next();
   return ""+file.getLastUpdated();
 }
  
  var folders = DriveApp.getFolders();
 while (folders.hasNext()) {
   var folder = folders.next();
 }
}


function checkAvailabilityInOWL() {
 var driveDocumentID =  PropertiesService.getScriptProperties().getProperty('driveDocumentID');
 

  var response = UrlFetchApp.fetch("http://130.211.155.163/document/rest/GetDocumentByID/"+driveDocumentID);


  
  var params = JSON.parse(response.getContentText());

  PropertiesService.getScriptProperties().setProperty('params', params);
  return params;
}

/**
* 
* addDocumentMetadata
* 
* Diese Methode ist dafür zuständig die Dokumentenmetadaten dem Jena-Fuseki Server zu übermitteln, sodass diese in der ABox neu angelegt werden
* 
*/

function addDocumentMetadata(status, version, employeeURI, project, documentClass, listKeywords) {  
  
 
   var fileName = PropertiesService.getScriptProperties().getProperty('fileName');
   var documentPath = PropertiesService.getScriptProperties().getProperty('documentPath');
   var driveDocumentID =  PropertiesService.getScriptProperties().getProperty('driveDocumentID');
   var creationDate =  PropertiesService.getScriptProperties().getProperty('creationDate');  
   var formattedDate = Utilities.formatDate(new Date(creationDate), "CET", "yyyy-MM-dd'T'HH:mm:ss");
    
  
   var payload =
   {
     "name" : fileName,
     "driveID" : driveDocumentID,
     "status" : status,
     "keywords" : "'nummer1'",
     "drivePath" : documentPath,
     "version" : version,
     "creationDate" : formattedDate,
     "createdBy" : employeeURI,
     "project" : project,
     "type" : "Spreadsheet",
     "documentClass" : documentClass,
   };
  
   // Because payload is a JavaScript object, it will be interpreted as
   // an HTML form. (We do not need to specify contentType; it will
   // automatically default to either 'application/x-www-form-urlencoded'
   // or 'multipart/form-data')

   var options =
   {
     "method" : "post",
     "payload" : payload
   }
   
   var ausgabe = UrlFetchApp.fetch("http://130.211.155.163/document/rest/AddDocumentMetadata", options);
}


/**
* 
* editDocumentMetadata
* 
* Diese Methode ist dafür zuständig die bearbeiteten Metadaten dem Jena-Fuseki Server zu übermitteln
* 
*/

function editDocumentMetadata(newName, oldDocumentName, newStatus, oldStatus) {  
  var driveDocumentID =  PropertiesService.getScriptProperties().getProperty('driveDocumentID'); 
  var payload =
   {
     "driveID" : driveDocumentID,
     "oldName" : oldDocumentName,
     "newName" : newName,
     "newStatus" : newStatus,
     "oldStatus": oldStatus,
   };
  
   // Because payload is a JavaScript object, it will be interpreted as
   // an HTML form. (We do not need to specify contentType; it will
   // automatically default to either 'application/x-www-form-urlencoded'
   // or 'multipart/form-data')

   var options =
   {
     "method" : "post",
     "payload" : payload
   }
   
   var ausgabe = UrlFetchApp.fetch("http://130.211.155.163/document/rest/EditDocumentMetadata", options);
}


function doSomething() {
  var response = UrlFetchApp.fetch("http://130.211.155.163/document/rest/GetEmployeeByDriveUserID/drive");
  
  return ""+response;
  
}
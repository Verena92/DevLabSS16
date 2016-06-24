function createSpreadsheetEditTrigger() {
 
  var ss =  SpreadsheetApp.openById("1onKOGZFLOKReA1unsstajHHy6eBK-C6rn52i1Ra4A78");
  
  
  ScriptApp.newTrigger('onOpen')
      .forSpreadsheet(ss)
      .onOpen()
      .create();
  
  var si =  SpreadsheetApp.openById("158XmEV097o9QTHiK1j7UyN5fBNvbzYVICXZgHRv3UuA");
    ScriptApp.newTrigger('onOpen')
      .forSpreadsheet(si)
      .onOpen()
      .create();
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
      .setHeight(470);
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
}


function checkAvailabilityInOWL() {
  var driveDocumentID =  PropertiesService.getScriptProperties().getProperty('driveDocumentID');
  var response = UrlFetchApp.fetch("http://104.155.140.18/document/rest/GetDocumentByDriveID/"+driveDocumentID);
  
  var params = JSON.parse(response.getContentText());
    
  if(params.data.length>0.0){
    Logger.log(params.data);
    var Name = params.data[0];
    return Name;
  } else {
    return false;
  }
}


function addDocumentMetadata(project, status, documentType, listKeywords, version) {  
   var fileName = PropertiesService.getScriptProperties().getProperty('fileName');
   var documentPath = PropertiesService.getScriptProperties().getProperty('documentPath');
   var driveDocumentID =  PropertiesService.getScriptProperties().getProperty('driveDocumentID');
   var creationDate =  PropertiesService.getScriptProperties().getProperty('creationDate');  
   var formattedDate = Utilities.formatDate(new Date(creationDate), "CET", "yyyy-MM-dd'T'HH:mm:ss");
  
   
   var payload =
   {
     "name" : fileName,
     "documentType" : documentType,
     "status" : status,
     "documentPath" : documentPath,
     "driveDocumetID" : driveDocumentID,
     "keyword" : ""+listKeywords+"",
     "version" : version,
     "project" : project,
     "creationDate" : formattedDate,
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
   
   var ausgabe = UrlFetchApp.fetch("http://104.155.140.18/document/rest/AddDocumentMetadata", options);
}
package de.hdm.wim.events.documentrepresentation;

import javax.ws.rs.core.Response;

import de.hdm.wim.events.model.Company;
import de.hdm.wim.events.model.Document;
import de.hdm.wim.events.model.Employee;
import de.hdm.wim.events.model.Project;
import de.hdm.wim.events.restclient.BaseRestClient;
import de.hdm.wim.helper.PropertiesHelper;

/**
 * Class used to request further information about a given 'thing',
 * i.e. a project, product, company or employee.
 * Further to request a document regarding given things,
 * i.e. documents in relation to a combination of a project and an employee.
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class DocumentRepresentationRequester {
	
	/**
	 * 
	 * @param searchRequest
	 * @return
	 */
	public DocumentIDsWrapper getDocumentIDs(SearchRequest searchRequest) {
		System.out.println( "Send SearchRequest: " + searchRequest);
		BaseRestClient restClient = new BaseRestClient(PropertiesHelper.getProperties("url.document_representation"));
		Response result = restClient.doPostSearchRequest("GetDocumentMetadata", searchRequest); 
		DocumentIDsWrapper documentIDsWrapper = result.readEntity(DocumentIDsWrapper.class);
		System.out.println( "Received DocmentIDs: " + documentIDsWrapper);
		return documentIDsWrapper;
	}	
	
	/**
	 * 
	 * @return
	 */
	public DocumentClassesWrapper getDocumentClasses() {
		BaseRestClient restClient = new BaseRestClient(PropertiesHelper.getProperties("url.document_representation") + "GetDocumentClasses/");
		Response response = restClient.doGet("");
		DocumentClassesWrapper documentClassesWrapper = response.readEntity(DocumentClassesWrapper.class);
		System.out.println( "Received DocmentClasses: " + documentClassesWrapper);
		return documentClassesWrapper;
	}
	
	/**
	 * 
	 * @param companyID
	 * @return
	 */
	public Company getCompany(String companyID) {
		BaseRestClient restClient = new BaseRestClient(PropertiesHelper.getProperties("url.document_representation") + "GetCompanyByID/");
		Response response = restClient.doGet(companyID);
		Company company = response.readEntity(Company.class);
		System.out.println( "Received Company: " + company);
		return company;
	}
	/**
	 * 
	 * @param documentID
	 * @return
	 */
	public Document getDocument(String documentID) {
		BaseRestClient restClient = new BaseRestClient(PropertiesHelper.getProperties("url.document_representation") + "GetDocumentByID/");
		Response response = restClient.doGet(documentID);
		Document document = response.readEntity(Document.class);
		System.out.println( "Received Document: " + document);
		return document;
	}
	
	/**
	 * 
	 * @param projectID
	 * @return
	 */
	public Project getProject(String projectID) {
		BaseRestClient restClient = new BaseRestClient(PropertiesHelper.getProperties("url.document_representation") + "GetProjectByID/");
		Response response = restClient.doGet(projectID);
		Project project = response.readEntity(Project.class);
		System.out.println( "Received Project: " + project);
		return project;
	}
	
	/**
	 * 
	 * @param employeeID
	 * @return
	 */
	public Employee getEmployee(String employeeID) {
		BaseRestClient restClient = new BaseRestClient(PropertiesHelper.getProperties("url.document_representation") + "GetEmployeeByID/");
		Response response = restClient.doGet(employeeID);
		Employee employee = response.readEntity(Employee.class);
		System.out.println( "Received Employee: " + employee);
		return employee;
	}
}
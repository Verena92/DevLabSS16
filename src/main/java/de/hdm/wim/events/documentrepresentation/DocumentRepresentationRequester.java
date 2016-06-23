package de.hdm.wim.events.documentrepresentation;

import javax.ws.rs.core.Response;

import de.hdm.wim.events.TestDataProvider;
import de.hdm.wim.events.model.Company;
import de.hdm.wim.events.model.Document;
import de.hdm.wim.events.model.Employee;
import de.hdm.wim.events.model.Product;
import de.hdm.wim.events.model.Project;
import de.hdm.wim.events.restclient.BaseRestClient;

/**
 * Class used to request further infrmation about a given 'thing',
 * i.e. a project, product, company or employee.
 * Further to request a document regarding given things,
 * i.e. documents in relation to a combination of a project and an employee.
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class DocumentRepresentationRequester {

	public static final String DOCUMENT_REPRESENTATION_URL = "http://104.155.140.18/document/rest/";
	
	//FIXME: use reasonable SearchRequest and act upon it instead of calling the 'dummy' GET GetDocumentMetadata 
	public Document getDocument(SearchRequest searchRequest) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL);
		//Response result = restClient.doPostSearchRequest("document", searchRequest); //TODO: use correct path
		
		//Waldis Dummy Schnittstelle
//		Response result = restClient.doGet("GetDocumentMetadata");
//		Document document = result.readEntity(Document.class);
		
		// Super Dummy
		Document document = TestDataProvider.createDummyDocument(); //FIXME: use real call
		
		return document;
	}	
	
	/**
	 * 
	 * @param companyID
	 * @return
	 */
	public Company getCompany(String companyID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetCompanyByID/");
		Response response = restClient.doGet(companyID);
		Company company = response.readEntity(Company.class);
		return company;
	}
	
	/**
	 * 
	 * @param projectID
	 * @return
	 */
	public Project getProject(String projectID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetProjectByID/");
		Response response = restClient.doGet(projectID);
		Project project = response.readEntity(Project.class);
		return project;
	}
	
	/**
	 * 
	 * @param employeeID
	 * @return
	 */
	public Employee getEmployee(String employeeID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetEmployeeByID/");
		Response response = restClient.doGet(employeeID);
		Employee employee = response.readEntity(Employee.class);
		return employee;
	}
	
	/**
	 * 
	 * @param productID
	 * @return
	 */
	public Product getProduct(String productID) {
		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL + "GetProductByID/");
		Response response = restClient.doGet(productID);
		Product product = response.readEntity(Product.class);
		return product;
	}
}
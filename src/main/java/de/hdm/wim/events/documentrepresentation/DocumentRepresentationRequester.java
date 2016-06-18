package de.hdm.wim.events.documentrepresentation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import de.hdm.wim.events.model.Company;
import de.hdm.wim.events.restclient.BaseRestClient;

public class DocumentRepresentationRequester {

	public static final String DOCUMENT_REPRESENTATION_URL = "http://104.155.140.18/document/rest/";
	
	//FIXME: use reasonable SearchRequest and act upon it
	public List<String> getDocuments(SearchRequest searchRequest) {
//		BaseRestClient restClient = new BaseRestClient(DOCUMENT_REPRESENTATION_URL);
//		String result = restClient.doGet("test");
		List<String> list = new ArrayList<String>();
//		list.add(result);
		return list;
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
}
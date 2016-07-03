package de.hdm.wim.events.documentrepresentation;

import java.util.List;

/**
 * Wrapper for the related Documents (regarding a SearchRequest) (such as
 * 'D0001').
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class DocumentIDsWrapper {

	private List<String> documents;

	public DocumentIDsWrapper() {

	}

	public DocumentIDsWrapper(List<String> documents) {
		this.documents = documents;
	}

	public List<String> getDocuments() {
		return documents;
	}

	public void setDocuments(List<String> documents) {
		this.documents = documents;
	}

	@Override
	public String toString() {
		return "DocumentIDsWrapper [documents=" + documents + "]";
	}

}

package de.hdm.wim.events.documentrepresentation;

import java.util.List;

/**
 * Wrapper for the available DocumentTypes
 * (such as 'Kalkulation', 'Abschlussbericht" or 'Kostenplan').
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class DocumentClassesWrapper {
	
	private List<String> documentClasses;
	
	public DocumentClassesWrapper() {
		
	}
	
	public DocumentClassesWrapper(List<String> documentClasses) {
		this.documentClasses = documentClasses;
	}

	public List<String> getDocumentClasses() {
		return documentClasses;
	}

	public void setDocumentClasses(List<String> documentClasses) {
		this.documentClasses = documentClasses;
	}

	@Override
	public String toString() {
		return "DocumentTypesWrapper [documentClasses=" + documentClasses + "]";
	}
}

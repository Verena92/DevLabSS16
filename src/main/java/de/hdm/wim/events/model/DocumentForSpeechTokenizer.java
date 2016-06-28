package de.hdm.wim.events.model;

import de.hdm.wim.events.model.event.User;

/**
 * Representation of a Document as we send it to the SpeechTokenizer
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */
public class DocumentForSpeechTokenizer {
	private User user;
	private InternalDocument internalDoc;

	public DocumentForSpeechTokenizer() {
		
	}
	
	public DocumentForSpeechTokenizer( User user, Document document) {
		this.user = user;
		this.internalDoc = new InternalDocument(document);
	}

	@Override
	public String toString() {
		return "DocumentForSpeechTokenizer [userId=" + user.getGoogle_id() + ", hangoutsId=" + user.getHangouts_id() + ", documentName=" + internalDoc.getDocumentName() + ", drivePath=" + internalDoc.getDrivePath() + "]";
	}

	public InternalDocument getInternalDoc() {
		return internalDoc;
	}

	public void setInternalDoc(InternalDocument internalDoc) {
		this.internalDoc = internalDoc;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
}
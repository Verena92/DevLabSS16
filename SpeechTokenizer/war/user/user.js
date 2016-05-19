/*
 * Autor: Benjamin Mecheels
 */

function getUserData(userData){
	this.userData=userData;
	var data='UserDataOutput';
	
	// Get object from Hangouts-API
	var localParticipant=gapi.hangout.getLocalParticipant();
	
	if(userData){
		if(userData=='name'){
			data=JSON.stringify(localParticipant.person.displayName);
		}else{
			data=JSON.stringify(localParticipant[userData]);		
		}
	}else{
		console.log('Falsches Attribut! Siehe: https://developers.google.com/+/hangouts/api/gapi.hangout.html#classes-in-gapihangout');
	}
	
	return data;
}
/*
 * Autor: Benjamin Mecheels
 */

function getUserData(userData){
	this.userData=userData;
	var data='UserDataOutput';
	
	// Get object from Hangouts-API
	var localParticipant=gapi.hangout.getLocalParticipant();
	
	if(userData){
		
		if(userData=='firstName'||userData=='lastName'){
			var name=JSON.stringify(localParticipant.person.displayName);
			name=name.replace(/\"/g,'');
			var nameArray=name.split(/\s+/);
			if(userData=='firstName'){
				data=nameArray[0];
			}else if(userData=='lastName'){
				data=nameArray[1];
			}
		}else{
			data=JSON.stringify(localParticipant[userData]);
			data=data.replace(/\"/g,'');
		}
	}else{
		console.log('Falsches Argument in getUserData! Siehe: https://developers.google.com/+/hangouts/api/gapi.hangout.html#classes-in-gapihangout');
	}
	
	return data;
}

function getHangoutId(){
	var hangoutId=gapi.hangout.getHangoutId();
	
	return hangoutId;
}

function getUniqueId(){
	var uniqueId=gapi.hangout.getLocalParticipant().person.id;
		
	return uniqueId;
}


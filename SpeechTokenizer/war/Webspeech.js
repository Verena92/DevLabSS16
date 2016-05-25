/**
 * Autor: Verena Hofmann
 */


var sttResult = '';	
var recognizing = false;
var ignore_onend;

	if (!('webkitSpeechRecognition' in window)) { //diese Funktion ermöglicht, dass der Click Button öfters ausgeführt werden kann, ohne dass die Seite aktualisiert werden muss.
			upgrade ();
		} else {
			/*Erlaubt der Seite Zugriff auf das Mikrofon - webkitSpeechRecognition Objekt stellt das Speech Interface bereit und definiert Eventhandler*/
			//That is the object that will manage our whole recognition process.
			var recognition = new webkitSpeechRecognition();
  			/* Continous: true = User kann Pause einlegen, bei false wird sofort geschickt sobald pause*/
  			recognition.continuous = true;
	  		//recognition.interimResults = true;
	
			//Listening (capturing voice from audio input) started.
			recognition.onstart = function() {
    			recognition.lang = "en-US";
    			recognizing = true;
  			};
  			  	
  			recognition.onend = function() {
    			recognizing = false;
    			if (ignore_onend) {
      				return;
    			}
  	};
  		
  			/*recognition.onresult = function(event) { //the event holds the results
  				The SpeechRecognitionEvent results property returns a SpeechRecognitionResultList object
  				The SpeechRecognitionResultList object contains SpeechRecognitionResult objects.
  				It has a getter so it can be accessed like an array
  				The first [0] returns the SpeechRecognitionResult at position 0.
  				Each SpeechRecognitionResult object contains SpeechRecognitionAlternative objects that contain individual results.
  				These also have getters so they can be accessed like arrays.
  				The second [0] returns the SpeechRecognitionAlternative at position 0.
  				We then return the transcript property of the SpeechRecognitionAlternative object
  				
  				var sttResult = event.results[0][0].transcript;
  				interim.innerHTML = linebreak(sttResult);
				for (var i = event.resultIndex; i < event.results.length; ++i) {
					if (event.results[i].isFinal) {
					sttResult += event.results[i][0].transcript;
				}
				sttResult = capitalize(sttResult);
				interim.innerHTML = linebreak(sttResult);
  			};*/
  	recognition.onspeechstart=function(){
  		var micstatuselement = document.getElementById('micstatus');
  		
  		/**
  		 * Outcomment the next two lines for tests on local server.
  		 * You need the two lines just for App Engine.
  		 */
  		//micstatuselement.style.color='green';
  		//micstatuselement.innerHTML='Mic aktiv';
  		
  	}
  	recognition.onspeechend=function(){
  		var micstatuselement = document.getElementById('micstatus');
  		
  		/**
  		 * Outcomment the next two lines for tests on local server.
  		 * You need the two lines just for App Engine.
  		 */
  		//micstatuselement.removeAttribute('style');
  		//micstatuselement.innerHTML='Mic inaktiv';
  		
  		
  		recognition.stop();
  	}
  	
  	recognition.onresult = function(event) {
    			for (var i = event.resultIndex; i < event.results.length; ++i) {
      				if (event.results[i].isFinal) {
        				sttResult = event.results[i][0].transcript;
      				}
      				
      				//Aufruf der Tokenize Funktion in Tokenize.js
      				//Tokenize(sttResult);
      				
      				//Token(event.results[i][0]);
      				//console.log(event.results[i][0]);
      				//console.log(sttResult);
      				
      				Token();
      				
    			}
    			/*linebreak für neuen Paragraphen, wird dann in HTML übersetzt*/
    			sttResult = capitalize(sttResult);
    			
    			/*final_span Anzeige in HTML*/
    			final_span.innerHTML = linebreak(sttResult);
  			};
  }
  			
  	
  	
  	/*recognition.onspeechend = function() {
  		recognition.stop();
  	}*/
  			

	
	/*Wird nach Drücken auf den Start-Button aufgerufen*/
function startButton(event) {	
	if (recognizing) {
    	recognition.stop();
    	return;
  	}
	sttResult = '';
	/*aktiviert den Speech Recognizer und ruft onstart Eventhandler auf*/
	recognition.start();
	ignore_onend = false;
	final_span.innerHTML = '';
}
	
/* Durch linebreak und capatilize wird Text in Box eingeblendet */

function linebreak(sttResult) {
	return sttResult;
}

function capitalize(sttResult) {
	return sttResult;
}
	



/*<script type="text/javascript">
	
	var ignore_onend;
	var start_timestamp;

	var sttResult = '';	
	var recognizing = false;

	Erlaubt der Seite Zugriff auf das Mikrofon - webkitSpeechRecognition Objekt stellt das Speech Interface bereit und definiert Eventhandler
	That is the object that will manage our whole recognition process.
	var recognition = new webkitSpeechRecognition();
  			Continous: true = User kann Pause einlegen, bei false wird sofort geschickt sobald pause
  			recognition.continuous = false;
  	        /* interimResults: true = angezeigte graue Wörter sind noch nicht festgestetzt, erst wenn sie schwarz sind, sind sie fest. 
  	        false: Wörter kommen erst wenn sich das System sicher ist
  			recognition.interimResults = false;
	//Listening (capturing voice from audio input) started.
	recognition.onstart = function() {
    	recognition.lang = "en-US";
    	recognizing = true;
  	}
  		
  	/*recognition.onresult = function(event) { //the event holds the results
  		/*The SpeechRecognitionEvent results property returns a SpeechRecognitionResultList object
  		The SpeechRecognitionResultList object contains SpeechRecognitionResult objects.
  		It has a getter so it can be accessed like an array
  		The first [0] returns the SpeechRecognitionResult at position 0.
  		Each SpeechRecognitionResult object contains SpeechRecognitionAlternative objects that contain individual results.
  		These also have getters so they can be accessed like arrays.
  		The second [0] returns the SpeechRecognitionAlternative at position 0.
  		We then return the transcript property of the SpeechRecognitionAlternative object
  		var sttResult = event.results[0][0].transcript;
  		interim.innerHTML = linebreak(sttResult);
  		
  	}
  			
  	/*interim_transcript und wird jedes mal neu befüllt, da nur vorübergehend. Der schwarze Text (sttResult) ändert sich nicht, deswegen kann er nur neue Audios aufnehmen
  	recognition.onresult = function(event) {
		for (var i = event.resultIndex; i < event.results.length; ++i) {
				//if (event.results[i].isFinal) {
				sttResult += event.results[i][0].transcript;
				//}
			}

	}
  	
  	
  	/*recognition.onspeechend = function() {
  		recognition.stop();
  	}
  			
  			
  			interim_transcript und wird jedes mal neu befüllt, da nur vorübergehend. Der schwarze Text (sttResult) ändert sich nicht, deswegen kann er nur neue Audios aufnehmen
  			recognition.onresult = function(event) {
   				var interim_transcript = '';
    			for (var i = event.resultIndex; i < event.results.length; ++i) {
      				if (event.results[i].isFinal) {
        				final_transcript += event.results[i][0].transcript;
      				} else {
        				interim_transcript += event.results[i][0].transcript;
      					}
    				}

			}
		
/*Wird nach Drücken auf den Start-Button aufgerufen
function startButton(event) {
	if (recognizing) {
    	recognition.stop();
    	return;
  	}
	sttResult = '';
	/*aktiviert den Speech Recognizer und ruft onstart Eventhandler auf
	recognition.start();
	/*ignore_onend = false;
	final_span.innerHTML = '';
	interim.innerHTML = '';
	}
	
	
</script>-->*/

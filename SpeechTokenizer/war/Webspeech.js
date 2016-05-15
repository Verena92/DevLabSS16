
<script type="text/javascript">
	/*
	var ignore_onend;
	var start_timestamp;*/

	var sttResult = '';	
	var recognizing = false;
	var sttResult;

	/*Erlaubt der Seite Zugriff auf das Mikrofon - webkitSpeechRecognition Objekt stellt das Speech Interface bereit und definiert Eventhandler*/
	//That is the object that will manage our whole recognition process.
	var recognition = new webkitSpeechRecognition();
  			/* Continous: true = User kann Pause einlegen, bei false wird sofort geschickt sobald pause
  			recognition.continuous = false;*/
  	        /* interimResults: true = angezeigte graue Wörter sind noch nicht festgestetzt, erst wenn sie schwarz sind, sind sie fest. 
  	        false: Wörter kommen erst wenn sich das System sicher ist
  			recognition.interimResults = false;*/
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
  		
  	}*/
  			
  	/*interim_transcript und wird jedes mal neu befüllt, da nur vorübergehend. Der schwarze Text (sttResult) ändert sich nicht, deswegen kann er nur neue Audios aufnehmen*/
  	recognition.onresult = function(event) {
		for (var i = event.resultIndex; i < event.results.length; ++i) {
				//if (event.results[i].isFinal) {
				sttResult += event.results[i][0].transcript;
				//}
			}

	}
  	
  	
  	recognition.onspeechend = function() {
  		recognition.stop();
  	}
  			
  			
  			/*interim_transcript und wird jedes mal neu befüllt, da nur vorübergehend. Der schwarze Text (sttResult) ändert sich nicht, deswegen kann er nur neue Audios aufnehmen
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
		
/*Wird nach Drücken auf den Start-Button aufgerufen*/
function startButton(event) {
	/*if (recognizing) {
    	recognition.stop();
    	return;
  	}*/
	sttResult = '';
	/*aktiviert den Speech Recognizer und ruft onstart Eventhandler auf*/
	recognition.start();
	/*ignore_onend = false;
	final_span.innerHTML = '';*/
	interim.innerHTML = '';
	}
	
	
</script>
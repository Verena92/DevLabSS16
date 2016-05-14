
<script type="text/javascript">
	var final_transcript = '';
	var recognizing = false;
	var ignore_onend;
	var start_timestamp;

	if (!('webkitSpeechRecognition' in window)) {
			upgrade ();
		} else {
			/*Erlaubt der Seite Zugriff auf das Mikrofon - webkitSpeechRecognition Objekt stellt das Speech Interface bereit und definiert Eventhandler*/
 			var recognition = new webkitSpeechRecognition();
  			/* Continous: true = User kann Pause einlegen, bei false wird sofort geschickt sobald pause*/
  			recognition.continuous = true;
  	        /* interimResults: true = angezeigte graue Wörter sind noch nicht festgestetzt, erst wenn sie schwarz sind, sind sie fest. 
  	        false: Wörter kommen erst wenn sich das System sicher ist*/
  			recognition.interimResults = false;
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
  			/*interim_transcript und wird jedes mal neu befüllt, da nur vorübergehend. Der schwarze Text (final_transcript) ändert sich nicht, deswegen kann er nur neue Audios aufnehmen
  			recognition.onresult = function(event) {
   				var interim_transcript = '';
    			for (var i = event.resultIndex; i < event.results.length; ++i) {
      				if (event.results[i].isFinal) {
        				final_transcript += event.results[i][0].transcript;
      				} else {
        				interim_transcript += event.results[i][0].transcript;
      					}
    				}
    			/*linebreak für neuen Paragraphen, wird dann in HTML übersetzt
    			final_transcript = capitalize(final_transcript);
    			/*final_span für schwarzen Text
    			final_span.innerHTML = linebreak(final_transcript);
    			/*interim_span für grauen Text
    			interim_span.innerHTML = linebreak(interim_transcript);
  			};*/
		}
		
/*Wird nach Drücken auf den Start-Button aufgerufen*/
function startButton(event) {
	/*if (recognizing) {
    	recognition.stop();
    	return;
  	}*/
	final_transcript = '';
	/*aktiviert den Speech Recognizer und ruft onstart Eventhandler auf*/
	recognition.start();
	ignore_onend = false;
	/*final_span.innerHTML = '';
	interim_span.innerHTML = '';*/
	}
	
/* Durch linebreak und capatilize wird Text in Box eingeblendet
var two_line = /\n\n/g;
var one_line = /\n/g;
function linebreak(s) {
	return s.replace(two_line, '<p></p>').replace(one_line, '<br>');
	}
var first_char = /\S/;
function capitalize(s) {
	return s.replace(first_char, function(m) { return m.toUpperCase(); });
	}*/
	
</script>
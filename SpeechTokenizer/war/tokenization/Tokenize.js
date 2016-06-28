/**
 * Hier wird der eingegangen Audio-Stream weiterverabeitet, tokenisiert.
 * Autor: Benjamin Mateja
 */

	/**
	 * uncommonArr ist ein Array, in welchem die als wichtig identifizierten Tokens gespeichert werden.
	 */
var uncommonArr = [];

function Tokenize(){
	
	/**
	 * Die Variable filterWords enthält die Wörter, welche gefiltert werden sollen.
	 * Diese Wörter können zu keinem Token mehr werden.
	 * Die Liste für die meisten üblichen englischen Wrtern kann hier gefunden werden (Datenbasis):
	 * https://en.wikipedia.org/wiki/Most_common_words_in_English
	 * Benutzt werden Verben, Präpositionen, Adjektive und Andere ....
	 * Diese Liste kann upgedated werden.
	 */
	
	var filterWords = 'be, have, do, say, get, make, get, know, take, see, come, think, look, want, give, use, find, tell, ask, work, seem, feel, try, leave, call, good, new, first, last, long, great, little, own, other, old, right, big, high, different, okay, small, large, next, early, young, important, few, public, bad, same, able, to, of, in, for, on, with, at, by, from, up, about, into, over, after, beneath, under, above, the, and, a, that, I, it, not, he, as, you, this, but, his, they, her, she, or, and, will, my, one, all, would, there, their, is, now, hi, hello, hey, up';
		
	/**
	 * In den verschiedenen sttResults werden Satzzeichen herausgefiltert.
	 */
	var sttResult1 = sttResult.split('!').join("");
	var sttResult2 = sttResult1.split(':').join("");
	var sttResult3 = sttResult2.split('.').join("");
	var sttResult4 = sttResult3.split('?').join("");
	var sttResultFin = sttResult4.split(';').join("");
	
	/**
	* Funktion, um unübliche Wörter zu filtern.
	*/
	function getUncommon(){
		var wordArr = sttResultFin.match(/\S+/g),
			commonObj = {},
			word, 
			i;
		
		/**
		 * Befühlt das Objekt commonObj mit den filterWords.
		 */
		filterWords = filterWords.split(',');
		for ( i = 0; i < filterWords.length; i++) {
				commonObj[ filterWords[i].trim() ] = true;
			}
		
		/**
		 * Checkt ob die Wörter zu den filterWords gehören oder nicht.
		 * Wenn nicht, werden diese in das uncommonArr Array gepushed.
		 */
		for ( i = 0; i < wordArr.length; i++){
			word = wordArr[i].trim();
			if ( !commonObj[word] ) {
				uncommonArr.push(word);
			}
		}
		
		return uncommonArr;
				
		}
	
	return getUncommon();
	
	
	
};
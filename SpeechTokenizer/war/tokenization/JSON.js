/**
 * Diese Klasse erzeugt aus dem @param TokenNew einen JSON mit Hilfe der Methode
 * <code>JSON.stringify(TokenNew)</code>.
 * @author Benjamin Mateja
 */

function Stringify (TokenNew){
	var jsonString = JSON.stringify(TokenNew);
	console.log(jsonString);
}
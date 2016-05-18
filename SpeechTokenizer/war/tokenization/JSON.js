/**
 * Autor: Benjamin Mateja
 */

function Stringify (){
	
	car1 = new Object();
	car2 = new Object();
	
	
	
	car1.marke = "BMW";
	car1.modell = "3er";
	
	car2.marke = "Audi";
	car2.modell = "A4";
	
	console.log(car1);
	console.log(car2);
	
	var jsonString = JSON.stringify(car1);
	
	console.log(jsonString);
	
	
	
}
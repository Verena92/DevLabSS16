/**
 * Autor Mona Brunner
 */

$.ajax({ method: "POST", url: "IP-Adresse-zur-Schnittstelle/nameSchnittstelle", data: { name: "John", location: "Boston" }

    error: function( ){

        console.log("Error");

    },

    success: function( response_data ){

        // hier response_data 
  

    	        new_keys = response_data["new_keys"]
    	}
});


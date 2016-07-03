package de.hdm.wim.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * A helper class to read the defined properties of a properties file.
 * The properties file is stored in the default resource package.
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */

public class PropertiesHelper {
	public static String getProperties(String key){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("config.properties");
		Properties properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(properties.getProperty(key));		
	}
}


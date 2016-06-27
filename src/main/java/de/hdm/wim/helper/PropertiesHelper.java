package de.hdm.wim.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * A helper class to read the defined properties of a properties file.
 * 
 * @author Jens Lindner, Max Harhoff, Sebastian Vaas, Stefan Sigel
 *
 */

public class PropertiesHelper {
	public static String getProperties(String key){
		File propertiesFile = new File("./src/main/resources/config.properties");
		Properties properties = new Properties();
		 
		if(propertiesFile.exists())
		{
		  BufferedInputStream bis;
			try {
				bis = new BufferedInputStream(new FileInputStream(propertiesFile));
				properties.load(bis);
				bis.close(); 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String str = properties.getProperty(key);
		return(str);
	}
}


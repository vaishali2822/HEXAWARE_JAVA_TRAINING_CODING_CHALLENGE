package com.company.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
	//Two static variables created
	private static final String File_Path = "resources/db.properties";
	private static Properties properties;
	
	static {
		// try - catch -- exception handling try is where your code is which may throw an exception and an exception catch will catch the exception
		
		try {
			FileInputStream fis = new FileInputStream(File_Path);
			properties = new Properties();
			//to load the file which is property file so we are loading with the help of object of property class
			properties.load(fis);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
//		String url = properties.getProperty("db.url");
//		String user = properties.getProperty("db.username");
//		String pass = properties.getProperty("db.password");

		
	}
	//return url + "|" +user +"|" +pass;
	
	public static String get(String key) {
		
	return properties.getProperty(key);
}
	

}


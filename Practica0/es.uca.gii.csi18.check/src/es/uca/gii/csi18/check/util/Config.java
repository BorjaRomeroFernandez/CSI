package es.uca.gii.csi18.check.util;

import java.util.*;
import java.io.*;


public class Config {

	public static Properties Properties(String sfile) throws IOException{
		
	InputStream inputStream = null ;
	
	try
	{
		inputStream = new FileInputStream(sfile);
		Properties result = new Properties();
		result.load(inputStream);
		return result;	
	}
	finally { if(inputStream != null) inputStream.close();}
	}
}



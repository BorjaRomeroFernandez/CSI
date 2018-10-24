package es.uca.gii.csi18.artyom.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import es.uca.gii.csi18.artyom.util.Config;

public class Data {
    public static String getPropertiesUrl() { return "./db.properties"; }
    public static Connection Connection() throws Exception {
        try {
            Properties properties = Config.Properties(getPropertiesUrl());
            return DriverManager.getConnection(
                properties.getProperty("jdbc.url"),
                properties.getProperty("jdbc.username"),
                properties.getProperty("jdbc.password"));
       }
       catch (Exception ee) { throw ee; }
	}
    
    public static void LoadDriver() 
        throws InstantiationException, IllegalAccessException, 
        ClassNotFoundException, IOException {
            Class.forName(Config.Properties(Data.getPropertiesUrl()
            ).getProperty("jdbc.driverClassName")).newInstance();
    }
    
    
    
    public static String String2sql(String s, boolean bAddQuotes, boolean bAddWildcards) {
    	
    	StringBuilder sb = new StringBuilder(s.replaceAll("'", "''"));
    	
        if (bAddQuotes && bAddWildcards) {
          sb.insert(0,"\'%");
          sb.append("%\'");
        } else if (bAddQuotes && !bAddWildcards) {
          sb.insert(0,"\'");
          sb.append("\'");
        } else if (!bAddQuotes && bAddWildcards) {
          sb.insert(0,"%");
          sb.append("%");
        } 
      
        return sb.toString();
    }
    	
     	
    public static int Boolean2sql(boolean b){
    	
    	return b ? 1 : 0 ;
    }
    

 	}


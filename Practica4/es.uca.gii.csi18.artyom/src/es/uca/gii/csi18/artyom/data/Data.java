package es.uca.gii.csi18.artyom.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import es.uca.gii.csi18.artyom.util.Config;

public class Data {
	public static String getPropertiesUrl() {
		return "./db.properties";
	}

	public static Connection Connection() throws Exception {
		try {
			Properties properties = Config.Properties(getPropertiesUrl());
			return DriverManager.getConnection(properties.getProperty("jdbc.url"),
					properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
		} catch (Exception ee) {
			throw ee;
		}
	}

	public static void LoadDriver()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Class.forName(Config.Properties(Data.getPropertiesUrl()).getProperty("jdbc.driverClassName")).newInstance();
	}

	public static String String2sql(String s, boolean bAddQuotes, boolean bAddWildcards) {
		s = s.replaceAll("'", "''");

		if (bAddQuotes && bAddWildcards) {
			s = "\'%" + s + "%\'";
		} else if (bAddQuotes && !bAddWildcards) {
			s = "'" + s + "'";
		} else if (!bAddQuotes && bAddWildcards) {
			s = "%" + s + "%";
		}

		return s;
	}

	public static int Boolean2sql(boolean b) {
		return b ? 1 : 0;
	}

	public static int LastId(Connection con) throws Exception {
		Properties properties = Config.Properties(Data.getPropertiesUrl());
		ResultSet rs = null;
		
		try {
			rs = con.createStatement().executeQuery(properties.getProperty("jdbc.lastIdSentence"));

			if(rs.next()) {
				return rs.getInt(1); 
			} else {
				throw new IllegalArgumentException("KEY NOT FOUND"); 
			}
		} catch (SQLException ee) {
			throw ee;
		}
		finally {
			if (rs != null)
				rs.close();
		}
	}
}
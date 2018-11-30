package es.uca.gii.csi18.artyom.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import es.uca.gii.csi18.artyom.util.Config;

public class Data {
	/**
	 * Method to get URL of db.properties file
	 * 
	 * @return db.properties
	 */
	public static String getPropertiesUrl() {
		return "./db.properties";
	}

	/**
	 * Method to get the properties from db.properties file
	 * 
	 * @return the database configuration to stablish a database connection
	 * @throws Exception
	 */
	public static Connection Connection() throws Exception {
		try {
			Properties properties = Config.Properties(getPropertiesUrl());
			return DriverManager.getConnection(properties.getProperty("jdbc.url"),
					properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
		} catch (Exception ee) {
			throw ee;
		}
	}

	/**
	 * Method to stablish the connection with the database from db.properties file
	 * 
	 * @throws Exception
	 */
	public static void LoadDriver()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		Class.forName(Config.Properties(Data.getPropertiesUrl()).getProperty("jdbc.driverClassName")).newInstance();
	}

	/**
	 * Method to convert a String to be Sql friendly
	 * 
	 * @param s             String that we want to convert
	 * @param bAddQuotes    Boolean that verifies if we want to add quotes to the
	 *                      string
	 * @param bAddWildCards Boolean that verifies if we want to add wildcards to the
	 *                      string
	 * @return the Sql friendly string
	 */
	public static String String2Sql(String s, boolean bAddQuotes, boolean bAddWildcards) {
		s = s.replace("'", "''");

		if (bAddWildcards)
			s = "%" + s + "%";
		if (bAddQuotes)
			s = "'" + s + "'";

		return s;
	}

	/**
	 * Method to convert a Boolean to be Sql friendly
	 * 
	 * @param b Boolean that we want to convert
	 * @return 1 if b is true and 0 if b is false
	 * @throws Exception
	 */
	public static int Boolean2Sql(boolean b) {
		return b ? 1 : 0;
	}

	/**
	 * Method to get the last inserted id in a connection
	 * 
	 * @param con Connection where we want to know what id was the last inserted
	 * @return the last inserted id
	 * @throws Exception
	 */
	public static int LastId(Connection con) throws Exception {
		Properties properties = Config.Properties(Data.getPropertiesUrl());
		ResultSet rs = null;

		try {
			rs = con.createStatement().executeQuery(properties.getProperty("jdbc.lastIdSentence"));

			rs.next();
			return rs.getInt(1);
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs != null)
				rs.close();
		}
	}
}
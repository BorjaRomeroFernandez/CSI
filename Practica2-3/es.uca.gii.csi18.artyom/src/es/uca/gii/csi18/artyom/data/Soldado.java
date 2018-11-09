package es.uca.gii.csi18.artyom.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Soldado {
	private String _sOldIdSoldier;
	private String _sIdSoldier;
	private String _sNameSoldier;
	private int _iHoursSoldier;
	private boolean _bIsDeleted = false;

	/**
	 * Soldier's Constructor
	 * 
	 * @param sId String that contains an id
	 * @throws Exception
	 */
	public Soldado(String sId) throws Exception {

		Connection con = null;
		ResultSet rs = null;

		try {

			con = Data.Connection();
			rs = con.createStatement().executeQuery(
					"SELECT IdSoldado,NombreSoldado,HorasGuardiaSoldado " + "FROM soldado WHERE IdSoldado = " + sId);

			while (rs.next()) {
				_sIdSoldier = rs.getString("IdSoldado");
				_sNameSoldier = rs.getString("NombreSoldado");
				_iHoursSoldier = rs.getInt("HorasGuardiaSoldado");
			}

		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs != null)
				rs.close();
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to create a soldier
	 * 
	 * @param sId    String that contains an id
	 * @param sName  String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return a Soldier's reference
	 * @throws Exception
	 */
	public static Soldado Create(String sId, String sName, int iHours) throws Exception {

		Connection con = null;

		String sIdSoldier = Data.String2sql(sId, true, false);
		String sNombreSoldado = Data.String2sql(sName, true, false);

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate(
					"INSERT INTO soldado VALUES(" + sIdSoldier + "," + sNombreSoldado + "," + iHours + ");");
			return new Soldado(sId);

		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}
	
	/**
	 * Method to delete a soldier
	 * 
	 * Preconditions: _bIsDeleted must be false. If not, it will throw an exception.
	 * @throws Exception
	 */
	public void Delete() throws Exception {

		if (_bIsDeleted)
			throw new Exception("La instancia ya esta eliminada");
		else {
			Connection con = null; 

			try {
				con = Data.Connection();
				con.createStatement().executeUpdate("DELETE FROM soldado WHERE IdSoldado = " + _sIdSoldier);
				_bIsDeleted = true;

			} catch (SQLException ee) {
				throw ee;
			} finally {
				if (con != null)
					con.close();
			}
		}
	}

	/**
	 * Method to update a soldier's atributes
	 * 
	 * Preconditions: _bIsDeleted must be false. If not it will throw an exception.
	 * @throws Exception
	 */
	public void Update() throws Exception {

		if (_bIsDeleted)
			throw new Exception("La instancia ya esta eliminada");
		else {

			Connection con = null;

			try {
				con = Data.Connection();
				con.createStatement().executeUpdate(
					"UPDATE soldado SET IdSoldado = " + Data.String2sql(_sIdSoldier,true,false) + ", NombreSoldado = " + Data.String2sql(_sNameSoldier, true, false) + ", HorasGuardiaSoldado = " + _iHoursSoldier + " WHERE IdSoldado LIKE " + _sOldIdSoldier + ";");
			} catch (SQLException ee) {
				throw ee;
			} finally {
				if (con != null)
					con.close();
			}
		}	
	}

	/**
	 * Method to select a soldier
	 * 
	 * @param sId	 String that contains an id
	 * @param sName	 String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return an ArrayList with all the Soldiers' references that have been selected
	 * @throws Exception
	 */
	public static ArrayList<Soldado> Select(String sId, String sName, Integer iHours) throws Exception {

		Connection con = null;
		ResultSet rs = null;
		ArrayList<Soldado> aSoldado = new ArrayList<Soldado>();
		
		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery(
					"SELECT IdSoldado, NombreSoldado, HorasGuardiaSoldado FROM soldado" + Where(sId, sName, iHours) + ";");

			while (rs.next()) {
				aSoldado.add(new Soldado(rs.getString("IdSoldado")));
			}

			return aSoldado;

		} catch (SQLException ee) {
			throw ee;
		} finally {

		}
	}
	
	/**
	 * Method to obtain a WHERE constraint
	 * 
	 * @param sId	 String that contains an id
	 * @param sName	 String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return a String with a WHERE constraint
	 * @throws Exception
	 */
	private static String Where(String sId, String sName, Integer iHours) {
		
		if (sId == null && sName == null && iHours == null)
			return "";
		else {
			String sCondiciones = null;

			if (sId != null) 
				sCondiciones = " IdSoldado LIKE " + Data.String2sql(sId, true, true) + " AND";

			if (sName != null) 
				sCondiciones = sCondiciones + " NombreSoldado LIKE " + Data.String2sql(sName, true, true) + " AND";
			
			if (iHours != null)
				sCondiciones = sCondiciones +" HorasGuardiaSoldado = " + iHours + " AND";
			
			sCondiciones = sCondiciones.substring(0, sCondiciones.length() - 4);
			return (" WHERE" + sCondiciones);
		}
	}

	@Override
	public String toString() {
		String sTring = super.toString() + ":" + _iHoursSoldier;
		return sTring;
	}

	public void setIdSoldier(String sIdSoldier) {
		_sIdSoldier = sIdSoldier;
	}

	public void setNameSoldier(String sName) {
		_sNameSoldier = sName;
	}

	public void setHoursSoldier(int iHours) {
		_iHoursSoldier = iHours;
	}

	public void setOldIdSoldier(String sOldIdSoldier)
	{
		_sOldIdSoldier = sOldIdSoldier;
	}

	public String getIdSoldier() {
		return _sIdSoldier;
	}

	public String getNameSoldier() {
		return _sNameSoldier;
	}

	public int getHoursSoldier() {
		return _iHoursSoldier;
	}

	public boolean getIsDeleted() {
		return _bIsDeleted;
	}

}

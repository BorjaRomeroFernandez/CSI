package es.uca.gii.csi18.artyom.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Soldado {
	private int _iId;
	private String _sName;
	private int _iHours;
	private boolean _bIsDeleted = false;

	/**
	 * Soldier's Constructor
	 * 
	 * @param iId Integer that contains an id
	 * @throws Exception
	 */
	public Soldado(int iId) throws Exception {
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Name, GuardHours FROM soldado WHERE Id = " + iId);

			if (rs.next()) {
				_sName = rs.getString("Name");
				_iHours = rs.getInt("GuardHours");
				_iId = iId;
			} else {
				throw new Exception("Id no se encuentra en la Base de Datos");
			}
		} catch (Exception ee) {
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
	 * Preconditions: sName needs to be initialized and iHours needs to be a
	 * positive number
	 * 
	 * @param iId    Integer that contains an id
	 * @param sName  String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return a Soldier's reference
	 * @throws Exception
	 */
	public static Soldado Create(String sName, int iHours) throws Exception {
		if (sName == null || sName == "")
			throw new IllegalArgumentException("El nombre no puede estar vacio.");
		if (iHours < 0)
			throw new IllegalArgumentException("Las horas no pueden ser negativas");

		Connection con = null;

		String sSqlName = Data.String2Sql(sName, true, false);

		try {
			con = Data.Connection();
			con.createStatement()
					.executeUpdate("INSERT INTO soldado (Name, GuardHours) VALUES(" + sSqlName + "," + iHours + ");");
			return new Soldado(Data.LastId(con));
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
	 * 
	 * @throws Exception
	 */
	public void Delete() throws Exception {
		if (_bIsDeleted)
			throw new IllegalArgumentException("La instancia ya esta eliminada");

		Connection con = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("DELETE FROM soldado WHERE Id = " + _iId);
			_bIsDeleted = true;
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to update a soldier's attributes
	 * 
	 * Preconditions: _bIsDeleted must be false. If not it will throw an exception.
	 * 
	 * @throws Exception
	 */
	public void Update() throws Exception {
		if (_bIsDeleted)
			throw new IllegalArgumentException("La instancia ya esta eliminada");

		Connection con = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("UPDATE soldado SET Name = " + Data.String2Sql(_sName, true, false)
					+ ", GuardHours = " + _iHours + " WHERE Id = " + _iId + ";");
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to select a soldier
	 * 
	 * @param sName  String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return an ArrayList with all the Soldiers' references that have been
	 *         selected
	 * @throws Exception
	 */
	public static ArrayList<Soldado> Select(String sName, Integer iHours) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Soldado> aSoldado = new ArrayList<Soldado>();

		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id FROM soldado" + Where(sName, iHours) + ";");
			while (rs.next())
				aSoldado.add(new Soldado(rs.getInt("Id")));
			return aSoldado;

		} catch (Exception ee) {
			throw ee;
		} finally {
			if (rs != null)
				rs.close();
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to obtain a WHERE constraint
	 * 
	 * @param sName  String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return a String with a WHERE constraint
	 * @throws Exception
	 */
	private static String Where(String sName, Integer iHours) {
		if (sName == null && iHours == null)
			return "";
		else {
			String sConditions = null;
			if (sName != null)
				sConditions = sConditions + "Name LIKE " + Data.String2Sql(sName, true, true) ;
			if (iHours != null)
				if(sName != null) sConditions += " AND ";
				sConditions = sConditions + "GuardHours = " + iHours;
				
			return " WHERE " + sConditions;
		}
	}

	@Override
	public String toString() {
		String sTring = super.toString() + ":" + _iHours;
		return sTring;
	}

	public void setName(String sName) {
		_sName = sName;
	}

	public void setHours(int iHours) {
		_iHours = iHours;
	}

	public int getId() {
		return _iId;
	}

	public String getName() {
		return _sName;
	}

	public int getHours() {
		return _iHours;
	}

	public boolean getIsDeleted() {
		return _bIsDeleted;
	}

}

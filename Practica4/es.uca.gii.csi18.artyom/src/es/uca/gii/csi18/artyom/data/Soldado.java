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
			rs = con.createStatement().executeQuery("SELECT Id, Nombre, HorasGuardia FROM soldado WHERE Id = " + iId);

			while (rs.next()) {
				_iId = rs.getInt("Id");
				_sName = rs.getString("Nombre");
				_iHours = rs.getInt("HorasGuardia");
			}
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}

	/**
	 * Method to create a soldier
	 * 
	 * @param iId    Integer that contains an id
	 * @param sName  String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return a Soldier's reference
	 * @throws Exception
	 */
	public static Soldado Create(String sName, int iHours) throws Exception {
		if (sName == null || sName == "") {
			throw new IllegalArgumentException("El nombre no puede estar vacio.");
		} else {
			Connection con = null;

			String sNombre = Data.String2sql(sName, true, false);

			try {
				con = Data.Connection();
				con.createStatement().executeUpdate("INSERT INTO soldado (Nombre, HorasGuardia) VALUES(" + sNombre + "," + iHours + ");");
				return new Soldado(Data.LastId(con));
			} catch (SQLException ee) {
				throw ee;
			} finally {
				if (con != null) con.close();
			}
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
		else {
			Connection con = null;

			try {
				con = Data.Connection();
				con.createStatement().executeUpdate("DELETE FROM soldado WHERE Id = " + _iId);
				_bIsDeleted = true;
			} catch (SQLException ee) {
				throw ee;
			} finally {
				if (con != null) con.close();
			}
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
		else {
			Connection con = null;

			try {
				con = Data.Connection();
				con.createStatement()
						.executeUpdate("UPDATE soldado SET Nombre = " + Data.String2sql(_sName, true, false)
								+ ", HorasGuardia = " + _iHours + " WHERE Id = " + _iId + ";");
			} catch (SQLException ee) {
				throw ee;
			} finally {
				if (con != null) con.close();
			}
		}
	}

	/**
	 * Method to select a soldier
	 * 
	 * @param iId    Integer that contains an id
	 * @param sName  String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return an ArrayList with all the Soldiers' references that have been
	 *         selected
	 * @throws Exception
	 */
	public static ArrayList<Soldado> Select(Integer iId, String sName, Integer iHours) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Soldado> aSoldado = new ArrayList<Soldado>();

		try {
			con = Data.Connection();
			rs = con.createStatement()
					.executeQuery("SELECT Id, Nombre, HorasGuardia FROM soldado" + Where(iId, sName, iHours) + ";");

			while (rs.next()) {
				aSoldado.add(new Soldado(rs.getInt("Id")));
			}

			return aSoldado;
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}

	/**
	 * Method to obtain a WHERE constraint
	 * 
	 * @param iId    Integer that contains an id
	 * @param sName  String that contains a name
	 * @param iHours Integer that contains the hours a soldier guards
	 * @return a String with a WHERE constraint
	 * @throws Exception
	 */
	private static String Where(Integer iId, String sName, Integer iHours) {
		if (iId == null && sName == null && iHours == null) {
			return "";
		} else {
			String sCondiciones = null;

			if (iId != null) sCondiciones = " Id = " + iId + " AND";
			if (sName != null) sCondiciones = sCondiciones + " Nombre LIKE " + Data.String2sql(sName, true, true) + " AND";
			if (iHours != null) sCondiciones = sCondiciones + " HorasGuardia = " + iHours + " AND";

			sCondiciones = sCondiciones.substring(0, sCondiciones.length() - 4);
			return (" WHERE" + sCondiciones);
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

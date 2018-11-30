package es.uca.gii.csi18.artyom.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GunType {
	private int _iId;
	private String _sType;
	private boolean _bIsDeleted = false;

	/**
	 * GunType's Constructor
	 * 
	 * @param iId Integer that contains an id
	 * @throws Exception
	 */
	public GunType(int iId) throws Exception {
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Type FROM guntype WHERE Id = " + iId);

			if (rs.next()) {
				_sType = rs.getString("Type");
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
	 * Method to select a GunType
	 * 
	 * @return an ArrayList with all the GunType's references that have been
	 *         selected
	 * @throws Exception
	 */
	public static ArrayList<GunType> Select() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<GunType> aGunType = new ArrayList<GunType>();

		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id FROM guntype ORDER BY Type;");
			while (rs.next())
				aGunType.add(new GunType(rs.getInt("Id")));
			return aGunType;

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
	 * Method to select a GunType
	 * 
	 * @param sType String that contains a gun type
	 * @return an ArrayList with all the GunType's references that have been
	 *         selected
	 * @throws Exception
	 */

	public static ArrayList<GunType> Select(String sType) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<GunType> aGunType = new ArrayList<GunType>();

		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery(
					"SELECT DISTINCT Id FROM guntype WHERE Type LIKE " + Data.String2Sql(sType, true, true) + " ;");
			while (rs.next())
				aGunType.add(new GunType(rs.getInt("Id")));
			return aGunType;

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
	 * Method to create a GunType
	 * 
	 * Preconditions: sType needs to be initialized
	 * 
	 * @param sType String that contains a gun type
	 * @return a GunType's reference
	 * @throws Exception
	 */
	public static GunType Create(String sType) throws Exception {
		if (sType == null)
			throw new IllegalArgumentException("El tipo de arma no puede estar vacio");

		Connection con = null;

		try {
			String sSqlType = Data.String2Sql(sType, true, false);

			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO guntype (Type) VALUES(" + sSqlType + ");");
			return new GunType(Data.LastId(con));
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to delete a GunType
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
			con.createStatement().executeUpdate("DELETE FROM guntype WHERE Id = " + _iId);
			_bIsDeleted = true;
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to update a GunType's attributes
	 * 
	 * Preconditions: _bIsDeleted must be false. If not, it will throw an exception.
	 * 
	 * @throws Exception
	 */
	public void Update() throws Exception {
		if (_bIsDeleted)
			throw new IllegalArgumentException("La instancia ya esta eliminada");

		Connection con = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate(
					"UPDATE guntype SET Type = " + Data.String2Sql(_sType, true, false) + " WHERE Id = " + _iId + ";");
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}

	@Override
	public String toString() {
		return _sType;
	}

	public int getId() {
		return _iId;
	}

	public String getType() {
		return _sType;
	}

	public void setType(String sType) {
		_sType = sType;
	}

	public boolean getIsDeleted() {
		return _bIsDeleted;
	}

}
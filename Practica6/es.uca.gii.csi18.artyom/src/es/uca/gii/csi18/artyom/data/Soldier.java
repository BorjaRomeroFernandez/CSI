package es.uca.gii.csi18.artyom.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Soldier {
	private int _iId;
	private GunType _gunType;
	private String _sName;
	private int _iHours;
	private boolean _bIsDeleted = false;

	/**
	 * Soldier's Constructor
	 * 
	 * @param iId Integer that contains an id
	 * @throws Exception
	 */
	public Soldier(int iId) throws Exception {
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			rs = con.createStatement()
					.executeQuery("SELECT Id_GunType, Name, GuardHours FROM soldier WHERE Id = " + iId);

			if (rs.next()) {
				_gunType = new GunType(rs.getInt("Id_GunType"));
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
	 * Method to create a Soldier
	 * 
	 * Preconditions: gunType and sName need to be initialized and iHours needs to
	 * be a positive number
	 * 
	 * @param gunType Object of class GunType that contains a reference to an
	 *                instance of this class
	 * @param sName   String that contains a name
	 * @param iHours  Integer that contains the hours a soldier guards
	 * @return a Soldier's reference
	 * @throws Exception
	 */
	public static Soldier Create(GunType gunType, String sName, int iHours) throws Exception {
		if (gunType == null)
			throw new IllegalArgumentException("El tipo de arma no puede estar vacio.");
		if (sName == null || sName == "")
			throw new IllegalArgumentException("El nombre no puede estar vacio.");
		if (iHours < 0)
			throw new IllegalArgumentException("Las horas no pueden ser negativas");

		Connection con = null;

		try {
			String sSqlName = Data.String2Sql(sName, true, false);

			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO soldier (Id_GunType, Name, GuardHours) VALUES("
					+ gunType.getId() + "," + sSqlName + "," + iHours + ");");
			return new Soldier(Data.LastId(con));
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to delete a Soldier
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
			con.createStatement().executeUpdate("DELETE FROM soldier WHERE Id = " + _iId);
			_bIsDeleted = true;
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to update a Soldier's attributes
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
			con.createStatement().executeUpdate("UPDATE soldier SET Id_GunType = " + _gunType.getId() + ", Name = "
					+ Data.String2Sql(_sName, true, false) + ", GuardHours = " + _iHours + " WHERE Id = " + _iId + ";");
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to select a Soldier
	 * 
	 * @param sGunType String that contains a gun type
	 * @param sName    String that contains a name
	 * @param iHours   Integer that contains the hours a soldier guards
	 * @return an ArrayList with all the Soldier's references that have been
	 *         selected
	 * @throws Exception
	 */
	public static ArrayList<Soldier> Select(String sGunType, String sName, Integer iHours) throws Exception {
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Soldier> aSoldier = new ArrayList<Soldier>();

		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT DISTINCT soldier.Id FROM soldier "
					+ Where(sGunType, sName, iHours) + " ORDER BY soldier.Id ASC ;");
			while (rs.next())
				aSoldier.add(new Soldier(rs.getInt("soldier.Id")));
			return aSoldier;

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
	 * @param sGunType String that contains a gun type
	 * @param sName    String that contains a name
	 * @param iHours   Integer that contains the hours a soldier guards
	 * @return a String with a WHERE constraint
	 * @throws Exception
	 */
	private static String Where(String sGunType, String sName, Integer iHours) {
		if (sName == null && iHours == null && sGunType == null)
			return "";
		else {
			String sConditions = "";
			String sGunConditions = "";

			if (sGunType != null) {
				sGunConditions += "INNER JOIN guntype ON soldier.Id_GunType IN "
						+ "(SELECT guntype.Id FROM guntype WHERE Type LIKE " + Data.String2Sql(sGunType, true, true)
						+ ")";
			}

			if (sName != null || iHours != null) {
				sConditions = "WHERE ";

				if (sName != null)
					sConditions += "Name LIKE " + Data.String2Sql(sName, true, true);

				if (iHours != null) {
					if (sName != null)
						sConditions += " AND ";
					sConditions += "GuardHours = " + (int) iHours;
				}
			}

			return sGunConditions + sConditions;
		}
	}

	@Override
	public String toString() {
		String sTring = super.toString() + ":" + _gunType.toString() + "; " + _iHours;
		return sTring;
	}

	public void setGunType(GunType gunType) {
		_gunType = gunType;
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

	public GunType getGunType() {
		return _gunType;
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
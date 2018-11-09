package es.uca.gii.csi18.artyom.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.artyom.data.Data;
import es.uca.gii.csi18.artyom.data.Soldado;

class SoldadoTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	/**
	 * Method to test Soldier's Constructor
	 * 
	 * @throws Exception
	 */
	@Test
	public void testConstructor() throws Exception {
		Soldado soldado = new Soldado("2");
		Connection con = null;
		ResultSet rs = null;

		try {

			con = Data.Connection();
			rs = con.createStatement().executeQuery(
					"SELECT IdSoldado,NombreSoldado,HorasGuardiaSoldado FROM soldado WHERE IdSoldado LIKE 2;");

			while (rs.next()) {
				assertEquals(rs.getString("IdSoldado"), soldado.getIdSoldier());
				assertEquals(rs.getString("NombreSoldado"), soldado.getNameSoldier());
				assertEquals(rs.getInt("HorasGuardiaSoldado"), soldado.getHoursSoldier());
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
	 * Method to test Soldier's Create method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreate() throws Exception {
		Soldado soldado = Soldado.Create("4", "Pepe el Cojo2", 88);

		Connection con = null;
		ResultSet rs = null;

		try {

			con = Data.Connection();
			rs = con.createStatement().executeQuery(
					"SELECT IdSoldado,NombreSoldado,HorasGuardiaSoldado FROM soldadodado WHERE IdSoldado LIKE 4;");

			while (rs.next()) {
				assertEquals(rs.getString("IdSoldado"), soldado.getIdSoldier());
				assertEquals(rs.getString("NombreSoldado"), soldado.getNameSoldier());
				assertEquals(rs.getInt("HorasGuardiaSoldado"), soldado.getHoursSoldier());
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
	 * Method to test Soldier's Select method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSelect() throws Exception {
		
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Soldado> aSoldado = Soldado.Select("4", null, null);
		int i = 0 ; 

		try {
			
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT IdSoldado, NombreSoldado, HorasGuardiaSoldado FROM soldado WHERE IdSoldado LIKE 4;");

			while (rs.next()) {
				assertEquals(rs.getString("IdSoldado"), aSoldado.get(i).getIdSoldier());
				assertEquals(rs.getString("NombreSoldado"), aSoldado.get(i).getNameSoldier());
				assertEquals(rs.getInt("HorasGuardiaSoldado"), aSoldado.get(i).getHoursSoldier());
				++i;
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
	 * Method to test Soldier's Update method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdate() throws Exception {

		Connection con = null;
		Soldado soldado = new Soldado("4");
		ResultSet rs1 = null, rs2 = null;

		try {

			con = Data.Connection();

			rs1 = con.createStatement().executeQuery("SELECT IdSoldado, NombreSoldado, HorasGuardiaSoldado FROM soldado WHERE IdSoldado LIKE 4");

			soldado.setOldIdSoldier(soldado.getIdSoldier());
			soldado.setIdSoldier("10");
			soldado.setNameSoldier("Junter");
			soldado.setHoursSoldier(10);
			soldado.Update();

			rs2 = con.createStatement().executeQuery("SELECT IdSoldado, NombreSoldado, HorasGuardiaSoldado FROM soldado WHERE IdSoldado LIKE 10");

			while(rs1.next() && rs2.next()) {
				assertEquals(rs1.getString("IdSoldado"), rs2.getString("IdSoldado"));
				assertEquals(rs1.getString("NombreSoldado"), rs2.getString("NombreSoldado"));
				assertEquals(rs1.getInt("HorasGuardiaSoldado"), rs2.getInt("HorasGuardiaSoldado"));
			}
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs1 != null)
				rs1.close();
			if (rs2 != null)
				rs2.close();
			if (con != null)
				con.close();
		}
	}

	/**
	 * Method to test Soldier's Delete method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelete() throws Exception {

		Connection con = null;
		ResultSet rs = null;

		try {
			Soldado soldado = new Soldado("10");
			soldado.Delete();
			
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT IdSoldado, NombreSoldado, HorasGuardiaSoldado FROM soldado WHERE IdSoldado LIKE 10");

			assertEquals(soldado.getIsDeleted(), true);
			
			while (rs.next()) {
				assertEquals(rs.getString("IdSoldado"), soldado.getIdSoldier());
				assertEquals(rs.getString("NombreSoldado"), soldado.getNameSoldier());
				assertEquals(rs.getInt("HorasGuardiaSoldado"), soldado.getHoursSoldier());
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
}
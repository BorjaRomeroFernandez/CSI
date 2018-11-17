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
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			Soldado soldado = new Soldado(1);
			rs = con.createStatement().executeQuery("SELECT Id, Nombre, HorasGuardia FROM soldado WHERE Id = 1;");

			if (rs.next()) {
				assertEquals(rs.getInt("Id"), soldado.getId());
				assertEquals(rs.getString("Nombre"), soldado.getName());
				assertEquals(rs.getInt("HorasGuardia"), soldado.getHours());
			}
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
		}
	}

	/**
	 * Method to test Soldier's Create method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreate() throws Exception {
		Soldado soldado = Soldado.Create("Pepe el Cojo2", 88);
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id, Nombre, HorasGuardia FROM soldado ORDER BY Id DESC LIMIT 1;");
			
			if (rs.next()) {
				assertEquals(rs.getInt("Id"), soldado.getId());
				assertEquals(rs.getString("Nombre"), soldado.getName());
				assertEquals(rs.getInt("HorasGuardia"), soldado.getHours());
			}
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
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
		int i = 0;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO soldado (Nombre, HorasGuardia) VALUES ('Perico', 17);");
			ArrayList<Soldado> aSoldado = Soldado.Select(Data.LastId(con), null, null);
			rs = con.createStatement().executeQuery("SELECT Id, Nombre, HorasGuardia FROM soldado WHERE Id = " + Data.LastId(con) + ";");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldado.get(i).getId());
				assertEquals(rs.getString("Nombre"), aSoldado.get(i).getName());
				assertEquals(rs.getInt("HorasGuardia"), aSoldado.get(i).getHours());
				++i;
			}
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs != null) rs.close();
			if (con != null) con.close();
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
		ResultSet rs1 = null, rs2 = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO soldado (Nombre, HorasGuardia) VALUES ('Federico', 14);");
			Soldado soldado = new Soldado(Data.LastId(con));
			rs1 = con.createStatement().executeQuery("SELECT Id, Nombre, HorasGuardia FROM soldado WHERE Id = " + Data.LastId(con) + ";");

			soldado.setName("Junter");
			soldado.setHours(10);
			soldado.Update();

			rs2 = con.createStatement().executeQuery("SELECT Id, Nombre, HorasGuardia FROM soldado WHERE Id = " + Data.LastId(con) + ";");

			if (rs1.next() && rs2.next()) {
				assertEquals(rs1.getInt("Id"), rs2.getInt("Id"));
				assertNotSame(rs1.getString("Nombre"), rs2.getString("Nombre"));
				assertNotSame(rs1.getInt("HorasGuardia"), rs2.getInt("HorasGuardia"));
			}
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs1 != null) rs1.close();
			if (rs2 != null) rs2.close();
			if (con != null) con.close();
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
		ResultSet rs1 = null, rs2 = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO soldado (Nombre, HorasGuardia) VALUES ('Artyom', 24);");
			Soldado soldado = new Soldado(Data.LastId(con));

			rs1 = con.createStatement().executeQuery("SELECT COUNT(Id) FROM soldado;");
			soldado.Delete();
			rs2 = con.createStatement().executeQuery("SELECT COUNT(Id) FROM soldado;");

			assertEquals(soldado.getIsDeleted(), true);
			rs1.next();
			rs2.next();
			assertNotSame(rs1.getInt(1), rs2.getInt(1));
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs1 != null) rs1.close();
			if (rs2 != null) rs2.close();
			if (con != null) con.close();
		}
	}
}
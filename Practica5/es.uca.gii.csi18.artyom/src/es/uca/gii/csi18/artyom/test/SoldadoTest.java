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
			rs = con.createStatement().executeQuery("SELECT Id, Name, GuardHours FROM soldado WHERE Id = 1;");

			if (rs.next()) {
				assertEquals(rs.getInt("Id"), soldado.getId());
				assertEquals(rs.getString("Name"), soldado.getName());
				assertEquals(rs.getInt("GuardHours"), soldado.getHours());
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
		Soldado soldado = Soldado.Create("Pepe el Cojo2", 88);
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			rs = con.createStatement()
					.executeQuery("SELECT Id, Name, GuardHours FROM soldado ORDER BY Id DESC LIMIT 1;");

			if (rs.next()) {
				assertEquals(rs.getInt("Id"), soldado.getId());
				assertEquals(rs.getString("Name"), soldado.getName());
				assertEquals(rs.getInt("GuardHours"), soldado.getHours());
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
		int i = 0;

		try {
			con = Data.Connection();
			ArrayList<Soldado> aSoldado1 = Soldado.Select("Federico", null);
			rs = con.createStatement().executeQuery("SELECT Id, Name, GuardHours FROM soldado ;");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldado1.get(i).getId());
				assertEquals(rs.getString("Name"), aSoldado1.get(i).getName());
				assertEquals(rs.getInt("GuardHours"), aSoldado1.get(i).getHours());
				++i;
			}
			rs.close();
			i = 0;
			ArrayList<Soldado> aSoldado2 = Soldado.Select("Federico", null);
			rs = con.createStatement()
					.executeQuery("SELECT Id, Name, GuardHours FROM soldado WHERE Name = 'Federico';");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldado2.get(i).getId());
				assertEquals(rs.getString("Name"), aSoldado2.get(i).getName());
				assertEquals(rs.getInt("GuardHours"), aSoldado2.get(i).getHours());
				++i;
			}
			
			i = 0;
			ArrayList<Soldado> aSoldado3 = Soldado.Select(null, 88);
			rs = con.createStatement().executeQuery("SELECT Id, Name, GuardHours FROM soldado GuardHours = 88 ;");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldado3.get(i).getId());
				assertEquals(rs.getString("Name"), aSoldado3.get(i).getName());
				assertEquals(rs.getInt("GuardHours"), aSoldado3.get(i).getHours());
				++i;
			}
			rs.close();
			i = 0;
			ArrayList<Soldado> aSoldado4 = Soldado.Select("Federico", 88);
			rs = con.createStatement().executeQuery(
					"SELECT Id, Name, GuardHours FROM soldado WHERE Name = 'Federico' AND GuardHours = 88 ;");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldado4.get(i).getId());
				assertEquals(rs.getString("Name"), aSoldado4.get(i).getName());
				assertEquals(rs.getInt("GuardHours"), aSoldado4.get(i).getHours());
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
		ResultSet rs1 = null, rs2 = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO soldado (Name, GuardHours) VALUES ('Federico', 14);");
			Soldado soldado = new Soldado(Data.LastId(con));
			rs1 = con.createStatement()
					.executeQuery("SELECT Id, Name, GuardHours FROM soldado WHERE Id = " + Data.LastId(con) + ";");

			soldado.setName("Junter");
			soldado.setHours(10);
			soldado.Update();

			rs2 = con.createStatement()
					.executeQuery("SELECT Id, Name, GuardHours FROM soldado WHERE Id = " + Data.LastId(con) + ";");

			if (rs1.next() && rs2.next()) {
				assertEquals(rs1.getInt("Id"), rs2.getInt("Id"));
				assertNotSame(rs1.getString("Name"), rs2.getString("Name"));
				assertNotSame(rs1.getInt("GuardHours"), rs2.getInt("GuardHours"));
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
		ResultSet rs1 = null, rs2 = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO soldado (Name, GuardHours) VALUES ('Artyom', 24);");
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
			if (rs1 != null)
				rs1.close();
			if (rs2 != null)
				rs2.close();
			if (con != null)
				con.close();
		}
	}
}
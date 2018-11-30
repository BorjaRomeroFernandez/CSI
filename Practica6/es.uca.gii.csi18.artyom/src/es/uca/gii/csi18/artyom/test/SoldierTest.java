package es.uca.gii.csi18.artyom.test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import es.uca.gii.csi18.artyom.data.Data;
import es.uca.gii.csi18.artyom.data.GunType;
import es.uca.gii.csi18.artyom.data.Soldier;

class SoldierTest {

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
			Soldier soldier = new Soldier(1);
			rs = con.createStatement().executeQuery("SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE Id = 1;");

			rs.next();
			assertEquals(rs.getInt("Id"), soldier.getId());
			assertEquals(rs.getInt("Id_GunType"), soldier.getGunType().getId());
			assertEquals(rs.getString("Name"), soldier.getName());
			assertEquals(rs.getInt("GuardHours"), soldier.getHours());
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
		Soldier soldier = Soldier.Create(new GunType(1), "Pepe el Cojo", 88);
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			rs = con.createStatement()
					.executeQuery("SELECT Id, Id_GunType, Name, GuardHours FROM soldier ORDER BY Id DESC LIMIT 1;");

			rs.next();
			assertEquals(rs.getInt("Id"), soldier.getId());
			assertEquals(rs.getInt("Id_GunType"), soldier.getGunType().getId());
			assertEquals(rs.getString("Name"), soldier.getName());
			assertEquals(rs.getInt("GuardHours"), soldier.getHours());
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
			ArrayList<Soldier> aSoldier1 = Soldier.Select(null, null, null);
			rs = con.createStatement().executeQuery("SELECT Id, Id_GunType, Name, GuardHours FROM soldier ORDER BY Id ASC ;");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldier1.get(i).getId());
				assertEquals(rs.getInt("Id_GunType"), aSoldier1.get(i).getGunType().getId());
				assertEquals(rs.getString("Name"), aSoldier1.get(i).getName());
				assertEquals(rs.getInt("GuardHours"), aSoldier1.get(i).getHours());
				++i;
			}
			rs.close();
			i = 0;
			ArrayList<Soldier> aSoldier2 = Soldier.Select(new GunType(3).getType(), null, null);
			rs = con.createStatement()
					.executeQuery("SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE Id_GunType = 3 ORDER BY Id ASC ;");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldier2.get(i).getId());
				assertEquals(rs.getInt("Id_GunType"), aSoldier2.get(i).getGunType().getId());
				assertEquals(rs.getString("Name"), aSoldier2.get(i).getName());
				assertEquals(rs.getInt("GuardHours"), aSoldier2.get(i).getHours());
				++i;
			}
			rs.close();
			i = 0 ; 
			ArrayList<Soldier> aSoldier3 = Soldier.Select(null, "Federico", null);
			rs = con.createStatement().executeQuery("SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE NAME LIKE 'Federico' ORDER BY Id ASC ;");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldier3.get(i).getId());
				assertEquals(rs.getInt("Id_GunType"), aSoldier3.get(i).getGunType().getId());
				assertEquals(rs.getString("Name"), aSoldier3.get(i).getName());
				assertEquals(rs.getInt("GuardHours"), aSoldier3.get(i).getHours());
				++i;
			}
			rs.close();
			i = 0;
			ArrayList<Soldier> aSoldier4 = Soldier.Select(null, null, 88);
			rs = con.createStatement().executeQuery(
					"SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE GuardHours = 88 ORDER BY Id ASC ;");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aSoldier4.get(i).getId());
				assertEquals(rs.getInt("Id_GunType"), aSoldier4.get(i).getGunType().getId());
				assertEquals(rs.getString("Name"), aSoldier4.get(i).getName());
				assertEquals(rs.getInt("GuardHours"), aSoldier4.get(i).getHours());
				++i;
			}
			rs.close();
			ArrayList<Soldier> aSoldier5 = Soldier.Select(new GunType(1).getType(), "Raul", null);
			rs = con.createStatement().executeQuery(
					"SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE Id_GunType = 1 AND Name LIKE 'Raul' ORDER BY Id ASC ;");

			rs.next();
			assertEquals(rs.getInt("Id"), aSoldier5.get(0).getId());
			assertEquals(rs.getInt("Id_GunType"), aSoldier5.get(0).getGunType().getId());
			assertEquals(rs.getString("Name"), aSoldier5.get(0).getName());
			assertEquals(rs.getInt("GuardHours"), aSoldier5.get(0).getHours());
			
			rs.close();
			ArrayList<Soldier> aSoldier6 = Soldier.Select(new GunType(3).getType(), null, 88);
			rs = con.createStatement().executeQuery(
					"SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE Id_GunType = 3 AND GuardHours = 88 ORDER BY Id ASC ;");

			rs.next();
			assertEquals(rs.getInt("Id"), aSoldier6.get(0).getId());
			assertEquals(rs.getInt("Id_GunType"), aSoldier6.get(0).getGunType().getId());
			assertEquals(rs.getString("Name"), aSoldier6.get(0).getName());
			assertEquals(rs.getInt("GuardHours"), aSoldier6.get(0).getHours());
			
			rs.close();
			ArrayList<Soldier> aSoldier7 = Soldier.Select(null, "Federico", 88);
			rs = con.createStatement().executeQuery(
					"SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE Name LIKE 'Federico' AND GuardHours = 88 ORDER BY Id ASC ;");

			rs.next();
			assertEquals(rs.getInt("Id"), aSoldier7.get(0).getId());
			assertEquals(rs.getInt("Id_GunType"), aSoldier7.get(0).getGunType().getId());
			assertEquals(rs.getString("Name"), aSoldier7.get(0).getName());
			assertEquals(rs.getInt("GuardHours"), aSoldier7.get(0).getHours());
			
			rs.close();
			ArrayList<Soldier> aSoldier8 = Soldier.Select(new GunType(3).getType(), "Federico", 88);
			rs = con.createStatement().executeQuery(
					"SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE Id_GunType = 3 AND Name LIKE 'Federico' AND GuardHours = 88 ORDER BY Id ASC ;");

			rs.next();
			assertEquals(rs.getInt("Id"), aSoldier8.get(0).getId());
			assertEquals(rs.getInt("Id_GunType"), aSoldier8.get(0).getGunType().getId());
			assertEquals(rs.getString("Name"), aSoldier8.get(0).getName());
			assertEquals(rs.getInt("GuardHours"), aSoldier8.get(0).getHours());
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
			con.createStatement().executeUpdate("INSERT INTO soldier (Id_GunType, Name, GuardHours) VALUES (2,'Federico', 14);");
			Soldier soldier = new Soldier(Data.LastId(con));
			rs1 = con.createStatement()
					.executeQuery("SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE Id = " + Data.LastId(con) + ";");
			
			soldier.setGunType(new GunType(3));
			soldier.setName("Junter");
			soldier.setHours(10);
			soldier.Update();

			rs2 = con.createStatement()
					.executeQuery("SELECT Id, Id_GunType, Name, GuardHours FROM soldier WHERE Id = " + Data.LastId(con) + ";");

			rs1.next();
			rs2.next();
			assertEquals(rs1.getInt("Id"), rs2.getInt("Id"));
			assertNotSame(rs1.getInt("Id_GunType"), rs2.getInt("Id_GunType"));
			assertNotSame(rs1.getString("Name"), rs2.getString("Name"));
			assertNotSame(rs1.getInt("GuardHours"), rs2.getInt("GuardHours"));
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
			con.createStatement().executeUpdate("INSERT INTO soldier (Id_GunType, Name, GuardHours) VALUES ('1','Artyom', 24);");
			Soldier soldier = new Soldier(Data.LastId(con));

			rs1 = con.createStatement().executeQuery("SELECT COUNT(Id) FROM soldier;");
			soldier.Delete();
			rs2 = con.createStatement().executeQuery("SELECT COUNT(Id) FROM soldier;");

			assertEquals(soldier.getIsDeleted(), true);
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
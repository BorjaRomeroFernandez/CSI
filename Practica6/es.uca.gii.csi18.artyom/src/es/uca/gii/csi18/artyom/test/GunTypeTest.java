package es.uca.gii.csi18.artyom.test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import es.uca.gii.csi18.artyom.data.Data;
import es.uca.gii.csi18.artyom.data.GunType;

class GunTypeTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	/**
	 * Method to test GunType's Constructor
	 * 
	 * @throws Exception
	 */
	@Test
	public void testConstructor() throws Exception {
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			GunType guntype = new GunType(1);
			rs = con.createStatement().executeQuery("SELECT Id, Type FROM guntype WHERE Id = 1;");

			rs.next();
			assertEquals(rs.getInt("Id"), guntype.getId());
			assertEquals(rs.getString("Type"), guntype.getType());
			
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
	 * Method to test GunType's Select method
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
			ArrayList<GunType> aGunType = GunType.Select();
			rs = con.createStatement().executeQuery("SELECT Id, Type FROM guntype ORDER BY Type;");

			while (rs.next()) {
				assertEquals(rs.getInt("Id"), aGunType.get(i).getId());
				assertEquals(rs.getString("Type"), aGunType.get(i).getType());
				++i;
			}
			rs.close();
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
	 * Method to test GunType's Select methods
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSelectParameter() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		int i = 0;

		try {
			con = Data.Connection();
			ArrayList<GunType> aGunType = GunType.Select("Escopeta");
			rs = con.createStatement().executeQuery("SELECT Id, Type FROM guntype WHERE Type LIKE 'Escopeta' ;");
			
			rs.next();
			assertEquals(rs.getInt("Id"), aGunType.get(i).getId());
			assertEquals(rs.getString("Type"), aGunType.get(i).getType());
			rs.close();
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
	 * Method to test GunType's Create method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreate() throws Exception {
		GunType guntype = GunType.Create("Francotirador");
		Connection con = null;
		ResultSet rs = null;

		try {
			con = Data.Connection();
			rs = con.createStatement()
					.executeQuery("SELECT Id, Type FROM guntype ORDER BY Id DESC LIMIT 1;");

			rs.next();
			assertEquals(rs.getInt("Id"), guntype.getId());
			assertEquals(rs.getString("Type"), guntype.getType());
			
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
	 * Method to test GunType's Update method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdate() throws Exception {
		Connection con = null;
		ResultSet rs1 = null, rs2 = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO guntype (Type) VALUES ('Rifle de Asalto');");
			GunType guntype = new GunType(Data.LastId(con));
			rs1 = con.createStatement()
					.executeQuery("SELECT Id, Type FROM guntype WHERE Id = " + Data.LastId(con) + ";");
			
			guntype.setType("Ametralladora");
			guntype.Update();

			rs2 = con.createStatement()
					.executeQuery("SELECT Id, Type FROM guntype WHERE Id = " + Data.LastId(con) + ";");

			rs1.next();
			rs2.next();
			assertEquals(rs1.getInt("Id"), rs2.getInt("Id"));
			assertNotSame(rs1.getString("Type"), rs2.getString("Type"));
			
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
	 * Method to test GunType's Delete method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelete() throws Exception {
		Connection con = null;
		ResultSet rs1 = null, rs2 = null;

		try {
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO guntype (Type) VALUES ('Cuchillo');");
			GunType guntype = new GunType(Data.LastId(con));

			rs1 = con.createStatement().executeQuery("SELECT COUNT(Id) FROM guntype;");
			guntype.Delete();
			rs2 = con.createStatement().executeQuery("SELECT COUNT(Id) FROM guntype;");

			assertEquals(guntype.getIsDeleted(), true);
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
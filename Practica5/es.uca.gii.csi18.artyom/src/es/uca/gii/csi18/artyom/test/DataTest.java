package es.uca.gii.csi18.artyom.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.artyom.data.Data;

class DataTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Data.LoadDriver();
	}

	@Test
	@Disabled
	public void testTableAccess() throws Exception {
		Connection con = null;
		ResultSet rs = null;
		int i = 0;

		try {
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT Id, Name, GuardHours FROM soldado;");

			while (rs.next()) {
				System.out.println(rs.getInt("Id") + " " + rs.getString("Name") + " " + rs.getInt("GuardHours"));
				++i;
			}
			rs.close();
			rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM soldado;");
			rs.next();
			assertEquals(rs.getInt(1), i);
		} catch (SQLException ee) {
			throw ee;
		} finally {
			if (rs != null)
				rs.close();
			if (con != null)
				con.close();
		}
	}

	@Test
	public void testString2Sql() {
		assertEquals("hola", Data.String2Sql("hola", false, false));
		assertEquals("'hola'", Data.String2Sql("hola", true, false));
		assertEquals("%hola%", Data.String2Sql("hola", false, true));
		assertEquals("'%hola%'", Data.String2Sql("hola", true, true));
		assertEquals("O''Connell", Data.String2Sql("O'Connell", false, false));
		assertEquals("'O''Connell'", Data.String2Sql("O'Connell", true, false));
		assertEquals("%''Smith ''%", Data.String2Sql("'Smith '", false, true));
		assertEquals("'''Smith '''", Data.String2Sql("'Smith '", true, false));
		assertEquals("'%''Smith ''%'", Data.String2Sql("'Smith '", true, true));
	}

	@Test
	public void testBoolean2Sql() {
		assertEquals(1, Data.Boolean2Sql(true));
		assertEquals(0, Data.Boolean2Sql(false));
	}

}

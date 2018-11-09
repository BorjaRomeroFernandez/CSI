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
		try {

			int i = 0;
			con = Data.Connection();
			rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM soldado;");
			rs.next();
			assertEquals(rs.getInt(1), i);
			rs.close();
			rs = con.createStatement().executeQuery("SELECT * FROM soldado;");

			while (rs.next()) {
				System.out.println(rs.getString("IdSoldado") + " " + rs.getString("NombreSoldado") + " "
						+ rs.getString("HorasGuardiaSoldado"));
				i++;
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

	@Test
	public void testString2SQL() {
		assertEquals("hola", Data.String2sql("hola", false, false));
		assertEquals("'hola'", Data.String2sql("hola", true, false));
		assertEquals("%hola%", Data.String2sql("hola", false, true));
		assertEquals("'%hola%'", Data.String2sql("hola", true, true));
		assertEquals("O''Connell", Data.String2sql("O'Connell", false, false));
		assertEquals("'O''Connell'", Data.String2sql("O'Connell", true, false));
		assertEquals("%''Smith ''%", Data.String2sql("'Smith '", false, true));
		assertEquals("'''Smith '''", Data.String2sql("'Smith '", true, false));
		assertEquals("'%''Smith ''%'", Data.String2sql("'Smith '", true, true));

	}

	@Test
	public void testBoolean2SQL() {
		assertEquals(1, Data.Boolean2sql(true));
		assertEquals(0, Data.Boolean2sql(false));

	}

}

package es.uca.gii.csi18.artyom.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import es.uca.gii.csi18.artyom.data.Data;
import es.uca.gii.csi18.artyom.data.Soldado;

class SoldadoTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception { Data.LoadDriver(); }

	/**
	 * Method to test Soldier's Constructor
	 * @throws Exception
	 */
	@Test
	public void testConstructor() throws Exception
	{
		Soldado sol = new Soldado("2");
		Connection con = null;
	    ResultSet rs = null;
	    
	    try {  	
	    	
	        con = Data.Connection();
	        rs = con.createStatement().executeQuery("SELECT IdSoldado,Nombre,HorasGuardia "
	        		+ "FROM soldado WHERE IdSoldado = 2;");
	        
	        while(rs.next())
	        {
	        	assertEquals(rs.getString("IdSoldado"),sol.getIdSoldier());
				assertEquals(rs.getString("Nombre"),sol.getName());
				assertEquals(rs.getInt("HorasGuardia"),sol.getHours());
		    	
	        }
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
	
	/**
	 * Method to test Soldier's Create method
	 * @throws Exception
	 */
	@Test
	public void testCreate() throws Exception
	{
		Soldado sol = Soldado.Create("5", "Pepe el Cojo", 7);
		
		Connection con = null;
	    ResultSet rs = null;
	    
	    try {  	
	    	
	        con = Data.Connection();
	        rs = con.createStatement().executeQuery("SELECT IdSoldado,Nombre,HorasGuardia "
	        		+ "FROM soldado WHERE IdSoldado = 5;");
	        
	        while(rs.next())
	        {
	        	assertEquals(rs.getString("IdSoldado"),sol.getIdSoldier());
				assertEquals(rs.getString("Nombre"),sol.getName());
				assertEquals(rs.getInt("HorasGuardia"),sol.getHours());
		    	
	        }
	    }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close();
	    	if (con != null) con.close();
	    }
	}
}

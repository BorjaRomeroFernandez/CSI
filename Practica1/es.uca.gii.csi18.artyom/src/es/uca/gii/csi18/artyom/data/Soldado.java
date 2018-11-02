package es.uca.gii.csi18.artyom.data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Soldado
{
	private String _sIdSoldier;
	private String _sName;
	private int _iHours;
	
	/**
	 * Soldier's Constructor
	 * @param sIdSoldado String that contains an id
	 * @throws Exception
	 */
	public Soldado(String sIdSoldado) throws Exception
	{
		Connection con = null;
	    ResultSet rs = null;
	    try {  	
	    	
	        con = Data.Connection();
	        rs = con.createStatement().executeQuery("SELECT IdSoldado,Nombre,HorasGuardia "
	        		+ "FROM soldado WHERE IdSoldado = "+sIdSoldado);
	      
	        while(rs.next())
	        {
	           _sIdSoldier = rs.getString("IdSoldado");
	 	       _sName = rs.getString("Nombre");
	 	       _iHours = rs.getInt("HorasGuardia");
	 	    }
	       
	       }
	    catch (SQLException ee) { throw ee; }
	    finally {
	    	if (rs != null) rs.close(); 
	    	if (con != null) con.close();
	    }
	    
	}
	
	/**
	 * Method to create soldier
	 * @param sIdSoldado String that contains an id 
	 * @param sName String that contains a name 
	 * @param iHoras Int that contains the hours soldier's guard
	 * @return a Soldier's reference
	 * @throws Exception
	 */
	public static Soldado Create(String sIdSoldado,String sName,int iHoras) throws Exception
	{

		Connection con = null;
		
		String sIdSoldier = Data.String2sql(sIdSoldado, true, false);
		String sNombre = Data.String2sql(sName, true, false);
		
		try
		{
			con = Data.Connection();
			con.createStatement().executeUpdate("INSERT INTO soldado VALUES("+sIdSoldier+","+sNombre+","+iHoras+");");
			
		}catch(SQLException ee) {throw ee; }
		finally
		{
			if (con != null) con.close();
		}
		
		return new Soldado(sIdSoldado);
	}

	@Override
	public String toString()
	{
		String sTring = super.toString() +":"+_iHours ; 
		
		return sTring ;
	}
	
	
	public void setIdSoldier(String sIdSoldier)
	{
		_sIdSoldier = sIdSoldier; 
	}
	public void setName(String sName)
	{
		_sName = sName;
	}
	public void setHours(int iHours)
	{
		_iHours = iHours ;
	}
	public String getIdSoldier()
	{
		return _sIdSoldier;
	}
	public String getName()
	{
		return _sName;
	}
	public int getHours()
	{
		return _iHours;
	}
	
}

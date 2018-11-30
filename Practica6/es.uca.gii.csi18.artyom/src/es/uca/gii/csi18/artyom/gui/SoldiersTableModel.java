package es.uca.gii.csi18.artyom.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import es.uca.gii.csi18.artyom.data.Soldier;

public class SoldiersTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private ArrayList<Soldier> _aData;

	/**
	* SoldierTableModel's Constructor
	* 
	* @param ArrayList<Soldier> ArrayList that contains all the soldiers
	*/	
	public SoldiersTableModel(ArrayList<Soldier> aData) {
		_aData = aData;
	}

	public Soldier getData(int iRow) { 
		return _aData.get(iRow);
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return _aData.size();
	}

	@Override
	public Object getValueAt(int iRow, int iCol) {
		if (iRow < 0 || iRow > getRowCount() - 1 || iCol < 0 || iCol > getColumnCount() - 1)
			throw new IllegalArgumentException("Indice invalido");

		switch (iCol) {
		case 0:
			return _aData.get(iRow).getName();
		case 1:
			return _aData.get(iRow).getHours();
		case 2:
			return _aData.get(iRow).getGunType().getType();
		default:
			return null;
		}
	}
}

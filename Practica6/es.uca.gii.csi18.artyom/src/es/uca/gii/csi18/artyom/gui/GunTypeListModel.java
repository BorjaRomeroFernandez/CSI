package es.uca.gii.csi18.artyom.gui;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import es.uca.gii.csi18.artyom.data.GunType; 

public class GunTypeListModel extends AbstractListModel<GunType> implements ComboBoxModel<GunType> {
	
	private static final long serialVersionUID = 1L;
	private List<GunType> _aData;
	private Object _oSelectedItem = null;

	/**
	* GunTypeListModel's Constructor
	* 
	* @param List<GunType> List that contains all the gun types
	*/
	public GunTypeListModel(List<GunType> aData) {
		_aData = aData;
	}
	
	@Override
	public GunType getElementAt(int iIndex) {
		return _aData.get(iIndex);
	}

	@Override
	public int getSize() {
		return _aData.size();
	}

	@Override
	public Object getSelectedItem() {
		return _oSelectedItem;
	}

	@Override
	public void setSelectedItem(Object oSelectedItem) {
		_oSelectedItem = oSelectedItem;
		
	}
}
package es.uca.gii.csi18.artyom.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import es.uca.gii.csi18.artyom.data.Soldier;
import es.uca.gii.csi18.artyom.data.GunType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class IfrSoldier extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtGuardHours;
	private Soldier _soldier = null;

	/**
	 * Create the frame.
	 */
	public IfrSoldier(Soldier soldier) {
		_soldier = soldier;
		setClosable(true);
		setResizable(true);
		setTitle("Soldado");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		JLabel lblName = new JLabel("Nombre");
		lblName.setBounds(10, 11, 37, 14);
		getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setBounds(57, 8, 86, 20);
		getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblGuardHours = new JLabel("Horas de guardia");
		lblGuardHours.setBounds(10, 36, 82, 14);
		getContentPane().add(lblGuardHours);
		
		txtGuardHours = new JTextField();
		txtGuardHours.setBounds(102, 33, 86, 20);
		getContentPane().add(txtGuardHours);
		txtGuardHours.setColumns(10);
		
		JLabel lblGunType = new JLabel("Tipo de Arma");
		lblGunType.setBounds(238, 11, 71, 14);
		getContentPane().add(lblGunType);

		JComboBox<GunType> cmbGunType = new JComboBox<GunType>();
		cmbGunType.setBounds(308, 8, 142, 20);
		getContentPane().add(cmbGunType);
		try {
			cmbGunType.setModel(new GunTypeListModel(GunType.Select()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningun tipo de arma");
		}
			
		JButton butSave = new JButton("Guardar");
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (_soldier == null) {
						_soldier = Soldier.Create((GunType)cmbGunType.getModel().getSelectedItem(), txtName.getText(),
								Integer.parseInt(txtGuardHours.getText()));
					} else {
						_soldier.setGunType((GunType)cmbGunType.getModel().getSelectedItem());
						_soldier.setName(txtName.getText());
						_soldier.setHours(Integer.parseInt(txtGuardHours.getText()));
						_soldier.Update();
					}
				} catch (Exception e) {
					{
						JOptionPane.showMessageDialog(null, "Rellene todos los campos");
					}
				}
			}
		});
		butSave.setBounds(10, 61, 89, 23);
		getContentPane().add(butSave);
	}
}

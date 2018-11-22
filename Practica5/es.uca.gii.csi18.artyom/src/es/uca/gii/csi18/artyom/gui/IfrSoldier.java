package es.uca.gii.csi18.artyom.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import es.uca.gii.csi18.artyom.data.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IfrSoldier extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtHorasDeGuardia;
	private Soldado _soldier = null;

	/**
	 * Create the frame.
	 */
	public IfrSoldier() {
		setClosable(true);
		setResizable(true);
		setTitle("Soldado");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(10, 11, 37, 14);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(57, 8, 86, 20);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblHorasDeGuardia = new JLabel("Horas de guardia");
		lblHorasDeGuardia.setBounds(10, 36, 82, 14);
		getContentPane().add(lblHorasDeGuardia);

		txtHorasDeGuardia = new JTextField();
		txtHorasDeGuardia.setBounds(102, 33, 86, 20);
		getContentPane().add(txtHorasDeGuardia);
		txtHorasDeGuardia.setColumns(10);

		JButton butSave = new JButton("Guardar");
		butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (_soldier == null) {
						_soldier = Soldado.Create(txtNombre.getText(), Integer.parseInt(txtHorasDeGuardia.getText()));
					} else {
						_soldier.setName(txtNombre.getText());
						_soldier.setHours(Integer.parseInt(txtHorasDeGuardia.getText()));
						_soldier.Update();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		butSave.setBounds(10, 61, 89, 23);
		getContentPane().add(butSave);

	}
}

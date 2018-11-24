package es.uca.gii.csi18.artyom.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import es.uca.gii.csi18.artyom.data.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IfrSoldado extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtHorasDeGuardia;
	private Soldado _soldado = null;

	/**
	 * Create the frame.
	 */
	public IfrSoldado(Soldado soldado) {
		_soldado = soldado;
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

		JButton butGuardar = new JButton("Guardar");
		butGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (_soldado == null) {
						_soldado = Soldado.Create(txtNombre.getText(), Integer.parseInt(txtHorasDeGuardia.getText()));
					} else {
						_soldado.setName(txtNombre.getText());
						_soldado.setHours(Integer.parseInt(txtHorasDeGuardia.getText()));
						_soldado.Update();
					}
				} catch (Exception e) {
					{
						JOptionPane.showMessageDialog(null, "Rellene todos los campos");
					}
				}
			}
		});
		butGuardar.setBounds(10, 61, 89, 23);
		getContentPane().add(butGuardar);

	}
}

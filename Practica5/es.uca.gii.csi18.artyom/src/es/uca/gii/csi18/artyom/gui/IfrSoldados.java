package es.uca.gii.csi18.artyom.gui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi18.artyom.data.Soldado;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IfrSoldados extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtHorasDeGuardia;
	private JTable tabResult;
	private Container pnlParent;

	/**
	 * Create the frame.
	 */
	public IfrSoldados(JFrame frame) {
		pnlParent = frame;
		setResizable(true);
		setClosable(true);
		setTitle("Soldados");
		setBounds(100, 100, 583, 300);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblNombre = new JLabel("Nombre");
		panel.add(lblNombre);

		txtNombre = new JTextField();
		panel.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblHorasDeGuardia = new JLabel("Horas de Guardia");
		panel.add(lblHorasDeGuardia);

		txtHorasDeGuardia = new JTextField();
		panel.add(txtHorasDeGuardia);
		txtHorasDeGuardia.setColumns(10);

		JButton butBuscar = new JButton("Buscar");
		butBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Integer iHoras;
					if ((txtHorasDeGuardia.getText().equals("")))
						iHoras = null;
					else
						iHoras = Integer.parseInt(txtHorasDeGuardia.getText());

					tabResult.setModel(new SoldadosTableModel(Soldado.Select(txtNombre.getText(), iHoras)));

				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, "Hubo un error en la busqueda: " + ee.getMessage());
				}
			}
		});
		panel.add(butBuscar);

		tabResult = new JTable();
		tabResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Se activa con doble clic sobre una fila
				if (e.getClickCount() == 2) {
					int iRow = ((JTable) e.getSource()).getSelectedRow();

					Soldado soldado = ((SoldadosTableModel) tabResult.getModel()).getData(iRow);

					if (soldado != null) {
						IfrSoldado ifrSoldado = new IfrSoldado(soldado);
						ifrSoldado.setBounds(10, 27, 244, 192);
						pnlParent.add(ifrSoldado, 0);
						ifrSoldado.setVisible(true);
					}
				}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER);

	}

}

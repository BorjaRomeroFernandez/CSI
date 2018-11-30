package es.uca.gii.csi18.artyom.gui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import es.uca.gii.csi18.artyom.data.GunType;
import es.uca.gii.csi18.artyom.data.Soldier;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class IfrSoldiers extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtGuardHours;
	private JTable tabResult;
	private Container pnlParent;

	/**
	* Create the frame.
	*  
	*/
	public IfrSoldiers(JFrame frame) {
		pnlParent = frame;
		setResizable(true);
		setClosable(true);
		setTitle("Soldados");
		setBounds(100, 100, 526, 300);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblName = new JLabel("Nombre");
		panel.add(lblName);

		txtName = new JTextField();
		panel.add(txtName);
		txtName.setColumns(10);

		JLabel lblGuardHours = new JLabel("Horas de Guardia");
		panel.add(lblGuardHours);

		txtGuardHours = new JTextField();
		panel.add(txtGuardHours);
		txtGuardHours.setColumns(10);

		JLabel lblGunType = new JLabel("Tipo de Arma");
		panel.add(lblGunType);

		JComboBox<GunType> cmbGunType = new JComboBox<GunType>();
		try {
			cmbGunType.setModel(new GunTypeListModel(GunType.Select()));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se ha encontrado ningun tipo de arma");
		}
		cmbGunType.setEditable(true);
		panel.add(cmbGunType);
		
		JButton butSearch = new JButton("Buscar");
		butSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Integer iHours;
					String sGunType;
					if (cmbGunType.getSelectedItem() == null)
						sGunType = null;
					else
						sGunType = cmbGunType.getSelectedItem().toString();

					if ((txtGuardHours.getText().equals("")))
						iHours = null;
					else
						iHours = Integer.parseInt(txtGuardHours.getText());

					tabResult.setModel(new SoldiersTableModel(Soldier.Select(sGunType, txtName.getText(), iHours)));

				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, "Introduzca valores validos: " + ee.getMessage());
				}
			}
		});
		
		panel.add(butSearch);
		
		tabResult = new JTable();
		tabResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Se activa con doble clic sobre una fila
				if (e.getClickCount() == 2) {
					int iRow = ((JTable) e.getSource()).getSelectedRow();

					Soldier soldier = ((SoldiersTableModel) tabResult.getModel()).getData(iRow);

					if (soldier != null) {
						IfrSoldier ifrSoldier = new IfrSoldier(soldier);
						ifrSoldier.setBounds(10, 27, 500, 300);
						pnlParent.add(ifrSoldier, 0);
						ifrSoldier.setVisible(true);
					}
				}
			}
		});
		getContentPane().add(tabResult, BorderLayout.CENTER);

	}

}

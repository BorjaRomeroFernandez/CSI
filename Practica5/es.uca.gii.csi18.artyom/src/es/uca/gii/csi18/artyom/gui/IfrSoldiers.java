package es.uca.gii.csi18.artyom.gui;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class IfrSoldiers extends JInternalFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * Create the frame.
	 */
	public IfrSoldiers() {
		setResizable(true);
		setClosable(true);
		setTitle("Soldados");
		setBounds(100, 100, 450, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);

	}

}

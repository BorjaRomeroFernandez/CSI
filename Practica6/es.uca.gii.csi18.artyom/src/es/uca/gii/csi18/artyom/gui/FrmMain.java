package es.uca.gii.csi18.artyom.gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrmMain {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public FrmMain() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Gesti\u00F3n de personal y suministros para la estaci\u00F3n VDNKH");
		frame.setBounds(300, 100, 700, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mitNew = new JMenu("Nuevo");
		menuBar.add(mitNew);

		JMenuItem mitNewSoldier = new JMenuItem("Soldado");
		mitNewSoldier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IfrSoldier ifrSoldier = new IfrSoldier(null);
				ifrSoldier.setBounds(300, 100, 700, 550);
				frame.getContentPane().add(ifrSoldier);
				ifrSoldier.setVisible(true);
			}
		});
		mitNew.add(mitNewSoldier);

		JMenu mitSearch = new JMenu("Buscar");
		menuBar.add(mitSearch);

		JMenuItem mitSearchSoldier = new JMenuItem("Soldado");
		mitSearchSoldier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IfrSoldiers ifrSoldiers = new IfrSoldiers(frame);
				ifrSoldiers.setBounds(300, 100, 700, 550);
				frame.getContentPane().add(ifrSoldiers, 0);
				ifrSoldiers.setVisible(true);
			}
		});
		mitSearch.add(mitSearchSoldier);
		frame.getContentPane().setLayout(null);
	}

}

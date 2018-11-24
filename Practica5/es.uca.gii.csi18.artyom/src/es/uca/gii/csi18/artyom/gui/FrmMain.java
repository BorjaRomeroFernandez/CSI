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

		JMenuItem mitNewSoldado = new JMenuItem("Soldado");
		mitNewSoldado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IfrSoldado ifrSoldado = new IfrSoldado(null);
				ifrSoldado.setBounds(300, 100, 700, 550);
				frame.getContentPane().add(ifrSoldado);
				ifrSoldado.setVisible(true);
			}
		});
		mitNew.add(mitNewSoldado);

		JMenu mitSearch = new JMenu("Buscar");
		menuBar.add(mitSearch);

		JMenuItem mitSearchSoldado = new JMenuItem("Soldado");
		mitSearchSoldado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				IfrSoldados ifrSoldados = new IfrSoldados(frame);
				ifrSoldados.setBounds(300, 100, 700, 550);
				frame.getContentPane().add(ifrSoldados, 0);
				ifrSoldados.setVisible(true);
			}
		});
		mitSearch.add(mitSearchSoldado);
		frame.getContentPane().setLayout(null);
	}

}

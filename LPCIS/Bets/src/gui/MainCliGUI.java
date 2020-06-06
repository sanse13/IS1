package gui;

/**
 * @author Software Engineering teachers
 */

import javax.swing.*;

import domain.Event;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainCliGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonMakeBet = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonLogOut = null;
	private JButton jButtonMiPerfil = null;

	protected JLabel jLabelWelcome;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	protected static JLabel jLabelMonedero;
	
	BLFacade facade = MainGUI.getBusinessLogic();
	/**
	 * This is the default constructor
	 */
	public MainCliGUI() {
		
		super();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(575, 400);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		redibujar();
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			JToolBar toolBar = new JToolBar();
			toolBar.setBounds(0, 0, 178, 32);
			jContentPane.add(toolBar);
			toolBar.add(getBoton4());
			jContentPane.add(getJLabelWelcome());
			jContentPane.add(getJLabelMonedero());
			jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getButton());
			jContentPane.add(getPanel());
		}
		return jContentPane;
	}
	
	private JButton getBoton2() {
		if (jButtonMakeBet == null) {
			jButtonMakeBet = new JButton();
			jButtonMakeBet.setBounds(168, 189, 239, 50);
			jButtonMakeBet.setEnabled(true);
			jButtonMakeBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));
			jButtonMakeBet.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//BLFacade facade=MainGUI.getBusinessLogic();
					close();
					JFrame a = new MakeBetGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonMakeBet;
	}
	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(168, 128, 239, 50);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
					JFrame a = new FindQuestionsCliGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
	}
	
	private JButton getBoton4() {
		if (jButtonMiPerfil == null) {
			jButtonMiPerfil = new JButton();
			jButtonMiPerfil.setBounds(168, 189, 239, 50);
			jButtonMiPerfil.setText(ResourceBundle.getBundle("Etiquetas").getString("MyProfile"));
			jButtonMiPerfil.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					close();
					JFrame a = new ClienteGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonMiPerfil;
	}
	
	private JLabel getJLabelWelcome() {
		if (jLabelWelcome == null) {
			jLabelWelcome = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Welcome"));
			jLabelWelcome.setBounds(168, 67, 239, 50);
			jLabelWelcome.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelWelcome.setForeground(Color.BLACK);
			jLabelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelWelcome;
	}
	
	private JLabel getJLabelMonedero() {
		if (jLabelMonedero == null) {
			jLabelMonedero = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Balance") + " " + facade.getLoggedUsuarioDat().getMonedero().getBalance() +" €");
			jLabelMonedero.setBounds(397, 0, 162, 50);
			jLabelMonedero.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelMonedero.setForeground(Color.BLACK);
			jLabelMonedero.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelMonedero;
	}
	
	public static void setLabelMonedero(String text) {
		jLabelMonedero.setText(text);
	}
	

	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(168, 311, 239, 50);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	public void redibujar() {
		jLabelWelcome.setText(ResourceBundle.getBundle("Etiquetas").getString("Welcome")+" "+ facade.getLoggedUsuario().getNomUsuario());
		jLabelMonedero.setText(ResourceBundle.getBundle("Etiquetas").getString("Balance") + " " + facade.getLoggedUsuarioDat().getMonedero().getBalance() +" €");
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonMakeBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
		jButtonMiPerfil.setText(ResourceBundle.getBundle("Etiquetas").getString("MyProfile"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
	
	private JButton getButton() {
		if (jButtonLogOut == null) {
			jButtonLogOut = new JButton();
			jButtonLogOut.setBounds(168, 250, 239, 50);
			jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("LogOut"));
			jButtonLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jButtonLogOut_actionPerformed(e);
				}
			});
		}
		return jButtonLogOut;
	}
	
	private void jButtonLogOut_actionPerformed(ActionEvent e) {
		this.dispose();
		LogRegMainGUI b = new LogRegMainGUI();
		b.setVisible(true);
	}
	private void close() {
		this.dispose();
	}
} // @jve:decl-index=0:visual-constraint="0,0"


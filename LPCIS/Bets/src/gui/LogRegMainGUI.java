package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import domain.Event;
import domain.Usuario;
import businessLogic.BLFacade;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import businessLogic.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LogRegMainGUI extends JFrame {

	private JPanel contentPane;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JButton btnShowEvents;
	
	private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}

	/**
	 * Create the frame.
	 */
	public LogRegMainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 474, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Inicio"));
		
		userNameField = new JTextField();
		userNameField.setBounds(233, 36, 191, 20);
		contentPane.add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(233, 100, 191, 20);
		contentPane.add(passwordField);
		
		JLabel lblUserName = new JLabel("Introduce tu nombre de usuario:");
		lblUserName.setBounds(26, 39, 197, 14);
		contentPane.add(lblUserName);
		
		JLabel lblPass = new JLabel("Introduce tu contraseña:");
		lblPass.setBounds(26, 103, 157, 14);
		contentPane.add(lblPass);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					String userName = userNameField.getText();
					String passText = new String(passwordField.getPassword());
					Boolean log = facade.comprobarLogin(userName, passText);
					Usuario logged = new Usuario(userName, passText);
					if(log) {
						facade.setLoggedUsuario(logged);
						if(facade.getPermi(logged).equals(0)) {
							MainCliGUI a = new MainCliGUI();
							a.setVisible(true);
							closewindow();
						} else {
							MainGUI a = new MainGUI();
							a.setVisible(true);
							closewindow();
						}	
					} else {
						JOptionPane.showMessageDialog(null, "El usuario o la contraseña no son correctas"); //Hacemos un dialogo "El usuario o la contraseña no son correctas"
					}
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Necesita rellenar ambos campos para poder loguearse."); //Hacemos un dialogo "Necesita rellenar ambos campos para poder loguearse."
				}
				userNameField.setText("");
				passwordField.setText("");
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					String userName = userNameField.getText();
					String passText = new String(passwordField.getPassword());
					Boolean log = facade.comprobarLogin(userName, passText);
					Usuario logged = new Usuario(userName, passText);
					if(log) {
						facade.setLoggedUsuario(logged);
						if(facade.getPermi(logged).equals(0)) {
							MainCliGUI a = new MainCliGUI();
							a.setVisible(true);
							closewindow();
						} else {
							MainGUI a = new MainGUI();
							a.setVisible(true);
							closewindow();
						}	
					} else {
						JOptionPane.showMessageDialog(null, "El usuario o la contraseña no son correctas"); //Hacemos un dialogo "El usuario o la contraseña no son correctas"
					}
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Necesita rellenar ambos campos para poder loguearse."); //Hacemos un dialogo "Necesita rellenar ambos campos para poder loguearse."
				}
				userNameField.setText("");
				passwordField.setText("");
			}
		});
		
		btnLogin.setBounds(77, 179, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnReg = new JButton("Register");
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					String userName = userNameField.getText();
					String passText = new String(passwordField.getPassword());
					Boolean log = facade.comprobarReg(userName);
					if(log) {
						RegGUI b = new RegGUI();
						b.setVisible(true);
						closewindow();
					} else {
						JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese nombre en la base de datos."); //Hacemos un dialogo "El usuario o la contraseña no son correctas"
					}
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(null, "Necesita rellenar ambos campos para poder loguearse."); //Hacemos un dialogo "Necesita rellenar ambos campos para poder loguearse."
				}
				
			}
		});
		btnReg.setBounds(271, 179, 89, 23);
		contentPane.add(btnReg);
		
		btnShowEvents = new JButton(ResourceBundle.getBundle("Etiquetas").getString("LogRegMainGUI.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnShowEvents.setBounds(166, 225, 117, 29);
		contentPane.add(btnShowEvents);
		
		btnShowEvents.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new PosiblesApuestasGUI1();
				a.setVisible(true);
				closewindow();
			}
		});
		
		
	}
	private void closewindow() {
		this.dispose();
	}
}

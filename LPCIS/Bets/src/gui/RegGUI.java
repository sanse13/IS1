package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Usuario;

public class RegGUI extends JFrame {

	private JPanel contentPane;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JPasswordField passwordFieldRep;
	
	private static BLFacade appFacadeInterface;

	/**
	 * Create the frame.
	 */
	public RegGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 308);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Register");
		
		userNameField = new JTextField();
		userNameField.setBounds(233, 36, 191, 20);
		contentPane.add(userNameField);
		userNameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(233, 89, 191, 20);
		contentPane.add(passwordField);
		
		passwordFieldRep = new JPasswordField();
		passwordFieldRep.setBounds(232, 148, 192, 20);
		contentPane.add(passwordFieldRep);
		
		JLabel lblUserName = new JLabel("Introduce tu nombre de usuario:");
		lblUserName.setBounds(26, 39, 197, 14);
		contentPane.add(lblUserName);
		
		JLabel lblPass = new JLabel("Introduce tu contraseña:");
		lblPass.setBounds(26, 92, 157, 14);
		contentPane.add(lblPass);
		
		JLabel lblPassRep = new JLabel("Repite tu contraseña tu contraseña:");
		lblPassRep.setBounds(26, 151, 175, 14);
		contentPane.add(lblPassRep);
		
		JButton btnReg = new JButton("Register");
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String userName = userNameField.getText();
				String passText = new String(passwordField.getPassword());
				String passTextRep = new String(passwordFieldRep.getPassword());
				System.out.println("passText: "+passText);
				System.out.println("passTextRep: "+passTextRep);
				Usuario usuario = new Usuario(userName, passText);
				Boolean reg = facade.comprobarReg(userName);
				if(reg && passText.equals(passTextRep)) {
					if (!userName.equals("") && !passText.equals("")) {
						facade.regUsuario(usuario);
						//MainCliGUI a = new MainCliGUI();
						//a.setVisible(true);
						JOptionPane.showMessageDialog(null, "Se ha registrado de forma exitosa.");
						close(e);
					} else {
						JOptionPane.showMessageDialog(null, "Necesita rellenar ambos campos para poder loguearse."); //Hacemos un dialogo "Necesita rellenar ambos campos para poder loguearse."
					}
				} else {
					JOptionPane.showMessageDialog(null, "El usuario o la contraseña no son correctas"); //Hacemos un dialogo "El usuario o la contraseña no son correctas"
				}
			}
		});
		btnReg.setBounds(201, 208, 89, 23);
		contentPane.add(btnReg);
	}
	public void close(ActionEvent e) {
		this.dispose();
		LogRegMainGUI b = new LogRegMainGUI();
		b.setVisible(true);
	}

}

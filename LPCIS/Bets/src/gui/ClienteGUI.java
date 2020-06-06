package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Apuesta;
import domain.Event;
import domain.Usuario;

import javax.swing.UIManager;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class ClienteGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNomUsuario;
	private JLabel nomUsuarioField;
	private static JLabel lblBalanza;
	private JLabel lblMonedero;
	
	private final JList<String> list = new JList<>();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	
	private JScrollPane scrollPaneInfo = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	
	private final JComboBox<String> comboBoxApuestas = new JComboBox<>();
	
	private String selectedApuesta;
	
	private JButton btnNewButtonCerrar;
	
	
	/**
	 * Create the frame.
	 */
	public ClienteGUI() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MyProfile"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		BLFacade facade = MainGUI.getBusinessLogic();
		setBounds(100, 100, 505, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(scrollPaneInfo, BorderLayout.CENTER);
		scrollPaneInfo.setBounds(new Rectangle(227, 147, 229, 148));
		scrollPaneInfo.setViewportView(list);
		
		JLabel imagenUsu = new JLabel("");
		imagenUsu.setIcon((Icon) new ImageIcon(ClienteGUI.class.getResource("/anonimo.jpg")));
		imagenUsu.setBounds(36, 12, 95, 100);
		contentPane.add(imagenUsu);
		
		lblNomUsuario = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("UserName"));
		lblNomUsuario.setBounds(158, 82, 137, 16);
		contentPane.add(lblNomUsuario);
		
		nomUsuarioField = new JLabel(facade.getLoggedUsuario().getNomUsuario());
		nomUsuarioField.setBounds(305, 82, 151, 16);
		contentPane.add(nomUsuarioField);
		
		lblBalanza = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Balance") + " " + facade.getLoggedUsuarioDat().getMonedero().getBalance() +" €");
		lblBalanza.setBounds(367, 12, 105, 16);
		contentPane.add(lblBalanza);
		
		lblMonedero = new JLabel("");
		lblMonedero.setBounds(417, 12, 55, 16);
		contentPane.add(lblMonedero);
		
		JButton jButtonAddBalance = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AñadirBalance"));
		jButtonAddBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
				JFrame a = new AddBalanceGUI();
				a.setVisible(true);
			}
		});
		jButtonAddBalance.setBounds(343, 39, 129, 23);
		contentPane.add(jButtonAddBalance);
		comboBoxApuestas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Apuesta apuesta = new Apuesta((float) 0.0);
				BLFacade facade = MainGUI.getBusinessLogic();
				selectedApuesta = (String) comboBoxApuestas.getSelectedItem();
				for (int i = 0; i < facade.getLoggedUsuarioDat().getApuestas().size(); i++) {
					if(selectedApuesta.equals(facade.getLoggedUsuarioDat().getApuestas().get(i).getStringPron())) {
						apuesta = facade.getLoggedUsuarioDat().getApuestas().get(i);
					}
				}
				model.removeAllElements();
				model.addElement(apuesta.getEvento().getDescription());
				model.addElement(String.valueOf(apuesta.getEvento().getEventDate()));
				model.addElement("- "+apuesta.getStringPron());
				model.addElement("- "+String.valueOf(apuesta.getdApostado())+" €");
				
				list.setModel(model);
				//for (int i = 0; i < facade.getLoggedUsuarioDat().getApuestas().size(); i++) {
					//list.add(facade.getLoggedUsuarioDat().getApuestas().get(i));
					//list.add(facade.getEventFromApuesta(facade.getLoggedUsuarioDat().getApuestas().get(i)).getDescription()+"\n");
					//comboBoxApuestas.addItem((String.valueOf("-"+facade.getLoggedUsuarioDat().getApuestas().get(i).getPronostico())));
					//comboBoxApuestas.addItem((String.valueOf("-"+facade.getLoggedUsuarioDat().getApuestas().get(i).getdApostado())+" €"));
				//}
				
			}
		});
		try {
		comboBoxApuestas.setBounds(40, 175, 151, 29);
		for (int i = 0; i < facade.getLoggedUsuarioDat().getApuestas().size(); i++) {
			comboBoxApuestas.addItem(facade.getLoggedUsuarioDat().getApuestas().get(i).getStringPron());
			//list.add(facade.getEventFromApuesta(facade.getLoggedUsuarioDat().getApuestas().get(i)).getDescription()+"\n");
			//comboBoxApuestas.addItem((String.valueOf("-"+facade.getLoggedUsuarioDat().getApuestas().get(i).getPronostico())));
			//comboBoxApuestas.addItem((String.valueOf("-"+facade.getLoggedUsuarioDat().getApuestas().get(i).getdApostado())+" €"));
		}
		} catch (NullPointerException a) {
			System.out.println("null");
		}
		contentPane.add(comboBoxApuestas);
		
		JLabel lblApuestas = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Bet"));
		lblApuestas.setBounds(36, 149, 95, 14);
		contentPane.add(lblApuestas);
		
		btnNewButtonCerrar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close")); //$NON-NLS-1$ //$NON-NLS-2$
		btnNewButtonCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitModify(e);
			}
		});
		btnNewButtonCerrar.setBounds(351, 298, 117, 29);
		contentPane.add(btnNewButtonCerrar);

	}

		


	public static void setLblBalanza(String text) {
		lblBalanza.setText(text);
	}

	private void exitModify(ActionEvent e) {
		this.dispose();
		MainCliGUI c = new MainCliGUI();
		c.setVisible(true);
	}
	private void close() {
		this.dispose();
	}

}

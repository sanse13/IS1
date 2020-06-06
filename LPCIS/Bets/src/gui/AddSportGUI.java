package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.*;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AddSportGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldDeporte;

	
	public AddSportGUI() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldDeporte = new JTextField();
		textFieldDeporte.setBounds(123, 80, 198, 20);
		contentPane.add(textFieldDeporte);
		textFieldDeporte.setColumns(10);
		
		JLabel JLabelDeporte = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sport"));
		JLabelDeporte.setBounds(97, 55, 46, 14);
		contentPane.add(JLabelDeporte);
		
		JButton btnAddDeporte = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddDeporte"));
		btnAddDeporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				String deporte = textFieldDeporte.getText();
				List<Deporte> deportes = facade.getDeportes();
				if (deporte.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe introducir un nombre válido para un deporte.");
				} else {
					boolean exists=false;
					for(int i=0; i<deportes.size(); i++) {
						if (deporte.equals(deportes.get(i).getTipo())) {
							exists = true;
						}
					}
					if (exists==false) {
						facade.createDeporte(deporte);
						JOptionPane.showMessageDialog(null, "El deporte se ha registrado de forma exitosa en la base de datos.");
					} else {
						JOptionPane.showMessageDialog(null, "El deporte introducido ya existe en la base de datos.");
					}
					
					
				}
			}
		});
		btnAddDeporte.setBounds(154, 158, 135, 23);
		contentPane.add(btnAddDeporte);
		
		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				exitModify(e);
				
			}
		});
		btnBack.setBounds(176, 215, 89, 23);
		contentPane.add(btnBack);
	}
	
	private void exitModify(ActionEvent e) {
		this.dispose();
		MoreOptions c = new MoreOptions();
		c.setVisible(true);
	}
}

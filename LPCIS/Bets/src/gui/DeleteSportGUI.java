package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Deporte;
import domain.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class DeleteSportGUI extends JFrame {

	private JPanel contentPane;

	private JComboBox<Deporte> comboBoxDeportes = new JComboBox<Deporte>();
	
	private Deporte selectedSport;
	private JButton btnClose;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JRadioButton rdtbnYes;
	private JRadioButton rdbtnNo = new JRadioButton("No");
	private JButton btnDelete;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public DeleteSportGUI() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("BorrarDeporte"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 508, 407);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EscogerDeporte"));
		lblNewLabel.setBounds(6, 60, 148, 16);
		contentPane.add(lblNewLabel);
		
		comboBoxDeportes.setBounds(166, 56, 169, 27);
		contentPane.add(comboBoxDeportes);
		
		btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(327, 327, 136, 40);
		contentPane.add(btnClose);
		
		rdtbnYes = new JRadioButton("Si");
		buttonGroup.add(rdtbnYes);

		buttonGroup.add(rdbtnNo);

		rdtbnYes.setBounds(36, 202, 79, 23);
		contentPane.add(rdtbnYes);
		buttonGroup.add(rdbtnNo);
		
		
		rdbtnNo.setBounds(129, 202, 79, 23);
		contentPane.add(rdbtnNo);
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(36, 144, 250, 16);
		contentPane.add(lblNewLabel_1);
		lblNewLabel_1.setVisible(false);
		
		rdbtnNo.setVisible(false);
		btnDelete = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Delete"));
		btnDelete.setBounds(36, 333, 148, 29);
		contentPane.add(btnDelete);
		
		rdtbnYes.setVisible(false);
		
		btnDelete.setEnabled(false);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		List<Deporte> sportList = facade.getDeportes();
		
		for (int i = 0; i < sportList.size(); i++) comboBoxDeportes.addItem(sportList.get(i));
		
		selectedSport = (Deporte) comboBoxDeportes.getSelectedItem();
		
		comboBoxDeportes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSport = (Deporte) comboBoxDeportes.getSelectedItem();
				
				lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("BorrarDeporteSeleccionado")+selectedSport+"?");
				
				lblNewLabel_1.setVisible(true);
				rdbtnNo.setVisible(true);
				rdtbnYes.setVisible(true);
				
			}
		});
		
		
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
				
			}
		});
		
		rdtbnYes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDelete.setEnabled(true);
				
			}
		});
		
		rdbtnNo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDelete.setEnabled(false);
				
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				if (rdtbnYes.getText().equals("Si")){
					Usuario user = facade.getLoggedUsuarioDat();
					facade.deleteSport(selectedSport, user);
					JOptionPane.showMessageDialog(null, "El deporte "+selectedSport+" ha sido borrado con exito.");
					close();
				} else {
					JOptionPane.showMessageDialog(null, "No se ha borrado el deporte");
				}
				
			}
		});
		
	}
	
	private void close() {
		this.dispose();
		this.setVisible(false);
		MoreOptions c = new MoreOptions();
		c.setVisible(true);
		
	}

}

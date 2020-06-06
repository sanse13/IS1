package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class MoreOptions extends JFrame {

	private JPanel contentPane;
	private JButton btnDeleteEvent;
	private JButton btnModifyEvent;
	private JButton btnNewButton_1;
	private JButton btnAddDeporte;
	private JButton btnDeleteSport;

	/**
	 * Create the frame.
	 */
	public MoreOptions() {
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Options"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 452, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnModifyProno = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ModifyProno"));
		btnModifyProno.setBounds(5, 165, 442, 54);
		btnModifyProno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				JFrame newFrame = new ModificarPronostico();
				newFrame.setVisible(true);
			}
		});
		
		btnAddDeporte = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddDeporte"));
		btnAddDeporte.setBounds(5, 5, 442, 54);
		btnAddDeporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
				JFrame newFrame = new AddSportGUI();
				newFrame.setVisible(true);
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnAddDeporte);
		
		btnDeleteEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
		btnDeleteEvent.setBounds(5, 58, 442, 54);
		contentPane.add(btnDeleteEvent);
		btnDeleteEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
				JFrame newFrame = new DeleteEvent();
				newFrame.setVisible(true);
				
			}
		});
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ModifyQuestion"));
		btnNewButton.setBounds(5, 111, 442, 54);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
				JFrame newFrame = new ModificarPreguntaGUI();
				newFrame.setVisible(true);
			}
		});
		contentPane.add(btnNewButton);
		contentPane.add(btnModifyProno);
		
		JButton button = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ResolucionApuesta"));
		button.setBounds(5, 218, 442, 54);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
				JFrame Resolucion = new ResolucionApuestaGUI();
				Resolucion.setVisible(true);
			}
		});
		contentPane.add(button);
		
		btnModifyEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ModifyEvent"));
		btnModifyEvent.setBounds(5, 271, 442, 54);
		contentPane.add(btnModifyEvent);
		
		btnModifyEvent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
				JFrame newFrame = new ModificarEvento();
				newFrame.setVisible(true);
				
			}
		});
		
		btnNewButton_1 = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GoBack"));
		btnNewButton_1.setBounds(5, 397, 442, 59);
		contentPane.add(btnNewButton_1);
		
		btnDeleteSport = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteSport")); //$NON-NLS-1$ //$NON-NLS-2$
		btnDeleteSport.setBounds(5, 326, 440, 59);
		contentPane.add(btnDeleteSport);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				closeButton(e);
				
			}
		});
		
		btnDeleteSport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame c = new DeleteSportGUI();
				close();
				c.setVisible(true);
				
			}
		});
		

		
	}
	public void closeButton(ActionEvent e) {
		this.dispose();
		MainGUI c = new MainGUI();
		c.setVisible(true);
	}
	
	private void close() {
		this.dispose();
	}
}
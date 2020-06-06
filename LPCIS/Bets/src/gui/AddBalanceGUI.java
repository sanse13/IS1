package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import businessLogic.BLFacade;
import domain.Usuario;

import javax.swing.JSlider;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


public class AddBalanceGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldBalance;

	BLFacade facade = MainGUI.getBusinessLogic();
	/**
	 * Create the frame.
	 */
	public AddBalanceGUI() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("AñadirBalance"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final JSlider sliderBalance = new JSlider(10, 1000, 10);
		sliderBalance.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				float slider = (float) sliderBalance.getValue()/10;
				String value = slider+"";
				textFieldBalance.setText(value);
			}
		});
		sliderBalance.setBounds(113, 66, 200, 26);
		contentPane.add(sliderBalance);
		
		JButton jButtonAddBalance = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AñadirBalance"));
		jButtonAddBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String intermedio="";
				Usuario usuario = new Usuario(intermedio, intermedio);
				if (textFieldBalance.getText().equals(1.0)) {
					JOptionPane.showMessageDialog(null, "Hay que añadir más balance");
				} else {
					usuario = facade.addBalance(facade.getLoggedUsuario(), Float.parseFloat(textFieldBalance.getText()));
					ClienteGUI.setLblBalanza("Balance "+usuario.getMonedero().getBalance().toString()+" €");
					MainCliGUI.setLabelMonedero("Balance "+usuario.getMonedero().getBalance().toString()+" €");
				}
				facade.setLoggedUsuario(usuario);
			}
		});
		jButtonAddBalance.setBounds(268, 186, 119, 23);
		contentPane.add(jButtonAddBalance);
		
		textFieldBalance = new JTextField();
		textFieldBalance.setBounds(331, 66, 39, 20);
		contentPane.add(textFieldBalance);
		textFieldBalance.setColumns(10);
		
		JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnNewButton.setBounds(106, 186, 105, 23);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
	}
	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		ClienteGUI d = new ClienteGUI();
		d.setVisible(true);
	}
}

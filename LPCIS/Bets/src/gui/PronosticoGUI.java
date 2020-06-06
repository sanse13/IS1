package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Pronostico;
import domain.Question;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class PronosticoGUI extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textFieldRatio;
	private JTextField textFieldPronostico;

	/**
	 * Create the frame.
	 */
	public PronosticoGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("AñadirPronostico"));
		
		final JSlider sliderRatio = new JSlider(10, 100, 10);
		sliderRatio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				float slider = (float) sliderRatio.getValue()/10;
				String value = slider+"";
				textFieldRatio.setText(value);
			}
		});
		sliderRatio.setBounds(183, 56, 200, 16);
		contentPane.add(sliderRatio);
		
		JLabel lblIntroduzcaElPronostico = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IntPronostico"));
		lblIntroduzcaElPronostico.setBounds(12, 12, 194, 15);
		contentPane.add(lblIntroduzcaElPronostico);
		
		JLabel lblIntroduzcaElRatio = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("IntRatio"));
		lblIntroduzcaElRatio.setBounds(12, 56, 150, 15);
		contentPane.add(lblIntroduzcaElRatio);
		
		JButton btnAadirPronostico = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AñadirPronostico"));
		btnAadirPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldPronostico.getText().equals("") && sliderRatio.getValue()>10) {
					BLFacade facade = MainGUI.getBusinessLogic();
					Question pregunta = facade.getSelectedQuestion();
					System.out.println(pregunta);
					Pronostico pronos = new Pronostico((float) sliderRatio.getValue()/10, textFieldPronostico.getText(), pregunta);
					facade.addPronostico(pronos);
					JOptionPane.showMessageDialog(null, "El pronóstico se ha añadido con éxito.");
					close(e);
				} else {
					JOptionPane.showMessageDialog(null, "Los valores seleccionados no son válidos.");
				}
			}

		});
		btnAadirPronostico.setBounds(134, 172, 187, 25);
		contentPane.add(btnAadirPronostico);
		
		textFieldRatio = new JTextField();
		textFieldRatio.setBounds(393, 52, 31, 20);
		contentPane.add(textFieldRatio);
		textFieldRatio.setColumns(10);
		
		textFieldPronostico = new JTextField();
		textFieldPronostico.setBounds(192, 9, 214, 19);
		contentPane.add(textFieldPronostico);
		textFieldPronostico.setColumns(10);
		
		
			
		}
	public void close(ActionEvent e) {
		this.dispose();
	}
}

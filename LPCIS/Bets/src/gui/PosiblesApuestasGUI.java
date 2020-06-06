package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.ComboPopup;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import configuration.*;
import domain.Event;
import domain.Pronostico;
import domain.Question;

public class PosiblesApuestasGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldAño;
	
	private JComboBox<String> comboBox = new JComboBox<String>();
	
	private Vector<String> meses = new Vector<String>();
	private List<Question> questionList;
	
	private JButton btnShowEvents;
	private JLabel lblNewLabel_2;
	private JTextField textFieldDay;
	private JComboBox<Event> comboBoxEvents;
	private JLabel noHayEventos;
	private JLabel eventsLabel;
	private JLabel questionsLabel;
	private JComboBox<Question> comboBoxQuestions;
	
	private Event selectedEvent;
	private JLabel noHayPreguntas;
	private JButton btnClose;
	private JComboBox<Pronostico> comboBoxPronosticos;
	private JLabel labelPronostico;
	private JLabel noHayPronostico;
	
	private Question selectedQuestion;


	/**
	 * Create the frame.
	 */
	public PosiblesApuestasGUI() {
		setTitle("Eventos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 331);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Elige el año: ");
		lblNewLabel.setBounds(6, 22, 90, 16);
		contentPane.add(lblNewLabel);
		
		textFieldAño = new JTextField();
		textFieldAño.setBounds(88, 17, 130, 26);
		contentPane.add(textFieldAño);
		textFieldAño.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Elige el mes:");
		lblNewLabel_1.setBounds(246, 22, 90, 16);
		contentPane.add(lblNewLabel_1);
		
		comboBox.setBounds(338, 18, 130, 27);
		contentPane.add(comboBox);
		
		btnShowEvents = new JButton("Ver eventos");
		btnShowEvents.setBounds(246, 63, 141, 30);
		contentPane.add(btnShowEvents);
		
		lblNewLabel_2 = new JLabel("Elige el dia:");
		lblNewLabel_2.setBounds(6, 72, 107, 16);
		contentPane.add(lblNewLabel_2);
		
		textFieldDay = new JTextField();
		textFieldDay.setBounds(88, 67, 130, 26);
		contentPane.add(textFieldDay);
		textFieldDay.setColumns(10);
		
		comboBoxEvents = new JComboBox<Event>();
		comboBoxEvents.setBounds(6, 131, 185, 27);
		contentPane.add(comboBoxEvents);
		
		noHayEventos = new JLabel("No hay eventos en esta fecha");
		noHayEventos.setBounds(6, 110, 199, 16);
		noHayEventos.setForeground(Color.red);
		contentPane.add(noHayEventos);
		
		eventsLabel = new JLabel("Eventos");
		eventsLabel.setBounds(49, 170, 107, 16);
		contentPane.add(eventsLabel);
		
		questionsLabel = new JLabel("Preguntas");
		questionsLabel.setBounds(306, 170, 120, 16);
		contentPane.add(questionsLabel);
		
		comboBoxQuestions = new JComboBox<Question>();
		comboBoxQuestions.setBounds(246, 131, 199, 27);
		contentPane.add(comboBoxQuestions);
		
		noHayPreguntas = new JLabel("El evento no tiene preguntas");
		noHayPreguntas.setBounds(246, 110, 199, 16);
		noHayPreguntas.setForeground(Color.red);
		contentPane.add(noHayPreguntas);
		
		btnClose = new JButton("Close");
		btnClose.setBounds(351, 244, 117, 29);
		contentPane.add(btnClose);
		
		comboBoxPronosticos = new JComboBox<Pronostico>();
		comboBoxPronosticos.setBounds(6, 223, 193, 27);
		contentPane.add(comboBoxPronosticos);
		
		labelPronostico = new JLabel("Pronosticos");
		labelPronostico.setBounds(59, 249, 107, 16);
		contentPane.add(labelPronostico);
		
		noHayPronostico = new JLabel("No hay pronosticos para la pregunta");
		noHayPronostico.setBounds(6, 206, 241, 16);
		contentPane.add(noHayPronostico);
		noHayPronostico.setForeground(Color.red);
		
		noHayPreguntas.setVisible(false);
		questionsLabel.setVisible(false);
		comboBoxQuestions.setVisible(false);
		eventsLabel.setVisible(false);
		noHayEventos.setVisible(false);
		comboBoxEvents.setVisible(false);
		noHayPronostico.setVisible(false);
		labelPronostico.setVisible(false);
		comboBoxPronosticos.setVisible(false);
		
		meses.add("Enero");
		meses.add("Febrero");
		meses.add("Marzo");
		meses.add("Abril");
		meses.add("Mayo");
		meses.add("Junio");
		meses.add("Julio");
		meses.add("Agosto");
		meses.add("Septiembre");
		meses.add("Octubre");
		meses.add("Noviembre");
		meses.add("Diciembre");
		
		addMonths(meses);
		
		btnShowEvents.addActionListener(new ActionListener() {
			
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				noHayPronostico.setVisible(false);
				labelPronostico.setVisible(false);
				comboBoxPronosticos.setVisible(false);
				noHayPreguntas.setVisible(false);
				comboBoxEvents.setVisible(false);
				comboBoxQuestions.setVisible(false);
				eventsLabel.setVisible(false);
				questionsLabel.setVisible(false);
				
				
				BLFacade facade = MainGUI.getBusinessLogic();
				
				comboBoxEvents.removeAllItems();
				comboBoxQuestions.removeAllItems();
				comboBoxPronosticos.removeAllItems();
				
				if (textFieldAño.getText().equals("") ||
						textFieldDay.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Los campos deben estar rellenos.");
				}
				
				try {
					int año = Integer.parseInt(textFieldAño.getText());
					int dia = Integer.parseInt(textFieldDay.getText());
					String selectedMes = (String) comboBox.getSelectedItem();
					int mes = getMonth(selectedMes);
					Date date = UtilDate.newDate(año, mes, dia);
					List<Event> eventList = facade.getEvents(date);
					for (int i = 0; i < eventList.size(); i++) {
						comboBoxEvents.addItem(eventList.get(i));
					}
					
					
					if (eventList.size() > 0) {
						comboBoxQuestions.removeAllItems();
						noHayEventos.setVisible(false);
						comboBoxEvents.setVisible(true);
						eventsLabel.setVisible(true);
						comboBoxQuestions.setVisible(true);
						questionsLabel.setVisible(true);
						
						selectedEvent = (Event) comboBoxEvents.getSelectedItem();
						
						questionList = facade.getQuestionsFromEvent(selectedEvent);
						if (questionList.size() == 0) {
							noHayPreguntas.setVisible(true);
							comboBoxQuestions.setVisible(false);
							questionsLabel.setVisible(false);
							
							
							
						} else {
						
							comboBoxQuestions.setVisible(true);
							questionsLabel.setVisible(true);
							noHayPreguntas.setVisible(false);
							for (int s = 0; s < questionList.size(); s++) 
								comboBoxQuestions.addItem(questionList.get(s));
							
							selectedQuestion = (Question) comboBoxQuestions.getSelectedItem();
							
							List<Pronostico> pronosList = facade.getPronosticosFromQuestion(selectedQuestion);
							if (pronosList.size() > 0) {
								for (int k = 0; k < pronosList.size(); k++)
									comboBoxPronosticos.addItem(pronosList.get(k));
								comboBoxPronosticos.setVisible(true);
								labelPronostico.setVisible(true);
							} else {
								noHayPronostico.setVisible(true);
								labelPronostico.setVisible(false);
								comboBoxPronosticos.setVisible(false);
							}
									
							
						}
					}
					else {
						comboBoxEvents.removeAllItems();
						comboBoxQuestions.removeAllItems();

						comboBoxEvents.setVisible(false);
						noHayEventos.setVisible(true);
						eventsLabel.setVisible(false);
						comboBoxQuestions.setVisible(false);
						questionsLabel.setVisible(false);
					}
					
					
				} catch (NumberFormatException a) {
					JOptionPane.showMessageDialog(null, "Año y dia deben estar correctamente escritos.");
				}
				
			}
		});
		
		comboBoxEvents.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					comboBoxQuestions.removeAllItems();
					BLFacade facade = MainGUI.getBusinessLogic();
	
					selectedEvent = (Event) comboBoxEvents.getSelectedItem();
					
					questionList = facade.getQuestionsFromEvent(selectedEvent);
					if (questionList.size() == 0) {
						noHayPreguntas.setVisible(true);
						comboBoxQuestions.setVisible(false);
						questionsLabel.setVisible(false);
						comboBoxPronosticos.setVisible(false);
						labelPronostico.setVisible(false);
						noHayPronostico.setVisible(false);
					} else {
					
						comboBoxQuestions.setVisible(true);
						questionsLabel.setVisible(true);
						noHayPreguntas.setVisible(false);
						for (int s = 0; s < questionList.size(); s++) {
							comboBoxQuestions.addItem(questionList.get(s));
						}
					}
					
				} catch (NullPointerException a ) {
					
				}
			}
		});
		
		comboBoxQuestions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				comboBoxPronosticos.removeAllItems();
				
				try {
				
				selectedQuestion = (Question)comboBoxQuestions.getSelectedItem();
				
				List<Pronostico> pronosList = facade.getPronosticosFromQuestion(selectedQuestion);
				
				
				if (pronosList.size() > 0) {
					System.out.println("he entrado");
					for (int i = 0; i < pronosList.size(); i++) 
						comboBoxPronosticos.addItem(pronosList.get(i));
					
					comboBoxPronosticos.setVisible(true);
					labelPronostico.setVisible(true);
					noHayPronostico.setVisible(false);
				} else {
					comboBoxPronosticos.setVisible(false);
					labelPronostico.setVisible(false);
					noHayPronostico.setVisible(true);
				}
				
				} catch (NullPointerException a) {
					
				}
				
				
			}
		});
		
		comboBoxPronosticos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				
				
			}
		});
		
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				closeWindow(e);
			}
		});
		
		
	}
	
	public void addMonths(Vector<String> meses) {
		for (int i = 0; i < meses.size(); i ++) {
			comboBox.addItem(meses.get(i));
		}
	}
	
	public int getMonth(String mes) {
		int res;
		switch (mes) {
		case "Enero":
			res = 0;
			break;
			
		case "Febrero":
			res = 1;
			break;
		case "Marzo":
			res = 2;
			break;
		case "Abril":
			res = 3;
			break;
		case "Mayo":
			res = 4;
			break;
		case "Junio":
			res = 5;
			break;
		case "Julio":
			res = 6;
			break;
		case "Agosto":
			res = 7;
			break;
		case "Septiembre":
			res = 8;
			break;
		case "Octubre":
			res = 9;
			break;
		case "Noviembre":
			res = 10;
			break;

		default:
			res = 11;
			break;
		}
		return res;
	}

	public void closeWindow(ActionEvent e) {
		this.setVisible(false);
	}

}

package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.objectdb.o.JOP;
import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Deporte;
import domain.Event;
import domain.Pronostico;
import domain.Question;
import domain.Usuario;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class MakeBetGUI extends JFrame {

	private JPanel contentPane;
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private DefaultTableModel tableModelEvents;
	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"), };
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private JTable tableEvents = new JTable();
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JList<Event> list = new JList<>();
	private DefaultListModel<Event> model = new DefaultListModel<>();

	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();

	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblQuestions;
	private JLabel lblEuros;
	private JLabel lblBetAmount;
	// private JLabel lblRatio;

	private Event selectedEvent;
	private Question selectedQuestion;
	private Pronostico selectedPronostico;
	private static Deporte deporteSeleccionado;
	private String pron;

	private final JList<Question> listAboutQuestions = new JList<Question>();
	private DefaultListModel<Question> modelQuestion = new DefaultListModel<Question>();
	private JLabel lblForecast;
	private JTextField textField;

	private JButton btnBet;
	private final JComboBox<Pronostico> comboBoxAnswers = new JComboBox<Pronostico>();
	private JButton btnNewButton;
	private Float ratio;

	/**
	 * Create the frame.
	 */
	public MakeBetGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Making bet");
		setBounds(100, 100, 781, 468);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(scrollPaneEvents, BorderLayout.CENTER);
		scrollPaneEvents.setBounds(new Rectangle(390, 147, 346, 150));
		scrollPaneEvents.setViewportView(list);
		list.setModel(model);

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				modelQuestion.removeAllElements();
				listAboutQuestions.removeAll();
				// lblRatio.setVisible(false);
				lblForecast.setVisible(false);
				comboBoxAnswers.removeAllItems();
				comboBoxAnswers.setVisible(false);
				lblQuestions.setVisible(false);
				listAboutQuestions.setVisible(false);
				btnBet.setVisible(false);
				textField.setVisible(false);
				lblBetAmount.setVisible(false);
				lblEuros.setVisible(false);
				selectedEvent = list.getSelectedValue();
				lblQuestions.setVisible(true);
				listAboutQuestions.setVisible(true);

				// añadir las questions al listQuestions
				try {
					List<Question> questionList = facade.getQuestionsFromEvent(selectedEvent);
					for (int i = 0; i < questionList.size(); i++) {
						modelQuestion.addElement(questionList.get(i));
					}
					listAboutQuestions.setModel(modelQuestion);
				} catch (Exception b) {
				}

			}
		});
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				BLFacade facade = MainGUI.getBusinessLogic();
				model.removeAllElements();
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
					jCalendar1.setCalendar(calendarMio);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					try {

						List<Event> events = facade.getEventFromDeporte(jCalendar1.getDate(), deporteSeleccionado);

						if (events.isEmpty()) {
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarMio.getTime()));
							model.removeAllElements();
							lblQuestions.setVisible(false);
							listAboutQuestions.setVisible(false);
						} else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						for (domain.Event ev : events) {
							model.addElement(ev);
						}

					} catch (Exception e1) {
						paintDaysWithSports(jCalendar1);
						jLabelQueries.setText(e1.getMessage());
					}
					paintDaysWithSports(jCalendar1);
				}
				paintDaysWithSports(jCalendar1);
			}
		});
		this.getContentPane().add(jCalendar1, null);

		lblNewLabel = new JLabel("Events:");
		lblNewLabel.setBounds(291, 126, 173, 16);
		contentPane.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Event date");
		lblNewLabel_1.setBounds(40, 25, 149, 16);
		contentPane.add(lblNewLabel_1);

		lblQuestions = new JLabel("Questions:");
		lblQuestions.setBounds(40, 221, 131, 16);
		contentPane.add(lblQuestions);
		listAboutQuestions.setBounds(40, 249, 225, 48);
		contentPane.add(listAboutQuestions);

		lblForecast = new JLabel("Select your forecast:");
		lblForecast.setBounds(18, 315, 233, 16);
		lblForecast.setVisible(false);
		contentPane.add(lblForecast);

		lblBetAmount = new JLabel("Bet amount:");
		lblBetAmount.setBounds(199, 316, 102, 16);
		lblBetAmount.setVisible(false);
		contentPane.add(lblBetAmount);

		textField = new JTextField();
		textField.setBounds(191, 345, 79, 26);
		textField.setVisible(false);
		contentPane.add(textField);
		textField.setColumns(10);

		lblEuros = new JLabel("€");
		lblEuros.setBounds(275, 352, 61, 16);
		lblEuros.setVisible(false);
		contentPane.add(lblEuros);

		btnBet = new JButton("Bet");
		btnBet.setBounds(352, 361, 210, 54);
		btnBet.setVisible(false);
		contentPane.add(btnBet);

		lblEuros = new JLabel("€");
		lblEuros.setBounds(274, 352, 61, 16);
		lblEuros.setVisible(false);
		contentPane.add(lblEuros);
		comboBoxAnswers.setBounds(41, 346, 102, 29);
		comboBoxAnswers.setVisible(false);
		contentPane.add(comboBoxAnswers);

		/*
		 * final JLabel lblRatio = new
		 * JLabel(ResourceBundle.getBundle("Etiquetas").getString("IntRatio"));
		 * lblRatio.setBounds(434, 258, 143, 29); contentPane.add(lblRatio);
		 */

		btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitModify(e);
			}
		});
		btnNewButton.setBounds(636, 386, 117, 29);
		contentPane.add(btnNewButton);
		lblQuestions.setVisible(false);
		listAboutQuestions.setVisible(false);

		listAboutQuestions.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				lblForecast.setVisible(true);
				comboBoxAnswers.setVisible(true);
				// lblRatio.setVisible(true);
				// lblBetAmount.setVisible(true);
				// lblEuros.setVisible(true);
				// textField.setVisible(true);
				// btnBet.setVisible(true);
				selectedQuestion = listAboutQuestions.getSelectedValue();
				List<Pronostico> pronosticos = null;
				BLFacade facade = MainGUI.getBusinessLogic();
				comboBoxAnswers.removeAllItems();
				if (!facade.getQuestionsFromEvent(selectedEvent).isEmpty()) {
					try {
						pronosticos = facade.getPronosticosFromQuestion(selectedQuestion);
						for (int i = 0; i < pronosticos.size(); i++) {
							System.out.println("he añadido " + pronosticos.get(i).getPronostico());
							comboBoxAnswers.addItem(pronosticos.get(i));
						}
						if (pronosticos.isEmpty()) {
							// lblRatio.setVisible(false);
							lblBetAmount.setVisible(false);
							lblEuros.setVisible(false);
							textField.setVisible(false);
							btnBet.setVisible(false);
						}
					} catch (Exception a) {
					}
				}
			}
		});

		comboBoxAnswers.addActionListener(new ActionListener() {
			// controlar que no se pueda crear 2 pronosticos con el mismo String
			@Override
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Float ratio = (float) 0.0;
				lblBetAmount.setVisible(true);
				lblEuros.setVisible(true);
				textField.setVisible(true);
				btnBet.setVisible(true);
				selectedPronostico = (Pronostico) comboBoxAnswers.getSelectedItem();

				try {
					if (!facade.getQuestionsFromEvent(selectedEvent).isEmpty()) {
						ratio = facade.getRatioPronostico(selectedQuestion, selectedPronostico);
					}
					// if (ratio != null)
					// lblRatio.setText("El ratio es de " + ratio.toString());
				} catch (Exception c) {
				}
			}
		});

		btnBet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				BLFacade facade = MainGUI.getBusinessLogic();

				System.out.println("El pronostico elegido es: " + selectedPronostico.getPronostico());
				Usuario user = facade.getLoggedUsuarioDat();
				ratio = facade.getRatioPronostico(selectedQuestion, selectedPronostico);
				if (textField == null) {
					JOptionPane.showMessageDialog(null, "Debes añadir dinero");
					return;
				}
				Float cantMoney = null;
				try {
					cantMoney = Float.parseFloat(textField.getText());
					if (textField == null)
						JOptionPane.showMessageDialog(null, "No hay cantidad apostada");
					Float userMoney = user.getMonedero().getBalance();
					if (userMoney < cantMoney) {
						JOptionPane.showMessageDialog(null, "No dispones del dinero suficiente para apostar.");
					} else {
						Float newMoney = facade.quitarDinero(user, cantMoney);

						facade.createBet(user, cantMoney, selectedPronostico, ratio, selectedEvent, selectedQuestion);
						MainCliGUI.setLabelMonedero(newMoney.toString());
						// ClienteGUI.setLblBalanza(newMoney.toString());
						JOptionPane.showMessageDialog(null, "Apuesta realizada.");
						if (userMoney != null && userMoney.toString().equals("0")) {
							JOptionPane.showMessageDialog(null, "La cantidad apostada tiene que ser superior a 0");
							System.out.println(userMoney.toString());
							System.out.println(userMoney.toString());
						}
					}
				} catch (NumberFormatException ee) {
					JOptionPane.showMessageDialog(null, "Numero no valido");
				}
			}
		});

		JLabel lblDeportes = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sport"));
		lblDeportes.setBounds(291, 49, 61, 16);
		contentPane.add(lblDeportes);

		final JComboBox<Deporte> comboBoxDeporte = new JComboBox<Deporte>();
		comboBoxDeporte.setBounds(358, 45, 182, 27);
		getContentPane().add(comboBoxDeporte);
		final BLFacade facade = MainGUI.getBusinessLogic();
		java.util.List<Deporte> deportes = facade.getDeportes();
		for (int i = 0; i < deportes.size(); i++) {
			Deporte dep = deportes.get(i);
			comboBoxDeporte.addItem(dep);
		}
		deporteSeleccionado = (Deporte) comboBoxDeporte.getSelectedItem();
		comboBoxDeporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deporteSeleccionado = (Deporte) comboBoxDeporte.getSelectedItem();
				if (!facade.getEventsFromDeporte(deporteSeleccionado).isEmpty()) {
					try {
						List<Event> eventos = facade.getEventsFromDeporte(deporteSeleccionado);
						for (int i = 0; i < eventos.size(); i++) {
							System.out.println("Estos son los eventos " + eventos.get(i).getEventDate());
						}
						if (eventos.isEmpty()) {
							System.out.println("No hay eventos para este deporte");
						}
					} catch (Exception d) {
					}
				}
				paintDaysWithSports(jCalendar1);
			}
		});

	}

	private void exitModify(ActionEvent e) {
		this.dispose();
		MainCliGUI c = new MainCliGUI();
		c.setVisible(true);
	}

	public static void paintDaysWithSports(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = MainGUI.getBusinessLogic();

		try {

			Vector<Date> dates = facade.getEventsMonthDeporte(jCalendar.getDate(), deporteSeleccionado);
			Vector<Date> dates1 = facade.getEventsMonthDeporteOpposite(jCalendar.getDate(), deporteSeleccionado);

			Calendar calendar = jCalendar.getCalendar();

			int month = calendar.get(Calendar.MONTH);
			int today = calendar.get(Calendar.DAY_OF_MONTH);

			calendar.set(Calendar.DAY_OF_MONTH, 1);
			int offset = calendar.get(Calendar.DAY_OF_WEEK);

			if (Locale.getDefault().equals(new Locale("es")))
				offset += 4;
			else
				offset += 5;

			for (Date d : dates1) {
				calendar.setTime(d);
				System.out.println(d);

				// Obtain the component of the day in the panel of the DayChooser of the
				// JCalendar.
				// The component is located after the decorator buttons of "Sun", "Mon",... or
				// "Lun", "Mar"...,
				// the empty days before day 1 of month, and all the days previous to each day.
				// That number of components is calculated with "offset" and is different in
				// English and Spanish
				// Component o=(Component)
				// jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);;
				Component o = (Component) jCalendar.getDayChooser().getDayPanel()
						.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
				o.setBackground(null);
			}

			for (Date d : dates) {
				calendar.setTime(d);
				System.out.println(d);

				// Obtain the component of the day in the panel of the DayChooser of the
				// JCalendar.
				// The component is located after the decorator buttons of "Sun", "Mon",... or
				// "Lun", "Mar"...,
				// the empty days before day 1 of month, and all the days previous to each day.
				// That number of components is calculated with "offset" and is different in
				// English and Spanish
				// Component o=(Component)
				// jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);;
				Component o = (Component) jCalendar.getDayChooser().getDayPanel()
						.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
				o.setBackground(Color.CYAN);
			}

			calendar.set(Calendar.DAY_OF_MONTH, today);
			calendar.set(Calendar.MONTH, month);
		} catch (Exception e8) {
		}
	}
}

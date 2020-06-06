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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Deporte;
import domain.Event;
import domain.Question;
import domain.Usuario;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class DeleteEvent extends JFrame {

	private JPanel contentPane;
	private JButton btnDeleteEvent;
	private Event selectedEvent = null;
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private final JList<Event> list = new JList<>();
	private DefaultListModel<Event> model = new DefaultListModel<>();
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private static Deporte deporteSeleccionado;

	/**
	 * Create the frame.
	 */
	public DeleteEvent() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 683, 426);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(scrollPaneEvents, BorderLayout.CENTER);
		scrollPaneEvents.setBounds(new Rectangle(390, 147, 346, 150));
		scrollPaneEvents.setViewportView(list);
		list.setModel(model);

		btnDeleteEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Delete"));
		btnDeleteEvent.setEnabled(false);
		btnDeleteEvent.setBounds(168, 321, 271, 55);
		contentPane.add(btnDeleteEvent);
		
		
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedEvent = list.getSelectedValue();
				if(selectedEvent!=null) {
					btnDeleteEvent.setEnabled(true);
				}
			}
		});

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
							btnDeleteEvent.setEnabled(false);
						} else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarMio.getTime()));
						for (domain.Event ev : events) {
							model.addElement(ev);
						}

					} catch (Exception e1) {
						paintDaysWithSports(jCalendar1);
					}
					paintDaysWithSports(jCalendar1);
				}
				paintDaysWithSports(jCalendar1);
			}
		});
		this.getContentPane().add(jCalendar1, null);
		scrollPaneEvents.setBounds(new Rectangle(294, 112, 346, 150));

		JButton btnNewButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnNewButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitModify(e);
			}
		});
		btnNewButtonClose.setBounds(504, 347, 117, 29);
		contentPane.add(btnNewButtonClose);

		btnDeleteEvent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Usuario user = facade.getLoggedUsuarioDat();
				System.out.println("usuario es: " + user.getNomUsuario());
				facade.deleteEvent(selectedEvent, user);
				btnDeleteEvent.setEnabled(false);
				btnDeleteEvent.setText(ResourceBundle.getBundle("Etiquetas").getString("Delete"));
				DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
				model.removeAllElements();
				System.out.println(jCalendar1.getLocale());
				try {

					List<Event> events = facade.getEventFromDeporte(jCalendar1.getDate(), deporteSeleccionado);

					if (events.isEmpty()) {
						jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
								+ dateformat1.format(calendarMio.getTime()));
						model.removeAllElements();
						btnDeleteEvent.setEnabled(false);
					}
					for (domain.Event ev : events) {
						model.addElement(ev);
					}
					paintDaysWithSports(jCalendar1);
				} catch (Exception e1) {
					paintDaysWithSports(jCalendar1);
				}
			}
		});

		final JComboBox<Deporte> comboBoxDeporte = new JComboBox<Deporte>();
		comboBoxDeporte.setBounds(399, 37, 182, 27);
		getContentPane().add(comboBoxDeporte);
		
		JLabel lblDeportes = new JLabel("Sport");
		lblDeportes.setBounds(328, 42, 61, 16);
		contentPane.add(lblDeportes);
		
		jLabelEvents.setBounds(298, 90, 346, 20);
		contentPane.add(jLabelEvents);
		
		JLabel jLabelFecha = new JLabel();
		jLabelFecha.setBounds(new Rectangle(175, 200, 305, 20));
		jLabelFecha.setBounds(150, 276, 305, 20);
		contentPane.add(jLabelFecha);
		
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

	private void exitModify(ActionEvent e) {
		this.dispose();
		MoreOptions c = new MoreOptions();
		c.setVisible(true);
	}
}

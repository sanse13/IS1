package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.*;
import exceptions.EventFinished;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

public class CreateEventGUI extends JFrame {
	//private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	//DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	//private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelQuery = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Event"));

	private JTextField jTextFieldQuery = new JTextField();
	//private Calendar calendarMio = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;

	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private JLabel jLabelFecha = new JLabel();
	
	private static Deporte deporteSeleccionado;
	
	
	public CreateEventGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));

		final BLFacade facade = MainGUI.getBusinessLogic();
		//jComboBoxEvents.setModel(modelEvents);
		//jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		//jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelQuery.setBounds(new Rectangle(10, 240, 75, 20));
		jTextFieldQuery.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent key) {
				if (jTextFieldQuery.getText().isEmpty()){
					jButtonCreate.setEnabled(false);
				}else{					
					jButtonCreate.setEnabled(true);
				}		
			}
		});
		
		jTextFieldQuery.setBounds(new Rectangle(100, 240, 429, 20));

		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));



		jButtonCreate.setBounds(new Rectangle(123, 275, 130, 30));
		jButtonCreate.setEnabled(false);



		
		jButtonClose.setBounds(new Rectangle(333, 275, 130, 30));
		
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jLabelMsg.setBounds(new Rectangle(275, 182, 305, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(175, 220, 305, 20));
		jLabelError.setForeground(Color.red);
		
		jLabelFecha.setBounds(new Rectangle(175, 200, 305, 20));

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);
		this.getContentPane().add(jLabelFecha, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldQuery, null);
		this.getContentPane().add(jLabelQuery, null);
		
		JLabel JLabelDeporte = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sport"));
		JLabelDeporte.setBounds(427, 11, 102, 14);
		getContentPane().add(JLabelDeporte);
		
		final JComboBox<Deporte> comboBox_1 = new JComboBox<Deporte>();
		comboBox_1.setBounds(419, 39, 147, 20);
		getContentPane().add(comboBox_1);
		final BLFacade fa348de = MainGUI.getBusinessLogic();
		java.util.List<Deporte> deportes = facade.getDeportes();
		for(int i=0; i<deportes.size(); i++) {
			Deporte dep = deportes.get(i);
			comboBox_1.addItem(dep);
		}
		deporteSeleccionado = (Deporte) comboBox_1.getSelectedItem();
		//40,50,225,150
		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));
		
		// Code for JCalendar
				this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
				{
					public void propertyChange(PropertyChangeEvent propertychangeevent)
					{
						if (propertychangeevent.getPropertyName().equals("locale"))
						{
							jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
						}
						else if (propertychangeevent.getPropertyName().equals("calendar"))
						{
							calendarMio = (Calendar) propertychangeevent.getNewValue();
							DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
							jCalendar1.setCalendar(calendarMio);
							Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

							try {

								//BLFacade facade=MainGUI.getBusinessLogic();

								//List<Event> events = facade.getEventFromDeporte(jCalendar1.getDate(),deporteSeleccionado);

								//if (events.isEmpty()) {
								//	jLabelFecha.setText(ResourceBundle.getBundle("Etiquetas").getString("Fecha")+dateformat1.format(calendarMio.getTime()));
								//} else {
									jLabelFecha.setText(ResourceBundle.getBundle("Etiquetas").getString("Date")+dateformat1.format(calendarMio.getTime()));	
								//}
								} catch (Exception e1) {
								paintDaysWithSports(jCalendar1);
								//jLabelQueries.setText(e1.getMessage());
							}
							paintDaysWithSports(jCalendar1);
						}
						paintDaysWithSports(jCalendar1);
					} 
				});
				this.getContentPane().add(jCalendar1, null);
		
		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
							String nombre = jTextFieldQuery.getText();			
							facade.createEvent(nombre, jCalendar1.getDate(), deporteSeleccionado);
							
							System.out.println("Añadido");
							JOptionPane.showMessageDialog(null, "El evento ha sido añadido con éxito");

				}catch(java.lang.NumberFormatException e2){
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
				}catch (EventFinished e1) {
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorDatePased"));
				}
				}
			});
				
		
		//paintDaysWithEvents(jCalendar);
		
		/*this.jCalendar.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{
				calendarMio = (Calendar) propertychangeevent.getNewValue();
				DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
				jCalendar.setCalendar(calendarMio);
				
				System.out.println(dateformat1.format(calendarMio.getTime()));
			}
		});*/
		
		//Calendar calendar = jCalendar.getCalendar();
		
		//final Date firstDay=UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
		
		//final DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
		//dateformat1.format(calendarMio.getTime());
		
		/*jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(dateformat1.format(calendarMio.getTime()));
			}
		});*/
		
		
	}
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.dispose();
		MainGUI c = new MainGUI();
		c.setVisible(true);
	}
	
	public static void paintDaysWithSports(JCalendar jCalendar) {
		// For each day in current month, it is checked if there are events, and in that
		// case, the background color for that day is changed.

		BLFacade facade = MainGUI.getBusinessLogic();
		
		try {

			Vector<Date> dates=facade.getEventsMonthDeporte(jCalendar.getDate(), deporteSeleccionado);
			Vector<Date> dates1=facade.getEventsMonthDeporteOpposite(jCalendar.getDate(), deporteSeleccionado);
			
		
			Calendar calendar = jCalendar.getCalendar();
		
			//jCalendar1.setCalendar(calendar);
		
			int month = calendar.get(Calendar.MONTH);
			int today=calendar.get(Calendar.DAY_OF_MONTH);

			calendar.set(Calendar.DAY_OF_MONTH, 1);
			int offset = calendar.get(Calendar.DAY_OF_WEEK);

			if (Locale.getDefault().equals(new Locale("es")))
				offset += 4;
			else
				offset += 5;
		
			
			for (Date d:dates1){
		 		calendar.setTime(d);
		 		System.out.println(d);
				
				// Obtain the component of the day in the panel of the DayChooser of the
				// JCalendar.
				// The component is located after the decorator buttons of "Sun", "Mon",... or
				// "Lun", "Mar"...,
				// the empty days before day 1 of month, and all the days previous to each day.
				// That number of components is calculated with "offset" and is different in
				// English and Spanish
//					    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
				Component o = (Component) jCalendar.getDayChooser().getDayPanel()
						.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
				o.setBackground(null);
		 	}
			
			for (Date d:dates){

				calendar.setTime(d);
				System.out.println(d);
			
				// Obtain the component of the day in the panel of the DayChooser of the
				// JCalendar.
				// The component is located after the decorator buttons of "Sun", "Mon",... or
				// "Lun", "Mar"...,
				// the empty days before day 1 of month, and all the days previous to each day.
				// That number of components is calculated with "offset" and is different in
				// English and Spanish
	//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
				Component o = (Component) jCalendar.getDayChooser().getDayPanel()
						.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
				o.setBackground(Color.CYAN);
		 	}
		 	
		 		calendar.set(Calendar.DAY_OF_MONTH, today);
		 		calendar.set(Calendar.MONTH, month);
		} catch (Exception e8) {}
	}
	/*private Date newDate(int year,int month,int day) {

	     Calendar calendar = Calendar.getInstance();
	     calendar.set(year, month, day,0,0,0);
	     calendar.set(Calendar.MILLISECOND, 0);

	     return calendar.getTime();
	}*/
}

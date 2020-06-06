package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Question;
import domain.Deporte;
import domain.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class ModificarEvento extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JTable tableEvents= new JTable();

	private DefaultTableModel tableModelEvents;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};


	private static Deporte deporteSeleccionado;
	private Event selectedEvent;
	private JTextField textFieldAño;
	private JTextField textFieldDia;
	private final JLabel lblNewLabel_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ModificarEvento.lblNewLabel_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private JTextField textField;
	private JComboBox<String> comboBoxMeses = new JComboBox<String>();
	
	private String[] listaMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
			"Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

	public ModificarEvento()
	{
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	private void jbInit() throws Exception
	{
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(692, 520));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ModifyEvent"));

		jLabelEvents.setBounds(294, 85, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelEvents);

		jButtonClose.setBounds(new Rectangle(274, 442, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

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
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));



					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();

						List<Event> events = facade.getEventFromDeporte(jCalendar1.getDate(),deporteSeleccionado);

						if (events.isEmpty()) {
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarMio.getTime()));
							((DefaultTableModel)tableEvents.getModel()).setNumRows(0);
						}
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarMio.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
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

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				selectedEvent = (domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries = selectedEvent.getQuestions();

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
				}
			}
		});
		
	

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);


		this.getContentPane().add(scrollPaneEvents, null);

		
		JLabel lblDeportes = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sport")); 
		lblDeportes.setBounds(309, 24, 61, 16);
		getContentPane().add(lblDeportes);
		
		final JComboBox<Deporte> comboBoxDeporte = new JComboBox<Deporte>();
		comboBoxDeporte.setBounds(382, 23, 147, 20);
		getContentPane().add(comboBoxDeporte);
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ModificarEvento.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(39, 298, 186, 16);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(237, 293, 346, 26);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textFieldAño = new JTextField();
		textFieldAño.setBounds(39, 340, 130, 26);
		getContentPane().add(textFieldAño);
		textFieldAño.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ModificarEvento.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_1.setBounds(76, 372, 61, 16);
		getContentPane().add(lblNewLabel_1);
		
		textFieldDia = new JTextField();
		textFieldDia.setBounds(437, 340, 130, 26);
		getContentPane().add(textFieldDia);
		textFieldDia.setColumns(10);
		lblNewLabel_2.setBounds(447, 372, 61, 16);
		
		getContentPane().add(lblNewLabel_2);
		
	
		comboBoxMeses.setBounds(237, 341, 147, 27);
		
		for (int i = 0; i < listaMeses.length; i++) {
			comboBoxMeses.addItem(listaMeses[i]);
		}
		
		getContentPane().add(comboBoxMeses);
		
		JLabel lblNewLabel_3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ModificarEvento.lblNewLabel_3.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_3.setBounds(280, 372, 61, 16);
		getContentPane().add(lblNewLabel_3);
		
		
		
		final BLFacade facade = MainGUI.getBusinessLogic();
		java.util.List<Deporte> deportes = facade.getDeportes();
		for(int i=0; i<deportes.size(); i++) {
			Deporte dep = deportes.get(i);
			comboBoxDeporte.addItem(dep);
		}
		
		deporteSeleccionado = (Deporte) comboBoxDeporte.getSelectedItem();
		JButton btnModify = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ModificarEvento.btnNewButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnModify.setBounds(150, 443, 117, 29);

		getContentPane().add(btnModify);
		
		comboBoxDeporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deporteSeleccionado = (Deporte) comboBoxDeporte.getSelectedItem();
				if(!facade.getEventsFromDeporte(deporteSeleccionado).isEmpty()) {
					try {
						List <Event> eventos = facade.getEventsFromDeporte(deporteSeleccionado);
						for (int i = 0; i < eventos.size(); i++) {
							System.out.println("Estos son los eventos "+eventos.get(i).getEventDate());
						}
						if (eventos.isEmpty()) {
							scrollPaneEvents.setVisible(false);
							System.out.println("No hay eventos para este deporte");
						}
					} catch (Exception d) {
					}
				}
				paintDaysWithSports(jCalendar1);
			}
		});
		
		
		btnModify.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					if (textFieldAño.getText().equals("") || textFieldDia.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Introduce el año y el dia.");
					} else if (textField.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Introduce la nueva descripcion del evento");
					} else if (selectedEvent == null){
						JOptionPane.showMessageDialog(null, "Selecciona un evento");
					} else {
					
						int año = Integer.parseInt(textFieldAño.getText());
						int dia = Integer.parseInt(textFieldDia.getText());
						String mes = (String)comboBoxMeses.getSelectedItem();
						int mesInt = escogerMes(mes);
						Date newDate = UtilDate.newDate(año, mesInt, dia);
						
						String descripcion = textField.getText();
						
						System.out.println("La nueva descripcion es: "+descripcion);
						
						facade.updateEvent(selectedEvent, descripcion, newDate);
						
						JOptionPane.showMessageDialog(null, "Evento modificado correctamente");
					}
				
				} catch (NumberFormatException a) {
					JOptionPane.showMessageDialog(null, "Año o dia mal introducido.");
				}
				
				
			}
		});
		
		
		
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
	
	private void jButton2_actionPerformed(ActionEvent e) {
		this.dispose();
		MoreOptions c = new MoreOptions();
		c.setVisible(true);
	}
	
	private int escogerMes(String mes) {
		int res = 0;
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
			
		case "Diciembre":
			res = 11;
			break;
			
		}
		return res;
	}
}
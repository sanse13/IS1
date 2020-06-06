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

public class ModificarPreguntaGUI extends JFrame {
	//private JPanel contentPane;
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private final JLabel NuevaPregunta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NuevaPregunta"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	
	private JComboBox<Deporte> comboBoxDeportes;
	
	private static Deporte deporteSeleccionado;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private JTextField textField;
	private JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ActualizarPregunta")); //$NON-NLS-1$ //$NON-NLS-2$
	/*public ModificarPreguntaGUI() {
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(309, 186, 81, 52);
		getContentPane().add(btnNewButton);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	}*/

	public ModificarPreguntaGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 525));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ModifyQuestion"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 211, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);
		NuevaPregunta.setBounds(40, 364, 104, 14);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		getContentPane().add(NuevaPregunta);

		jButtonClose.setBounds(new Rectangle(478, 408, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
		
		
		

		this.getContentPane().add(jButtonClose, null);


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
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();
						
						List<Event> events = facade.getEventFromDeporte(jCalendar1.getDate(), deporteSeleccionado);

	//					if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarMio.getTime()));
	//					else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarMio.getTime()));
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
						jLabelQueries.setText(e1.getMessage());
					}
					paintDaysWithSports(jCalendar1);
				}
				paintDaysWithSports(jCalendar1);
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 242, 598, 86));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					Vector<Object> row = new Vector<Object>();

					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					tableModelQueries.addRow(row);	
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});
		
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int i=tableQueries.getSelectedRow();
				Integer que = (Integer) tableModelQueries.getValueAt(i, 0);
				System.out.println(que);
				//Integer questionNum = Integer.parseInt(que);
				Question question = facade.getQuestion(que);
				facade.setSelectedQuestion(question);
				
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);


		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		
		textField = new JTextField();
		textField.setText(""); //$NON-NLS-1$ //$NON-NLS-2$
		textField.setBounds(143, 361, 495, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = new MainGUI().getBusinessLogic();
				//String descripcion = facade.getSelectedQuestion().getQuestion();
				Question selectedQuestion = facade.getSelectedQuestion();
				String descripcion = null;
				if (selectedQuestion == null) {
					JOptionPane.showMessageDialog(null, "No hay pregunta seleccionada");
					
				} else {
					descripcion = selectedQuestion.getQuestion();
				}
				if (textField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Debe introducir una pregunta");
				} else {
					descripcion = textField.getText();
				}
				Question pregunta = facade.getSelectedQuestion();
				
				if (pregunta != null) {
					facade.actualizarPregunta(pregunta, descripcion);
					JOptionPane.showMessageDialog(null, "La pregunta ha sido modificada con éxito");
					jButton2_actionPerformed(arg0);
				}
			}
		});
		btnNewButton.setBounds(131, 408, 150, 30);
		
		getContentPane().add(btnNewButton);
		
		comboBoxDeportes = new JComboBox<Deporte>();
		comboBoxDeportes.setBounds(378, 15, 140, 27);
		getContentPane().add(comboBoxDeportes);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		List<Deporte> sportList = facade.getDeportes();
		for (int i = 0; i < sportList.size(); i++) comboBoxDeportes.addItem(sportList.get(i));
		
		deporteSeleccionado = (Deporte) comboBoxDeportes.getSelectedItem();
		
		comboBoxDeportes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deporteSeleccionado = (Deporte) comboBoxDeportes.getSelectedItem();
				paintDaysWithSports(jCalendar1);
				
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
}



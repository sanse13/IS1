package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Deporte;
import domain.Event;
import domain.Pronostico;
import domain.Question;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JComboBox;

public class ModificarPronostico extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 
	private final JLabel NuevoPronostico = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("NuevoPronostico"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPanePronos = new JScrollPane();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();
	private JTable tablePronos = new JTable(); 

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelProno; 
	
	private static Deporte deporteSeleccionado;

	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	
	private String[] columnNamesPronos = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("PronoN"), 
			ResourceBundle.getBundle("Etiquetas").getString("PronoN")

	};
	private JTextField textFieldPronostico;
	private final JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ActuProno"));
	private JTextField textFieldRatio;


	
	public ModificarPronostico() {
		
		this.getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(714, 558));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ModifyProno"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 223, 406, 14);
		jLabelEvents.setBounds(295, 44, 259, 16);
		NuevoPronostico.setBounds(40, 413, 225, 14);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		getContentPane().add(NuevoPronostico);

		//Boton de close 
		jButtonClose.setBounds(new Rectangle(402, 478, 225, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				jButton2_actionPerformed(e);
			}
		});
		

		this.getContentPane().add(jButtonClose, null);
		
		//Calendario
		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

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
						tableModelEvents.setColumnCount(3); 

						BLFacade facade=MainGUI.getBusinessLogic();

						List<Event> events = facade.getEventFromDeporte(jCalendar1.getDate(),deporteSeleccionado);

						if (events.isEmpty()) {
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarMio.getTime()));
							((DefaultTableModel)tableEvents.getModel()).setNumRows(0);
							((DefaultTableModel)tableQueries.getModel()).setNumRows(0);
							((DefaultTableModel)tablePronos.getModel()).setNumRows(0);
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
						jLabelQueries.setText(e1.getMessage());
					}
					paintDaysWithSports(jCalendar1);
				}
				paintDaysWithSports(jCalendar1);
			} 
		});

		this.getContentPane().add(jCalendar1, null);
		
		scrollPaneEvents.setBounds(new Rectangle(295, 71, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 238, 598, 64));
		scrollPanePronos.setBounds(new Rectangle(40, 323, 598, 73));
		

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty()) {
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				}
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());
				((DefaultTableModel)tablePronos.getModel()).setNumRows(0);
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
		//tableModelProno.setColumnCount(2); 
		
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int i=tableQueries.getSelectedRow();
				Integer que = (Integer) tableModelQueries.getValueAt(i, 0);

				Question ques = facade.getQuestion(que);
				List<Pronostico> prono = facade.getPronosticosFromQuestion(ques);
				tableModelProno.setDataVector(null, columnNamesPronos);
			
				
				for (domain.Pronostico p: prono){
					try {
						Vector<Object> row = new Vector<Object>();
						System.out.println("Pronosticos" +p);
						row.add(p.getIdPronostico());
						row.add(p.getPronostico());
						//row.add(p);
						tableModelProno.addRow(row);		
					} catch (Exception e3) { 
						
					}					
				}
				tablePronos.getColumnModel().getColumn(0).setPreferredWidth(25);
				tablePronos.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		}); 
		

		
		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		/*
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		*/

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		
		

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		
		scrollPanePronos.setViewportView(tablePronos);
		tableModelProno = new DefaultTableModel(null, columnNamesPronos);
		
		tablePronos.setModel(tableModelProno);
		
		tablePronos.getColumnModel().getColumn(0).setPreferredWidth(25);
		tablePronos.getColumnModel().getColumn(1).setPreferredWidth(268);
		
		

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPanePronos, null);
		
		//Ratio 
		final JSlider sliderRatio = new JSlider();
		sliderRatio.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				float numero = (float) sliderRatio.getValue()/10;
				String valor = numero+"";
				textFieldRatio.setText(valor);
				
			}
		});
		sliderRatio.setBounds(261, 438, 330, 29);
		getContentPane().add(sliderRatio);
	
		
		//Boton de actualizar el pronostico
		textFieldPronostico = new JTextField();
		textFieldPronostico.setBounds(261, 407, 379, 20);
		getContentPane().add(textFieldPronostico);
		textFieldPronostico.setColumns(10);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					BLFacade facade = MainGUI.getBusinessLogic();
					if(textFieldPronostico.getText().equals("") && sliderRatio.getValue()>10) {
						JOptionPane.showMessageDialog(null, "Debe introducir un pronostico o un ratio");
					}
						else {
							String descripcion = textFieldPronostico.getText();
							float numero = (float) sliderRatio.getValue()/10;
							int i = tablePronos.getSelectedRow();
							Integer que = (Integer) tablePronos.getValueAt(i, 0);
							String pronostico = (String) tablePronos.getValueAt(i, 1);
							Pronostico prono = facade.getPronostico(que, pronostico);
							facade.updateProno(numero, prono, descripcion);
							JOptionPane.showMessageDialog(null, "El pronostico y el ratio han sido modificados con éxito");
						}
				}
		});
		
		btnNewButton.setBounds(95, 478, 231, 30);
		getContentPane().add(btnNewButton);
		
	
		
		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Ratiou")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(40, 438, 186, 16);
		getContentPane().add(lblNewLabel);
		
		textFieldRatio = new JTextField();
		textFieldRatio.setBounds(585, 441, 53, 26);
		getContentPane().add(textFieldRatio);
		textFieldRatio.setColumns(10);
		
		JLabel pronoAnterior = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("pronoAnterior")); //$NON-NLS-1$ //$NON-NLS-2$
		pronoAnterior.setBounds(40, 307, 186, 16);
		getContentPane().add(pronoAnterior);
		
		final JComboBox<Deporte> comboBoxDeporte = new JComboBox<Deporte>();
		final BLFacade facade = MainGUI.getBusinessLogic();
	    java.util.List<Deporte> deportes = facade.getDeportes();
		for(int i=0; i<deportes.size(); i++) {
			Deporte dep = deportes.get(i);
			comboBoxDeporte.addItem(dep);
		}
		deporteSeleccionado = (Deporte) comboBoxDeporte.getSelectedItem();
		comboBoxDeporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DefaultTableModel)tableEvents.getModel()).setNumRows(0);
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
		comboBoxDeporte.setBounds(402, 17, 140, 20);
		getContentPane().add(comboBoxDeporte);
		
		JLabel lblNewLabel_1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Sport"));
		lblNewLabel_1.setBounds(295, 20, 97, 14);
		getContentPane().add(lblNewLabel_1);
		
		
		tablePronos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int i=tableQueries.getSelectedRow();
				Integer pro = (Integer) tablePronos.getValueAt(i, 0);
				String pronostico = (String) tablePronos.getValueAt(i, 1);
				System.out.println(pro);
				//Integer questionNum = Integer.parseInt(que);
				Pronostico prono = (Pronostico) facade.getPronostico(pro, pronostico);
				facade.setSelectedProno(prono);
			}
		});
		
		
		//scrollPanePronos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    //scrollPanePronos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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


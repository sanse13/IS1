package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Apuesta;
import domain.Deporte;
import domain.Event;
import domain.Monedero;
import domain.Pronostico;
import domain.Question;
import domain.Usuario;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

public class ResolucionApuestaGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JList<String> list = new JList<>();
	private DefaultListModel<String> model = new DefaultListModel<String>();
	private JButton resolucion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ResolverApuesta"));
	private JButton close = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JScrollPane scrollPaneInfo = new JScrollPane();
	private boolean cierto=false;
	private float pronos;
	private float dApostado;
	private final JComboBox<Apuesta> comboBoxApuestas = new JComboBox<Apuesta>();
	private Vector<Apuesta> vectorApuestas = new Vector<Apuesta>();
	private Usuario usuario;
	
	private static Deporte deporteSeleccionado;
	
	int index;
	
	private Apuesta selectedApuesta;
	private final JButton btnNewButton = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MostrarApuestas"));
	
	
	
	private static final long serialVersionUID1 = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

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
	

	public ResolucionApuestaGUI() {
		this.getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(700, 525));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("ResolucionApuesta"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 211, 406, 14);
		jLabelEvents.setBounds(292, 46, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);

		//Boton de close 
		jButtonClose.setBounds(new Rectangle(413, 450, 225, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				exit(e);
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
							jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
							((DefaultTableModel) tableEvents.getModel()).setNumRows(0);
							((DefaultTableModel) tableQueries.getModel()).setNumRows(0);
							((DefaultTableModel) tablePronos.getModel()).setNumRows(0);
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
		
		scrollPaneEvents.setBounds(new Rectangle(292, 62, 346, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 236, 598, 64));
		scrollPanePronos.setBounds(new Rectangle(40, 322, 598, 73));
		

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

	
		
		//Boton de actualizar el pronostico
		
		btnNewButton.setBounds(84, 450, 231, 30);
		getContentPane().add(btnNewButton);
		btnNewButton.setEnabled(false);
		
		
		JLabel pronoAnterior = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("pronoAnterior"));
		pronoAnterior.setBounds(40, 301, 186, 16);
		getContentPane().add(pronoAnterior);
		
		final JCheckBox chckbxcierto = new JCheckBox("cierto");
		chckbxcierto.setEnabled(false);
		chckbxcierto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int i=tableQueries.getSelectedRow();
				Integer pro = (Integer) tablePronos.getValueAt(i, 0);
				String pronostico = (String) tablePronos.getValueAt(i, 1);
				Pronostico prono = (Pronostico) facade.getPronostico(pro, pronostico);
				if(chckbxcierto.isSelected()) {
					cierto=true;
					btnNewButton.setEnabled(true);
				}else {
					cierto=false;
					btnNewButton.setEnabled(false);
				}
			}
		});
		chckbxcierto.setBounds(40, 402, 97, 23);
		getContentPane().add(chckbxcierto);
		
		final JComboBox<Deporte> comboBoxDeporte = new JComboBox<Deporte>();
		final BLFacade facade = MainGUI.getBusinessLogic();
	    java.util.List<Deporte> deportes = facade.getDeportes();
		for(int i=0; i<deportes.size(); i++) {
			Deporte dep = deportes.get(i);
			comboBoxDeporte.addItem(dep);
		}
		deporteSeleccionado = (Deporte) comboBoxDeporte.getSelectedItem();
		comboBoxDeporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
				((DefaultTableModel) tableEvents.getModel()).setNumRows(0);
				((DefaultTableModel) tableQueries.getModel()).setNumRows(0);
				((DefaultTableModel) tablePronos.getModel()).setNumRows(0);
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
						paintDaysWithSports(jCalendar1);
					}
					paintDaysWithSports(jCalendar1);
				}
				paintDaysWithSports(jCalendar1);
			}
		});
		comboBoxDeporte.setBounds(292, 11, 140, 20);
		getContentPane().add(comboBoxDeporte);
		
		
		tablePronos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int i=tableQueries.getSelectedRow();
				Integer pro = (Integer) tablePronos.getValueAt(i, 0);
				String pronostico = (String) tablePronos.getValueAt(i, 1);
				Pronostico prono = (Pronostico) facade.getPronostico(pro, pronostico);
				chckbxcierto.setEnabled(true);
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				if(cierto) {
					Vector<Apuesta> apuestas= new Vector<Apuesta>();
					int i=tableQueries.getSelectedRow();
					Integer pro = (Integer) tablePronos.getValueAt(i, 0);
					String pronostico = (String) tablePronos.getValueAt(i, 1);
					Pronostico prono = (Pronostico) facade.getPronostico(pro, pronostico);
					apuestas= prono.getApuestas();
					for(int j=0; j<apuestas.size();j++) {
						facade.setSelectedProno(prono);
						float dinero =apuestas.get(j).getdApostado()*apuestas.get(j).getRatio();
						facade.addBalance(apuestas.get(j).getUser(), dinero);
						facade.deleteApuesta(apuestas.get(j), apuestas.get(j).getUser());
					}
				}
				int i=tableQueries.getSelectedRow();
				Integer que = (Integer) tableModelQueries.getValueAt(i, 0);
				Question ques = facade.getQuestion(que);
				List<Pronostico> listapronost = facade.getPronosticosFromQuestion(ques);
				for(int k=0; k<listapronost.size();k++) {
					Vector<Apuesta> sobrantes= new Vector<Apuesta>();
					Integer pro = (Integer) listapronost.get(k).getIdPronostico();
					String pronostico = (String) listapronost.get(k).getPronostico();
					Pronostico pronosti = (Pronostico) facade.getPronostico(pro, pronostico);
					sobrantes= listapronost.get(k).getApuestas();
					for(int n=0; n<sobrantes.size();n++) {
						facade.setSelectedProno(pronosti);
						facade.deleteApuesta(sobrantes.get(n), sobrantes.get(n).getUser());
					}
				}
				chckbxcierto.setEnabled(false);
				btnNewButton.setEnabled(false);
				while (tableModelProno.getRowCount()>0){
					tableModelProno.removeRow(0);
				}
				chckbxcierto.setSelected(false);
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
	
	private void exit(ActionEvent e) {
		this.dispose();
		MoreOptions c = new MoreOptions();
		c.setVisible(true);
	}
}

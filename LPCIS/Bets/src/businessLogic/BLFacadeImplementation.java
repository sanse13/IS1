package businessLogic;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.TypedQuery;
import javax.xml.crypto.Data;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Usuario;
import domain.Apuesta;
import domain.Deporte;
import domain.Event;
import domain.Pronostico;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	private Usuario loggedUsuario;
	private Question selectedQuestion;
	private Pronostico selectedProno;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c=ConfigXML.getInstance();
		
		if (c.getDataBaseOpenMode().equals("initialize")) {
			DataAccess dbManager=new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
			}
		
	}
	

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
 	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
   @WebMethod
   public Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist{
	   
	    //The minimum bed must be greater than 0
	    DataAccess dBManager=new DataAccess();
		Question qry=null;
		
	    
		if(new Date().compareTo(event.getEventDate())>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
				
		
		 qry=dBManager.createQuestion(event,question,betMinimum);		

		dBManager.close();
		
		return qry;
   };
	
	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
    @WebMethod	
	public Vector<Event> getEvents(Date date)  {
		DataAccess dbManager=new DataAccess();
		Vector<Event>  events=dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

    
	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date) {
		DataAccess dbManager=new DataAccess();
		Vector<Date>  dates=dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}
	
	
	

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
    @WebMethod	
	 public void initializeBD(){
		DataAccess dBManager=new DataAccess();
		dBManager.initializeDB();
		dBManager.close();
	}
    
    @WebMethod
    public Boolean comprobarLogin(String nomUsuario, String contra) {
    	DataAccess dbManager=new DataAccess();
    	boolean log = dbManager.comprobarLogin(nomUsuario, contra);
    	return log;
    }
    
    @WebMethod
    public Boolean comprobarReg(String nomUsuario) {
    	DataAccess dbManager = new DataAccess();
    	boolean reg = dbManager.comprobarReg(nomUsuario);
    	return reg;
    }
    
    @WebMethod
    public void regUsuario(Usuario usuario) {
    	DataAccess dbManager = new DataAccess();
    	dbManager.regUsuario(usuario);
    }
    
    @WebMethod
    public void setLoggedUsuario(Usuario loggedUsuario) {
    	this.loggedUsuario = loggedUsuario;
    }
    
    @WebMethod
    public Usuario getLoggedUsuario() {
    	return loggedUsuario;
    }
    
    @WebMethod
    public Integer getPermi(Usuario logged) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getPermi(logged);
    }

    
    @WebMethod
    public Question getSelectedQuestion() {
		return selectedQuestion;
	}

    @WebMethod
	public void setSelectedQuestion(Question selectedQuestion) {
		this.selectedQuestion = selectedQuestion;
	}
    
    @WebMethod
	public Pronostico getSelectedProno() {
		return selectedProno;
	}

	@WebMethod
	public void setSelectedProno(Pronostico selectedProno) {
		this.selectedProno =selectedProno;
	}
    
    @WebMethod
    public Question getQuestion(Integer questionNumber) {
    	DataAccess dbManager = new DataAccess();
		return dbManager.getQuestion(questionNumber);
	}
    
    @WebMethod
    public Event createEvent(String nombre, Date fecha, Deporte deporte) throws EventFinished{
 	   
 	    //The minimum bed must be greater than 0
 	    DataAccess dBManager=new DataAccess();
 		Event qry=null;
 		
 		if(new Date().compareTo(fecha)>0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));
	
 		 qry=dBManager.createEvent(nombre,fecha, deporte);		

 		dBManager.close();
 		
 		return qry;
    };
    
    @WebMethod
    public Pronostico addPronostico(Pronostico pronostico) {
    	DataAccess dbManager=new DataAccess();
    	Pronostico pronos = dbManager.addPronostico(pronostico);
    	return pronos;
    }
    
    @WebMethod
    public List<Event> getAllEvents(){
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getAllEvents();
    }
    
    @WebMethod
    public void deleteEvent(Event e, Usuario user) {
    	DataAccess dbManager = new DataAccess();
    	dbManager.deleteEvent(e, user);
    }
    
    @WebMethod
    public void updateEvent(Event event, String description, Date eventDate) {
    	DataAccess dbManager = new DataAccess();
    	dbManager.updateEvent(event, description, eventDate);
    }
    
    @WebMethod
    public void actualizarPregunta(Question pregunta, String descripcion) {
    	DataAccess dbManager = new DataAccess();
    	dbManager.actualizaPregunta(pregunta, descripcion);
    }
    
    @WebMethod 
    public void updateProno (Float ratio, Pronostico prono, String descripcion) {
    	DataAccess dbManager = new DataAccess(); 
    	dbManager.updateProno(ratio, prono, descripcion);
    }
    
    @WebMethod
     public Usuario addBalance(Usuario usuario, Float suma) {
    	 DataAccess dbManager = new DataAccess();
    	 return dbManager.addBalance(usuario, suma);
     }
    
    @WebMethod
    public Usuario getLoggedUsuarioDat() {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getLoggedUsuarioDat(loggedUsuario);
    }
    
    @WebMethod
    public List<Question> getQuestionsFromEvent(Event e){
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getQuestionsFromEvent(e);
    }
    
    @WebMethod
    public Float getRatioPronostico(Question question, Pronostico pronostico) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getRatioPronostico(question, pronostico);
    }
    
    @WebMethod
    public Float quitarDinero(Usuario usuario, Float cantMoney) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.quitarDinero(usuario, cantMoney);
    }
    
    @WebMethod
    public List<Pronostico> getPronosticosFromQuestion(Question q){
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getPronosticosFromQuestion(q);
    }
    
    @WebMethod
    public Apuesta createBet(Usuario usuario, Float cantApostada, Pronostico pronostico, Float radio, Event selectedEvent, Question selectedQuestion) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.createBet(usuario, cantApostada, pronostico, radio, selectedEvent, selectedQuestion);
    }
    
    @WebMethod
    public Event getEventFromApuesta(Apuesta apuesta) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getEventFromApuesta(apuesta);
    }
    
    @WebMethod 
    public Pronostico getPronostico(Integer idpronostico, String pron) {
    	DataAccess dbManager = new DataAccess();
		return dbManager.getPronostico(idpronostico, pron);
    }
    
    @WebMethod
    public List<Apuesta> getApuestas(){
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getApuesta();
    }
    
    @WebMethod
    public List<Apuesta> getAllApuestas(){
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getAllApuestas();
    }
    
    @WebMethod
    public void deleteApuesta(Apuesta a, Usuario u) {
    	DataAccess dbManager = new DataAccess();
    	dbManager.deleteApuesta(a, u);
    }
    
    @WebMethod
    public Pronostico getPronFromString(String pronostico, Question selectedQuestion) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getPronFromString(pronostico, selectedQuestion);
    }
    
    @WebMethod
    public Apuesta getApuestaID(int id) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getApuestaID(id);
    }
    
    @WebMethod
    public Deporte createDeporte(String tipo) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.createDeporte(tipo);
    }
    
    @WebMethod
    public List<Deporte> getDeportes(){
		DataAccess dbManager = new DataAccess();
		return dbManager.getDeportes();
	}
    
    @WebMethod
    public Deporte getDeporte(String tipo) {
    	DataAccess dbManager = new DataAccess();
		return dbManager.getDeporte(tipo);
	}
    @WebMethod 
    public Vector<Event> getEventsFromDeporte(Deporte sport){
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getEventsFromDeporte(sport);
    }
    
    @WebMethod 
    public Vector<Date> getEventsMonthDeporte(Date date, Deporte sport){
    	DataAccess dbManager = new DataAccess(); 
    	return dbManager.getEventsMonthDeporte(date, sport);
    }
    
    @WebMethod
	public Vector<Event> getEventFromDeporte(Date date, Deporte sport) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getEventFromDeporte(date, sport);
    }
    
    @WebMethod
    public Vector<Date> getEventsMonthDeporteOpposite(Date date,Deporte sport) {
    	DataAccess dbManager = new DataAccess();
    	return dbManager.getEventsMonthDeporteOpposite(date, sport);
    }
    
    @WebMethod
    public void deleteSport(Deporte sport, Usuario user) {
    	DataAccess dbManager = new DataAccess();
    	dbManager.deleteSport(sport, user);
    }
    

}


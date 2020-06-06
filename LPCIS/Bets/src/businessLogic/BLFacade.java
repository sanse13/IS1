package businessLogic;

import java.util.Vector;
import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.*;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	

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
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;
	
	
	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);
	
	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	@WebMethod public Boolean comprobarLogin(String nomUsuario, String contra);

	@WebMethod public Boolean comprobarReg(String nomUsuario);
	
	@WebMethod public void regUsuario(Usuario usuario);
	
	@WebMethod public void setLoggedUsuario(Usuario loggedUsuario);
	
	@WebMethod public Usuario getLoggedUsuario();
	
	@WebMethod public Integer getPermi(Usuario logged);
	
	@WebMethod public Pronostico addPronostico(Pronostico pronostico);
	
	@WebMethod public Question getSelectedQuestion();
	
	@WebMethod public void setSelectedQuestion(Question pregunta);
	
	@WebMethod public Question getQuestion(Integer questionNumber);
	
	@WebMethod public Event createEvent(String nombre, Date fecha, Deporte deporte) throws EventFinished;
	
	@WebMethod public List<Event> getAllEvents();
	
	@WebMethod public void deleteEvent(Event e, Usuario user);
	
	@WebMethod public void updateEvent(Event event, String description, Date eventDate);
	
	@WebMethod public void actualizarPregunta(Question pregunta, String descripcion);
	
	@WebMethod public Usuario addBalance(Usuario usuario, Float suma);
	
	@WebMethod public Usuario getLoggedUsuarioDat();
	
	@WebMethod public List<Question> getQuestionsFromEvent(Event e);
	
	@WebMethod public Float getRatioPronostico(Question question, Pronostico pronostico);
	
	@WebMethod public Float quitarDinero(Usuario usuario, Float cantMoney);
	
	@WebMethod public List<Pronostico> getPronosticosFromQuestion(Question q);
	
	@WebMethod public Apuesta createBet(Usuario usuario, Float cantApostada, Pronostico pronostico, Float radio, Event selectedEvent, Question selectedQuestion);
	
	@WebMethod public Event getEventFromApuesta(Apuesta apuesta);
	
	@WebMethod public void setSelectedProno(Pronostico prono);

	@WebMethod public Pronostico getSelectedProno();
	
	@WebMethod public Pronostico getPronostico(Integer idpronostico, String pron); 
	
	@WebMethod public void updateProno(Float ratio,Pronostico prono, String descripcion);
	
	@WebMethod public List<Apuesta> getApuestas();
	
	@WebMethod public List<Apuesta> getAllApuestas();
	
	@WebMethod public void deleteApuesta(Apuesta a, Usuario u);
	
	@WebMethod public Pronostico getPronFromString(String pronostico, Question selectedQuestion);
	
	@WebMethod public Apuesta getApuestaID(int id);
	
	@WebMethod public Deporte createDeporte(String tipo); 
	
	@WebMethod public List<Deporte> getDeportes();
	
	@WebMethod public Deporte getDeporte(String tipo);
	
	@WebMethod public Vector<Event> getEventsFromDeporte(Deporte sport);
	
	@WebMethod public Vector<Date> getEventsMonthDeporte(Date date, Deporte sport);
	
	@WebMethod public Vector<Event> getEventFromDeporte(Date date,Deporte sport);
	
	@WebMethod public Vector<Date> getEventsMonthDeporteOpposite(Date date,Deporte sport);
	
	@WebMethod public void deleteSport(Deporte sport, Usuario user);
}

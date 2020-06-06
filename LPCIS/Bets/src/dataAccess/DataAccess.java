package dataAccess;

import java.awt.Window.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.Locale.Category;

import javax.jws.WebMethod;
import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c;

	public DataAccess(boolean initializeMode) {

		c = ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode)
			fileName = fileName + ";drop";

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public DataAccess() {
		new DataAccess(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();
			
			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}
			
			Usuario us1 = new Usuario("haizea", "haizea", 1);
			Usuario us2 = new Usuario("salas", "salas", 1);
			Usuario us3 = new Usuario("marcos", "marcos", 1);
			Usuario us4 = new Usuario("sanse", "sanse", 1);
			Usuario us5 = new Usuario("mikel", "mikel");
			Usuario us6 = new Usuario("jon", "jon");
			Usuario us7 = new Usuario("unai", "unai");
			
			Deporte d1 = new Deporte("Baloncesto");
			Deporte d2 = new Deporte("Futbol");
			Deporte d3 = new Deporte("Golf");
			

			Event ev1 = new Event("Atlético-Athletic", UtilDate.newDate(year, month, 17), d2);
			Event ev2 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 17), d2);
			Event ev3 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 17), d2);
			Event ev4 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 17), d2);
			Event ev5 = new Event("Español-Villareal", UtilDate.newDate(year, month, 17), d2);
			Event ev6 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 17), d2);
			Event ev7 = new Event("Malaga-Valencia", UtilDate.newDate(year, month, 17), d2);
			Event ev8 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 17), d2);
			Event ev9 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 17), d2);
			Event ev10 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 17), d2);

			Event ev11 = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1), d2);
			Event ev12 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 1), d2);
			Event ev13 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 1), d2);
			Event ev14 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 1), d2);
			Event ev15 = new Event("Español-Villareal", UtilDate.newDate(year, month, 1), d2);
			Event ev16 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 1), d2);

			Event ev17 = new Event("Málaga-Valencia", UtilDate.newDate(year, month, 28), d2);
			Event ev18 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 28), d2);
			Event ev19 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 28), d2);
			Event ev20 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 28), d2);

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;
			
			

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
			}
			
			d1.addEvent(ev1);
			
			d2.addEvent(ev1);
			d2.addEvent(ev2);
			d2.addEvent(ev3);
			d2.addEvent(ev4);
			d2.addEvent(ev5);
			d2.addEvent(ev6);
			d2.addEvent(ev7);
			d2.addEvent(ev8);
			d2.addEvent(ev9);
			d2.addEvent(ev10);
			d2.addEvent(ev11);
			d2.addEvent(ev12);
			d2.addEvent(ev13);
			d2.addEvent(ev14);
			d2.addEvent(ev15);
			d2.addEvent(ev16);
			d2.addEvent(ev17);
			d2.addEvent(ev18);
			d2.addEvent(ev19);
			d2.addEvent(ev20);

			
			
			d3.addEvent(ev3);
			
			db.persist(us1);
			db.persist(us2);
			db.persist(us3);
			db.persist(us4);
			db.persist(us5);
			db.persist(us6);
			
			db.persist(d1);
			db.persist(d2);
			db.persist(d3);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event to which question is added
	 * 
	 * @param question text of the question
	 * 
	 * @param betMinimum minimum quantity of the bet
	 * 
	 * @return the created question, or null, or an exception
	 * 
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 * event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	
	public void updateEvent(Event event, String description, Date eventDate) {
		Event ev = db.find(Event.class, event.getEventNumber());
		db.getTransaction().begin();
		ev.setDescription(description);
		ev.setEventDate(eventDate);
		db.getTransaction().commit();
		
	}
	
	public void actualizaPregunta(Question pregunta, String descripcion) {
		Question q= db.find(Question.class, pregunta.getQuestionNumber());
		
		db.getTransaction().begin();
		q.setQuestion(descripcion);
		db.getTransaction().commit();
	}
	
	public void updateProno(Float ratio,Pronostico prono, String newprono) {
		TypedQuery<Pronostico> query = 
				db.createQuery("SELECT p FROM Pronostico p WHERE p.idPronostico=?1 AND "
						+ "p.pronostico=?2", Pronostico.class);
		query.setParameter(1, prono.getIdPronostico());
		query.setParameter(2, prono.getPronostico());
		Pronostico p = query.getSingleResult();
		db.getTransaction().begin();
		p.setPronostico(newprono);
		p.setProporcion(ratio);
		db.getTransaction().commit();
		
	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}
	
	public Event getEventFromApuesta(Apuesta apuesta) {
		Event evento = new Event(); 
		db.getTransaction().begin();
		TypedQuery<Event> query = 
				db.createQuery("SELECT e FROM Event e", Event.class);
		List<Event> eventList = query.getResultList();
		for(int i = 0; i<1; i++ ) {
			evento = eventList.get(i);
		}
		db.getTransaction().commit();
		return evento;
	}
	
	public List<Event> getAllEvents(){
		System.out.println(">> DataAccess: getAllEvents");
		db.getTransaction().begin();
		TypedQuery<Event> query = 
				db.createQuery("SELECT e FROM Event e WHERE e.deporte.tipo=?1", Event.class);
		query.setParameter(1, "Baloncesto");
		List<Event> eventList = query.getResultList();
		System.out.println(eventList.get(1));
		db.getTransaction().commit();
		return eventList;
	}
	
	public Float getRatioPronostico(Question question, Pronostico pronostico) {
		System.out.println(">> DataAccess: getRatioPronostico");
		db.getTransaction().begin();
		TypedQuery<Question> query = 
				db.createQuery("SELECT q FROM Question q WHERE q.questionNumber=?1" ,Question.class);
		query.setParameter(1, question.getQuestionNumber());
		Question q = query.getSingleResult();
		List<Pronostico> pronosticos = q.getPronosticos();
		Float resPronostico = null;
		for (int i = 0; i < pronosticos.size(); i++) {
			if (pronosticos.get(i).getPronostico().equals(pronostico.getPronostico()))
				resPronostico = pronosticos.get(i).getProporcion();
		}
		return resPronostico;
	}
	
	private List<Apuesta> getApuestasFromUser(Usuario user) {
		TypedQuery<Usuario> query = 
				db.createQuery("SELECT u FROM Usuario u WHERE u.nomUsuario=?1", Usuario.class);
		query.setParameter(1, user.getNomUsuario());
		Usuario res = query.getSingleResult();
		return res.getApuestas();
	}
	
	public Usuario addDinero(Usuario usuario, Float suma) {
		Usuario usu = db.find(Usuario.class, usuario.getNomUsuario());
		
		usu.getMonedero().setBalance(usu.getMonedero().getBalance() + suma);
		
		Usuario usu1 = db.find(Usuario.class, usuario.getNomUsuario());
		return usu1;
	}
	
	
	public void deleteEvent(Event e, Usuario user) {
        System.out.println(">> DataAccess: deleteEvent");
        db.getTransaction().begin();
        Usuario u;
        Float dinero=(float) 0.0;
        
        Event event = db.find(Event.class, e.getEventNumber());
        List<Question> listaQue = event.getQuestions();
        for(int i=0; i<listaQue.size(); i++) {
            Vector<Pronostico> vectorProno = listaQue.get(i).getPronosticos();
            for(int j=0; j<vectorProno.size(); j++) {
                List<Apuesta> vectorApuesta = consultarApuestas(e);
                //Vector<Apuesta> vectorApuesta = vectorProno.get(j).getApuestas();
                if(vectorApuesta!=null) {
                    for(int k=0; k<vectorApuesta.size(); k++) {
                        Apuesta apuesta = vectorApuesta.get(k);
                        Usuario usuario = apuesta.getUser();
                        u = db.find(Usuario.class, usuario.getNomUsuario());
                        List<Apuesta> apuestasDeUser = u.getApuestas();
                        for (int s = 0; s < apuestasDeUser.size(); s++) {
                        	if (apuestasDeUser.get(s).getIdApuesta() == apuesta.getIdApuesta()) {
                        		dinero = apuestasDeUser.get(s).getdApostado();
                        		addDinero(u, dinero);
                        		deleteApuestaBD(apuestasDeUser.get(s));
                        		apuestasDeUser.remove(apuestasDeUser.get(s));
                        		System.out.println("he entrado");
                        	}
                        		
                        }
                        if (apuesta == null) System.out.println("es null");
                        else {
                        	deleteApuestaBD(apuesta);
                        }
                    }
                    Pronostico prono = db.find(Pronostico.class, vectorProno.get(j).getIdPronostico());
                    db.remove(prono);
                }
            }
            Question question = listaQue.get(i);
            deleteQuestion(question);
        }
        db.remove(event);
        db.getTransaction().commit();
    }
	
	
	public void deleteSport(Deporte sport, Usuario user) {
		System.out.println(">>DataAcces: deleteSport");
		
		Deporte deporte = db.find(Deporte.class, sport.getTipo());
		
		Vector<Event> eventList = deporte.getEvents();
		for (int i = 0; i < eventList.size(); i++) {
			System.out.println(eventList.get(i));
			deleteEvent(eventList.get(i), user);
		}
		
		
		db.getTransaction().begin();
		db.remove(deporte);
		
		db.getTransaction().commit();
		
	}
	
	public void deleteApuesta(Apuesta e, Usuario u) {
		db.getTransaction().begin();
		//Apuesta apuesta = db.find(Apuesta.class, e.getIdApuesta());

		Apuesta apuesta = db.find(Apuesta.class, e.getIdApuesta());
		Usuario usuario = apuesta.getUser();
		
		TypedQuery<Pronostico> query = 
				db.createQuery("SELECT p FROM Pronostico p", Pronostico.class);
		
		List<Pronostico> listaPronosticos = query.getResultList();
		System.out.println("el size de lsitaProno es: "+listaPronosticos.size());
		
		for (int i = 0; i < listaPronosticos.size(); i++) {
			List<Apuesta> listApuesta = listaPronosticos.get(i).getApuestas();
			for (int j = 0; j < listApuesta.size(); j++) {
				if (listApuesta.get(j).getIdApuesta() == apuesta.getIdApuesta())
					listApuesta.remove(j);
			}
				
		}
		
		
		db.remove(apuesta);
        u = db.find(Usuario.class, usuario.getNomUsuario());
        List<Apuesta> apuestasDeUser = u.getApuestas();
        for (int s = 0; s < apuestasDeUser.size(); s++) {
        	if (apuestasDeUser.get(s).getIdApuesta() == apuesta.getIdApuesta()) {
        		deleteApuestaBD(apuestasDeUser.get(s));
        		apuestasDeUser.remove(apuestasDeUser.get(s));
        	}
    		
        }
		db.getTransaction().commit();
		
	}
	
	public List<Apuesta> consultarApuestas(Event evento) {
        TypedQuery<Apuesta> query1 = 
                db.createQuery("SELECT e FROM Apuesta e WHERE e.evento.eventNumber=?1", Apuesta.class);
        query1.setParameter(1, evento.getEventNumber());
        List<Apuesta> vectorApuesta = query1.getResultList();
        return vectorApuesta;
    }
	
	private void deleteQuestion(Question question) {
        db.remove(question);
    }
	
	public void deleteApuestaBD(Apuesta apuesta) {

		TypedQuery<Apuesta> query = 
				db.createQuery("SELECT e FROM Apuesta e WHERE e.idApuesta=?1",Apuesta.class);
		query.setParameter(1, apuesta.getIdApuesta());

		Apuesta e = query.getSingleResult();

		db.remove(e);
    }

    public void deletePronostico(Pronostico prono) {

        Pronostico pronostico = db.find(Pronostico.class, prono.getIdPronostico());
        db.remove(pronostico);
    }

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}
	
	public Boolean comprobarLogin(String nomUsuario, String contra) {
		c = ConfigXML.getInstance();
		String fileName = c.getDbFilename();
		TypedQuery<Usuario> query = (TypedQuery<Usuario>) db.createQuery("SELECT p FROM Usuario p WHERE p.nomUsuario=?1", Usuario.class);
		query.setParameter(1, nomUsuario);
		List<Usuario> usu = query.getResultList();
		if (usu.size() == 0) return false;
		Usuario usuario = usu.get(0);
		if ((usuario.getPassword().equals(contra)) && (usuario.getAdmin().equals(1))) {
			return true;
		} else if(usuario.getPassword().equals(contra)){
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean comprobarReg(String nomUsuario) {
		Usuario usuario = db.find(Usuario.class, nomUsuario);
		if(usuario == null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void regUsuario(Usuario usuario){
		c = ConfigXML.getInstance();
		String fileName = c.getDbFilename();
		emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
		db = emf.createEntityManager();
		db.getTransaction().begin();
		db.persist(usuario);
		db.getTransaction().commit();
	}
	
	public Integer getPermi(Usuario logged) {
		Usuario usuario = db.find(Usuario.class, logged.getNomUsuario());
		return usuario.getAdmin();
	}
	

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}
	
	public Question getQuestion(Integer questionNumber) {
		Question pregunta = db.find(Question.class, questionNumber);
		return pregunta;
	}
	
	public Pronostico getPronostico(Integer idpronostico, String pron) {
		TypedQuery<Pronostico> query = 
				db.createQuery("SELECT p FROM Pronostico p WHERE p.idPronostico=?1 AND p.pronostico=?2", Pronostico.class);
		query.setParameter(1, idpronostico);
		query.setParameter(2, pron);
		return query.getSingleResult();
	}
	
	public List<Question> getQuestionsFromEvent(Event e){
		System.out.println(">> DataAccess: getQuestionsFromEvent");
		db.getTransaction().begin();
		TypedQuery<Event> query = 
				db.createQuery("SELECT e FROM Event e WHERE e.eventNumber=?1", Event.class);
		query.setParameter(1, e.getEventNumber());
		Event ev = query.getSingleResult();
		return ev.getQuestions();
	}
	
	public Event createEvent(String nombre, Date fecha, Deporte deporte){
		System.out.println(">> DataAccess: createEvent=> event= " + nombre + " date= " + fecha);

		db.getTransaction().begin();
		// db.persist(q);
		
		Deporte dep = db.find(Deporte.class, deporte.getTipo());
		
		Event ev = new Event(nombre, fecha, dep);
		dep.addEvent(ev);
		db.persist(dep);
		db.getTransaction().commit();
		return ev;

	}
	
	public Deporte createDeporte(String tipo) {
		Deporte d = new Deporte(tipo);
		db.getTransaction().begin();
		db.persist(d);
		db.getTransaction().commit();
		return d;
	}
	
	public List<Deporte> getDeportes(){
		db.getTransaction().begin();
		TypedQuery<Deporte> query = 
				db.createQuery("SELECT d FROM Deporte d", Deporte.class);
		List<Deporte> deportes = query.getResultList();
		db.getTransaction().commit();
		return deportes;
	}
	
	public Deporte getDeporte(String tipo) {
		db.getTransaction().begin();
		Deporte dep = db.find(Deporte.class , tipo);
		db.getTransaction().commit();
		return dep;
	}
	
	public Pronostico getPronFromString(String pronostico, Question selectedQuestion) {
		System.out.println(">> DataAcess: getPronFromString");

		TypedQuery<Pronostico> query = 
				db.createQuery("SELECT p FROM Pronostico p WHERE p.pronostico=?1 AND p.pregunta.questionNumber=?2", Pronostico.class);
		query.setParameter(1, pronostico);
		query.setParameter(2, selectedQuestion.getQuestionNumber());
		Pronostico res = query.getSingleResult();
		System.out.println("el pronostico desde el string es: "+res);

		return res;
		
	}
	
	public Apuesta createBet(Usuario usuario, Float cantApostada, Pronostico pronostico, Float radio, Event selectedEvent,
			Question selectedQuestion) {
		System.out.println(">> DataAcess: createBet");
		db.getTransaction().begin();
		Usuario user = db.find(Usuario.class, usuario.getNomUsuario());
		
		//Apuesta apuesta = user.addApuesta(cantApostada);
		
		Apuesta apuesta = new Apuesta(cantApostada, pronostico);
		Pronostico pron = db.find(Pronostico.class, pronostico.getIdPronostico());
		
		int id = 0;
		
		List<Apuesta> listaApuestas = user.getApuestas();
		
		if (listaApuestas.size() > 0) 
			id = listaApuestas.get(listaApuestas.size()-1).getIdApuesta()+1;
		

		apuesta.setPronostico(pron.getPronostico());
		apuesta.setIdApuesta(id);
		apuesta.setUser(user);
		
		apuesta.setRatio(radio);
		apuesta.setEvento(selectedEvent);
		
		user.addApuesta(apuesta);
		
		pron.addApuesta(apuesta);
		
		db.persist(user);
		
		db.getTransaction().commit();
		return apuesta;
		
	}
	
	public Pronostico addPronostico(Pronostico pronostico){
		Question pregunta = pronostico.getPregunta();
		int idPregunta = pregunta.getQuestionNumber();
		Question que = db.find(Question.class, idPregunta);

		db.getTransaction().begin();
		Pronostico pronos = que.addPronostico(pronostico);
		db.persist(pronos);
		db.persist(que);
		db.getTransaction().commit();
		return pronos;
	}
	
	public Usuario addBalance(Usuario usuario, Float suma) {
		Usuario usu = db.find(Usuario.class, usuario.getNomUsuario());
		
		db.getTransaction().begin();
		usu.getMonedero().setBalance(usu.getMonedero().getBalance() + suma);
		db.getTransaction().commit();
		
		Usuario usu1 = db.find(Usuario.class, usuario.getNomUsuario());
		return usu1;
	}
	
	public Usuario getLoggedUsuarioDat(Usuario usuario) {
		Usuario usu = db.find(Usuario.class, usuario.getNomUsuario());
		return usu;		
	}
	
	public Float quitarDinero(Usuario usuario, Float cantMoney) {
		Usuario usu = db.find(Usuario.class, usuario.getNomUsuario());
		Float newMoney = usu.getMonedero().getBalance()-cantMoney;
		db.getTransaction().begin();
		usu.getMonedero().setBalance(newMoney);
		db.getTransaction().commit();
		return newMoney;
	}
	
	public List<Pronostico> getPronosticosFromQuestion(Question q){
		db.getTransaction().begin();
		Question question = db.find(Question.class, q.getQuestionNumber());
		List<Pronostico> pronosticos = question.getPronosticos();
		db.getTransaction().commit();
		return pronosticos;
	}
	
	public List<Apuesta> getApuesta(){
		db.getTransaction().begin();
		TypedQuery<Apuesta> query = 
				db.createQuery("SELECT e FROM Apuesta e", Apuesta.class);
		List<Apuesta> ListaApuesta = query.getResultList();
		db.getTransaction().commit();
		return ListaApuesta;
	}
	
	public List<Apuesta> getAllApuestas(){
		db.getTransaction().begin();
		TypedQuery<Apuesta> query = 
				db.createQuery("SELECT e FROM Apuesta e", Apuesta.class);
		List<Apuesta> ListaApuesta = query.getResultList();
		db.getTransaction().commit();
		return ListaApuesta;
	}
	
	public void getApuestaFromString(String pronostico) {
		TypedQuery<Apuesta> query = 
				db.createQuery("SELECT a FROM Apuesta a WHERE a.pronostico.pronostico=?1", Apuesta.class);
		query.setParameter(1, pronostico);
	}
	
	public Apuesta getApuestaID(int id) {
		TypedQuery<Apuesta> query = db.createQuery("SELECT a FROM Apuesta a WHERE a.idApuesta=?1", Apuesta.class);
		query.setParameter(1, id);
		return query.getSingleResult();
	}
	
	public Vector<Date> getEventsMonthDeporte(Date date, Deporte sport) {
		System.out.println(">> DataAccess: getEventsMonthDeporte");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE (ev.eventDate BETWEEN ?1 and ?2) AND ev.deporte.tipo=?3", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		query.setParameter(3, sport.getTipo());
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}
	
	public Vector<Date> getEventsMonthDeporteOpposite(Date date, Deporte sport) {
		System.out.println(">> DataAccess: getEventsMonthDeporte");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE (ev.eventDate BETWEEN ?1 and ?2) AND  NOT ev.deporte.tipo=?3", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		query.setParameter(3, sport.getTipo());
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}
	
	
	public Vector<Event> getEventFromDeporte(Date date, Deporte sport) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1 AND ev.deporte.tipo=?2", Event.class);
		query.setParameter(1, date);
		query.setParameter(2, sport.getTipo());
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}
	
	public Vector<Event> getEventsFromDeporte(Deporte sport){
		Vector<Event> res = new Vector <Event>();
		db.getTransaction().begin();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.deporte.tipo=?1", Event.class);
		query.setParameter(1, sport.getTipo());
		List<Event> events = query.getResultList();
		for(Event ev: events) {
			System.out.println("Los eventos del deporte seleccionado:"+" "+ev.toString());
			res.add(ev);
		}
		db.getTransaction().commit();
		return res;
	}
	

}

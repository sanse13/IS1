package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Deporte implements Serializable {
	@Id
	@XmlID
	private String tipo;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Event> events = new Vector<Event>();

	public Deporte() {
		super();
	}
	public Deporte(String tipo) {
		this.tipo = tipo;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public Vector<Event> getEvents() {
		return events;
	}
	
	public void setEvents(Vector<Event> events) {
		this.events = events;
	}
	
	public Event addEvent(Event evento) {
		events.add(evento);
		return evento;
	}
	
	public String toString() {
		return tipo;
	}
}
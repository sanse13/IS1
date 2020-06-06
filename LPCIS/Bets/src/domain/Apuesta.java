package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Apuesta implements Serializable {
	@Id
	@GeneratedValue
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@XmlID
	private Integer idApuesta;
	private Float dApostado;
	@XmlIDREF
	private Pronostico pronostico;
	@XmlIDREF
	private Usuario user;
	private Float ratio;
	@XmlIDREF
	private Event evento;
	
	public Apuesta() {
		super();
	}
	
	public void setPronostico(Pronostico pronostico) {
		this.pronostico = pronostico;
	}

	public Event getEvento() {
		return evento;
	}

	public void setEvento(Event evento) {
		this.evento = evento;
	}

	public Apuesta(Integer idApuesta, Float dApostado) {	
		this.idApuesta = idApuesta;
		this.dApostado = dApostado;
	}
	
	public Apuesta(Integer idApuesta, Float dApostado, Float ratio) {	
		this.idApuesta = idApuesta;
		this.dApostado = dApostado;
		this.ratio = ratio;
	}
	
	public Apuesta(Float dApostado) {
		this.dApostado = dApostado;
	}
	
	public Apuesta(Float dApostado, Pronostico pron) {
		this.dApostado = dApostado;
		this.pronostico = pron;
	}
	
	public Float getRatio() {
		return ratio;
	}
	
	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}
	
	public Integer getIdApuesta() {
		return idApuesta;
	}
	
	public void setIdApuesta(Integer idApuesta) {
		this.idApuesta = idApuesta;
	}
	
	public Float getdApostado() {
		return dApostado;
	}
	
	public void setdApostado(Float dApostado) {
		this.dApostado = dApostado;
	}
	
	public Usuario getUser() {
		return this.user;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public Pronostico getPronostico() {
		return this.pronostico;
	}
	
	public void setPronostico(String pronostico) {
		this.pronostico.setPronostico(pronostico);
	}
	
	public String getStringPron() {
		return this.pronostico.getPronostico();
	}
	
	public String toString(){
		return pronostico.getPronostico();
	}
}

package domain;

import java.io.Serializable;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Pronostico implements Serializable {
	@Id
	@GeneratedValue
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	private Integer idPronostico;
	@XmlIDREF
	private Question pregunta;
	private Float proporcion;
	private String pronostico;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Apuesta> apuestas = new Vector<Apuesta>();
	
	public Pronostico() {
		super();
	}
	
	@Override
	public String toString() {
		return pronostico;
	}

	public Vector<Apuesta> getApuestas() {
		return apuestas;
	}

	public void setApuestas(Vector<Apuesta> apuestas) {
		this.apuestas = apuestas;
	}

	public Pronostico(Float proporcion, String pronostico, Question pregunta) {
		this.proporcion = proporcion;
		this.pronostico = pronostico;
		this.pregunta = pregunta;
	}

	public Integer getIdPronostico() {
		return idPronostico;
	}

	public void setIdPronostico(Integer idPronostico) {
		this.idPronostico = idPronostico;
	}
	

	public Question getPregunta() {
		return pregunta;
	}

	public void setPregunta(Question pregunta) {
		this.pregunta = pregunta;
	}

	public Float getProporcion() {
		return proporcion;
	}

	public void setProporcion(Float proporcion) {
		this.proporcion = proporcion;
	}

	public String getPronostico() {
		return pronostico;
	}

	public void setPronostico(String pronostico) {
		this.pronostico = pronostico;
	}
	
	public Apuesta addApuesta(Apuesta apuesta)  {
        apuestas.add(apuesta);
        return apuesta;
	}

}

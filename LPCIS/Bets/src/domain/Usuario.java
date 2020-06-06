package domain;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;
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

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Usuario implements Serializable {
	@XmlID
	@Id
	private String nomUsuario;
	private String password;
	private Integer admin;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Monedero monedero;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Apuesta> apuestas = new Vector<Apuesta>();
	
	public Usuario() {
		super();
	}
	
	public Usuario(String nomUsuario, String password) {
		super();
		this.nomUsuario = nomUsuario;
		this.password = password;
		admin = 0;
		Monedero monedero = new Monedero(this);
		this.monedero = monedero;
	}

	public Usuario(String nomUsuario, String password, Integer admin) {
		super();
		this.nomUsuario = nomUsuario;
		this.password = password;
		this.admin = admin; 
	}
	
	public String getNomUsuario(){
		return nomUsuario;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Integer getAdmin() {
		return admin;
	}
	
	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Monedero getMonedero() {
		return monedero;
	}

	public void setMonedero(Monedero monedero) {
		this.monedero = monedero;
	}
	
	public List<Apuesta> getApuestas(){
		return this.apuestas;
	}
	
	public void addApuesta(Apuesta apuesta) {
		this.apuestas.add(apuesta);
	}
}

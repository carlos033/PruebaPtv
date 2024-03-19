package com.ptv.aplicacion.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Registro")
public class Registro implements Serializable{

	private static final long serialVersionUID = 2L;
	private String codTecnico;
	private String nombre;
	private String email;
	private String delegacion;
	private int puntos;
	private String cargo;

	@XmlAttribute(name = "CodTecnico")
	public String getCodTecnico() {
		return codTecnico;
	}

	@XmlAttribute(name = "Nombre")
	public String getNombre() {
		return nombre;
	}

	@XmlAttribute(name = "Email")
	public String getEmail() {
		return email;
	}


	@XmlAttribute(name = "Delegacion")
	public String getDelegacion() {
		return delegacion;
	}

	@XmlAttribute(name = "Puntos")
	public int getPuntos() {
		return puntos;
	}

	@XmlAttribute(name = "Cargo")
	public String getCargo() {
		return cargo;
	}
}

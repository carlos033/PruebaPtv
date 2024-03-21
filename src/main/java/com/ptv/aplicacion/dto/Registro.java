package com.ptv.aplicacion.dto;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@XmlAccessorType(XmlAccessType.FIELD)
	public class Registro implements Serializable{
	
		private static final long serialVersionUID = 2L;
		@XmlAttribute(name = "CodTecnico")
		private String codTecnico;
	
		@XmlAttribute(name = "Nombre")
		private String nombre;
	
		@XmlAttribute(name = "Email")
		private String email;
	
		@XmlAttribute(name = "Delegacion")
		private String delegacion;
	
		@XmlAttribute(name = "Puntos")
		private int puntos;
	
		@XmlAttribute(name = "Cargo")
		private String cargo;

//	@XmlAttribute(name = "CodTecnico")
//	public String getCodTecnico() {
//		return codTecnico;
//	}
//
//	@XmlAttribute(name = "Nombre")
//	public String getNombre() {
//		return nombre;
//	}
//
//	@XmlAttribute(name = "Email")
//	public String getEmail() {
//		return email;
//	}
//
//
//	@XmlAttribute(name = "Delegacion")
//	public String getDelegacion() {
//		return delegacion;
//	}
//
//	@XmlAttribute(name = "Puntos")
//	public int getPuntos() {
//		return puntos;
//	}
//
//	@XmlAttribute(name = "Cargo")
//	public String getCargo() {
//		return cargo;
//	}
}

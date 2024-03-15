/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.ptv.aplicacion.adapter;

import java.io.Serializable;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Recoge los datos de dentro del campo registro del XML
 *
 * @author ck
 */
@XmlRootElement(name = "Registro")
public class Registro implements Serializable{

	private static final long serialVersionUID = 2L;
	private String codTecnico;
	private String nombre;
	private String email;
	private String delegacion;
	private int puntos;
	private String cargo;

	public Registro(String codTecnico, String nombre, String email, String delegacion, int puntos,
			String cargo) {
		this.codTecnico = codTecnico;
		this.nombre = nombre;
		this.email = email;
		this.delegacion = delegacion;
		this.puntos = puntos;
		this.cargo = cargo;
	}

	public Registro() {}

	@XmlAttribute(name = "CodTecnico")
	public String getCodTecnico() {
		return codTecnico;
	}

	public void setCodTecnico(String codTecnico) {
		this.codTecnico = codTecnico;
	}

	@XmlAttribute(name = "Nombre")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@XmlAttribute(name = "Email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlAttribute(name = "Delegacion")
	public String getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	@XmlAttribute(name = "Puntos")
	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	@XmlAttribute(name = "Cargo")
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Override
	public String toString() {
		return "Registro{" + "codTecnico=" + codTecnico + ", nombre=" + nombre + ", email=" + email
				+ ", delegacion=" + delegacion + ", puntos=" + puntos + ", cargo=" + cargo + '}';
	}

}

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.ptv.infraestructura.entidad;

import java.io.Serializable;
import java.util.Arrays;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Clase que representa la tabla de la BD
 *
 * @author ck
 */
@Entity
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "nombre")
	private String nombre;
	@Id
	@Basic(optional = false)
	@Column(name = "email")
	private String email;
	@Basic(optional = false)
	@Column(name = "firma", length = 100000)
	private byte[] firma;

	public Usuario() {}

	public Usuario(String nombre, String email, String firma) {
		this.nombre = nombre;
		this.email = email;
		this.firma = firma.getBytes();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getFirma() {
		return firma;
	}

	public void setFirma(byte[] firma) {
		this.firma = firma;
	}

	@Override
	public String toString() {
		return "Usuario{" + "email=" + email + ", nombre=" + nombre + ", firma="
				+ Arrays.toString(firma) + '}';
	}
}

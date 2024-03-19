package com.ptv.infraestructura.entidad;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

	public Usuario(String nombre, String email, String firma) {
		this.nombre = nombre;
		this.email = email;
		this.firma = firma.getBytes();
	}
}

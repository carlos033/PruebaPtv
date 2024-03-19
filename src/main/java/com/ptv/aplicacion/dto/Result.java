package com.ptv.aplicacion.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "result")
public class Result{
	private Registro registro;

	@XmlElement(name = "Registro")
	public Registro getRegistro() {
		return registro;
	}
}

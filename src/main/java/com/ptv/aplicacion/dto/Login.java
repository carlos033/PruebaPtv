package com.ptv.aplicacion.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Login implements Serializable{

	private static final long serialVersionUID = 1L;
	private String dNI;
	private String codTecnico;
}

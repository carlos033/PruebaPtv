package com.ptv.dominio.excepcion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorDeConexion extends RuntimeException{

	private static final long serialVersionUID = 4L;
	private final int code;

	public ErrorDeConexion(int codigoError, String string) {
		super(string);
		this.code = codigoError;
	}
}

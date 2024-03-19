package com.ptv.dominio.excepcion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ErrorDeConexion extends InternalError{

	private static final long serialVersionUID = 1L;
	private final int code;

	public ErrorDeConexion(int codigoError, Throwable throwable) {
		super(throwable);
		this.code = codigoError;
	}
}

package com.ptv.dominio.excepcion;

public class DatosNoValidos extends Exception {
  
    private static final long serialVersionUID = 3L;

    public DatosNoValidos(String msg) {
        super("Ha ocurrido una excepcion: " + msg);
    }
}

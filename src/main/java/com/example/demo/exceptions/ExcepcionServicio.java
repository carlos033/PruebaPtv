/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.exceptions;

/**
 * Clase para mostrar las posibles excepciones de los metodos
 * @author ck
 */
public class ExcepcionServicio extends Exception {
  
    private static final long serialVersionUID = 3L;
/**
 *  Muestra el mensaje en caso de ocurrruna excepcion
 * @param msg 
 */
    public ExcepcionServicio(String msg) {
        super("Ha ocurrido una excepcion: " + msg);
    }
}

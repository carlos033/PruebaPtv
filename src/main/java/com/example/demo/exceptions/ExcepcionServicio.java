/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.exceptions;

/**
 *
 * @author ck
 */
public class ExcepcionServicio extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 3L;

    public ExcepcionServicio(String msg) {
        super("Ha ocurrido una excepcion en el servicio: " + msg);
    }
}

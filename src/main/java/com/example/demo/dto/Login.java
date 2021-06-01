/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.dto;

import java.io.Serializable;

/**
 * Clase que reocge los datos del login
 *
 * @author ck
 */
public class Login implements Serializable {

    private static final long serialVersionUID = 2L;
    private String dNI;
    private String codTecnico;

    public Login() {
    }

    public Login(String dNI, String codTecnico) {
        this.dNI = dNI;
        this.codTecnico = codTecnico;
    }

    public String getdNI() {
        return dNI;
    }

    public void setdNI(String dNI) {
        this.dNI = dNI;
    }

    public String getCodTecnico() {
        return codTecnico;
    }

    public void setCodTecnico(String codTecnico) {
        this.codTecnico = codTecnico;
    }

    @Override
    public String toString() {
        return "Login{" + "dNI=" + dNI + ", codTecnico=" + codTecnico + '}';
    }

}

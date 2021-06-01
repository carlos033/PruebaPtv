/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.serviciosI;

import com.example.demo.dto.Registro;
import com.example.demo.exceptions.ExcepcionServicio;
import com.example.demo.modelos.Usuario;
import com.itextpdf.text.Image;

/**
 *
 * @author ck
 */
public interface ServiciosI {

    public void insert(Usuario usuario) throws ExcepcionServicio;

    public Registro obtenerDatos(String codigoTecnico, String dni) throws ExcepcionServicio;

    public void envioEmail(String email);

    public void crearPDF(String nombre, String email, String strImagen) throws ExcepcionServicio;

    public Image base64ToImagen(String imagen) throws ExcepcionServicio;

}

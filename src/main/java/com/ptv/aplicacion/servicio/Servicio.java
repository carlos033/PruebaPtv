/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ptv.aplicacion.servicio;

import com.itextpdf.text.Image;
import com.ptv.aplicacion.adapter.Registro;
import com.ptv.dominio.excepcion.ExcepcionServicio;
import com.ptv.infraestructura.entidad.Usuario;

/**
 *
 * @author ck
 */
public interface Servicio {

    public void insert(Usuario usuario) throws ExcepcionServicio;

    public Registro obtenerDatos(String codigoTecnico, String dni) throws ExcepcionServicio;

    public void envioEmail(String email);

    public void crearPDF(String nombre, String email, String strImagen) throws ExcepcionServicio;

    public Image base64ToImagen(String imagen) throws ExcepcionServicio;

}

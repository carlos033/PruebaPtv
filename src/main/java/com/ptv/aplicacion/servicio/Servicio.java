package com.ptv.aplicacion.servicio;

import java.io.IOException;
import com.itextpdf.layout.element.Image;
import com.ptv.aplicacion.dto.Registro;
import com.ptv.dominio.excepcion.DatosNoValidos;
import com.ptv.infraestructura.entidad.Usuario;
import jakarta.mail.MessagingException;

public interface Servicio{

	public void insert(Usuario usuario) throws DatosNoValidos;

	public Registro obtenerDatos(String codigoTecnico, String dni) throws Exception;

	public void envioEmail(String email) throws MessagingException;

	public void crearPDF(String nombre, String email, String strImagen) throws IOException;

	public Image base64ToImagen(String imagen) throws IOException;

}

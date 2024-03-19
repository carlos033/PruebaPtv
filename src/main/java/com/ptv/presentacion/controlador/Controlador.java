/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.ptv.presentacion.controlador;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import com.ptv.aplicacion.dto.Login;
import com.ptv.aplicacion.dto.Registro;
import com.ptv.aplicacion.servicio.Servicio;
import com.ptv.dominio.excepcion.DatosNoValidos;
import com.ptv.infraestructura.entidad.Usuario;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class Controlador{

	private Servicio servicios;

	@GetMapping("/")
	public String showForm(Model model) {
		model.addAttribute("login", new Login());
		return "login";
	}

	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute("login") Login login, ModelMap model) {
		try {
			Registro usuario = servicios.obtenerDatos(login.getCodTecnico(), login.getDNI());
			model.addAttribute("usuario", usuario);
			return new ModelAndView("Formulario", model);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}

	@PostMapping("/guardar")
	public ModelAndView guardar(HttpServletRequest request, HttpServletResponse response) {
		String imagenVacia = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAA"
				+ "AAAXNSR0IArs4c6QAABGJJREFUeF7t1AEJAAAMAsHZv/RyPNwSyDncOQIECEQEFskpJgECBM5geQICBDICBi"
				+ "tTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSo"
				+ "CAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0"
				+ "CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBA"
				+ "xWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEp"
				+ "QAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH"
				+ "6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyA"
				+ "gYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCU"
				+ "oAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8"
				+ "AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARM"
				+ "FiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUA"
				+ "IEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QE"
				+ "CBDICBitTlaAECBgsP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwM"
				+ "lUJSoCAwfIDBAhkBAxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBg"
				+ "sP0CAQEbAYGWqEpQAAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkB"
				+ "AxWpipBCRAwWH6AAIGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQ"
				+ "AAYPlBwgQyAgYrExVghIgYLD8AAECGQGDlalKUAIEDJYfIEAgI2CwMlUJSoCAwfIDBAhkBAxWpipBCRAwWH6AA"
				+ "IGMgMHKVCUoAQIGyw8QIJARMFiZqgQlQMBg+QECBDICBitTlaAECBgsP0CAQEbAYGWqEpQAgQdWMQCX4yW9owA"
				+ "AAABJRU5ErkJggg==";
		String nombre = request.getParameter("nombre");
		String correo = request.getParameter("correo");
		String imagen = request.getParameter("imagen");
		if (eliminarEspaciosEnBlanco(imagen).equals(eliminarEspaciosEnBlanco(imagenVacia))) {
			return new ModelAndView("SinFirma");
		}
		Usuario usuario = new Usuario(nombre, correo, imagen);


		try {
			servicios.crearPDF(nombre, correo, imagen);
			servicios.envioEmail(correo);
			servicios.insert(usuario);
			return new ModelAndView("Exito");
		} catch (IOException | DatosNoValidos | MessagingException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ((Throwable) e).getMessage());
		}
	}

	public String eliminarEspaciosEnBlanco(String cadena) {
		return cadena.replaceAll("\\s", "");
	}
}

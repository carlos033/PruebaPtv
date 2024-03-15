/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.ptv.presentacion.controlador;


import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import com.ptv.aplicacion.adapter.Login;
import com.ptv.aplicacion.adapter.Registro;
import com.ptv.aplicacion.servicio.Servicio;
import com.ptv.dominio.excepcion.ExcepcionServicio;
import com.ptv.infraestructura.entidad.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

/**
 *
 * @author ck
 */
@RestController
@AllArgsConstructor
public class Controlador{

	private Servicio servicios;

	/**
	 * Nos introduce a la pagina principal
	 *
	 * @param model
	 * @return la pagina Login.html
	 */
	@GetMapping("/")
	public String showForm(Model model) {
		model.addAttribute("login", new Login());
		return "login";
	}

	/**
	 * Al pulsar el boton en la pagina de login envia los datos al endpoint para poder obtener los
	 * datos del xml, guardando los datos en un objeto usuario
	 *
	 * @param login
	 * @param model
	 * @return Envia al html de formulario
	 */
	@PostMapping("/login")
	public ModelAndView login(@ModelAttribute("login") Login login, ModelMap model) {
		Registro usuario = null;
		try {
			usuario = servicios.obtenerDatos(login.getCodTecnico(), login.getdNI());
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
		}
		model.addAttribute("usuario", usuario);
		return new ModelAndView("Formulario", model);
	}

	/**
	 * Recoge los datos del formulario realiza para llamar a los metodos para hacer el insert, crear
	 * el pdf y enviar el email. Si el formulario no estuviese firmado reenvia a realizar el login de
	 * nuevo
	 *
	 * @param request
	 * @param response
	 * @return envia al pdf de exito si todo a ido correctamente
	 */
	@PostMapping("/guardar")
	public ModelAndView guardar(HttpServletRequest request, HttpServletResponse response) {
		String imagenVacia = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAARQAAAC"
				+ "0CAYAAABVG9MjAAAE9klEQVR4Xu3UsQ0AMAzDsPb/o12gN2hkDvBABLrbdhwBAg"
				+ "QCgSsogaIJAgS+gKB4BAIEMgFBySgNESAgKH6AAIFMQFAySkMECAiKHyBAIBMQlIz"
				+ "SEAECguIHCBDIBAQlozREgICg+AECBDIBQckoDREgICh+gACBTEBQMkpDBAgIih8gQ"
				+ "CATEJSM0hABAoLiBwgQyAQEJaM0RICAoPgBAgQyAUHJKA0RICAofoAAgUxAUDJKQwQI"
				+ "CIofIEAgExCUjNIQAQKC4gcIEMgEBCWjNESAgKD4AQIEMgFBySgNESAgKH6AAIFMQFA"
				+ "ySkMECAiKHyBAIBMQlIzSEAECguIHCBDIBAQlozREgICg+AECBDIBQckoDREgICh+gACBT"
				+ "EBQMkpDBAgIih8gQCATEJSM0hABAoLiBwgQyAQEJaM0RICAoPgBAgQyAUHJKA0RICAofoA"
				+ "AgUxAUDJKQwQICIofIEAgExCUjNIQAQKC4gcIEMgEBCWjNESAgKD4AQIEMgFBySgNESAgKH6"
				+ "AAIFMQFAySkMECAiKHyBAIBMQlIzSEAECguIHCBDIBAQlozREgICg+AECBDIBQckoDREgICh+g"
				+ "ACBTEBQMkpDBAgIih8gQCATEJSM0hABAoLiBwgQyAQEJaM0RICAoPgBAgQyAUHJKA0RICAofoA"
				+ "AgUxAUDJKQwQICIofIEAgExCUjNIQAQKC4gcIEMgEBCWjNESAgKD4AQIEMgFBySgNESAgKH6AAI"
				+ "FMQFAySkMECAiKHyBAIBMQlIzSEAECguIHCBDIBAQlozREgICg+AECBDIBQckoDREgICh+gACBT"
				+ "EBQMkpDBAgIih8gQCATEJSM0hABAoLiBwgQyAQEJaM0RICAoPgBAgQyAUHJKA0RICAofoAAgUx"
				+ "AUDJKQwQICIofIEAgExCUjNIQAQKC4gcIEMgEBCWjNESAgKD4AQIEMgFBySgNESAgKH6AAIFMQFA"
				+ "ySkMECAiKHyBAIBMQlIzSEAECguIHCBDIBAQlozREgICg+AECBDIBQckoDREgICh+gACBTEBQMkpD"
				+ "BAgIih8gQCATEJSM0hABAoLiBwgQyAQEJaM0RICAoPgBAgQyAUHJKA0RICAofoAAgUxAUDJKQwQICIo"
				+ "fIEAgExCUjNIQAQKC4gcIEMgEBCWjNESAgKD4AQIEMgFBySgNESAgKH6AAIFMQFAySkMECAiKHyBAI"
				+ "BMQlIzSEAECguIHCBDIBAQlozREgICg+AECBDIBQckoDREgICh+gACBTEBQMkpDBAgIih8gQCATE"
				+ "JSM0hABAoLiBwgQyAQEJaM0RICAoPgBAgQyAUHJKA0RICAofoAAgUxAUDJKQwQICIofIEAgExCUjN"
				+ "IQAQKC4gcIEMgEBCWjNESAgKD4AQIEMgFBySgNESAgKH6AAIFMQFAySkMECAiKHyBAIBMQlIzSEAE"
				+ "CguIHCBDIBAQlozREgICg+AECBDIBQckoDREgICh+gACBTEBQMkpDBAgIih8gQCATEJSM0hABAoLiB"
				+ "wgQyAQEJaM0RICAoPgBAgQyAUHJKA0RICAofoAAgUxAUDJKQwQICIofIEAgExCUjNIQAQKC4gcIEMgEB"
				+ "CWjNESAgKD4AQIEMgFBySgNESAgKH6AAIFMQFAySkMECAiKHyBAIBMQlIzSEAECguIHCBDIBAQlozRE"
				+ "gICg+AECBDIBQckoDREg8ABEWc4DB0ygSQAAAABJRU5ErkJggg==";
		String nombre = request.getParameter("nombre");
		String correo = request.getParameter("correo");
		String imagen = request.getParameter("imagen");
		if (imagen.equals(imagenVacia)) {
			return new ModelAndView("SinFirma");
		}
		Usuario usuario = new Usuario(nombre, correo, imagen);
		try {
			servicios.crearPDF(nombre, correo, imagen);
			servicios.insert(usuario);
		} catch (ExcepcionServicio ex) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
		}
		return new ModelAndView("Exito");
	}
}

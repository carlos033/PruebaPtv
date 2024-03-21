package com.ptv.presentacion.controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import com.ptv.aplicacion.dto.Login;
import com.ptv.aplicacion.servicio.Servicio;
import com.ptv.infraestructura.entidad.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(MockitoExtension.class)
class ControladorTest{
	@Test
	void showForm_ReturnsLoginPage() {
		// Arrange
		Controlador controlador = new Controlador(mock(Servicio.class));
		Model model = mock(Model.class);

		// Act
		String viewName = controlador.showForm(model);

		// Assert
		assertEquals("login", viewName);
		verify(model).addAttribute("login", new Login());
		verify(model).addAttribute(eq("login"), ArgumentMatchers.any(Login.class)); // Nuevo assert
	}

	@Test
	void login_InvalidLogin_ThrowsResponseStatusException() throws Exception {
		// Arrange
		Servicio servicioMock = mock(Servicio.class);
		Controlador controlador = new Controlador(servicioMock);
		Login login = new Login("123", "456");
		ModelMap modelMap = new ModelMap();
		when(servicioMock.obtenerDatos("123", "456")).thenThrow(new RuntimeException("Error"));

		// Act & Assert
		assertThrows(ResponseStatusException.class, () -> controlador.login(login, modelMap));
	}


	@Test
	void guardar_ValidData_ReturnsExitoPage() throws Exception {
		// Arrange
		Servicio servicioMock = mock(Servicio.class);
		Controlador controlador = new Controlador(servicioMock);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(request.getParameter("nombre")).thenReturn("John Doe");
		when(request.getParameter("correo")).thenReturn("john@example.com");
		when(request.getParameter("imagen")).thenReturn("base64ImageString");

		// Act
		ModelAndView modelAndView = controlador.guardar(request, response);

		// Assert
		assertEquals("Exito", modelAndView.getViewName());
		verify(servicioMock).crearPDF("John Doe", "john@example.com", "base64ImageString");
		verify(servicioMock).envioEmail("john@example.com");
		verify(servicioMock).insert(any(Usuario.class));
	}


	@Test
	void guardar_EmptyImage_ReturnsSinFirmaPage() throws Exception {
		// Arrange
		Controlador controlador = new Controlador(mock(Servicio.class));
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(request.getParameter("nombre")).thenReturn("John Doe");
		when(request.getParameter("correo")).thenReturn("john@example.com");
		when(request.getParameter("imagen"))
				.thenReturn("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAACWCAYAAABkW7XSAA"
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
						+ "AAABJRU5ErkJggg=="); // Imagen vacía
		Servicio servicioMock = mock(Servicio.class);

		// Act
		ModelAndView modelAndView = controlador.guardar(request, response);

		// Assert
		assertEquals("SinFirma", modelAndView.getViewName());
		verifyNoInteractions(servicioMock);
	}

}

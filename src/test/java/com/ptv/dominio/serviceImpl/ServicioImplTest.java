package com.ptv.dominio.serviceImpl;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ptv.dominio.excepcion.DatosNoValidos;
import com.ptv.dominio.repositorio.UsuarioRespository;
import com.ptv.infraestructura.entidad.Usuario;

@ExtendWith(MockitoExtension.class)
class ServicioImplTest{

	@Mock
	UsuarioRespository repositorio;

	@InjectMocks
	ServicioImpl servicio;

	@Test
	void insert_ValidUsuario_SaveUsuario() throws DatosNoValidos {
		// Arrange
		Usuario usuario = new Usuario();
		usuario.setNombre("John Doe");
		usuario.setEmail("john@example.com");

		// Act
		servicio.insert(usuario);

		// Assert
		verify(repositorio, times(1)).save(usuario);
	}
}


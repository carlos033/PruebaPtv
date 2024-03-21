package com.ptv.dominio.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ptv.infraestructura.entidad.Usuario;

public interface UsuarioRespository extends JpaRepository<Usuario, Long>{

}

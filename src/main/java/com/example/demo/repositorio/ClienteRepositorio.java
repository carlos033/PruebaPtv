/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorio;

import com.example.demo.modelos.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ck
 */
@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, String> {
    
    @Query("Select c from Cliente c where c.nombre = :nombre")
    public List<Cliente> buscarXNombre(@Param("nombre") String nombre);

    @Query("Select c from Cliente c where c.apellido = :apellido")
    public List<Cliente> buscarXApellido(@Param("apellido") String apellido);

}

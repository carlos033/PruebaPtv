/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorio;

import com.example.demo.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio que extiende de JPA
 * @author ck
 */
public interface UsuarioRespository extends JpaRepository<Usuario, String> {

}

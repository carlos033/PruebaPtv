/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.serviciosI;

import com.example.demo.exceptions.ExcepcionServicio;
import com.example.demo.modelos.Cliente;
import java.util.List;

/**
 *
 * @author ck
 */
public interface IServiciosCl {

    public void crearCliente(Cliente cliente);

    public List<Cliente> buscarXNombre(String nombre) throws ExcepcionServicio;

    public List<Cliente> buscarXApellido(String apellido) throws ExcepcionServicio;

    public List<Cliente> buscarTodos() throws ExcepcionServicio;

    public void eliminar(String dni) throws ExcepcionServicio;
}

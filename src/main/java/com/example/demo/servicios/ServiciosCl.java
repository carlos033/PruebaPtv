/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.exceptions.ExcepcionServicio;
import com.example.demo.modelos.Cliente;
import com.example.demo.repositorio.ClienteRepositorio;
import com.example.demo.serviciosI.IServiciosCl;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ck
 */
@Service("IServiciosCl")
@Transactional
public class ServiciosCl implements IServiciosCl {

    @Autowired
    private ClienteRepositorio repositorioC;

    @Override
    public List<Cliente> buscarXNombre(String nombre) throws ExcepcionServicio {
        List<Cliente> listaClientes = repositorioC.buscarXNombre(nombre);
        if (listaClientes.isEmpty()) {
            throw new ExcepcionServicio("No hay clientes con esos datos");
        }
        return listaClientes;
    }

    @Override
    public List<Cliente> buscarXApellido(String apellido) throws ExcepcionServicio {
        List<Cliente> listaClientes = repositorioC.buscarXNombre(apellido);
        if (listaClientes.isEmpty()) {
            throw new ExcepcionServicio("No hay clientes con esos datos");
        }
        return listaClientes;
    }

    @Override
    public void crearCliente(Cliente cliente) {
        repositorioC.save(cliente);
    }

    @Override
    public List<Cliente> buscarTodos() throws ExcepcionServicio {
        List<Cliente> listaClientes = repositorioC.findAll();
        if (listaClientes.isEmpty()) {
            throw new ExcepcionServicio("No hay clientes con esos datos");
        }
        return listaClientes;
    }

    @Override
    public void eliminar(String dni) throws ExcepcionServicio {
        Optional<Cliente> listaClientes = repositorioC.findById(dni);
        if (!listaClientes.isPresent()) {
            throw new ExcepcionServicio("No hay clientes con esos datos");
        }else{
           repositorioC.deleteById(dni);
        }
    }

}

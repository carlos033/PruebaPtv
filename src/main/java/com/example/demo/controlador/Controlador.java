/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import com.example.demo.exceptions.ExcepcionServicio;
import com.example.demo.modelos.Cliente;
import com.example.demo.serviciosI.IServiciosCl;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author ck
 */
@Controller
@RequestMapping("*")
public class Controlador {

    @Autowired
    private IServiciosCl sCliente;

    @GetMapping("/*")
    public String buscarTodos(Model model) {
        List<Cliente> list = new ArrayList<>();
        try {
            list = sCliente.buscarTodos();
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no hay clientes");
        }
        model.addAttribute("list", list);
        return "todos";
    }

}

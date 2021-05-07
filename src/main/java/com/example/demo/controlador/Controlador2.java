/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controlador;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.exceptions.ExcepcionServicio;
import com.example.demo.modelos.Cliente;
import com.example.demo.serviciosI.IServiciosCl;
import com.example.demo.util.Transformadores;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author ck
 */
@Controller
public class Controlador2 {

    @Autowired
    private Transformadores transformador;
    @Autowired
    private IServiciosCl sCliente;

    @GetMapping("/crearShow")
    public String showForm2(Model model) {
        return "crear";
    }

    @GetMapping("/nombre")
    @ResponseBody
    public String bustarXnombre(@RequestParam(name = "nombre", required = true) String nombre) {
        List<Cliente> listaClientes = new ArrayList<>();
        try {
            listaClientes = sCliente.buscarXNombre(nombre);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado");
        }
        return "busar";
    }

    @PostMapping("/new")
    public String aniadirCliente(@ModelAttribute ClienteDTO dTO, BindingResult result) {
        Cliente cliente = transformador.convertirAEntidadC(dTO);
        sCliente.crearCliente(cliente);
        ClienteDTO resultado = transformador.convertirADTOC(cliente);
        return "redirect:/*";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String eliminarMedico(@RequestParam String dni) {
        try {
            sCliente.eliminar(dni);
        } catch (ExcepcionServicio ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, ex.getMessage());
        }
        return "todos";
    }
}

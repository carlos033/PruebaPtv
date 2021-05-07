/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.util;

import com.example.demo.exceptions.ExcepcionServicio;
import com.example.demo.modelos.Cliente;
import com.example.demo.servicios.ServiciosCl;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ck
 */
@Component
public class PrecargaBD {

    @Autowired
    private ServiciosCl sCliente;

    @Transactional
    public void precargarBaseDeDatos() {
        try {
            // lanza excepcion si no lo encuentra, entonces en ese caso precargamos
            sCliente.buscarXNombre("Carlos");
        } catch (ExcepcionServicio ex) {
            try {
                String entrada = "12/03/2016";
                DateFormat format = new SimpleDateFormat("DD/MM/YYYY"); // Creamos un formato de fecha
                Date fecha = format.parse(entrada);
                Cliente cliente = new Cliente("45748030h", "Carlos", "Diaz", fecha);
                sCliente.crearCliente(cliente);
                String entrada2 = "13/02/2012";
                DateFormat format2 = new SimpleDateFormat("DD/MM/YYYY"); // Creamos un formato de fecha
                Date fecha2 = format2.parse(entrada2);
                Cliente cliente2 = new Cliente("45746030f", "Pedro", "Martinez", fecha2);
                sCliente.crearCliente(cliente2);
                String entrada3 = "2/04/2020";
                DateFormat format3 = new SimpleDateFormat("DD/MM/YYYY"); // Creamos un formato de fecha
                Date fecha3 = format3.parse(entrada3);
                Cliente cliente3 = new Cliente("45745630z", "Maria", "Perez", fecha3);
                sCliente.crearCliente(cliente3);
            } catch (ParseException ex1) {
                Logger.getLogger(PrecargaBD.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.util;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.modelos.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 *
 * @author ck
 */
@Component
public class Transformadores {

    @Autowired
    private ModelMapper modelMapper;

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public Cliente convertirAEntidadC(ClienteDTO dTO) {
        Cliente entidad = modelMapper.map(dTO, Cliente.class);
        return entidad;
    }

    public ClienteDTO convertirADTOC(Cliente entidad) {
        ClienteDTO dTO = modelMapper.map(entidad, ClienteDTO.class);
        return dTO;
    }
}

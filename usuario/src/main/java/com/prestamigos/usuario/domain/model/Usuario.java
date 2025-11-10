package com.prestamigos.usuario.domain.model;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor

    public class Usuario {

        private Long id;
        private Long vendedorId; // FK microservicio vendedor

        private String email;
        private String password;
        private String nombre;

        private LocalDate fechaNacimiento; //yyyy-MM-dd

        private String direccion;
        private String ciudad;
        private String departamento;
        private String telefono;

        private Boolean activo; // si el usuario confirmó su correo
        private String tokenConfirmacion;
    }



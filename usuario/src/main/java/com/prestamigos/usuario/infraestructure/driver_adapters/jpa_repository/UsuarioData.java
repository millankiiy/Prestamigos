package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class UsuarioData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long vendedorId; // FK microservicio vendedor
    @Column(unique = true, nullable = false)
    private String email;
    @Column(length = 100, nullable = false)
    private String password;
    private String nombre;
    private LocalDate fechaNacimiento; // yyyy-MM-dd
    private String direccion;
    private String ciudad;
    private String departamento;
    private String telefono;
    private boolean activo;
    private String tokenConfirmacion;
}

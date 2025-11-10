package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioDataJpaRepository extends JpaRepository<UsuarioData, Long> {

    // JPA no tiene la consulta por eso se crea.Buscar usuario por correo. opcional porque puede arrojar una valor o no
    Optional<UsuarioData> findByEmail(String email);

    // Buscar usuario por token de confirmación
    Optional<UsuarioData> findByTokenConfirmacion(String token);
}

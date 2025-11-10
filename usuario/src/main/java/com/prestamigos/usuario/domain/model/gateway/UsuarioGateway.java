package com.prestamigos.usuario.domain.model.gateway;

import com.prestamigos.usuario.domain.model.Usuario;

import java.util.List;


public interface UsuarioGateway {

    Usuario guardarUsuario(Usuario usuario);
    List<Usuario> buscarTodos();
    Usuario buscarPorId(Long id);
    Usuario buscarPorEmail(String Email);
    Usuario actualizarUsuario (Usuario usuario);
    void eliminarUsuario(Long id);
    boolean existePorEmail(String email); //Verifica si un correo ya está registrado (para evitar duplicados).
    Usuario buscarPorToken(String token);

}



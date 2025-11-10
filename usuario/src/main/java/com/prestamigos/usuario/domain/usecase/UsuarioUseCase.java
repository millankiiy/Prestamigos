package com.prestamigos.usuario.domain.usecase;

import com.prestamigos.usuario.domain.model.Usuario;
import com.prestamigos.usuario.domain.model.gateway.EncrypterGateway;
import com.prestamigos.usuario.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioUseCase {

    private final UsuarioGateway usuarioGateway;
    private final EncrypterGateway encrypterGateway;
    private final CorreoService correoService; // Servicio para enviar correos

    public Usuario guardarUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Los datos del usuario no pueden estar vacíos.");
        }
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Verifica el nombre que ingresaste. No puede estar vacío.");
        }
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }
        if (!usuario.getEmail().contains("@")) {
            throw new IllegalArgumentException("El correo ingresado no tiene un formato válido.");
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (usuario.getPassword().length() < 3) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 3 caracteres.");
        }

        //fechaNacimiento
        if (usuario.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }
        LocalDate hoy = LocalDate.now();
        if (usuario.getFechaNacimiento().plusYears(15).isAfter(hoy)) {
            throw new IllegalArgumentException("El usuario debe ser mayor de 15 años.");
        }

        if (usuarioGateway.existePorEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con el correo: " + usuario.getEmail());
        }

        // Encriptar contraseña
        if (!usuario.getPassword().startsWith("$2a$")) {
            usuario.setPassword(encrypterGateway.encrypt(usuario.getPassword()));
        }
        usuario.setActivo(false);

        String token = UUID.randomUUID().toString();
        usuario.setTokenConfirmacion(token);

        Usuario usuarioGuardado = usuarioGateway.guardarUsuario(usuario);

        correoService.enviarCorreoConfirmacion(usuarioGuardado);

        return usuarioGuardado;
    }

    public List<Usuario> buscarTodos() {
        return usuarioGateway.buscarTodos();
    }

    public String eliminarUsuarioPorId(Long id) {
        try {
            usuarioGateway.eliminarUsuario(id);
            return "Usuario eliminado correctamente con ID: " + id;
        } catch (Exception error) {
            throw new IllegalArgumentException("Error al eliminar usuario: " + error.getMessage());
        }
    }

    public Usuario buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioGateway.buscarPorId(id);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        return usuario;
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Debes ingresar un correo válido para la búsqueda.");
        }

        Usuario usuario = usuarioGateway.buscarPorEmail(email);
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario registrado con el correo: " + email);
        }
        return usuario;
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("El ID es obligatorio para actualizar el usuario.");
        }

        Usuario existente = usuarioGateway.buscarPorId(usuario.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + usuario.getId());
        }

        if (usuario.getActivo() == null) {
            usuario.setActivo(existente.getActivo());
        }

        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            if (!usuario.getPassword().startsWith("$2a$")) {
                usuario.setPassword(encrypterGateway.encrypt(usuario.getPassword()));
            }
        } else {
            usuario.setPassword(existente.getPassword());
        }

        return usuarioGateway.actualizarUsuario(usuario);
    }

    public Usuario buscarPorEmail(String email, String password) {
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo y la contraseña son obligatorios para iniciar sesión.");
        }

        Usuario usuario = usuarioGateway.buscarPorEmail(email);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con el email: " + email);
        }

        if (usuario.getActivo() != null && !usuario.getActivo()) {
            throw new IllegalStateException("Debes confirmar tu correo antes de iniciar sesión.");
        }

        if (!encrypterGateway.checkPass(password, usuario.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta.");
        }

        return usuario;
    }

    public Usuario buscarPorToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token inválido.");
        }
        Usuario usuario = usuarioGateway.buscarPorToken(token);
        if (usuario == null) {
            throw new IllegalArgumentException("Token inválido o usuario no encontrado.");
        }
        return usuario;
    }
}

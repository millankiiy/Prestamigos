package com.prestamigos.usuario.infraestructure.entry_points;

import com.prestamigos.usuario.domain.model.Usuario;
import com.prestamigos.usuario.domain.usecase.UsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamigos/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;

    @PostMapping("/save")
    public ResponseEntity<?> saveUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioUseCase.guardarUsuario(usuario);
            return ResponseEntity.ok("Usuario registrado correctamente. Revisa tu correo para confirmar la cuenta.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.ok(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.ok("Error interno al registrar el usuario.");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> listarTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioUseCase.buscarTodos();
            if (usuarios.isEmpty()) {
                return ResponseEntity.ok("No hay usuarios registrados actualmente.");
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.ok("Error al obtener la lista de usuarios.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuarioById(@PathVariable Long id) {
        try {
            String mensaje = usuarioUseCase.eliminarUsuarioPorId(id);
            return ResponseEntity.ok(mensaje);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.ok("Error interno al eliminar el usuario.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioUseCase.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.ok("Usuario no encontrado con ID: " + id);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        try {
            Usuario usuario = usuarioUseCase.buscarUsuarioPorEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.ok(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.ok("No existe un usuario registrado con el correo: " + email);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioUseCase.actualizarUsuario(usuario);
            return ResponseEntity.ok("Usuario actualizado correctamente con ID: " + usuarioActualizado.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.ok("No existe un usuario con ese ID.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestParam String email, @RequestParam String password) {
        try {
            Usuario usuario = usuarioUseCase.buscarPorEmail(email, password);
            return ResponseEntity.ok("Login exitoso. Bienvenido, " + usuario.getNombre() + ".");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.ok(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.ok("Error interno al procesar el inicio de sesión.");
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmarUsuario(@RequestParam String token) {
        try {
            Usuario usuario = usuarioUseCase.buscarPorToken(token);
            usuario.setActivo(true);
            usuario.setTokenConfirmacion(null);
            usuarioUseCase.actualizarUsuario(usuario);

            return ResponseEntity.ok("✅ Usuario confirmado correctamente. Ahora puedes iniciar sesión.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok("⚠️ " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.ok("⚠️ Error interno al confirmar el usuario.");
        }
    }
}

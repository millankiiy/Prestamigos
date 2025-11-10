package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository;

import com.prestamigos.usuario.domain.model.Usuario;
import com.prestamigos.usuario.domain.model.gateway.UsuarioGateway;
import com.prestamigos.usuario.infraestructure.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UsuarioGatewayImpl implements UsuarioGateway {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioDataJpaRepository repository;
    private final RestTemplate restTemplate; // << agregado para validar vendedor

    @Override
    public Usuario guardarUsuario(Usuario usuario) {

        // Validamos vendedorId si viene
        if (usuario.getVendedorId() != null) {
            String url = "http://localhost:5001/vendedores/" + usuario.getVendedorId();
            try {
                restTemplate.getForObject(url, Object.class);
            } catch (Exception e) {
                throw new IllegalArgumentException("El vendedor con ID " + usuario.getVendedorId() + " no existe");
            }
        }

        UsuarioData usuarioData = usuarioMapper.toData(usuario);
        UsuarioData guardado = repository.save(usuarioData);
        return usuarioMapper.toUsuario(guardado);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return repository.findAll().stream()
                .map(usuarioMapper::toUsuario)
                .collect(Collectors.toList());
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .map(usuarioMapper::toUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado con ID: " + id)
                );
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .map(usuarioMapper::toUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario no encontrado con el correo: " + email)
                );
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("El ID del usuario es obligatorio para actualizarlo.");
        }

        if (!repository.existsById(usuario.getId())) {
            throw new IllegalArgumentException("No se encontró un usuario con ID: " + usuario.getId());
        }

        UsuarioData usuarioData = usuarioMapper.toData(usuario);
        UsuarioData actualizado = repository.save(usuarioData);
        return usuarioMapper.toUsuario(actualizado);
    }

    @Override
    public void eliminarUsuario(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró el usuario con ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.findByEmail(email).isPresent();

    }



    @Override
    public Usuario buscarPorToken(String token) {
        UsuarioData usuarioData = repository.findByTokenConfirmacion(token)
                .orElseThrow(() -> new RuntimeException("Usuario con token " + token + " no encontrado"));
        return usuarioMapper.toUsuario(usuarioData);
    }
}

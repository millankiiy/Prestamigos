package com.prestamigos.usuario.infraestructure.mapper;

import com.prestamigos.usuario.domain.model.Usuario;
import com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.UsuarioData;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toUsuario(UsuarioData data) {
        if (data == null) return null;

        return new Usuario(
                data.getId(),
                data.getVendedorId(),
                data.getEmail(),
                data.getPassword(),
                data.getNombre(),
                data.getFechaNacimiento(),
                data.getDireccion(),
                data.getCiudad(),
                data.getDepartamento(),
                data.getTelefono(),
                data.isActivo(),
                data.getTokenConfirmacion()
        );
    }

    public UsuarioData toData(Usuario usuario) {
        if (usuario == null) return null;

        return new UsuarioData(
                usuario.getId(),
                usuario.getVendedorId(),
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getNombre(),
                usuario.getFechaNacimiento(),
                usuario.getDireccion(),
                usuario.getCiudad(),
                usuario.getDepartamento(),
                usuario.getTelefono(),
                usuario.getActivo(),
                usuario.getTokenConfirmacion()
        );
    }
}

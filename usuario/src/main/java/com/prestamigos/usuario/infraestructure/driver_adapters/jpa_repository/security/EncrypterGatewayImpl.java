package com.prestamigos.usuario.infraestructure.driver_adapters.jpa_repository.security;

import com.prestamigos.usuario.domain.model.gateway.EncrypterGateway;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncrypterGatewayImpl implements EncrypterGateway {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override //Encriptacion de password
    public String encrypt(String password) {
        return encoder.encode(password);
    }

    @Override   //el matches es para verificar la contraseña ingresada con la contrasela encriptada de la base de datos
    public Boolean checkPass(String passUser, String passBD) {
        return encoder.matches(passUser, passBD);
    }
}

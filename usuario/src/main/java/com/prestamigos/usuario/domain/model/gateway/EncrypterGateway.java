package com.prestamigos.usuario.domain.model.gateway;

public interface EncrypterGateway {

    //encripta la contraseña
    String encrypt(String password);

    //se pone en EncrypterGatewayImpl para hacer el match
    Boolean checkPass(String passUser , String passBD);
}




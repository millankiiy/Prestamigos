package com.prestamigos.usuario.application;

import com.prestamigos.usuario.domain.model.gateway.EncrypterGateway;
import com.prestamigos.usuario.domain.model.gateway.UsuarioGateway;
import com.prestamigos.usuario.domain.usecase.CorreoService;
import com.prestamigos.usuario.domain.usecase.UsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class UseCaseConfig {

    @Bean
    public UsuarioUseCase usuarioUseCase(
            UsuarioGateway usuarioGateway,
            EncrypterGateway encrypterGateway,
            CorreoService correoService) {
        return new UsuarioUseCase(usuarioGateway, encrypterGateway, correoService);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}


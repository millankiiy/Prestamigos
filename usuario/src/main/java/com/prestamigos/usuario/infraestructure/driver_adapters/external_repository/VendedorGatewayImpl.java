package com.prestamigos.usuario.infraestructure.driver_adapters.external_repository;

import com.prestamigos.usuario.domain.model.gateway.VendedorGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class VendedorGatewayImpl implements VendedorGateway {

    private final RestTemplate restTemplate;

    @Override
    public boolean existeVendedor(Long vendedorId) {
        try {
            String url = "http://localhost:5001/api/prestamigos/vendedor/" + vendedorId;
            restTemplate.getForObject(url, Object.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

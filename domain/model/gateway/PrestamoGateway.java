package com.prestaamigos.pago.domain.model.gateway;

public interface PrestamoGateway {

    boolean existePrestamo(Long prestamoId);

    boolean prestamoPerteneceAlCliente(Long prestamoId, Long clienteId);
}


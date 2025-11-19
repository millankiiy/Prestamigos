package com.prestaamigos.pago.domain.model.gateway;

import com.prestaamigos.pago.domain.model.Pago;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PagoGateway {

    Pago guardarPago(Pago pago);
    Optional<Pago> buscarPorId(Long id);
    List<Pago> listarTodos();
    List<Pago> listarPorPrestamoId(Long prestamoId);
    List<Pago> listarPorClienteId(Long clienteId);
    List<Pago> listarPorFecha(LocalDate fecha);
    Pago actualizarPago(Pago pago);
    void eliminarPago(Long id);
}

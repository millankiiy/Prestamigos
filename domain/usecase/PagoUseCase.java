package com.prestaamigos.pago.domain.usecase;

import com.prestaamigos.pago.domain.model.Pago;
import com.prestaamigos.pago.domain.model.gateway.ClienteGateway;
import com.prestaamigos.pago.domain.model.gateway.PagoGateway;
import com.prestaamigos.pago.domain.model.gateway.PrestamoGateway;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PagoUseCase {

    private final PagoGateway pagoGateway;
    private final ClienteGateway clienteGateway;
    private final PrestamoGateway prestamoGateway;

    // -----------------------------------------------------------
    // GUARDAR
    // -----------------------------------------------------------
    public Object guardarPago(Pago pago) {

        // PRESTAMO EXISTE?
        if (!prestamoGateway.existePrestamo(pago.getPrestamoId())) {
            return "El préstamo con ID " + pago.getPrestamoId() + " no existe.";
        }

        // CLIENTE EXISTE?
        if (!clienteGateway.existeCliente(pago.getClienteId())) {
            return "El cliente con ID " + pago.getClienteId() + " no existe.";
        }

        // PRESTAMO PERTENECE AL CLIENTE?
        if (!prestamoGateway.prestamoPerteneceAlCliente(pago.getPrestamoId(), pago.getClienteId())) {
            return "El préstamo " + pago.getPrestamoId() + " NO pertenece al cliente " + pago.getClienteId();
        }

        // MÉTODO DE PAGO
        if (pago.getMetodo() == null || pago.getMetodo().isBlank()) {
            return "Debe enviar el método de pago (efectivo, nequi, etc).";
        }

        if (!esMetodoValido(pago.getMetodo())) {
            return "Método de pago inválido.";
        }

        // TIPO DE PAGO
        if (pago.getTipoPago() == null || pago.getTipoPago().isBlank()) {
            return "Debe enviar el tipo de pago (cuota, abono capital, etc).";
        }

        if (!esTipoValido(pago.getTipoPago())) {
            return "Tipo de pago inválido.";
        }

        // MONTO
        if (pago.getMonto() == null || pago.getMonto().doubleValue() <= 0) {
            return "El monto debe ser mayor a 0.";
        }

        // ⛔ AQUÍ ESTABA EL ERROR
        // Tu PagoData exige fecha NOT NULL, pero Pago venía sin fecha
        pago.setFecha(LocalDate.now());

        return pagoGateway.guardarPago(pago);
    }


    private boolean esMetodoValido(String metodo) {
        return metodo.equalsIgnoreCase("efectivo")
                || metodo.equalsIgnoreCase("nequi")
                || metodo.equalsIgnoreCase("daviplata");
    }

    private boolean esTipoValido(String tipo) {
        return tipo.equalsIgnoreCase("cuota")
                || tipo.equalsIgnoreCase("abono capital");
    }

    // -----------------------------------------------------------
    // CRUD
    // -----------------------------------------------------------

    public Optional<Pago> buscarPorId(Long id) {
        return pagoGateway.buscarPorId(id);
    }

    public List<Pago> listarTodos() {
        return pagoGateway.listarTodos();
    }

    public List<Pago> listarPorPrestamoId(Long prestamoId) {
        return pagoGateway.listarPorPrestamoId(prestamoId);
    }

    public List<Pago> listarPorClienteId(Long clienteId) {
        return pagoGateway.listarPorClienteId(clienteId);
    }

    public List<Pago> listarPorFecha(LocalDate fecha) {
        return pagoGateway.listarPorFecha(fecha);
    }

    public Object actualizarPago(Pago pago) {

        // 1. VALIDAR QUE EL ID EXISTA
        Optional<Pago> existente = pagoGateway.buscarPorId(pago.getId());
        if (existente.isEmpty()) {
            return "El pago con ID " + pago.getId() + " no existe.";
        }

        // 2. VALIDAR PRESTAMO
        if (!prestamoGateway.existePrestamo(pago.getPrestamoId())) {
            return "El préstamo con ID " + pago.getPrestamoId() + " no existe.";
        }

        // 3. VALIDAR CLIENTE
        if (!clienteGateway.existeCliente(pago.getClienteId())) {
            return "El cliente con ID " + pago.getClienteId() + " no existe.";
        }

        // 4. VALIDAR QUE EL PRÉSTAMO PERTENEZCA AL CLIENTE
        if (!prestamoGateway.prestamoPerteneceAlCliente(pago.getPrestamoId(), pago.getClienteId())) {
            return "El préstamo " + pago.getPrestamoId() + " NO pertenece al cliente " + pago.getClienteId();
        }

        // 5. VALIDAR MÉTODO
        if (pago.getMetodo() == null || pago.getMetodo().isBlank()) {
            return "Debe enviar el método de pago.";
        }
        if (!esMetodoValido(pago.getMetodo())) {
            return "Método de pago inválido.";
        }

        // 6. VALIDAR TIPO
        if (pago.getTipoPago() == null || pago.getTipoPago().isBlank()) {
            return "Debe enviar el tipo de pago.";
        }
        if (!esTipoValido(pago.getTipoPago())) {
            return "Tipo de pago inválido.";
        }

        // 7. VALIDAR MONTO
        if (pago.getMonto() == null || pago.getMonto().doubleValue() <= 0) {
            return "El monto debe ser mayor a 0.";
        }

        // ⭐ 8. ACTUALIZAR FECHA AUTOMÁTICAMENTE
        pago.setFecha(LocalDate.now());

        // ⭐ 9. GUARDAR
        return pagoGateway.guardarPago(pago);
    }



    public Object eliminarPago(Long id) {
        Optional<Pago> pago = pagoGateway.buscarPorId(id);
        if (pago.isEmpty()) {
            return "El pago con ID " + id + " no existe.";
        }
        pagoGateway.eliminarPago(id);
        return "Pago eliminado correctamente";
    }
}

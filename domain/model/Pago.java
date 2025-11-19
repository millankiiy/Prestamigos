package com.prestaamigos.pago.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Pago {

    private Long id;
    private Long prestamoId;
    private Long clienteId;

    private String metodo;      // efectivo, nequi, tarjeta, pse
    private String tipoPago;    // cuota, abono capital, pago total

    private Double monto;       // valor pagado
    private LocalDate fecha;    // fecha del pago
}


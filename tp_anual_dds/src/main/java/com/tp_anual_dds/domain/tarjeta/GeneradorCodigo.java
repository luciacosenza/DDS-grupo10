package com.tp_anual_dds.domain.tarjeta;

import java.util.concurrent.atomic.AtomicLong;

public class GeneradorCodigo {
    private static final AtomicLong contadorColaboradores = new AtomicLong(1);
    private static final AtomicLong contadorPersonas = new AtomicLong(1);

    public static String generarCodigo(boolean esColaborador) {
        String prefijo = esColaborador ? "C" : "P";
        long numero = esColaborador ? contadorColaboradores.getAndIncrement() : contadorPersonas.getAndIncrement();

        // Convertir el numero a un String de longitud fija con ceros a la izquierda
        String numeroStr = String.format("%010d", numero);

        return prefijo + numeroStr;
    }
}

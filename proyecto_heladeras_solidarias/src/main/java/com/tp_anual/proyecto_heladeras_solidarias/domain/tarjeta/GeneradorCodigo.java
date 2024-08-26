package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.util.concurrent.atomic.AtomicLong;

public class GeneradorCodigo {
    private static final AtomicLong contadorColaboradores = new AtomicLong(1);
    private static final AtomicLong contadorPersonas = new AtomicLong(1);

    public static String generarCodigo(boolean esColaborador) {
        // Asigno el prefijo del Código, según el tipo de titular
        String prefijo = esColaborador ? "C" : "P";
        
        // Obtengo el siguiente número (e itero) del auto-incremental, según el tipo de titular
        long numero = esColaborador ? contadorColaboradores.getAndIncrement() : contadorPersonas.getAndIncrement();

        // Convierto el número a un String de longitud fija con ceros a la izquierda
        String numeroStr = String.format("%010d", numero);

        return prefijo + numeroStr;
    }
}

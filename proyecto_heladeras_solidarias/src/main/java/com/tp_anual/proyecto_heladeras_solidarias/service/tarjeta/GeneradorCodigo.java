package com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@Log
public class GeneradorCodigo {
    private static final AtomicLong contadorColaboradores = new AtomicLong(1);
    private static final AtomicLong contadorPersonas = new AtomicLong(1);

    public static String generarCodigo(boolean esColaborador) {
        // Asigno el prefijo del Código, según el tipo de titular
        String prefijo = esColaborador ? "C" : "P";
        
        // Obtengo el siguiente número (e itero) del auto-incremental, según el tipo de titular
        Long numero = esColaborador ? contadorColaboradores.getAndIncrement() : contadorPersonas.getAndIncrement();

        // Convierto el número a un String de longitud fija con ceros a la izquierda
        String numeroStr = String.format("%010d", numero);

        return prefijo + numeroStr;
    }
}

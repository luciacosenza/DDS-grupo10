package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.contribuciones.DonacionDinero.FrecuenciaDePago;

public class DonacionDineroTest {
    private ColaboradorHumano colaborador;

    @BeforeEach
    void setUp() {
        colaborador = new ColaboradorHumano(null, new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", null, null); 
        //funciona igual para colaborador juridico    
    };

    @Test
    @DisplayName("Testeo el calculo de puntos con frecuencia UNICA_VEZ")
    void testCalcularPuntosConFrecuenciaUnicaVez() {
        Double monto = 100.0;
        FrecuenciaDePago frecuencia = FrecuenciaDePago.UNICA_VEZ;
        LocalDateTime fechaContribucion = LocalDateTime.now();

        DonacionDinero donacion = new DonacionDinero(colaborador, fechaContribucion, monto, frecuencia);
        donacion.calcularPuntos();

        assertEquals(monto * 0.5, colaborador.getPuntos());
    }

    @Test
    @DisplayName("Testeo el calculo de puntos con frecuencia MENSUAL")
    void testCalcularPuntosConFrecuenciaPeriodica() throws InterruptedException {
        Double monto = 200.0;
        FrecuenciaDePago frecuencia = FrecuenciaDePago.MENSUAL;
        LocalDateTime fechaContribucion = LocalDateTime.now();

        DonacionDinero donacion = new DonacionDinero(colaborador, fechaContribucion, monto, frecuencia);

        // Simulamos el paso del tiempo para verificar el cálculo periódico de puntos
        Thread.sleep(1000);

        donacion.calcularPuntos();

        assertEquals(monto * 0.5, colaborador.getPuntos());
    }

    @Test
    void testCalcularPuntosConFrecuenciaAnual() throws InterruptedException {
        Double monto = 500.0;
        FrecuenciaDePago frecuencia = FrecuenciaDePago.ANUAL;
        LocalDateTime fechaContribucion = LocalDateTime.now();

        DonacionDinero donacion = new DonacionDinero(colaborador, fechaContribucion, monto, frecuencia);

        // Espera suficiente para simular el paso de un año
        Thread.sleep(1000 * 60 * 60 * 24 * 365); // Aproximadamente 365 días

        donacion.calcularPuntos();

        assertEquals(monto * 0.5, colaborador.getPuntos());
    }
}



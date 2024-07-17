package com.tp_anual_dds.domain.contribuciones;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.heladera.Vianda;


public class DonacionViandaTest {
    private ColaboradorHumano colaborador;
    private Heladera heladera;
    private Vianda vianda1;
    private Vianda vianda2;


    @BeforeEach
    void setUp() {
        colaborador = new ColaboradorHumano(null, new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", null, null); 
        heladera = new Heladera("Heladera Test", null, new ArrayList<>(), 10, LocalDateTime.now(), 2.0f, 8.0f);
        
        LocalDateTime fechaCaducidad = LocalDateTime.now().plusMonths(1);
        LocalDateTime fechaDonacion = LocalDateTime.now();

        vianda1 = new Vianda("Fideos", heladera, colaborador, fechaCaducidad, fechaDonacion, 200, 200, true);
        vianda2 = new Vianda("Milanesa", heladera, colaborador, fechaCaducidad, fechaDonacion, 200, 200, true);

    }

    @Test
    @DisplayName("Testeo el calculo de puntos de la donacion de vianda")
    void testCalcularPuntos() {
        Double multiplicador = 1.5;
        LocalDateTime fechaContribucion = LocalDateTime.now();

        DonacionVianda donacion1 = new DonacionVianda(colaborador, fechaContribucion, vianda1, heladera);
        DonacionVianda donacion2 = new DonacionVianda(colaborador, fechaContribucion, vianda2, heladera);

        donacion1.calcularPuntos();
        donacion2.calcularPuntos();

        assertEquals(multiplicador*2, colaborador.getPuntos());
    }
}

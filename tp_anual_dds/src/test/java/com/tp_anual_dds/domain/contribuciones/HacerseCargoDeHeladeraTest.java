package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.persona.PersonaJuridica.TipoPersonaJuridica;

public class HacerseCargoDeHeladeraTest {

    private ColaboradorJuridico colaborador;
    private Heladera heladera;
    private HacerseCargoDeHeladera hacerseCargoDeHeladera;

    @BeforeEach
    void setUp() {
        colaborador = new ColaboradorJuridico(new ArrayList<>(), null, new ArrayList<>(), 0d, "Razon Social", "Rubro", TipoPersonaJuridica.EMPRESA);
        heladera = new Heladera("Heladera Test", null, new ArrayList<>(), 10, LocalDateTime.now(), 2.0f, 8.0f);
        hacerseCargoDeHeladera = new HacerseCargoDeHeladera(colaborador, LocalDateTime.now(), heladera);
    }

    @Test
    void testCalcularPuntos() throws InterruptedException {
        // Simulamos el tiempo para que pase al menos un mes
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));

        hacerseCargoDeHeladera.calcularPuntos();
        assertEquals(5d, colaborador.getPuntos()); 
    }

}

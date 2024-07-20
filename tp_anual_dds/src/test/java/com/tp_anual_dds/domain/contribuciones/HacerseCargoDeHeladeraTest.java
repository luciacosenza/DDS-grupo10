package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class HacerseCargoDeHeladeraTest {
    
    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una HacerseCargoDeHeladera")
    public void CargaHacerseCargoDeHeladeraTest() { 
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        colaboradorJuridico.darDeAlta();

        LocalDateTime fechaAperturaH1   = LocalDateTime.parse("2024-01-01T00:00:00");
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, fechaAperturaH1, -20f, 5f);

        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaAperturaH1, heladera);
        heladera.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera);

        Assertions.assertTrue(Sistema.getHeladeras().size() == 1 && colaboradorJuridico.getContribuciones().size() == 1 && colaboradorJuridico.getContribuciones().getFirst().getClass() == HacerseCargoDeHeladera.class);
    }
}

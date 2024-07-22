package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;
import com.tp_anual_dds.domain.oferta.Oferta;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class CargaOfertaTest {
    
    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una CargaOferta")
    public void CargaCargaOfertaTest() { 
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        colaboradorJuridico.darDeAlta();

        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");
        
        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();
        CargaOferta cargaOferta = (CargaOferta) colaboradorJuridico.colaborar(cargaOfertaCreator, LocalDateTime.now(), oferta);
        oferta.darDeAlta();
        colaboradorJuridico.confirmarContribucion(cargaOferta, LocalDateTime.now());

        Assertions.assertTrue(Sistema.getOfertas().size() == 1 && colaboradorJuridico.getContribuciones().size() == 1 && colaboradorJuridico.getContribuciones().getFirst().getClass() == CargaOferta.class);
    }
}
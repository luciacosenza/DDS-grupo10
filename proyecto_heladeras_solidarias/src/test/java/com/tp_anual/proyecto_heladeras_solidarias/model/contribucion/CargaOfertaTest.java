package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.CargaOfertaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.CargaOfertaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class CargaOfertaTest {

    @Autowired
    CargaOfertaService cargaOfertaService;

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    OfertaService ofertaService;

    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una CargaOferta")
    public void CargaCargaOfertaTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Long colaboradorJuridicoId = colaboradorService.guardarColaborador(colaboradorJuridico).getId();

        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");

        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();

        Long cargaOfertaId = colaboradorService.colaborar(colaboradorJuridico.getId(), cargaOfertaCreator, LocalDateTime.now(), oferta).getId();
        colaboradorService.confirmarContribucion(colaboradorJuridicoId, cargaOfertaId, LocalDateTime.now());

        Assertions.assertTrue(ofertaService.obtenerOfertas().size() == 1 && colaboradorJuridico.getContribuciones().size() == 1 && colaboradorJuridico.getContribuciones().getFirst().getClass() == CargaOferta.class);
    }
}
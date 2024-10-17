package com.tp_anual.proyecto_heladeras_solidarias.model.oferta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.CargaOferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorPuntosService;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionDineroService;
import com.tp_anual.proyecto_heladeras_solidarias.service.oferta.OfertaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.CargaOfertaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OfertaTest {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    OfertaService ofertaService;

    @Autowired
    DonacionDineroService donacionDineroService;

    ColaboradorHumano colaboradorHumano;
    ColaboradorJuridico colaboradorJuridico;
    Oferta oferta;
    Long colaboradorHumanoId;
    Long colaboradorJuridicoId;

    @Autowired
    private ColaboradorPuntosService colaboradorPuntosService;

    @BeforeEach
    void setup() {
        colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorJuridico = new ColaboradorJuridico(null, new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");

        LocalDateTime fechaCarga = LocalDateTime.parse("2024-07-15T00:00:00");

        colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();
        colaboradorJuridicoId = colaboradorService.guardarColaborador(colaboradorJuridico).getId();

        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();
        CargaOferta cargaOferta = (CargaOferta) colaboradorService.colaborar(colaboradorJuridicoId, cargaOfertaCreator, fechaCarga, oferta);

        ofertaService.guardarOferta(oferta);

        colaboradorService.confirmarContribucion(colaboradorJuridicoId, cargaOferta, fechaCarga);
    }
    @Test
    @DisplayName("Testeo el correcto funcionamiento de carga de Oferta y adquisicion de la misma")
    public void IntentarAdquirirBeneficioTest() {
        colaboradorPuntosService.sumarPuntos(colaboradorHumanoId, 60d);

        colaboradorService.intentarAdquirirBeneficio(colaboradorHumanoId, oferta);

        Assertions.assertEquals(1, colaboradorHumano.getBeneficiosAdquiridos().size());
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer adquirir un beneficio por parte de un Colaborador que no tiene los puntos suficientes")
    public void UnsupportedOperationIntentarAdquirirBeneficioTest() {
        colaboradorPuntosService.sumarPuntos(colaboradorHumanoId, 15d);

        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> colaboradorService.intentarAdquirirBeneficio(colaboradorHumanoId, oferta));

        Assertions.assertEquals(I18n.getMessage("oferta.Oferta.validarPuntos_exception"), exception.getMessage());
    }
}

package com.tp_anual.proyecto_heladeras_solidarias.model.oferta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.CargaOferta;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.CargaOfertaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionDineroCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class OfertaTest {
    
    @Test
    @DisplayName("Testeo el correcto funcionamiento de carga de Oferta e adquisicion de la misma")
    public void IntentarAdquirirBeneficioTest() throws InterruptedException {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorJuridico.darDeAlta();

        LocalDateTime fechaDonacion = LocalDateTime.parse("2024-07-10T00:00:00");

        DonacionDineroCreator donacionDineroCreator = new DonacionDineroCreator();
        DonacionDinero donacionDinero = (DonacionDinero) colaboradorHumano.colaborar(donacionDineroCreator, fechaDonacion, 60d, DonacionDinero.FrecuenciaDePago.UNICA_VEZ);
        colaboradorHumano.confirmarContribucion(donacionDinero, fechaDonacion);

        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");
        LocalDateTime fechaCarga = LocalDateTime.parse("2024-07-15T00:00:00");

        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();
        CargaOferta cargaOferta = (CargaOferta) colaboradorJuridico.colaborar(cargaOfertaCreator, fechaCarga, oferta);
        oferta.darDeAlta();
        colaboradorJuridico.confirmarContribucion(cargaOferta, fechaCarga);
        
        Thread.sleep(5000);

        colaboradorHumano.intentarAdquirirBeneficio(oferta);

        Assertions.assertTrue(colaboradorHumano.getBeneficiosAdquiridos().size() == 1);
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer adquirir un beneficio por parte de un Colaborador que no tiene los puntos suficientes")
    public void UnsupportedOperationIntentarAdquirirBeneficioTest() throws InterruptedException {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorJuridico.darDeAlta();
        
        LocalDateTime fechaDonacion = LocalDateTime.parse("2024-07-10T00:00:00");

        DonacionDineroCreator donacionDineroCreator = new DonacionDineroCreator();
        DonacionDinero donacionDinero = (DonacionDinero) colaboradorHumano.colaborar(donacionDineroCreator, fechaDonacion, 20d, DonacionDinero.FrecuenciaDePago.UNICA_VEZ);
        colaboradorHumano.confirmarContribucion(donacionDinero, fechaDonacion);

        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");
        LocalDateTime fechaCarga = LocalDateTime.parse("2024-07-15T00:00:00");

        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();
        CargaOferta cargaOferta = (CargaOferta) colaboradorJuridico.colaborar(cargaOfertaCreator, fechaCarga, oferta);
        oferta.darDeAlta();
        colaboradorJuridico.confirmarContribucion(cargaOferta, fechaCarga);
        
        Thread.sleep(5000);

        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            colaboradorHumano.intentarAdquirirBeneficio(oferta);
        });

        Assertions.assertEquals(I18n.getMessage("oferta.Oferta.validarPuntos_exception"), exception.getMessage());
    }
}

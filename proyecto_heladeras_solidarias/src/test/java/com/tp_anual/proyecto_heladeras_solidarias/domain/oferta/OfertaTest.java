package com.tp_anual.proyecto_heladeras_solidarias.domain.oferta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.CargaOferta;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.CargaOfertaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionDinero;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionDineroCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class OfertaTest {
    
    @Test
    @DisplayName("Testeo el correcto funcionamiento de carga de Oferta e adquisicion de la misma")
    public void IntentarAdquirirBeneficioTest() throws InterruptedException {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano.darDeAlta();
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
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

        Assertions.assertTrue(colaboradorHumano.getBeneficiosAdquiridos().size() == 1 && Sistema.getOfertas().size() == 0); // Debería haber 0 Ofertas pero, como Sistema es static, se carga la Oferta del otro test si los corremos juntos
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer adquirir un beneficio por parte de un Colaborador que no tiene los puntos suficientes")
    public void UnsupportedOperationIntentarAdquirirBeneficioTest() throws InterruptedException {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano.darDeAlta();
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
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
        Assertions.assertEquals(1, Sistema.getOfertas().size());
    }
}

package com.tp_anual_dds.domain.oferta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;
import com.tp_anual_dds.domain.contribucion.CargaOferta;
import com.tp_anual_dds.domain.contribucion.CargaOfertaCreator;
import com.tp_anual_dds.domain.contribucion.DonacionDinero;
import com.tp_anual_dds.domain.contribucion.DonacionDineroCreator;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

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
        colaboradorHumano.confirmarContribucion(donacionDinero);

        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");
        LocalDateTime fechaCarga = LocalDateTime.parse("2024-07-15T00:00:00");

        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();
        CargaOferta cargaOferta = (CargaOferta) colaboradorJuridico.colaborar(cargaOfertaCreator, fechaCarga, oferta);
        oferta.darDeAlta();
        colaboradorJuridico.confirmarContribucion(cargaOferta);
        
        Thread.sleep(5000);

        colaboradorHumano.intentarAdquirirBeneficio(oferta);

        Assertions.assertTrue(colaboradorHumano.getBeneficiosAdquiridos().size() == 1 && Sistema.getOfertas().size() == 1); // En realidad deberia haber 0 Ofertas pero, como Sistema es static, se carga la Oferta del otro test
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer adquirir un beneficio por parte de un Colaborador que no tiene los puntos suficientes")
    public void IllegalArgumentIntentarAdquirirBeneficioTest() throws InterruptedException {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano.darDeAlta();
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        colaboradorJuridico.darDeAlta();
        
        LocalDateTime fechaDonacion = LocalDateTime.parse("2024-07-10T00:00:00");

        DonacionDineroCreator donacionDineroCreator = new DonacionDineroCreator();
        DonacionDinero donacionDinero = (DonacionDinero) colaboradorHumano.colaborar(donacionDineroCreator, fechaDonacion, 20d, DonacionDinero.FrecuenciaDePago.UNICA_VEZ);
        colaboradorHumano.confirmarContribucion(donacionDinero);

        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");
        LocalDateTime fechaCarga = LocalDateTime.parse("2024-07-15T00:00:00");

        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();
        CargaOferta cargaOferta = (CargaOferta) colaboradorJuridico.colaborar(cargaOfertaCreator, fechaCarga, oferta);
        oferta.darDeAlta();
        colaboradorJuridico.confirmarContribucion(cargaOferta);
        
        Thread.sleep(5000);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorHumano.intentarAdquirirBeneficio(oferta);
        });

        Assertions.assertTrue(exception.getMessage() == "No se cuenta con los puntos necesarios para adquirir este beneficio" && Sistema.getOfertas().size() == 1);
    }
}

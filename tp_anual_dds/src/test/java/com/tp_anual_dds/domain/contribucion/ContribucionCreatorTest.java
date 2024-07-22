package com.tp_anual_dds.domain.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.documento.Documento.Sexo;
import com.tp_anual_dds.domain.documento.Documento.TipoDocumento;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.oferta.Oferta;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class ContribucionCreatorTest {
    
    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una CargaOferta con los argumentos inadecuados")
    public void IllegalArgumentCrearCargaOfertaTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        colaboradorJuridico.darDeAlta();
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        
        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorJuridico.colaborar(cargaOfertaCreator, LocalDateTime.now(), heladera);
        });

        Assertions.assertEquals("Argumentos inválidos para realizar una Carga de Oferta", exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DistribucionViandas con los argumentos inadecuados")
    public void IllegalArgumentCrearDistribucionViandasTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano.darDeAlta();
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, LocalDateTime.parse("2024-07-15T00:00:00"), LocalDateTime.now(), 0, 0, false);
        
        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorHumano.colaborar(distribucionViandasCreator, LocalDateTime.now(), vianda, heladera);
        });

        Assertions.assertEquals("Argumentos inválidos para realizar una Distribución de Viandas", exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DonacionDinero con los argumentos inadecuados")
    public void IllegalArgumentCrearDonacionDineroTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano.darDeAlta();
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, LocalDateTime.parse("2024-07-15T00:00:00"), LocalDateTime.now(), 0, 0, false);
        
        DonacionDineroCreator donacionDineroCreator = new DonacionDineroCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorHumano.colaborar(donacionDineroCreator, LocalDateTime.now(), vianda, heladera);
        });

        Assertions.assertEquals("Argumentos inválidos para realizar una Donación de Dinero", exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DonacionVianda con los argumentos inadecuados")
    public void IllegalArgumentCrearDonacionViandaTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano.darDeAlta();
        HeladeraActiva heladera1 = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        HeladeraActiva heladera2 = new HeladeraActiva("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-02-01T00:00:00"), -20f, 5f);
        
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), heladera1, heladera2, 5, DistribucionViandas.MotivoDistribucion.DESPERFECTO_EN_LA_HELADERA);
        });

        Assertions.assertEquals("Argumentos inválidos para realizar una Donación de Vianda", exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una HacerseCargoDeHeladera con los argumentos inadecuados")
    public void IllegalArgumentCrearHacerseCargoDeHeladeraTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        colaboradorJuridico.darDeAlta();
        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");

        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, LocalDateTime.now(), oferta);
        });

        Assertions.assertEquals("Argumentos inválidos para Hacerse Cargo de una Heladera", exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una RegistroDePersonaEnSituacionVulnerable con los argumentos inadecuados")
    public void IllegalArgumentCrearRegistroDePersonaEnSituacionVulnerableTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano.darDeAlta();
        Double monto = 1000000d;
        DonacionDinero.FrecuenciaDePago frecuencia = DonacionDinero.FrecuenciaDePago.UNICA_VEZ;
        
        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorHumano.colaborar(registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), monto, frecuencia);
        });

        Assertions.assertEquals("Argumentos inválidos para Registrar una Persona En Situacion Vulnerable", exception.getMessage());
    }
}

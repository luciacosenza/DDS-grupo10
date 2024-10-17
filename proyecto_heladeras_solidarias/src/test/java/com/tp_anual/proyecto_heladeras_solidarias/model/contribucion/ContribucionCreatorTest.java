package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaPersonaEnSituacionVulnerableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContribucionCreatorTest {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    private TarjetaPersonaEnSituacionVulnerableService tarjetaPersonaEnSituacionVulnerableService;

    ColaboradorHumano colaboradorHumano;
    ColaboradorJuridico colaboradorJuridico;
    Long colaboradorHumanoId;
    Long colaboradorJuridicoId;

    @BeforeEach
    void setUp() {
        colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorJuridico = new ColaboradorJuridico(null, new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);

        colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();
        colaboradorJuridicoId = colaboradorService.guardarColaborador(colaboradorJuridico).getId();
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una CargaOferta con los argumentos inadecuados")
    public void IllegalArgumentCrearCargaOfertaTest() {
        Heladera heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.parse("2024-01-01T00:00:00"), true);

        CargaOfertaCreator cargaOfertaCreator = new CargaOfertaCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorJuridicoId, cargaOfertaCreator, LocalDateTime.now(), heladera));

        Assertions.assertEquals(I18n.getMessage("contribucion.CargaOfertaCreator.crearContribucion_exception"), exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DistribucionViandas con los argumentos inadecuados")
    public void IllegalArgumentCrearDistribucionViandasTest() {
        Heladera heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.parse("2024-01-01T00:00:00"), true);
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, LocalDateTime.parse("2024-07-15T00:00:00"), LocalDateTime.now(), 0, 0, false);

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, distribucionViandasCreator, LocalDateTime.now(), vianda, heladera));

        Assertions.assertEquals(I18n.getMessage("contribucion.DistribucionViandasCreator.crearContribucion_exception"), exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DonacionDinero con los argumentos inadecuados")
    public void IllegalArgumentCrearDonacionDineroTest() {
        TarjetaPersonaEnSituacionVulnerable tarjeta = tarjetaPersonaEnSituacionVulnerableService.crearTarjeta(colaboradorHumanoId);

        DonacionDineroCreator donacionDineroCreator = new DonacionDineroCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, donacionDineroCreator, LocalDateTime.now(), tarjeta));

        Assertions.assertEquals(I18n.getMessage("contribucion.DonacionDineroCreator.crearContribucion_exception"), exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DonacionVianda con los argumentos inadecuados")
    public void IllegalArgumentCrearDonacionViandaTest() {
        Heladera heladera1 = new Heladera("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.parse("2024-01-01T00:00:00"), true);
        Heladera heladera2 = new Heladera("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "1086", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.parse("2024-02-01T00:00:00"), true);

        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), heladera1, heladera2, 5, DistribucionViandas.MotivoDistribucion.DESPERFECTO_EN_LA_HELADERA));

        Assertions.assertEquals(I18n.getMessage("contribucion.DonacionViandaCreator.crearContribucion_exception"), exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una HacerseCargoDeHeladera con los argumentos inadecuados")
    public void IllegalArgumentCrearHacerseCargoDeHeladeraTest() {
        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");

        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorJuridicoId, hacerseCargoDeHeladeraCreator, LocalDateTime.now(), oferta));

        Assertions.assertEquals(I18n.getMessage("contribucion.HacerseCargoDeHeladeraCreator.crearContribucion_exception"), exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una RegistroDePersonaEnSituacionVulnerable con los argumentos inadecuados")
    public void IllegalArgumentCrearRegistroDePersonaEnSituacionVulnerableTest() {
        Double monto = 1000000d;
        DonacionDinero.FrecuenciaDePago frecuencia = DonacionDinero.FrecuenciaDePago.UNICA_VEZ;
        
        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), monto, frecuencia));

        Assertions.assertEquals(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_exception"), exception.getMessage());
    }
}

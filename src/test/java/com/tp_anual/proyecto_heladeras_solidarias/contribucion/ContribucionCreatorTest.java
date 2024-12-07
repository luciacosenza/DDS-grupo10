package com.tp_anual.proyecto_heladeras_solidarias.contribucion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

import com.tp_anual.proyecto_heladeras_solidarias.exception.tarjeta.DatosInvalidosCrearTarjetaPESVException;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionDinero;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaPersonaEnSituacionVulnerableService;
import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
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
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

@SpringBootTest
public class ContribucionCreatorTest {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    TarjetaPersonaEnSituacionVulnerableService tarjetaPersonaEnSituacionVulnerableService;

    @Autowired
    CargaOfertaCreator cargaOfertaCreator;

    @Autowired
    DistribucionViandasCreator distribucionViandasCreator;

    @Autowired
    DonacionDineroCreator donacionDineroCreator;

    @Autowired
    DonacionViandaCreator donacionViandaCreator;

    @Autowired
    HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator;

    @Autowired
    RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator;

    ColaboradorHumano colaboradorHumano;
    ColaboradorJuridico colaboradorJuridico;
    Long colaboradorHumanoId;
    Long colaboradorJuridicoId;

    @BeforeEach
    void setUp() {
        colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDate.parse("2003-01-01")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorJuridico = new ColaboradorJuridico(null, new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);

        colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();
        colaboradorJuridicoId = colaboradorService.guardarColaborador(colaboradorJuridico).getId();
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una CargaOferta con los argumentos inadecuados")
    public void IllegalArgumentCrearCargaOfertaTest() {
        Heladera heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.parse("2024-01-01T00:00:00"), true);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorJuridicoId, cargaOfertaCreator, LocalDateTime.now(), heladera));

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String exceptionMessage = messageSource.getMessage("contribucion.CargaOfertaCreator.crearContribucion_exception", null, Locale.getDefault());

        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DistribucionViandas con los argumentos inadecuados")
    public void IllegalArgumentCrearDistribucionViandasTest() {
        Heladera heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.parse("2024-01-01T00:00:00"), true);
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, LocalDate.parse("2024-07-15"), LocalDateTime.now(), 0, 0, false);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, distribucionViandasCreator, LocalDateTime.now(), vianda, heladera));

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String exceptionMessage = messageSource.getMessage("contribucion.DistribucionViandasCreator.crearContribucion_exception", null, Locale.getDefault());

        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DonacionDinero con los argumentos inadecuados")
    public void IllegalArgumentCrearDonacionDineroTest() throws DatosInvalidosCrearTarjetaPESVException {
        TarjetaPersonaEnSituacionVulnerable tarjeta = tarjetaPersonaEnSituacionVulnerableService.crearTarjeta(colaboradorHumanoId);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, donacionDineroCreator, LocalDateTime.now(), tarjeta));

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String exceptionMessage = messageSource.getMessage("contribucion.DonacionDineroCreator.crearContribucion_exception", null, Locale.getDefault());

        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una DonacionVianda con los argumentos inadecuados")
    public void IllegalArgumentCrearDonacionViandaTest() {
        Heladera heladera1 = new Heladera("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.parse("2024-01-01T00:00:00"), true);
        Heladera heladera2 = new Heladera("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "1086", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.parse("2024-02-01T00:00:00"), true);

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), heladera1, heladera2, 5, DistribucionViandas.MotivoDistribucion.DESPERFECTO_EN_LA_HELADERA));

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String exceptionMessage = messageSource.getMessage("contribucion.DonacionViandaCreator.crearContribucion_exception", null, Locale.getDefault());

        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una HacerseCargoDeHeladera con los argumentos inadecuados")
    public void IllegalArgumentCrearHacerseCargoDeHeladeraTest() {
        Oferta oferta = new Oferta("PlayStation 5", 20d, Oferta.Categoria.ELECTRONICA, "ImagenPrueba");

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorJuridicoId, hacerseCargoDeHeladeraCreator, LocalDateTime.now(), oferta));

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String exceptionMessage = messageSource.getMessage("contribucion.HacerseCargoDeHeladeraCreator.crearContribucion_exception", null, Locale.getDefault());

        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer crear una RegistroDePersonaEnSituacionVulnerable con los argumentos inadecuados")
    public void IllegalArgumentCrearRegistroDePersonaEnSituacionVulnerableTest() {
        Double monto = 1000000d;
        DonacionDinero.FrecuenciaDePago frecuencia = DonacionDinero.FrecuenciaDePago.UNICA_VEZ;

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), monto, frecuencia));

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String exceptionMessage = messageSource.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerableCreator.crearContribucion_exception", null, Locale.getDefault());

        Assertions.assertEquals(exceptionMessage, exception.getMessage());
    }
}

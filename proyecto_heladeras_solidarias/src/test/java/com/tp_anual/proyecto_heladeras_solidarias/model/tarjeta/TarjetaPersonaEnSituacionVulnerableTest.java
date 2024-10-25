package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.ViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaPersonaEnSituacionVulnerableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.RegistroDePersonaEnSituacionVulnerableCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TarjetaPersonaEnSituacionVulnerableTest {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    HeladeraService heladeraService;

    @Autowired
    ViandaService viandaService;

    @Autowired
    PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;

    @Autowired
    TarjetaPersonaEnSituacionVulnerableService tarjetaPersonaEnSituacionVulnerableService;

    ColaboradorHumano colaboradorHumano;
    TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable;
    Long colaboradorHumanoId;
    Heladera heladera;
    Vianda vianda1;
    Vianda vianda2;
    Vianda vianda3;
    Vianda vianda4;
    Vianda vianda5;
    Vianda vianda6;
    Vianda vianda7;
    Vianda vianda8;
    Vianda vianda9;
    Vianda vianda10;
    Long heladeraId;
    String codigoTarjeta;

    @BeforeEach
    void setup() {
        colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDate.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();

        heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.now() , true);
        heladeraId = heladeraService.guardarHeladera(heladera).getId();

        LocalDate fechaCaducidadV = LocalDate.parse("2025-01-01");

        vianda1 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda1);
        vianda2 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda2);
        vianda3 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda3);
        vianda4 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda4);
        vianda5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda5);
        vianda6 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda6);
        vianda7 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda7);
        vianda8 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda8);
        vianda9 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda9);
        vianda10 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda10);

        List<Vianda> viandasAAgregar = new ArrayList<>();
        viandasAAgregar.add(vianda1);
        viandasAAgregar.add(vianda2);
        viandasAAgregar.add(vianda3);
        viandasAAgregar.add(vianda4);
        viandasAAgregar.add(vianda5);
        viandasAAgregar.add(vianda6);
        viandasAAgregar.add(vianda7);
        viandasAAgregar.add(vianda8);
        viandasAAgregar.add(vianda9);
        viandasAAgregar.add(vianda10);

        Integer cantidadAAgregar = 10;

        for (Integer i = 1; i <= cantidadAAgregar; i++) {
            Vianda viandaAux = viandasAAgregar.removeFirst();
            heladeraService.agregarVianda(heladeraId, viandaAux);
            viandaService.agregarAHeladera(viandaAux.getId(), heladera);
        }

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable("NombrePruebaPESV", "ApellidoPruebaPESV", new Documento(TipoDocumento.DNI, "40123450", Sexo.MASCULINO), LocalDate.parse("2003-01-01"), new Ubicacion(-34.63927052902741, -58.50938609197106, "Avenida Rivadavia 10357", "1048", "Ciudad Autónoma de Buenos Aires", "Argentina"), LocalDateTime.now(), 2, true);
        Long personaEnSituacionVulnerableId = personaEnSituacionVulnerableService.guardarPersonaEnSituacionVulnerable(personaEnSituacionVulnerable).getId();

        tarjetaPersonaEnSituacionVulnerable = tarjetaPersonaEnSituacionVulnerableService.crearTarjeta(personaEnSituacionVulnerableId);

        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();
        RegistroDePersonaEnSituacionVulnerable registroPersonaEnSituacionVulnerable = (RegistroDePersonaEnSituacionVulnerable) colaboradorService.colaborar(colaboradorHumanoId, registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), tarjetaPersonaEnSituacionVulnerable);

        codigoTarjeta = personaEnSituacionVulnerableService.agregarTarjeta(personaEnSituacionVulnerableId, tarjetaPersonaEnSituacionVulnerable).getCodigo();

        colaboradorService.confirmarContribucion(colaboradorHumanoId, registroPersonaEnSituacionVulnerable, LocalDateTime.now());
    }

    @Test
    @DisplayName("Testeo el correcto funcionamiento de la Tarjeta y sus Usos")
    public void UsosTarjetaTest() {
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);

        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);

        Assertions.assertTrue(heladera.viandasActuales() == 4 && tarjetaPersonaEnSituacionVulnerable.cantidadUsos() == 6);
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer usar la Tarjeta sin tener más Usos disponibles")
    public void UnsupportedOperationUsosTarjetaTest() {

        heladeraService.retirarVianda(heladera.getId());
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.retirarVianda(heladeraId);

        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladera);
            heladeraService.retirarVianda(heladeraId);
        });

        Assertions.assertEquals(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_usos_agotados"), exception.getMessage());
        Assertions.assertEquals(2, heladera.viandasActuales());
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer retirar una Vianda de una Heladera vacía")
    public void UnsupportedOperationEstaVaciaTarjetaPESVTest() {
        Heladera heladeraC5 = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 5, -20f, 5f, new ArrayList<>(), 3f, LocalDateTime.now() , true);
        Long heladeraC5Id = heladeraService.guardarHeladera(heladeraC5).getId();

        LocalDate fechaCaducidadV = LocalDate.parse("2025-01-01");
        Vianda vianda1C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda1C5);
        Vianda vianda2C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda2C5);
        Vianda vianda3C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda3C5);
        Vianda vianda4C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda4C5);
        Vianda vianda5C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda5C5);
        Vianda vianda6C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda6C5);
        Vianda vianda7C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda7C5);
        Vianda vianda8C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda8C5);
        Vianda vianda9C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda9C5);
        Vianda vianda10C5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda10C5);

        List<Vianda> viandasAAgregar = new ArrayList<>();
        viandasAAgregar.add(vianda1C5);
        viandasAAgregar.add(vianda2C5);
        viandasAAgregar.add(vianda3C5);
        viandasAAgregar.add(vianda4C5);
        viandasAAgregar.add(vianda5C5);
        viandasAAgregar.add(vianda6C5);
        viandasAAgregar.add(vianda7C5);
        viandasAAgregar.add(vianda8C5);
        viandasAAgregar.add(vianda9C5);
        viandasAAgregar.add(vianda10C5);

        Integer cantidadAAgregar = 10;

        for (Integer i = 1; i <= cantidadAAgregar; i++) {
            Vianda viandaAux = viandasAAgregar.removeFirst();
            heladeraService.agregarVianda(heladeraC5Id, viandaAux);
            viandaService.agregarAHeladera(viandaAux.getId(), heladeraC5);
        }

        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladeraC5);
        heladeraService.retirarVianda(heladeraC5Id);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladeraC5);
        heladeraService.retirarVianda(heladeraC5Id);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladeraC5);
        heladeraService.retirarVianda(heladeraC5Id);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladeraC5);
        heladeraService.retirarVianda(heladeraC5Id);
        tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladeraC5);
        heladeraService.retirarVianda(heladeraC5Id);

        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            tarjetaPersonaEnSituacionVulnerableService.intentarApertura(codigoTarjeta, heladeraC5);
            heladeraService.retirarVianda(heladeraC5Id);
        });

        Assertions.assertEquals(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_heladera_vacia"), exception.getMessage());
    }
}

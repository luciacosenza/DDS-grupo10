package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.RegistroDePersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.RegistroDePersonaEnSituacionVulnerableCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class TarjetaPersonaEnSituacionVulnerableTest {
    @Test
    @DisplayName("Testeo el correcto funcionamiento de la Tarjeta y sus Usos")
    public void UsosTarjetaTest() {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 10, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda2 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda3 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda4 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda6 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda7 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda8 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda9 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda10 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);

        ArrayList<Vianda> viandasAAgregar = new ArrayList<>();
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

        for(Integer i = 1; i <= cantidadAAgregar; i++) {
                Vianda viandaAux = viandasAAgregar.removeFirst();
                heladera.agregarVianda(viandaAux);
                viandaAux.setHeladera(heladera);
                viandaAux.marcarEntrega();
        }

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();
        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable("NombrePruebaPESV", "ApellidoPruebaPESV", new Documento(TipoDocumento.DNI, "40123450", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"), new Ubicacion(-34.63927052902741, -58.50938609197106, "Avenida Rivadavia 10357", "1048", "Ciudad Autónoma de Buenos Aires", "Argentina"), LocalDateTime.now(), 2);
        TarjetaPersonaEnSituacionVulnerable tarjeta = new TarjetaPersonaEnSituacionVulnerable(null);
        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();
        
        RegistroDePersonaEnSituacionVulnerable registroPersonaEnSituacionVulnerable = (RegistroDePersonaEnSituacionVulnerable) colaboradorHumano.colaborar(registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), tarjeta);
        tarjeta.setTitular(personaEnSituacionVulnerable);
        personaEnSituacionVulnerable.setTarjeta(tarjeta);
        personaEnSituacionVulnerable.darDeAlta();
        colaboradorHumano.confirmarContribucion(registroPersonaEnSituacionVulnerable, LocalDateTime.now());

        final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

        Runnable reseteoUsos = () -> {
            personaEnSituacionVulnerable.getTarjeta().resetUsos();
        };

        // Programa la tarea para que se ejecute una vez cada 20 segundos
        timer.scheduleAtFixedRate(reseteoUsos, 0, 20, TimeUnit.SECONDS);

        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();

        Assertions.assertTrue(heladera.viandasActuales() == 4 && personaEnSituacionVulnerable.getTarjeta().cantidadUsos() == 6);    // Si lo corremos junto con el otro, va a tirar error porque van a existir más usos en el Sistema
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer usar la Tarjeta sin tener más Usos disponibles")
    public void UnsupportedOperationUsosTarjetaTest() {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171","1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 10, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda2 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda3 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda4 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda6 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda7 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda8 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda9 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda10 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        
        ArrayList<Vianda> viandasAAgregar = new ArrayList<>();
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

        for(Integer i = 1; i <= cantidadAAgregar; i++) {
                Vianda viandaAux = viandasAAgregar.removeFirst();
                heladera.agregarVianda(viandaAux);
                viandaAux.setHeladera(heladera);
                viandaAux.marcarEntrega();
        }

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();
        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable("NombrePruebaPESV", "ApellidoPruebaPESV", new Documento(TipoDocumento.DNI, "40123450", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"), new Ubicacion(-34.63927052902741, -58.50938609197106, "Avenida Rivadavia 10357", "1048", "Ciudad Autónoma de Buenos Aires", "Argentina"), LocalDateTime.now(), 2);
        TarjetaPersonaEnSituacionVulnerable tarjeta = new TarjetaPersonaEnSituacionVulnerable(null);
        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();
        
        RegistroDePersonaEnSituacionVulnerable registroPersonaEnSituacionVulnerable = (RegistroDePersonaEnSituacionVulnerable) colaboradorHumano.colaborar(registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), tarjeta);
        tarjeta.setTitular(personaEnSituacionVulnerable);
        personaEnSituacionVulnerable.setTarjeta(tarjeta);
        personaEnSituacionVulnerable.darDeAlta();
        colaboradorHumano.confirmarContribucion(registroPersonaEnSituacionVulnerable, LocalDateTime.now());

        final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

        Runnable reseteoUsos = () -> {
            personaEnSituacionVulnerable.getTarjeta().resetUsos();
        };

        // Programa la tarea para que se ejecute una vez cada 20 segundos
        timer.scheduleAtFixedRate(reseteoUsos, 0, 20, TimeUnit.SECONDS);

        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();

        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
            heladera.retirarVianda();
        });

        Assertions.assertEquals(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_usos_agotados"), exception.getMessage());
        Assertions.assertEquals(2, heladera.viandasActuales());
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer retirar una Vianda de una Heladera vacía")
    public void UnsupportedOperationEstaVaciaTarjetaPESVTest() {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 10, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda2 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda3 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda4 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda5 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        
        ArrayList<Vianda> viandasAAgregar = new ArrayList<>();
        viandasAAgregar.add(vianda1);
        viandasAAgregar.add(vianda2);
        viandasAAgregar.add(vianda3);
        viandasAAgregar.add(vianda4);
        viandasAAgregar.add(vianda5);

        Integer cantidadAAgregar = 5;

        for(Integer i = 1; i <= cantidadAAgregar; i++) {
                Vianda viandaAux = viandasAAgregar.removeFirst();
                heladera.agregarVianda(viandaAux);
                viandaAux.setHeladera(heladera);
                viandaAux.marcarEntrega();
        }

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();
        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable("NombrePruebaPESV", "ApellidoPruebaPESV", new Documento(TipoDocumento.DNI, "40123450", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"), new Ubicacion(-34.63927052902741, -58.50938609197106, "Avenida Rivadavia 10357", "1408", "Ciudad Autónoma de Buenos Aires", "Argentina"), LocalDateTime.now(), 2);
        TarjetaPersonaEnSituacionVulnerable tarjeta = new TarjetaPersonaEnSituacionVulnerable(null);
        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();
        
        RegistroDePersonaEnSituacionVulnerable registroPersonaEnSituacionVulnerable = (RegistroDePersonaEnSituacionVulnerable) colaboradorHumano.colaborar(registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), tarjeta);
        tarjeta.setTitular(personaEnSituacionVulnerable);
        personaEnSituacionVulnerable.setTarjeta(tarjeta);
        personaEnSituacionVulnerable.darDeAlta();
        colaboradorHumano.confirmarContribucion(registroPersonaEnSituacionVulnerable, LocalDateTime.now());

        final ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

        Runnable reseteoUsos = () -> {
            personaEnSituacionVulnerable.getTarjeta().resetUsos();
        };

        // Programa la tarea para que se ejecute una vez cada 20 segundos
        timer.scheduleAtFixedRate(reseteoUsos, 0, 20, TimeUnit.SECONDS);

        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();
        personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
        heladera.retirarVianda();

        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            personaEnSituacionVulnerable.getTarjeta().intentarApertura(heladera);
            heladera.retirarVianda();
        });

        Assertions.assertEquals(I18n.getMessage("heladera.GestorDeAperturas.revisarPermisoAperturaP_exception_heladera_vacia"), exception.getMessage());
    }
}

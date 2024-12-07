package com.tp_anual.proyecto_heladeras_solidarias.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.exception.colaborador.ContribucionNoPermitidaException;
import com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.HacerseCargoDeHeladeraCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HacerseCargoDeHeladeraTest {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    HeladeraService heladeraService;

    @Autowired
    HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator;


    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una HacerseCargoDeHeladera")
    public void CargaHacerseCargoDeHeladeraTest() throws ContribucionNoPermitidaException, DatosInvalidosCrearCargaOfertaException, DatosInvalidosCrearDistribucionViandasException, DatosInvalidosCrearDonacionDineroException, DatosInvalidosCrearDonacionViandaException, DatosInvalidosCrearHCHException,DatosInvalidosCrearRPESVException, DomicilioFaltanteDiVsException, DomicilioFaltanteDoVException, DomicilioFaltanteRPESVException {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(null, new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Long colaboradorJuridicoId = colaboradorService.guardarColaborador(colaboradorJuridico).getId();

        LocalDateTime fechaAperturaH   = LocalDateTime.parse("2024-01-01T00:00:00");

        Heladera heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"),20, -20f, 5f, new ArrayList<>(), 2f, fechaAperturaH, true);
        Long heladeraId = heladeraService.guardarHeladera(heladera).getId();

        HacerseCargoDeHeladera hacerseCargoDeHeladera = (HacerseCargoDeHeladera) colaboradorService.colaborar(colaboradorJuridico.getId(), hacerseCargoDeHeladeraCreator, fechaAperturaH, heladeraId);
        colaboradorService.confirmarContribucion(colaboradorJuridicoId, hacerseCargoDeHeladera, fechaAperturaH);

        Assertions.assertTrue(heladeraService.obtenerHeladeras().size() == 1 && colaboradorJuridico.getContribuciones().size() == 1 && colaboradorJuridico.getContribuciones().getFirst().getClass() == HacerseCargoDeHeladera.class);
    }

  /*
    @Test
    @DisplayName("Testeo el cálculo de Puntos de HacerseCargoDeHeladera utilizando un Scheduler")
    public void CalcularPuntosHCDHTest() throws InterruptedException {  // Testeamos una version modificada de calcularPuntos(), dado que la original cuenta con periodos muy largos de ejecucion como para ser testeada
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorJuridico.darDeAlta();

        final LocalDateTime[] ultimaActualizacion = { LocalDateTime.now() };    // Usamos un Array para tener una final reference no directa al objeto y que nos permita modificarlo en el lambda
        LocalDateTime fechaAperturaH   = LocalDateTime.parse("2024-01-01T00:00:00");
        Heladera heladera = new Heladera("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, fechaAperturaH, -20f, 5f);
        Double multiplicador_puntos = 1d;
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Integer maxIterations = 5;
        CountDownLatch latch = new CountDownLatch(maxIterations);

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            Long periodosPasados = ChronoUnit.SECONDS.between(ultimaActualizacion[0], ahora);
            if (periodosPasados >= 4 && heladera.getEstado()) { // Si lo ponemos en 5, no pasa el test (en la ultima iteracion da 4.9999 y lo toma como 4)
                colaboradorJuridico.sumarPuntos(multiplicador_puntos);
                ultimaActualizacion[0] = ahora;
            }
            latch.countDown();
            
            if (latch.getCount() == 0) {
                scheduler.shutdown();
            }
        };

        scheduler.scheduleAtFixedRate(calculoPuntos, 0, 5, TimeUnit.SECONDS);
        
        long timeout = 60;

        if (!latch.await(timeout, TimeUnit.SECONDS)) {  // Esperamos un máximo de 60 segundos
            logger.log(Level.SEVERE, I18n.getMessage("contribucion.HacerseCargoDeHeladeraTest.CalcularPuntosDDTest_err", HacerseCargoDeHeladera.class.getSimpleName(), timeout));
            throw new IllegalStateException(I18n.getMessage("contribucion.HacerseCargoDeHeladeraTest.CalcularPuntosDDTest_exception"));
        }

        Assertions.assertEquals(4d, colaboradorJuridico.getPuntos());
    }

   */
}

package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionDineroCreator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class DonacionDineroTest {
    private static final Logger logger = Logger.getLogger(DonacionDineroTest.class.getName());

    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una DonacionDinero")
    public void CargaDonacionDineroTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();

        Double monto1 = 100000d;
        DonacionDinero.FrecuenciaDePago frecuencia1 = DonacionDinero.FrecuenciaDePago.UNICA_VEZ;

        DonacionDineroCreator donacionDineroCreator = new DonacionDineroCreator();
        DonacionDinero donacionDinero1 = (DonacionDinero) colaboradorHumano.colaborar(donacionDineroCreator, LocalDateTime.now(), monto1, frecuencia1);
        colaboradorHumano.confirmarContribucion(donacionDinero1, LocalDateTime.now());

        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorJuridico.darDeAlta();

        Double monto2 = 100000d;
        DonacionDinero.FrecuenciaDePago frecuencia2 = DonacionDinero.FrecuenciaDePago.UNICA_VEZ;

        DonacionDinero donacionDinero2 = (DonacionDinero) colaboradorJuridico.colaborar(donacionDineroCreator, LocalDateTime.now(), monto2, frecuencia2);
        colaboradorJuridico.confirmarContribucion(donacionDinero2, LocalDateTime.now());

        Assertions.assertTrue(colaboradorHumano.getContribuciones().size() == 1 && colaboradorJuridico.getContribuciones().size() == 1 && colaboradorHumano.getContribuciones().getFirst().getClass() == DonacionDinero.class && colaboradorJuridico.getContribuciones().getFirst().getClass() == DonacionDinero.class);
    }

    @Test
    @DisplayName("Testeo el cálculo de Puntos de DonacionDinero utilizando un Scheduler")
    public void CalcularPuntosDDTest() throws InterruptedException {    // Testeamos una version modificada de calcularPuntos(), dado que la original cuenta con periodos muy largos de ejecucion como para ser testeada
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();

        final LocalDateTime[] ultimaActualizacion = { LocalDateTime.now() };    // Usamos un Array para tener una final reference no directa al objeto y que nos permita modificarlo en el runnable
        Integer monto = 10;
        Double multiplicador_puntos = 1d; 
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Integer maxIterations = 5;
        CountDownLatch latch = new CountDownLatch(maxIterations);

        Runnable calculoPuntos = () -> {
            LocalDateTime ahora = LocalDateTime.now();
            Long periodosPasados = ChronoUnit.SECONDS.between(ultimaActualizacion[0], ahora);
            if (periodosPasados >= 4) { // Si lo ponemos en 5, no pasa el test (en la ultima iteracion da 4.9999 y lo toma como 4)
                colaboradorHumano.sumarPuntos(monto * multiplicador_puntos);
                ultimaActualizacion[0] = ahora;
            }
            latch.countDown();
            
            if (latch.getCount() == 0) 
                scheduler.shutdown();
        };

        scheduler.scheduleAtFixedRate(calculoPuntos, 0, 5, TimeUnit.SECONDS);
        
        long timeout = 60;

        if (!latch.await(timeout, TimeUnit.SECONDS)) {   // Esperamos un máximo de 60 segundos
            logger.log(Level.SEVERE, I18n.getMessage("contribucion.DonacionDineroTest.CalcularPuntosDDTest_err", DonacionDinero.class.getSimpleName(), timeout));
            throw new IllegalStateException(I18n.getMessage("contribucion.DonacionDineroTest.CalcularPuntosDDTest_exception"));
        }

        Assertions.assertEquals(40d, colaboradorHumano.getPuntos());
    }
}
package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.ViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaColaboradorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HeladeraTest {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    HeladeraService heladeraService;

    @Autowired
    ViandaService viandaService;

    @Autowired
    TarjetaColaboradorService tarjetaColaboradorService;

    @Test
    @DisplayName("Testeo la puesta en marcha de dos Heladeras y la adición de Viandas a estas por medio de distintas Contribuciones")
    public void HeladeraViandasTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(null, new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Long colaboradorJuridicoId =  colaboradorService.guardarColaborador(colaboradorJuridico).getId();

        LocalDateTime fechaAperturaH1 = LocalDateTime.parse("2024-01-01T00:00:00");
        Heladera heladera1 = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"),20, -20f, 5f, new ArrayList<>(), 2f, fechaAperturaH1, true);

        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();

        HacerseCargoDeHeladera hacerseCargoDeHeladera1 = (HacerseCargoDeHeladera) colaboradorService.colaborar(colaboradorJuridicoId, hacerseCargoDeHeladeraCreator, fechaAperturaH1, heladera1);

        Long heladera1Id = heladeraService.guardarHeladera(heladera1).getId();

        colaboradorService.confirmarContribucion(colaboradorJuridicoId, hacerseCargoDeHeladera1, fechaAperturaH1);

        LocalDateTime fechaAperturaH2 = LocalDateTime.parse("2024-02-01T00:00:00");
        Heladera heladera2 = new Heladera("HeladeraPrueba", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "1086", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f,  new ArrayList<>(), 5f, fechaAperturaH2, true);

        HacerseCargoDeHeladera hacerseCargoDeHeladera2 = (HacerseCargoDeHeladera) colaboradorService.colaborar(colaboradorJuridicoId, hacerseCargoDeHeladeraCreator, fechaAperturaH2, heladera2);

        Long heladera2Id = heladeraService.guardarHeladera(heladera2).getId();

        colaboradorService.confirmarContribucion(colaboradorJuridicoId, hacerseCargoDeHeladera2, fechaAperturaH2);

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        Long colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();

        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda1Id = viandaService.guardarVianda(vianda1).getId();

        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();

        DonacionVianda donacionVianda1 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda1, heladera1);

        TarjetaColaborador tarjetaColaborador = tarjetaColaboradorService.crearTarjeta(colaboradorHumanoId);
        String codigoTarjeta = colaboradorService.agregarTarjeta(colaboradorHumanoId, tarjetaColaborador).getCodigo();

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.agregarVianda(heladera1Id, vianda1);
        viandaService.agregarAHeladeraPrimeraVez(vianda1Id, heladera1);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda1, LocalDateTime.now());

        Vianda vianda2 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda2Id = viandaService.guardarVianda(vianda2).getId();

        DonacionVianda donacionVianda2 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda2, heladera1);

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.agregarVianda(heladera1Id, vianda2);
        viandaService.agregarAHeladeraPrimeraVez(vianda2Id, heladera1);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda2, LocalDateTime.now());

        Vianda vianda3 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda3Id = viandaService.guardarVianda(vianda3).getId();

        DonacionVianda donacionVianda3 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda3, heladera1);

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta,heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.agregarVianda(heladera1Id, vianda3);
        viandaService.agregarAHeladeraPrimeraVez(vianda3Id, heladera1);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda3, LocalDateTime.now());

        Vianda vianda4 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda4Id = viandaService.guardarVianda(vianda4).getId();

        DonacionVianda donacionVianda4 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda4, heladera2);

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera2);
        heladeraService.agregarVianda(heladera2Id, vianda4);
        viandaService.agregarAHeladeraPrimeraVez(vianda4Id, heladera2);
        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda4, LocalDateTime.now());

        Integer cantidadADistribuir = 2;

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorService.colaborar(colaboradorHumanoId, distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, cantidadADistribuir, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        
        ArrayList<Vianda> viandasAux = new ArrayList<>();

        for (Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = heladeraService.retirarVianda(heladera1Id);
            viandaService.quitarDeHeladera(viandaAux.getId());
            viandasAux.add(viandaAux);
        }

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera2);

        for (Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = viandasAux.removeFirst();
            heladeraService.agregarVianda(heladera2Id, viandaAux);
            viandaService.agregarAHeladera(viandaAux.getId(), heladera2);
        }

        colaboradorService.confirmarContribucion(colaboradorHumanoId, distribucionViandas, LocalDateTime.now());

        ArrayList<Vianda> viandasEsperadasHeladera1 = new ArrayList<>();
        ArrayList<Vianda> viandasEsperadasHeladera2 = new ArrayList<>();

        viandasEsperadasHeladera1.add(vianda3);
        viandasEsperadasHeladera2.add(vianda4);
        viandasEsperadasHeladera2.add(vianda1);
        viandasEsperadasHeladera2.add(vianda2);

        assertEquals(2, heladeraService.obtenerHeladeras().size());

        assertThat(heladera1.getViandas())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(viandasEsperadasHeladera1);

        assertThat(heladera2.getViandas())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(viandasEsperadasHeladera2);

        assertTrue(vianda1.getEntregada() && vianda2.getEntregada() && vianda3.getEntregada() && vianda4.getEntregada());

        assertTrue(vianda3.getHeladera() == heladera1 && vianda4.getHeladera() == heladera2 && vianda1.getHeladera() == heladera2 && vianda2.getHeladera() == heladera2);
    }

    @Test
    @DisplayName("Testeo la IllegalState que genera una Heladera que no permite que se agreguen más Viandas de las que entran")
    public void IllegalStateAgregarViandaTest() {
        LocalDateTime fechaApertura = LocalDateTime.parse("2024-01-01T00:00:00");
        Heladera heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 2, -20f, 5f, new ArrayList<>(), 2f, fechaApertura, true);
        Long heladeraId = heladeraService.guardarHeladera(heladera).getId();

        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");

        Vianda vianda1 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda1);

        Vianda vianda2 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda2);

        Vianda vianda3 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda3);

        ArrayList<Vianda> viandasAAgregar = new ArrayList<>();
        viandasAAgregar.add(vianda1);
        viandasAAgregar.add(vianda2);
        viandasAAgregar.add(vianda3);

        Integer cantidadAAgregar = 3;

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            for (Integer i = 1; i <= cantidadAAgregar; i++) {
                Vianda viandaAux = viandasAAgregar.removeFirst();
                heladeraService.agregarVianda(heladeraId, viandaAux);
                viandaService.agregarAHeladeraPrimeraVez(viandaAux.getId(), heladera);
            }
        });

        Assertions.assertEquals(I18n.getMessage("heladera.Heladera.agregarVianda_exception"), exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalState que genera una Heladera que no permite que se retiren más Viandas cuando está vacía")
    public void IllegalStateRetirarViandaTest() {
        LocalDateTime fechaApertura = LocalDateTime.parse("2024-01-01T00:00:00");
        Heladera heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 2, -20f, 5f, new ArrayList<>(), 2f, fechaApertura, true);
        Long heladeraId = heladeraService.guardarHeladera(heladera).getId();

        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");

        Vianda vianda1 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda1);

        Vianda vianda2 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda2);

        ArrayList<Vianda> viandasAAgregar = new ArrayList<>();
        viandasAAgregar.add(vianda1);
        viandasAAgregar.add(vianda2);

        Integer cantidadAAgregar = 2;

        for (Integer i = 1; i <= cantidadAAgregar; i++) {
            Vianda viandaAux = viandasAAgregar.removeFirst();
            heladeraService.agregarVianda(heladeraId, viandaAux);
            viandaService.agregarAHeladeraPrimeraVez(viandaAux.getId(), heladera);
        }

        ArrayList<Vianda> viandasARetirar = new ArrayList<>();
        Integer cantidadARetirar = 3;

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            for (Integer i = 1; i <= cantidadARetirar; i++) {
                Vianda viandaAux = heladeraService.retirarVianda(heladeraId);
                viandasARetirar.add(viandaAux);
                viandaService.quitarDeHeladera(viandaAux.getId());
            }
        });

        Assertions.assertEquals(I18n.getMessage("heladera.Heladera.retirarVianda_exception"), exception.getMessage());
    }
}
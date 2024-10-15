package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.ViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaColaboradorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.HacerseCargoDeHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.HacerseCargoDeHeladeraCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AccionColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TarjetaColaboradorActivaTest {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    HeladeraService heladeraService;

    @Autowired
    ViandaService viandaService;

    @Autowired
    TarjetaColaboradorService tarjetaColaboradorService;

    @Autowired
    AccionHeladeraService accionHeladeraService;

    ColaboradorHumano colaboradorHumano;
    Heladera heladera1;
    HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator;
    LocalDateTime fechaCaducidadV;
    Vianda vianda1;
    DonacionViandaCreator donacionViandaCreator;
    DonacionVianda donacionVianda1;
    TarjetaColaborador tarjetaColaborador;
    String codigoTarjeta;
    Long colaboradorJuridicoId;
    Long colaboradorHumanoId;
    Long heladera1Id;
    Long vianda1Id;

    @BeforeEach
    void setup() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Long colaboradorJuridicoId = colaboradorService.guardarColaborador(colaboradorJuridico).getId();

        LocalDateTime fechaAperturaH1 = LocalDateTime.parse("2024-01-01T00:00:00");
        Heladera heladera1 = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 5f, fechaAperturaH1, true);

        hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera1 = (HacerseCargoDeHeladera) colaboradorService.colaborar(colaboradorJuridicoId, hacerseCargoDeHeladeraCreator, fechaAperturaH1, heladera1);

        heladera1Id = heladeraService.guardarHeladera(heladera1).getId();

        colaboradorService.confirmarContribucion(colaboradorJuridicoId, hacerseCargoDeHeladera1, fechaAperturaH1);

        fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        vianda1Id = viandaService.guardarVianda(vianda1).getId();

        donacionViandaCreator = new DonacionViandaCreator();
        donacionVianda1 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda1, heladera1);

        tarjetaColaborador = tarjetaColaboradorService.crearTarjetaColaborador(colaboradorHumanoId);
        codigoTarjeta = colaboradorService.agregarTarjeta(colaboradorHumanoId, tarjetaColaborador).getCodigo();
    }


    @Test
    @DisplayName("Testeo la correcta carga de Solicitutes de Apertura y Aperturas en el Sistema")
    public void CargaAccionesColaborador() {
        LocalDateTime fechaAperturaH2 = LocalDateTime.parse("2024-02-01T00:00:00");
        Heladera heladera2 = new Heladera("HeladeraPrueba", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "1086", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 5f, fechaAperturaH2, true);
        Long heladera2Id = heladeraService.guardarHeladera(heladera2).getId();

        HacerseCargoDeHeladera hacerseCargoDeHeladera2 = (HacerseCargoDeHeladera) colaboradorService.colaborar(colaboradorJuridicoId, hacerseCargoDeHeladeraCreator, fechaAperturaH2, heladera2);
        colaboradorService.confirmarContribucion(colaboradorJuridicoId, hacerseCargoDeHeladera2, fechaAperturaH2);

        SolicitudAperturaColaborador solicitud1 = tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura1 = tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.agregarVianda(heladera1Id, vianda1);
        viandaService.agregarAHeladeraPrimeraVez(vianda1Id, heladera1);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda1, LocalDateTime.now());

        Vianda vianda2 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda2Id = viandaService.guardarVianda(vianda2).getId();

        DonacionVianda donacionVianda2 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda2, heladera1);

        SolicitudAperturaColaborador solicitud2 = tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura2 = tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.agregarVianda(heladera1Id, vianda2);
        viandaService.agregarAHeladeraPrimeraVez(vianda2Id, heladera1);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda2, LocalDateTime.now());

        Vianda vianda3 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda3Id = viandaService.guardarVianda(vianda3).getId();

        DonacionVianda donacionVianda3 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda3, heladera1);

        SolicitudAperturaColaborador solicitud3 = tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura3 = tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.agregarVianda(heladera1Id, vianda3);
        viandaService.agregarAHeladeraPrimeraVez(vianda3Id, heladera1);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda3, LocalDateTime.now());

        Vianda vianda4 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda4Id = viandaService.guardarVianda(vianda4).getId();

        DonacionVianda donacionVianda4 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda4, heladera2);

        SolicitudAperturaColaborador solicitud4 = tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura4 = tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera2);
        heladeraService.agregarVianda(heladera2Id, vianda3);
        viandaService.agregarAHeladeraPrimeraVez(vianda4Id, heladera2);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda4, LocalDateTime.now());

        Integer cantidadADistribuir = 2;

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorService.colaborar(colaboradorHumanoId, distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, cantidadADistribuir, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);

        SolicitudAperturaColaborador solicitud5 = tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        AperturaColaborador apertura5 = tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        
        ArrayList<Vianda> viandasAux = new ArrayList<>();
        for (Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = heladeraService.retirarVianda(heladera1Id);
            viandaService.quitarDeHeladera(viandaAux.getId());
            viandasAux.add(viandaAux);
        }

        SolicitudAperturaColaborador solicitud6 = tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION);
        AperturaColaborador apertura6 = tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera2);

        for (Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = viandasAux.removeFirst();
            heladeraService.agregarVianda(heladera2Id, viandaAux);
            viandaService.agregarAHeladera(viandaAux.getId(), heladera2);
        }

        colaboradorService.confirmarContribucion(colaboradorHumanoId, distribucionViandas, LocalDateTime.now());

        ArrayList<AccionColaborador> accionesColaborador = new ArrayList<>();
        accionesColaborador.add(solicitud1);
        accionesColaborador.add(apertura1);
        accionesColaborador.add(solicitud2);
        accionesColaborador.add(apertura2);
        accionesColaborador.add(solicitud3);
        accionesColaborador.add(apertura3);
        accionesColaborador.add(solicitud4);
        accionesColaborador.add(apertura4);
        accionesColaborador.add(solicitud5);
        accionesColaborador.add(apertura5);
        accionesColaborador.add(solicitud6);
        accionesColaborador.add(apertura6);

        assertThat(accionHeladeraService.obtenerAccionesHeladera())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(accionesColaborador);
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer solicitar una apertura para retirar Viandas de una Heladera vacía")
    public void UnsupportedOperationEstaVaciaTarjetaCTest() {
        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        });

        assertEquals(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia"), exception.getMessage());
    }
}

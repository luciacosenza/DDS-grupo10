package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.ViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaColaboradorCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.HacerseCargoDeHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasService;
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
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraNula;
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
    TarjetaColaboradorService tarjetaColaboradorService;

    @Autowired
    HeladeraService heladeraService;

    @Autowired
    HacerseCargoDeHeladeraService hacerseCargoDeHeladeraService;

    @Autowired
    ViandaService viandaService;

    @Autowired
    DonacionViandaService donacionViandaService;

    @Autowired
    DistribucionViandasService distribucionViandasService;

    @Autowired
    AccionHeladeraService accionHeladeraService;


    @Test
    @DisplayName("Testeo la correcta carga de Solicitutes de Apertura y Aperturas en el Sistema")
    public void CargaAccionesColaborador() throws InterruptedException {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Long colaboradorJuridicoID = colaboradorService.guardarColaborador(colaboradorJuridico).getId();

        LocalDateTime fechaAperturaH1 = LocalDateTime.parse("2024-01-01T00:00:00");
        Heladera heladera1 = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 5f, fechaAperturaH1, true);
        Long heladera1Id = heladeraService.guardarHeladera(heladera1).getId();
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera1 = (HacerseCargoDeHeladera) colaboradorService.colaborar(colaboradorJuridicoID, hacerseCargoDeHeladeraCreator, fechaAperturaH1, heladera1);
        Long hacerseCargoDeHeladera1ID = hacerseCargoDeHeladeraService.guardarHacerseCargoDeHeladera(hacerseCargoDeHeladera1).getId();
        colaboradorService.confirmarContribucion(colaboradorJuridicoID, hacerseCargoDeHeladera1ID, fechaAperturaH1);

        LocalDateTime fechaAperturaH2 = LocalDateTime.parse("2024-02-01T00:00:00");
        Heladera heladera2 = new Heladera("HeladeraPrueba", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "1086", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 5f, fechaAperturaH2, true);
        Long heladera2Id = heladeraService.guardarHeladera(heladera2).getId();
        HacerseCargoDeHeladera hacerseCargoDeHeladera2 = (HacerseCargoDeHeladera) colaboradorService.colaborar(colaboradorJuridicoID, hacerseCargoDeHeladeraCreator, fechaAperturaH2, heladera2);
        Long hacerseCargoDeHeladera2ID = hacerseCargoDeHeladeraService.guardarHacerseCargoDeHeladera(hacerseCargoDeHeladera2).getId();
        colaboradorService.confirmarContribucion(colaboradorJuridicoID, hacerseCargoDeHeladera2ID, fechaAperturaH2);

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        Long colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();

        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda1Id = viandaService.guardarVianda(vianda1).getId();
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda1 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda1, heladera1);
        Long donacionVianda1Id = donacionViandaService.guardarDonacionVianda(donacionVianda1).getId();

        TarjetaColaborador tarjetaColaborador = tarjetaColaboradorService.crearTarjetaColaborador(colaboradorHumanoId);
        String tarjetaColaboradorId = tarjetaColaboradorService.guardarTarjetaColaborador(tarjetaColaborador).getCodigo();
        colaboradorHumano.setTarjeta(tarjetaColaborador);

        SolicitudAperturaColaborador solicitud1 = tarjetaColaboradorService.solicitarApertura(tarjetaColaboradorId, heladera1Id, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura1 = tarjetaColaboradorService.intentarApertura(tarjetaColaboradorId, heladera1Id);
        heladeraService.agregarVianda(heladera1Id, vianda1Id);
        vianda1.setHeladera(heladera1);
        vianda1.marcarEntrega();
        vianda1.setFechaDonacion(LocalDateTime.now());
        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda1Id, LocalDateTime.now());

        Vianda vianda2 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda2Id = viandaService.guardarVianda(vianda2).getId();
        DonacionVianda donacionVianda2 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda2, heladera1);
        Long donacionVianda2Id = donacionViandaService.guardarDonacionVianda(donacionVianda2).getId();

        SolicitudAperturaColaborador solicitud2 = tarjetaColaboradorService.solicitarApertura(tarjetaColaboradorId, heladera1Id, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura2 = tarjetaColaboradorService.intentarApertura(tarjetaColaboradorId, heladera1Id);
        heladeraService.agregarVianda(heladera1Id, vianda2Id);
        vianda2.setHeladera(heladera1);
        vianda2.marcarEntrega();
        vianda2.setFechaDonacion(LocalDateTime.now());
        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda2Id, LocalDateTime.now());

        Vianda vianda3 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda3Id = viandaService.guardarVianda(vianda3).getId();
        DonacionVianda donacionVianda3 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda3, heladera1);
        Long donacionVianda3Id = donacionViandaService.guardarDonacionVianda(donacionVianda3).getId();

        SolicitudAperturaColaborador solicitud3 = tarjetaColaboradorService.solicitarApertura(tarjetaColaboradorId, heladera1Id, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura3 = tarjetaColaboradorService.intentarApertura(tarjetaColaboradorId, heladera1Id);
        heladeraService.agregarVianda(heladera1Id, vianda3Id);
        vianda3.setHeladera(heladera1);
        vianda3.marcarEntrega();
        vianda3.setFechaDonacion(LocalDateTime.now());
        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda3Id, LocalDateTime.now());

        Vianda vianda4 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long vianda4Id = viandaService.guardarVianda(vianda4).getId();
        DonacionVianda donacionVianda4 = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda4, heladera2);
        Long donacionVianda4Id = donacionViandaService.guardarDonacionVianda(donacionVianda4).getId();

        SolicitudAperturaColaborador solicitud4 = tarjetaColaboradorService.solicitarApertura(tarjetaColaboradorId, heladera2Id, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura4 = tarjetaColaboradorService.intentarApertura(tarjetaColaboradorId, heladera2Id);
        heladeraService.agregarVianda(heladera2Id, vianda3Id);
        vianda4.setHeladera(heladera2);
        vianda4.marcarEntrega();
        vianda4.setFechaDonacion(LocalDateTime.now());
        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda4Id, LocalDateTime.now());

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        Integer cantidadADistribuir = 2;
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorService.colaborar(colaboradorHumanoId, distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, cantidadADistribuir, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);
        Long distribucionViandasId = distribucionViandasService.guardarDistribucionViandas(distribucionViandas).getId();
        SolicitudAperturaColaborador solicitud5 = tarjetaColaboradorService.solicitarApertura(tarjetaColaboradorId, heladera1Id, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        AperturaColaborador apertura5 = tarjetaColaboradorService.intentarApertura(tarjetaColaboradorId, heladera1Id);
        
        ArrayList<Vianda> viandasAux = new ArrayList<>();

        for(Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = heladeraService.retirarVianda(heladera1Id);
            viandaAux.setHeladera(new HeladeraNula());
            viandaAux.desmarcarEntrega();
            viandasAux.add(viandaAux);
        }

        SolicitudAperturaColaborador solicitud6 = tarjetaColaboradorService.solicitarApertura(tarjetaColaboradorId, heladera2Id, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION);
        AperturaColaborador apertura6 = tarjetaColaboradorService.intentarApertura(tarjetaColaboradorId, heladera2Id);

        for(Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = viandasAux.removeFirst();
            Long viandaAuxId = viandaService.guardarVianda(viandaAux).getId();
            heladeraService.agregarVianda(heladera2Id, viandaAuxId);
            viandaAux.setHeladera(heladera2);
            viandaAux.marcarEntrega();
        }

        colaboradorService.confirmarContribucion(colaboradorHumanoId, distribucionViandasId, LocalDateTime.now());

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
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Long colaboradorJuridicoID = colaboradorService.guardarColaborador(colaboradorJuridico).getId();

        LocalDateTime fechaApertura = LocalDateTime.parse("2024-01-01T00:00:00");
        Heladera heladera = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 5f, fechaApertura, true);
        Long heladeraId = heladeraService.guardarHeladera(heladera).getId();
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera1 = (HacerseCargoDeHeladera) colaboradorService.colaborar(colaboradorJuridicoID, hacerseCargoDeHeladeraCreator, fechaApertura, heladera);
        Long hacerseCargoDeHeladera1ID = hacerseCargoDeHeladeraService.guardarHacerseCargoDeHeladera(hacerseCargoDeHeladera1).getId();
        heladeraService.guardarHeladera(heladera);
        colaboradorService.confirmarContribucion(colaboradorJuridicoID, hacerseCargoDeHeladera1ID, fechaApertura);

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long viandaId = viandaService.guardarVianda(vianda).getId();
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorService.colaborar(colaboradorJuridicoID, donacionViandaCreator, LocalDateTime.now(), vianda, heladera);
        Long donacionViandaId = donacionViandaService.guardarDonacionVianda(donacionVianda).getId();

        TarjetaColaborador tarjetaColaborador = tarjetaColaboradorService.crearTarjetaColaborador(colaboradorJuridicoID);
        String tarjetaColaboradorId = tarjetaColaboradorService.guardarTarjetaColaborador(tarjetaColaborador).getCodigo();
        colaboradorHumano.setTarjeta(tarjetaColaborador);

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            tarjetaColaboradorService.solicitarApertura(tarjetaColaboradorId, heladeraId, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        });

        assertEquals(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia"), exception.getMessage());
    }
}

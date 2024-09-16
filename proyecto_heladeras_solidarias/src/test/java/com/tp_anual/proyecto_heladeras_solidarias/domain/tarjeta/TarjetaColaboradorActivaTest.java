package com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaFisica;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorJuridico;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DistribucionViandasCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.HacerseCargoDeHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.HacerseCargoDeHeladeraCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraNula;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AccionColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class TarjetaColaboradorActivaTest {
    
    @Test
    @DisplayName("Testeo la correcta carga de Solicitutes de Apertura y Aperturas en el Sistema")
    public void CargaAccionesColaborador() throws InterruptedException {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        
        LocalDateTime fechaAperturaH1 = LocalDateTime.parse("2024-01-01T00:00:00");
        HeladeraActiva heladera1 = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaAperturaH1, -20f, 5f);
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera1 = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaAperturaH1, heladera1);
        heladera1.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera1, fechaAperturaH1);

        LocalDateTime fechaAperturaH2 = LocalDateTime.parse("2024-02-01T00:00:00");
        HeladeraActiva heladera2 = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "1086", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaAperturaH2, -20f, 5f);
        HacerseCargoDeHeladera hacerseCargoDeHeladera2 = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaAperturaH2, heladera2);
        heladera2.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera2, fechaAperturaH2);

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda1 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda1, heladera1);
        
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaboradorHumano);
        colaboradorHumano.setTarjeta(tarjetaColaboradorActiva);

        SolicitudAperturaColaborador solicitud1 = colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura1 = colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda1);
        vianda1.setHeladera(heladera1);
        vianda1.marcarEntrega();
        vianda1.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda1, LocalDateTime.now());

        Vianda vianda2 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda2 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda2, heladera1);

        SolicitudAperturaColaborador solicitud2 = colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura2 = colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda2);
        vianda2.setHeladera(heladera1);
        vianda2.marcarEntrega();
        vianda2.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda2, LocalDateTime.now());

        Vianda vianda3 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda3 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda3, heladera1);

        SolicitudAperturaColaborador solicitud3 = colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura3 = colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda3);
        vianda3.setHeladera(heladera1);
        vianda3.marcarEntrega();
        vianda3.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda3, LocalDateTime.now());

        Vianda vianda4 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda4 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda4, heladera2);

        SolicitudAperturaColaborador solicitud4 = colaboradorHumano.getTarjeta().solicitarApertura(heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        AperturaColaborador apertura4 = colaboradorHumano.getTarjeta().intentarApertura(heladera2);
        heladera2.agregarVianda(vianda4);
        vianda4.setHeladera(heladera2);
        vianda4.marcarEntrega();
        vianda4.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda4, LocalDateTime.now());

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        Integer cantidadADistribuir = 2;
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorHumano.colaborar(distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, cantidadADistribuir, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);
        
        SolicitudAperturaColaborador solicitud5 = colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        AperturaColaborador apertura5 = colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        
        ArrayList<Vianda> viandasAux = new ArrayList<>();

        for(Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = heladera1.retirarVianda();
            viandaAux.setHeladera(new HeladeraNula());
            viandaAux.desmarcarEntrega();
            viandasAux.add(viandaAux);
        }

        SolicitudAperturaColaborador solicitud6 = colaboradorHumano.getTarjeta().solicitarApertura(heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION);
        AperturaColaborador apertura6 = colaboradorHumano.getTarjeta().intentarApertura(heladera2);

        for(Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = viandasAux.removeFirst();
            heladera2.agregarVianda(viandaAux);
            viandaAux.setHeladera(heladera2);
            viandaAux.marcarEntrega();
        }

        colaboradorHumano.confirmarContribucion(distribucionViandas, LocalDateTime.now());

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

        assertThat(Sistema.getAccionesHeladeras())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(accionesColaborador);
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException al querer solicitar una apertura para retirar Viandas de una Heladera vacía")
    public void UnsupportedOperationEstaVaciaTarjetaCTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
    
        LocalDateTime fechaApertura = LocalDateTime.parse("2024-01-01T00:00:00");
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaApertura, -20f, 5f);
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera1 = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaApertura, heladera);
        heladera.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera1, fechaApertura);

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(Documento.TipoDocumento.DNI, "40123456", Documento.Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda, heladera);
        
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaboradorHumano);
        colaboradorHumano.setTarjeta(tarjetaColaboradorActiva);

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            colaboradorHumano.getTarjeta().solicitarApertura(heladera, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        });

        assertEquals(I18n.getMessage("heladera.GestorDeAperturas.revisarSolicitudApertura_exception_heladera_vacia"), exception.getMessage());
    }
}

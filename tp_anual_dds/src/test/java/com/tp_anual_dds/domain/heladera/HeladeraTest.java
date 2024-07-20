package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;
import com.tp_anual_dds.domain.contribuciones.DistribucionViandas;
import com.tp_anual_dds.domain.contribuciones.DistribucionViandasCreator;
import com.tp_anual_dds.domain.contribuciones.DonacionVianda;
import com.tp_anual_dds.domain.contribuciones.DonacionViandaCreator;
import com.tp_anual_dds.domain.contribuciones.HacerseCargoDeHeladera;
import com.tp_anual_dds.domain.contribuciones.HacerseCargoDeHeladeraCreator;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.documento.Documento.Sexo;
import com.tp_anual_dds.domain.documento.Documento.TipoDocumento;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorCreator;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class HeladeraTest {
    
    @Test
    @DisplayName("Testeo la puesta en marcha de dos Heladeras y la adición de Viandas a estas por medio de distintas Contribuciones")
    public void HeladeraViandasTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        
        LocalDateTime fechaApertura1 = LocalDateTime.parse("2024-01-01T00:00:00");
        HeladeraActiva heladera1 = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaApertura1, -20f, 5f);
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera1 = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaApertura1, heladera1);
        heladera1.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera1);

        LocalDateTime fechaApertura2 = LocalDateTime.parse("2024-02-01T00:00:00");
        HeladeraActiva heladera2 = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaApertura2, -20f, 5f);
        HacerseCargoDeHeladera hacerseCargoDeHeladera2 = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaApertura2, heladera2);
        heladera2.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera2);

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda1 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda1, heladera1);
        
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaboradorHumano);
        colaboradorHumano.setTarjeta(tarjetaColaboradorActiva);

        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda1);
        vianda1.setHeladera(heladera1);
        vianda1.marcarEntrega();
        vianda1.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda1);

        Vianda vianda2 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda2 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda2, heladera1);

        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda2);
        vianda2.setHeladera(heladera1);
        vianda2.marcarEntrega();
        vianda2.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda2);

        Vianda vianda3 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda3 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda3, heladera1);

        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda3);
        vianda3.setHeladera(heladera1);
        vianda3.marcarEntrega();
        vianda3.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda3);

        Vianda vianda4 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda4 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda4, heladera2);

        colaboradorHumano.getTarjeta().solicitarApertura(heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera2);
        heladera2.agregarVianda(vianda4);
        vianda4.setHeladera(heladera2);
        vianda4.marcarEntrega();
        vianda4.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda4);

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        Integer cantidadADistribuir = 2;
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorHumano.colaborar(distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, cantidadADistribuir, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);
        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        
        ArrayList<Vianda> viandasAux = new ArrayList<>();

        for(Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = heladera1.retirarVianda();
            viandaAux.setHeladera(new HeladeraNula());
            viandaAux.desmarcarEntrega();
            viandasAux.add(viandaAux);
        }

        colaboradorHumano.getTarjeta().solicitarApertura(heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera2);

        for(Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = viandasAux.removeFirst();
            heladera2.agregarVianda(viandaAux);
            viandaAux.setHeladera(heladera2);
            viandaAux.marcarEntrega();
        }

        colaboradorHumano.confirmarContribucion(distribucionViandas);

        ArrayList<Vianda> viandasEsperadasHeladera1 = new ArrayList<>();
        ArrayList<Vianda> viandasEsperadasHeladera2 = new ArrayList<>();

        viandasEsperadasHeladera1.add(vianda3);
        viandasEsperadasHeladera2.add(vianda4);
        viandasEsperadasHeladera2.add(vianda1);
        viandasEsperadasHeladera2.add(vianda2);

        assertThat(heladera1.getViandas())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(viandasEsperadasHeladera1);

        assertThat(heladera2.getViandas())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(viandasEsperadasHeladera2);

        assertEquals(2, Sistema.getHeladeras().size());
    }

    @Test
    @DisplayName("Testeo que una Heladera no permita que se agreguen más Viandas de las que entran")
    public void IllegalStateAgregarViandaTest() {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda2 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda3 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);

        ArrayList<Vianda> viandasAAgregar = new ArrayList<>();
        viandasAAgregar.add(vianda1);
        viandasAAgregar.add(vianda2);
        viandasAAgregar.add(vianda3);

        Integer cantidadAAgregar = 3;

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            for(Integer i = 1; i <= cantidadAAgregar; i++) {
                Vianda viandaAux = viandasAAgregar.removeFirst();
                heladera.agregarVianda(viandaAux);
                viandaAux.setHeladera(heladera);
                viandaAux.marcarEntrega();
            }
        });

        Assertions.assertEquals("No se puede agregar la vianda. Se superaría la capacidad de la heladera.", exception.getMessage());
    }
}

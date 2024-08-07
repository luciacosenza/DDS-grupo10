package com.tp_anual_dds.reporte;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;
import com.tp_anual_dds.domain.contribucion.DonacionVianda;
import com.tp_anual_dds.domain.contribucion.DonacionViandaCreator;
import com.tp_anual_dds.domain.contribucion.HacerseCargoDeHeladera;
import com.tp_anual_dds.domain.contribucion.HacerseCargoDeHeladeraCreator;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.documento.Documento.Sexo;
import com.tp_anual_dds.domain.documento.Documento.TipoDocumento;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorCreator;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class ReporteIncidentesPorHeladeraTest {

    @Test
    @DisplayName("Testeo la carga correcta del ReporteIncidentesPorHeladera")
    public void CargaReporteIncidentesPorHeladeraTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        colaboradorJuridico.darDeAlta();

        LocalDateTime fechaAperturaH1 = LocalDateTime.parse("2024-01-01T00:00:00");
        HeladeraActiva heladera1 = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaAperturaH1, -20f, 5f);
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera1 = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaAperturaH1, heladera1);
        heladera1.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera1, fechaAperturaH1);

        LocalDateTime fechaAperturaH2 = LocalDateTime.parse("2024-02-01T00:00:00");
        HeladeraActiva heladera2 = new HeladeraActiva("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaAperturaH2, -20f, 5f);
        HacerseCargoDeHeladera hacerseCargoDeHeladera2 = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaAperturaH2, heladera2);
        heladera2.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera2, fechaAperturaH2);

        LocalDateTime fechaAperturaH3 = LocalDateTime.parse("2024-03-01T00:00:00");
        HeladeraActiva heladera3 = new HeladeraActiva("HeladeraPrueba3", new Ubicacion(-34.59866429352556, -58.419901047570306, "Avenida Medrano 951", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaAperturaH3, -20f, 5f);
        HacerseCargoDeHeladera hacerseCargoDeHeladera3 = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaAperturaH3, heladera3);
        heladera3.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera3, fechaAperturaH3);

        ColaboradorHumano colaboradorHumano1 = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba1", "ApellidoPrueba1", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano1.darDeAlta();

        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", colaboradorHumano1, fechaCaducidadV, null, 0, 0, false);
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda1 = (DonacionVianda) colaboradorHumano1.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda1, heladera1);
        
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva1 = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaboradorHumano1);
        colaboradorHumano1.setTarjeta(tarjetaColaboradorActiva1);

        colaboradorHumano1.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano1.reportarFallaTecnica(heladera1, "DescripcionPrueba1", "ImagenPrueba1");

        ColaboradorHumano colaboradorHumano2 = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba2", "ApellidoPrueba2", new Documento(TipoDocumento.DNI, "40123460", Sexo.MASCULINO), LocalDateTime.parse("2003-01-02T00:00:00"));
        colaboradorHumano2.darDeAlta();

        Vianda vianda2 = new Vianda("ComidaPrueba", colaboradorHumano1, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda2 = (DonacionVianda) colaboradorHumano1.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda2, heladera2);

        TarjetaColaboradorActiva tarjetaColaboradorActiva2 = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaboradorHumano2);
        colaboradorHumano2.setTarjeta(tarjetaColaboradorActiva2);

        colaboradorHumano2.getTarjeta().solicitarApertura(heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano2.reportarFallaTecnica(heladera2, "DescripcionPrueba2", "ImagenPrueba2");

        ColaboradorHumano colaboradorHumano3 = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba3", "ApellidoPrueba3", new Documento(TipoDocumento.DNI, "40123470", Sexo.MASCULINO), LocalDateTime.parse("2003-01-03T00:00:00"));
        colaboradorHumano3.darDeAlta();

        Vianda vianda3 = new Vianda("ComidaPrueba", colaboradorHumano3, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda3 = (DonacionVianda) colaboradorHumano3.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda3, heladera3);
        TarjetaColaboradorActiva tarjetaColaboradorActiva3 = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaboradorHumano3);
        colaboradorHumano3.setTarjeta(tarjetaColaboradorActiva3);

        colaboradorHumano3.getTarjeta().solicitarApertura(heladera3, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano3.reportarFallaTecnica(heladera3, "DescripcionPrueba3", "ImagenPrueba3");

        ReporteIncidentesPorHeladera reporte =  Sistema.getReporteIncidentesPorHeladera();
        reporte.programarReporte();
        LinkedHashMap<HeladeraActiva, Integer> hashMap = reporte.getHashMap();

        Integer incidentesH1 = 1;
        Integer incidentesH2 = 1;
        Integer incidentesH3 = 1;

        LinkedHashMap<HeladeraActiva, Integer> hashMapEsperado = new LinkedHashMap<>();

        hashMapEsperado.put(heladera1, incidentesH1);
        hashMapEsperado.put(heladera2, incidentesH2);
        hashMapEsperado.put(heladera3, incidentesH3);

        Assertions.assertThat(hashMap).containsExactlyEntriesOf(hashMapEsperado);
    }
}

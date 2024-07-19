package com.tp_anual_dds.domain.estados_de_solicitud;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
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
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva.MotivoSolicitud;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorCreator;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class EstadoSolicitudTest {
    
    @Test
    @DisplayName("Testeo el correcto funcionamiento de los Estados de Solicitud")
    public void EstadoSolicitudManejarTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        LocalDateTime fechaApertura = LocalDateTime.parse("2024-01-01T00:00:00");
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, fechaApertura, -20f, 5f);
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaApertura, heladera);
        heladera.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera);
        
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, LocalDateTime.now(), 0, 0, false);
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda, heladera);
        
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta("CodigoPrueba", colaboradorHumano);
        colaboradorHumano.setTarjeta(tarjetaColaboradorActiva);

        colaboradorHumano.getTarjeta().solicitarApertura(MotivoSolicitud.INGRESAR_DONACION, heladera);
        colaboradorHumano.getTarjeta().intentarApertura(heladera);
        heladera.agregarVianda(vianda);
        vianda.setHeladera(heladera);
        colaboradorHumano.confirmarContribucion(donacionVianda);
        
        Assertions.assertTrue(heladera.getViandas().getFirst() == vianda && vianda.getHeladera() == heladera && colaboradorHumano.getContribuciones().size() == 1);
    }

    @Test
    @DisplayName("Testeo la UnsupportedOperationException generada al querer hacer una Solicitud cuando ya hay una Realizada")
    public void UnsupportedOperationEstadoRealizadaTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        LocalDateTime fechaApertura = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDateTime fechaAperturaH2   = LocalDateTime.parse("2024-02-01T00:00:00");
        HeladeraActiva heladera1 = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, fechaApertura, -20f, 5f);
        HeladeraActiva heladera2 = new HeladeraActiva("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaAperturaH2, -20f, 5f);
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        HacerseCargoDeHeladera hacerseCargoDeHeladera = (HacerseCargoDeHeladera) colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaApertura, heladera1);
        heladera1.darDeAlta();
        colaboradorJuridico.confirmarContribucion(hacerseCargoDeHeladera);

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, LocalDateTime.now(), 0, 0, false);
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda, heladera1);
        
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta("CodigoPrueba", colaboradorHumano);
        colaboradorHumano.setTarjeta(tarjetaColaboradorActiva);

        colaboradorHumano.getTarjeta().solicitarApertura(MotivoSolicitud.INGRESAR_DONACION, heladera1);
        
        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorHumano.colaborar(distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, 1, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);
        
        UnsupportedOperationException exception = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            colaboradorHumano.getTarjeta().solicitarApertura(TarjetaColaboradorActiva.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION, heladera1);
        });

        Assertions.assertEquals("La solicitud ya fue realizada", exception.getMessage());
    }
}

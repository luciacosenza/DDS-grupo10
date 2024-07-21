package com.tp_anual_dds.domain.contribuciones;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.documento.Documento.Sexo;
import com.tp_anual_dds.domain.documento.Documento.TipoDocumento;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorActiva;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorCreator;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class DistribucionViandasTest {
    
    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una DistribucionViandas")
    public void CargaDistribucionViandasTest() { 
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano.darDeAlta();

        LocalDateTime fechaAperturaH1   = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDateTime fechaAperturaH2   = LocalDateTime.parse("2024-02-01T00:00:00");
        LocalDateTime fechaCaducidadV   = LocalDateTime.parse("2025-01-01T00:00:00");
        
        HeladeraActiva heladera1 = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, fechaAperturaH1, -20f, 5f);
        HeladeraActiva heladera2 = new HeladeraActiva("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaAperturaH2, -20f, 5f);
        Vianda vianda = new Vianda("ComidaPrueba" , colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda, heladera1);
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaboradorHumano);
        colaboradorHumano.setTarjeta(tarjetaColaboradorActiva);
        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda);
        vianda.setHeladera(heladera1);
        vianda.marcarEntrega();
        vianda.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda);

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorHumano.colaborar(distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, 1, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);
        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.retirarVianda();
        vianda.quitarDeHeladera();
        vianda.desmarcarEntrega();
        colaboradorHumano.getTarjeta().solicitarApertura(heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera2);
        heladera2.agregarVianda(vianda);
        vianda.setHeladera(heladera2);
        vianda.marcarEntrega();
        colaboradorHumano.confirmarContribucion(distribucionViandas);

        Assertions.assertTrue(Sistema.getAccionesHeladeras().size() == 6 && colaboradorHumano.getContribuciones().size() == 2 && colaboradorHumano.getContribuciones().get(1).getClass() == DistribucionViandas.class);
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer hacer una DistribucionViandas sin tener domicilio registrado")
    public void IllegalArgumentValidarIdentidadDistribucionViandasTest() { 
        ColaboradorHumano colaboradorHumano1 = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        colaboradorHumano1.darDeAlta();

        LocalDateTime fechaAperturaH1   = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDateTime fechaAperturaH2   = LocalDateTime.parse("2024-02-01T00:00:00");
        LocalDateTime fechaCaducidadV   = LocalDateTime.parse("2025-01-01T00:00:00");
        
        HeladeraActiva heladera1 = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, fechaAperturaH1, -20f, 5f);
        HeladeraActiva heladera2 = new HeladeraActiva("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 5, fechaAperturaH2, -20f, 5f);
        Vianda vianda = new Vianda("ComidaPrueba" , colaboradorHumano1, fechaCaducidadV, null, 0, 0, false);
        
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorHumano1.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda, heladera1);
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta(colaboradorHumano1);
        colaboradorHumano1.setTarjeta(tarjetaColaboradorActiva);
        colaboradorHumano1.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano1.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda);
        vianda.setHeladera(heladera1);
        vianda.marcarEntrega();
        vianda.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano1.confirmarContribucion(donacionVianda);

        ColaboradorHumano colaboradorHumano2 = new ColaboradorHumano(null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123460", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorHumano2.colaborar(distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, 1, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);
        });

        Assertions.assertEquals("El colaborador aspirante no posee domicilio. Para recibir la Tarjeta Solidaria debe actualizar su información", exception.getMessage());
    }
}

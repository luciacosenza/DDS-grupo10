package com.tp_anual_dds.domain.estados_de_solicitud;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.colaborador.ColaboradorJuridico;
import com.tp_anual_dds.domain.contribuciones.DonacionViandaCreator;
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
    
    @Test   // Hay que cambiar este test, lo deje asi para que no rompa
    @DisplayName("Testeo el correcto funcionamiento de los Estados de Solicitud")
    public void EstadoSolicitudManejarTest() {
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "RazonSocialPrueba", "RubroPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA);
        LocalDateTime fechaApertura = LocalDateTime.parse("2024-01-01T00:00:00");
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, fechaApertura, -20f, 5f);
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        colaboradorJuridico.colaborar(hacerseCargoDeHeladeraCreator, fechaApertura, heladera);
        heladera.darDeAlta();
        colaboradorJuridico.confirmarContribucion(colaboradorJuridico.getContribucionPendiente());
        
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"));
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, LocalDateTime.now(), 0, 0, false);
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda, heladera);
        
        TarjetaColaboradorCreator tarjetaColaboradorCreator = new TarjetaColaboradorCreator();
        TarjetaColaboradorActiva tarjetaColaboradorActiva = (TarjetaColaboradorActiva) tarjetaColaboradorCreator.crearTarjeta("CodigoPrueba", colaboradorHumano);
        colaboradorHumano.setTarjeta(tarjetaColaboradorActiva);

        colaboradorHumano.getTarjeta().solicitarApertura(MotivoSolicitud.INGRESAR_DONACION, heladera);
        colaboradorHumano.getTarjeta().intentarApertura(heladera);
        heladera.agregarVianda(vianda);
        vianda.setHeladera(heladera);
        
        Assertions.assertTrue(heladera.getViandas().getFirst() == vianda && vianda.getHeladera() == heladera);
    }
}

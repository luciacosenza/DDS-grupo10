package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class HeladeraTest {
    
    @Test
    @DisplayName("Testeo la puesta en marcha de dos Heladeras y la adición de Viandas a estas por medio de distintas Contribuciones")
    public void HeladeraViandasTest() throws InterruptedException{
        ColaboradorJuridico colaboradorJuridico = new ColaboradorJuridico(new PersonaJuridica("RazonSocialPrueba", PersonaJuridica.TipoPersonaJuridica.EMPRESA, "RubroPrueba"), new Ubicacion(-34.6098, -58.3925, "Avenida Entre Ríos", "1033", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorJuridico.darDeAlta();

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

        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();

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
        colaboradorHumano.confirmarContribucion(donacionVianda1, LocalDateTime.now());

        Vianda vianda2 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda2 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda2, heladera1);

        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda2);
        vianda2.setHeladera(heladera1);
        vianda2.marcarEntrega();
        vianda2.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda2, LocalDateTime.now());

        Vianda vianda3 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda3 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda3, heladera1);

        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        heladera1.agregarVianda(vianda3);
        vianda3.setHeladera(heladera1);
        vianda3.marcarEntrega();
        vianda3.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda3, LocalDateTime.now());

        Vianda vianda4 = new Vianda("ComidaPrueba", colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        DonacionVianda donacionVianda4 = (DonacionVianda) colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda4, heladera2);

        colaboradorHumano.getTarjeta().solicitarApertura(heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera2);
        heladera2.agregarVianda(vianda4);
        vianda4.setHeladera(heladera2);
        vianda4.marcarEntrega();
        vianda4.setFechaDonacion(LocalDateTime.now());
        colaboradorHumano.confirmarContribucion(donacionVianda4, LocalDateTime.now());

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        Integer cantidadADistribuir = 2;
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorHumano.colaborar(distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, cantidadADistribuir, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);
        colaboradorHumano.getTarjeta().solicitarApertura(heladera1, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        colaboradorHumano.getTarjeta().intentarApertura(heladera1);
        
        ArrayList<Vianda> viandasAux = new ArrayList<>();

        for(Integer i = 1; i <= cantidadADistribuir; i++) {
            Vianda viandaAux = heladera1.retirarVianda();
            viandaAux.quitarDeHeladera();
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

        colaboradorHumano.confirmarContribucion(distribucionViandas, LocalDateTime.now());

        ArrayList<Vianda> viandasEsperadasHeladera1 = new ArrayList<>();
        ArrayList<Vianda> viandasEsperadasHeladera2 = new ArrayList<>();

        viandasEsperadasHeladera1.add(vianda3);
        viandasEsperadasHeladera2.add(vianda4);
        viandasEsperadasHeladera2.add(vianda1);
        viandasEsperadasHeladera2.add(vianda2);

        assertEquals(2, Sistema.getHeladeras().size());

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
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, fechaApertura, -20f, 5f);
        heladera.darDeAlta();
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

        Assertions.assertEquals(I18n.getMessage("heladera.HeladeraActiva.agregarVianda_exception"), exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalState que genera una Heladera que no permite que se retiren más Viandas cuando está vacía")
    public void IllegalStateRetirarViandaTest() {
        LocalDateTime fechaApertura = LocalDateTime.parse("2024-01-01T00:00:00");
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, fechaApertura, -20f, 5f);
        heladera.darDeAlta();
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        Vianda vianda1 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);
        Vianda vianda2 = new Vianda("ComidaPrueba", null, fechaCaducidadV, null, 0, 0, false);

        ArrayList<Vianda> viandasAAgregar = new ArrayList<>();
        viandasAAgregar.add(vianda1);
        viandasAAgregar.add(vianda2);

        Integer cantidadAAgregar = 2;

        for(Integer i = 1; i <= cantidadAAgregar; i++) {
            Vianda viandaAux = viandasAAgregar.removeFirst();
            heladera.agregarVianda(viandaAux);
            viandaAux.setHeladera(heladera);
            viandaAux.marcarEntrega();
        }

        ArrayList<Vianda> viandasARetirar = new ArrayList<>();
        Integer cantidadARetirar = 3;

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
            for(Integer i = 1; i <= cantidadARetirar; i++) {
                Vianda viandaAux = heladera.retirarVianda();
                viandasARetirar.add(viandaAux);
                viandaAux.quitarDeHeladera();
                viandaAux.desmarcarEntrega();
            }
        });

        Assertions.assertEquals(I18n.getMessage("heladera.HeladeraActiva.retirarVianda_exception"), exception.getMessage());
    }
}
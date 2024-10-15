package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.ViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaColaboradorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DistribucionViandasTest {

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
    LocalDateTime fechaAperturaH1;
    LocalDateTime fechaAperturaH2;
    LocalDateTime fechaCaducidadV;
    Heladera heladera1;
    Heladera heladera2;
    Vianda vianda;
    Long colaboradorHumanoId;
    Long heladera1Id;
    Long heladera2Id;
    Long viandaId;

    @BeforeEach
    void setUp() {
        colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        fechaAperturaH1 = LocalDateTime.parse("2024-01-01T00:00:00");
        fechaAperturaH2 = LocalDateTime.parse("2024-02-01T00:00:00");
        fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");
        heladera1 = new Heladera("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 2f, fechaAperturaH1, true);
        heladera2 = new Heladera("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "1086", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 2f,  fechaAperturaH2, true);
        vianda = new Vianda("ComidaPrueba" , colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();
        heladera1Id = heladeraService.guardarHeladera(heladera1).getId();
        heladera2Id = heladeraService.guardarHeladera(heladera2).getId();
        viandaId = viandaService.guardarVianda(vianda).getId();
    }

    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una DistribucionViandas")
    public void CargaDistribucionViandasTest() {
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda, heladera1);

        TarjetaColaborador tarjeta = tarjetaColaboradorService.crearTarjeta(colaboradorHumanoId);
        String codigoTarjeta = colaboradorService.agregarTarjeta(colaboradorHumanoId, tarjeta).getCodigo();

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.agregarVianda(heladera1Id, vianda);
        viandaService.agregarAHeladeraPrimeraVez(viandaId, heladera1);
        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda, LocalDateTime.now());

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorService.colaborar(colaboradorHumanoId, distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, 1, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.retirarVianda(heladera1Id);
        viandaService.quitarDeHeladera(viandaId);

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera2, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera2);
        heladeraService.agregarVianda(heladera2Id, vianda);
        viandaService.agregarAHeladera(viandaId, heladera2);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, distribucionViandas, LocalDateTime.now());

        Assertions.assertTrue(accionHeladeraService.obtenerAccionesHeladera().size() == 6 && colaboradorHumano.getContribuciones().size() == 2 && colaboradorHumano.getContribuciones().get(1).getClass() == DistribucionViandas.class);
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer hacer una DistribucionViandas sin tener domicilio registrado")
    public void IllegalArgumentValidarIdentidadDistribucionViandasTest() {
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda, heladera1);

        TarjetaColaborador tarjetaColaborador = tarjetaColaboradorService.crearTarjeta(colaboradorHumanoId);
        String codigoTarjeta = tarjetaColaboradorService.guardarTarjeta(tarjetaColaborador).getCodigo();
        colaboradorService.agregarTarjeta(colaboradorHumanoId, tarjetaColaborador);

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1);
        heladeraService.agregarVianda(heladera1Id, vianda);
        viandaService.agregarAHeladeraPrimeraVez(viandaId, heladera1);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda, LocalDateTime.now());

        ColaboradorHumano colaboradorHumanoND = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123460", Sexo.MASCULINO), LocalDateTime.parse("2003-02-01T00:00:00")), null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Long colaboradorHumanoNDId = colaboradorService.guardarColaborador(colaboradorHumanoND).getId();

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoNDId, distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, 1, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO));

        Assertions.assertEquals(I18n.getMessage("contribucion.DistribucionViandas.validarIdentidad_exception"), exception.getMessage());
    }
}

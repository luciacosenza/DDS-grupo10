package com.tp_anual.proyecto_heladeras_solidarias.model.colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.ViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaColaboradorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DistribucionViandas;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DistribucionViandasCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.DonacionVianda;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Colaborador2Test {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    ViandaService viandaService;

    @Autowired
    HeladeraService heladeraService;

    @Autowired
    TarjetaColaboradorService tarjetaColaboradorService;

    @Autowired
    DonacionViandaService donacionViandaService;

    @Autowired
    DistribucionViandasService distribucionViandasService;

    @Test
    @DisplayName("Testeo la correcta creación de Contribuciones y que se agreguen a las contribuciones del Colaborador")
    public void ColaborarTest() throws InterruptedException {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        Long colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();

        LocalDateTime fechaAperturaH1 = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDateTime fechaAperturaH2 = LocalDateTime.parse("2024-02-01T00:00:00");
        LocalDateTime fechaCaducidadV = LocalDateTime.parse("2025-01-01T00:00:00");

        Heladera heladera1 = new Heladera("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 3f, fechaAperturaH1 , true);
        Heladera heladera2 = new Heladera("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "1086", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f, new ArrayList<>(), 5f, fechaAperturaH2, true);

        Long heladera1Id = heladeraService.guardarHeladera(heladera1).getId();
        Long heladera2Id = heladeraService.guardarHeladera(heladera2).getId();

        Vianda vianda = new Vianda("ComidaPrueba" , colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long viandaId = viandaService.guardarVianda(vianda).getId();

        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda, heladera1);
        Long donacionViandaID = donacionViandaService.guardarDonacionVianda(donacionVianda).getId();

        TarjetaColaborador tarjeta = tarjetaColaboradorService.crearTarjetaColaborador(colaboradorHumanoId);
        colaboradorHumano.setTarjeta(tarjeta);
        String codigoTarjeta = tarjetaColaboradorService.guardarTarjetaColaborador(tarjeta).getCodigo();

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1Id, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1Id);

        heladeraService.agregarVianda(heladera1Id, viandaId);

        vianda.setHeladera(heladera1);
        vianda.marcarEntrega();
        vianda.setFechaDonacion(LocalDateTime.now());
        viandaService.guardarVianda(vianda);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionViandaID, LocalDateTime.now());

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        DistribucionViandas distribucionViandas = (DistribucionViandas) colaboradorService.colaborar(colaboradorHumanoId, distribucionViandasCreator, LocalDateTime.now(), heladera1, heladera2, 1, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);
        Long distribucionViandasID = distribucionViandasService.guardarDistribucionViandas(distribucionViandas).getId();

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera1Id, SolicitudAperturaColaborador.MotivoSolicitud.RETIRAR_LOTE_DE_DISTRIBUCION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera1Id);

        heladeraService.retirarVianda(heladera1Id);

        vianda.quitarDeHeladera();
        vianda.desmarcarEntrega();
        viandaService.guardarVianda(vianda);

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta ,heladera2Id, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_LOTE_DE_DISTRIBUCION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera2Id);

        heladeraService.agregarVianda(heladera2Id, viandaId);

        vianda.setHeladera(heladera2);
        vianda.marcarEntrega();
        viandaService.guardarVianda(vianda);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, distribucionViandasID, LocalDateTime.now());

        ArrayList<Contribucion> contribuciones = new ArrayList<>();
        contribuciones.add(donacionVianda);
        contribuciones.add(distribucionViandas);

        Assertions.assertThat(colaboradorHumano.getContribuciones())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(contribuciones);
    }
}

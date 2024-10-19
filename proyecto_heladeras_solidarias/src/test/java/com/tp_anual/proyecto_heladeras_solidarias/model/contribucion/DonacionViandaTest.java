package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionViandaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.AccionHeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.HeladeraService;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.ViandaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaColaboradorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
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
public class DonacionViandaTest {
    
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

    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una DonacionVianda")
    public void CargaDonacionViandaTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDate.parse("2003-01-01")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        Long colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();

        LocalDateTime fechaAperturaH1   = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDate fechaCaducidadV   = LocalDate.parse("2025-01-01");

        Heladera heladera = new Heladera("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f,  new ArrayList<>(), 2f, fechaAperturaH1, true);
        Long heladeraId = heladeraService.guardarHeladera(heladera).getId();

        Vianda vianda = new Vianda("ComidaPrueba" , colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        Long viandaId = viandaService.guardarVianda(vianda).getId();

        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        DonacionVianda donacionVianda = (DonacionVianda) colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda, heladera);

        TarjetaColaborador tarjetaColaborador = tarjetaColaboradorService.crearTarjeta(colaboradorHumanoId);
        String codigoTarjeta = colaboradorService.agregarTarjeta(colaboradorHumanoId, tarjetaColaborador).getCodigo();

        tarjetaColaboradorService.solicitarApertura(codigoTarjeta, heladera, SolicitudAperturaColaborador.MotivoSolicitud.INGRESAR_DONACION);
        tarjetaColaboradorService.intentarApertura(codigoTarjeta, heladera);
        heladeraService.agregarVianda(heladeraId, vianda);
        viandaService.agregarAHeladeraPrimeraVez(viandaId, heladera);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, donacionVianda, LocalDateTime.now());

        Assertions.assertTrue(accionHeladeraService.obtenerAccionesHeladera().size() == 2 && colaboradorHumano.getContribuciones().size() == 1 && colaboradorHumano.getContribuciones().getFirst().getClass() == DonacionVianda.class);
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer hacer una DonacionVianda sin tener domicilio registrado")
    public void IllegalArgumentValidarIdentidadDDonacionViandaTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDate.parse("2003-01-01")), null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        Long colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();

        LocalDateTime fechaAperturaH   = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDate fechaCaducidadV   = LocalDate.parse("2025-01-01");
        
        Heladera heladera = new Heladera("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), 20, -20f, 5f,  new ArrayList<>(), 2f, fechaAperturaH, true);
        heladeraService.guardarHeladera(heladera);

        Vianda vianda = new Vianda("ComidaPrueba" , colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        viandaService.guardarVianda(vianda);

        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, donacionViandaCreator, LocalDateTime.now(), vianda, heladera));

        Assertions.assertEquals(I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_exception"), exception.getMessage());
    }
}

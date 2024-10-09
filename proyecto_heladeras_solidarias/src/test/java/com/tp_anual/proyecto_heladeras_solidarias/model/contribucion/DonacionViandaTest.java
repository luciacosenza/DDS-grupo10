package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.DonacionViandaCreator;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Vianda;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaColaboradorCreator;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class DonacionViandaTest {
    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una DonacionVianda")
    public void CargaDonacionViandaTest() throws InterruptedException {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();

        LocalDateTime fechaAperturaH1   = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDateTime fechaCaducidadV   = LocalDateTime.parse("2025-01-01T00:00:00");
        
        HeladeraActiva heladera1 = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, fechaAperturaH1, -20f, 5f);
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
        colaboradorHumano.confirmarContribucion(donacionVianda, LocalDateTime.now());

        Assertions.assertTrue(Sistema.getAccionesHeladeras().size() == 2 && colaboradorHumano.getContribuciones().size() == 1 && colaboradorHumano.getContribuciones().getFirst().getClass() == DonacionVianda.class);
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer hacer una DonacionVianda sin tener domicilio registrado")
    public void IllegalArgumentValidarIdentidadDDonacionViandaTest() throws InterruptedException { 
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorHumano.darDeAlta();

        LocalDateTime fechaAperturaH   = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDateTime fechaCaducidadV   = LocalDateTime.parse("2025-01-01T00:00:00");
        
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "1049", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 2, fechaAperturaH, -20f, 5f);
        Vianda vianda = new Vianda("ComidaPrueba" , colaboradorHumano, fechaCaducidadV, null, 0, 0, false);
        
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorHumano.colaborar(donacionViandaCreator, LocalDateTime.now(), vianda, heladera);
        });

        Assertions.assertEquals(I18n.getMessage("contribucion.DonacionVianda.validarIdentidad_exception"), exception.getMessage());
    }
}

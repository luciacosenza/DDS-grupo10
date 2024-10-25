package com.tp_anual.proyecto_heladeras_solidarias.model.contribucion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.contribucion.RegistroDePersonaEnSituacionVulnerableCreator;
import com.tp_anual.proyecto_heladeras_solidarias.service.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerableService;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.TarjetaPersonaEnSituacionVulnerableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegistroDePersonaEnSituacionVulnerableTest {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    PersonaEnSituacionVulnerableService personaEnSituacionVulnerableService;

    @Autowired
    TarjetaPersonaEnSituacionVulnerableService tarjetaPersonaEnSituacionVulnerableService;
    
    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una RegistroDePersonaEnSituacionVulnerable")
    public void CargaRegistroDePersonaEnSituacionVulnerableTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDate.parse("2003-01-01")), new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "1064", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        Long colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable(new PersonaFisica("NombrePruebaPESV", "ApellidoPruebaPESV", new Documento(TipoDocumento.DNI, "40123450", Sexo.MASCULINO), LocalDate.parse("2003-01-01")), new Ubicacion(-34.63927052902741, -58.50938609197106, "Avenida Rivadavia 10357", "1048", "Ciudad Autónoma de Buenos Aires", "Argentina"), LocalDateTime.now(), 2, true);
        Long personaEnSituacionVulnerableId = personaEnSituacionVulnerableService.guardarPersonaEnSituacionVulnerable(personaEnSituacionVulnerable).getId();
        TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable = tarjetaPersonaEnSituacionVulnerableService.crearTarjeta(personaEnSituacionVulnerableId);

        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();
        RegistroDePersonaEnSituacionVulnerable registroPersonaEnSituacionVulnerable = (RegistroDePersonaEnSituacionVulnerable) colaboradorService.colaborar(colaboradorHumanoId, registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), tarjetaPersonaEnSituacionVulnerable);

        personaEnSituacionVulnerableService.agregarTarjeta(personaEnSituacionVulnerableId, tarjetaPersonaEnSituacionVulnerable);

        colaboradorService.confirmarContribucion(colaboradorHumanoId, registroPersonaEnSituacionVulnerable, LocalDateTime.now());

        Assertions.assertTrue(personaEnSituacionVulnerableService.obtenerPersonasEnSituacionVulnerable().size() == 1 && colaboradorHumano.getContribuciones().size() == 1 && colaboradorHumano.getContribuciones().getFirst().getClass() == RegistroDePersonaEnSituacionVulnerable.class);
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException al querer hacer una RegistroDePersonaEnSituacionVulnerable sin tener domicilio registrado")
    public void IllegalArgumentValidarIdentidadDRegistroDePersonaEnSituacionVulnerableTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(null, new PersonaFisica("NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDate.parse("2003-01-01")), null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        Long colaboradorHumanoId = colaboradorService.guardarColaborador(colaboradorHumano).getId();

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable(new PersonaFisica("NombrePruebaPESV", "ApellidoPruebaPESV", new Documento(TipoDocumento.DNI, "40123450", Sexo.MASCULINO), LocalDate.parse("2003-01-01")), new Ubicacion(-34.63927052902741, -58.50938609197106, "Avenida Rivadavia 10357", "1048", "Ciudad Autónoma de Buenos Aires", "Argentina"), LocalDateTime.now(), 2, true);
        Long personaEnSituacionVulnerableId = personaEnSituacionVulnerableService.guardarPersonaEnSituacionVulnerable(personaEnSituacionVulnerable).getId();

        TarjetaPersonaEnSituacionVulnerable tarjetaPErsonaEnSituacoinVulnerable = tarjetaPersonaEnSituacionVulnerableService.crearTarjeta(personaEnSituacionVulnerableId);

        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> colaboradorService.colaborar(colaboradorHumanoId, registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), tarjetaPErsonaEnSituacoinVulnerable));

        Assertions.assertEquals(I18n.getMessage("contribucion.RegistroDePersonaEnSituacionVulnerable.validarIdentidad_exception"), exception.getMessage());
    }
}

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
import com.tp_anual_dds.domain.persona_en_situacion_vulnerable.PersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class RegistroDePersonaEnSituacionVulnerableTest {
    
    @Test
    @DisplayName("Testeo la carga y correcto funcionamiento de una RegistroDePersonaEnSituacionVulnerable")
    public void CargaRegistroDePersonaEnSituacionVulnerableTest() { 
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();

        PersonaEnSituacionVulnerable personaEnSituacionVulnerable = new PersonaEnSituacionVulnerable("NombrePruebaPESV", "ApellidoPruebaPESV", new Documento(TipoDocumento.DNI, "40123450", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00"), new Ubicacion(-34.63927052902741, -58.50938609197106, "Avenida Rivadavia 10357", "Ciudad Autónoma de Buenos Aires", "Argentina"), LocalDateTime.now(), 2);
        TarjetaPersonaEnSituacionVulnerable tarjeta = new TarjetaPersonaEnSituacionVulnerable(null);

        RegistroDePersonaEnSituacionVulnerableCreator registroDePersonaEnSituacionVulnerableCreator = new RegistroDePersonaEnSituacionVulnerableCreator();
        RegistroDePersonaEnSituacionVulnerable registroPersonaEnSituacionVulnerable = (RegistroDePersonaEnSituacionVulnerable) colaboradorHumano.colaborar(registroDePersonaEnSituacionVulnerableCreator, LocalDateTime.now(), personaEnSituacionVulnerable, tarjeta);
        tarjeta.setTitular(personaEnSituacionVulnerable);
        personaEnSituacionVulnerable.setTarjeta(tarjeta);
        personaEnSituacionVulnerable.darDeAlta();
        colaboradorHumano.confirmarContribucion(registroPersonaEnSituacionVulnerable);

        Assertions.assertTrue(Sistema.getPersonasEnSituacionVulnerable().size() == 1 && colaboradorHumano.getContribuciones().size() == 1 && colaboradorHumano.getContribuciones().getFirst().getClass() == RegistroDePersonaEnSituacionVulnerable.class);
    }
}

package com.tp_anual.proyecto_heladeras_solidarias.model.contacto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.Sexo;
import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento.TipoDocumento;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

public class EMailTest {

    @Test
    @DisplayName("Testeo el método contactar() de EMail")
    public void EMailContactarTest() {
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new PersonaFisica("Santiago", "Ale", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")), new Ubicacion(-34.638991720104336, -58.51001658862685, "Avenida Rivadavia 10391", "1408", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d);
        colaboradorHumano.darDeAlta();
        EMail eMail = new EMail("salemarino@frba.utn.edu.ar");
        colaboradorHumano.agregarContacto(eMail);

        colaboradorHumano.getMedioDeContacto(EMail.class).contactar("Correo Prueba", "Este es un mensaje de prueba");

        // No sé cómo testear esto con un Assert
    }
}

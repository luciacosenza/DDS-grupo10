package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class MigradorTest {
    @Test
    @DisplayName("Testeo el correcto funcionamiento del Migrador")
    public void CargaMasivaTest() throws Exception {
        ExtraccionDeDatos protocoloExtraccion = new ExtraccionCSV();
        EnvioDeDatos protocoloEnvio = new EnvioNulo();
        
        String rutaCSV = "C:\\Users\\santi\\OneDrive\\Desktop\\Yo\\UTN\\TERCERO\\Diseño de Sistemas\\DDS-grupo10\\proyecto_heladeras_solidarias\\src\\test\\resources\\registro_migracion.csv";

        Migrador.setExtraccionDeDatosStrategy(protocoloExtraccion);
        Migrador.setEnvioDeDatosStrategy(protocoloEnvio);
        Migrador.migrar(rutaCSV);

        ArrayList<Colaborador> colaboradores = Sistema.getColaboradores();

        for (Colaborador colaborador : colaboradores) {
            PersonaFisica personaFisica = (PersonaFisica) colaborador.getPersona();
            System.out.println(personaFisica.getNombre());
        }

        assertEquals(20, colaboradores.size());
    }
}
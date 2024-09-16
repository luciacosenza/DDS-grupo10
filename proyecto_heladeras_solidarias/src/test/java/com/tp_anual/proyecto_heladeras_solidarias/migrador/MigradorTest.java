package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.util.ArrayList;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

public class MigradorTest {
    @Test
    @DisplayName("Testeo el correcto funcionamiento del Migrador")
    public void CargaMasivaTest() throws Exception {
        ExtraccionDeDatos protocoloExtraccion = new ExtraccionCSV();
        EnvioDeDatos protocoloEnvio = new EnvioNulo();
        
        String rutaCSV = "registro_migracion.csv";

        Migrador.setProtocoloExtraccion(protocoloExtraccion);
        Migrador.setProtocoloEnvio(protocoloEnvio);
        Migrador.migrar(rutaCSV);

        ArrayList<Colaborador> colaboradores = Sistema.getColaboradores();

        System.out.println("\n COLABORADORES MIGRADOS \n");
        for (Colaborador colaborador : colaboradores) {
            colaborador.obtenerDetalles();
            System.out.println("\n");
        }

        assertEquals(20, colaboradores.size());
    }
}
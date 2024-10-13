package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.service.migrador.*;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import org.springframework.beans.factory.annotation.Autowired;

public class MigradorTest {
    Migrador migrador;

    @Test
    @DisplayName("Testeo el correcto funcionamiento del Migrador")
    public void CargaMasivaTest() throws Exception {
        ExtraccionDeDatos protocoloExtraccion = new ExtraccionCSV();
        TransformacionDeDatos transformador = new TransformacionDeDatos();
        EnvioDeDatos protocoloEnvio = new EnvioNulo();

        migrador.setProtocoloExtraccion(protocoloExtraccion);
        migrador.setTransformador(transformador);
        migrador.setProtocoloEnvio(protocoloEnvio);

        String rutaCSV = "registro_migracion.csv";
        migrador.migrar(rutaCSV);

        ArrayList<Colaborador> colaboradores = Sistema.getColaboradores();

        System.out.println("\n COLABORADORES MIGRADOS \n");
        for (Colaborador colaborador : colaboradores) {
            colaborador.obtenerDetalles();
            System.out.println("\n");
        }

        assertEquals(20, colaboradores.size());
    }
}
package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class MigradorTest{
    @Test
    @DisplayName("Testeo el correcto funcionamiento del Migrador")
    public void CargaMasivaTest() throws Exception {
        ExtraccionDeDatos protocoloExtraccion = new ExtraccionCSV();
        EnvioDeDatos protocoloEnvio = new EnvioNulo();
        
        String rutaCSV = "/tp_anual/proyecto_heladeras_solidarias/src/test/resources/registro_migracion.csv";

        Migrador.setExtraccionDeDatosStrategy(protocoloExtraccion);
        Migrador.setEnvioDeDatosStrategy(protocoloEnvio);
        Migrador.migrar(rutaCSV);
    }
}
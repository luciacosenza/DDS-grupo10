package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import com.tp_anual.proyecto_heladeras_solidarias.service.migrador.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MigradorTest {

    @Autowired
    private Migrador migrador;

    @Autowired
    private ColaboradorService colaboradorService;

    @Test
    @DisplayName("Testeo el correcto funcionamiento del Migrador")
    public void CargaMasivaTest() throws Exception {

        String rutaCSV = "registro_migracion.csv";
        ArrayList<ColaboradorHumano> colaboradores = migrador.migrar(rutaCSV, false);

        System.out.println("\n COLABORADORES MIGRADOS \n");
        for (Colaborador colaborador : colaboradores) {
            colaborador.obtenerDetalles();
            System.out.println("\n");
        }

        assertEquals(20, colaboradores.size()); // TODO: Hacerlo que compare con los que están en el Sistema
    }
}
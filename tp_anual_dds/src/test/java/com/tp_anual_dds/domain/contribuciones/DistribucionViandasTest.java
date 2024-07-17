package com.tp_anual_dds.domain.contribuciones;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.contribuciones.DistribucionViandas.MotivoDistribucion;
import com.tp_anual_dds.domain.heladera.Heladera;

public class DistribucionViandasTest{

    private Heladera origen;
    private Heladera destino;
    private ColaboradorHumano colaborador;

    @BeforeEach
    void setUp(){
        colaborador = new ColaboradorHumano(null, new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", null, null); 
        origen = new Heladera("Heladera Origen", null, new ArrayList<>(), 10, LocalDateTime.now(), 2.0f, 8.0f);
        destino = new Heladera("Heladera Destino", null, new ArrayList<>(), 10, LocalDateTime.now(), 2.0f, 8.0f);
    }
    @Test
    @DisplayName("Testeo método calcularPuntos")
    public void testCalcularPuntos(){
        Integer cantidadViandasAMover = 5;
        MotivoDistribucion motivo = MotivoDistribucion.DESPERFECTO_EN_LA_HELADERA;

        DistribucionViandas distribucion = new DistribucionViandas(colaborador, LocalDateTime.now(), origen, destino, cantidadViandasAMover, motivo);
        distribucion.accionar();  
        
        Double puntosEsperados = cantidadViandasAMover.doubleValue();
        assertEquals(puntosEsperados, colaborador.getPuntos());
    }

	
	}


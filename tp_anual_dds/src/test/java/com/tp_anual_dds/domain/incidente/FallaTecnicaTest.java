package com.tp_anual_dds.domain.incidente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.documento.Documento.Sexo;
import com.tp_anual_dds.domain.documento.Documento.TipoDocumento;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;
import com.tp_anual_dds.sistema.Sistema;

public class FallaTecnicaTest {
    @Test
    @DisplayName("Testeo la carga de Falla Técnica")
    public void CargaFallaTecnicaTest() {
        HeladeraActiva heladera = new HeladeraActiva("HeladeraPrueba", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, LocalDateTime.parse("2024-01-01T00:00:00"), -20f, 5f);
        heladera.darDeAlta();
        
        ColaboradorHumano colaboradorHumano = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        colaboradorHumano.darDeAlta();
        
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaboradorHumano.reportarFallaTecnica(heladera, "La Heladera hace un ruido raro y muy fuerte", "FotoPrueba");
        });
        

        ArrayList<Incidente> fallasTecnicasDelSistema = Sistema.getIncidentes().stream()
            .filter(incidente -> incidente instanceof FallaTecnica)
            .map(fallaTecnica -> (FallaTecnica) fallaTecnica)
            .collect(Collectors.toCollection(ArrayList::new));

        Assertions.assertEquals("No hay ningún técnico que cubra la heladera HeladeraPrueba", exception.getMessage());  // Verificamos que se lanza la Exception del Técnico
        Assertions.assertTrue(heladera.getEstado() == false && fallasTecnicasDelSistema.size() == 1);
    }

}

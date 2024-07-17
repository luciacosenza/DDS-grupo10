package com.tp_anual_dds.domain.colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.contribuciones.DistribucionViandas;
import com.tp_anual_dds.domain.contribuciones.DistribucionViandasCreator;
import com.tp_anual_dds.domain.contribuciones.DonacionVianda;
import com.tp_anual_dds.domain.contribuciones.DonacionViandaCreator;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.documento.Documento.Sexo;
import com.tp_anual_dds.domain.documento.Documento.TipoDocumento;
import com.tp_anual_dds.domain.heladera.Heladera;
import com.tp_anual_dds.domain.heladera.Vianda;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class Colaborador2Test {
    @Test
    @DisplayName("Testeo la correcta creación de la Contribucion y que se agregue a las contribuciones del Colaborador")
    public void ColaborarTest() {
        ColaboradorHumano colaborador = new ColaboradorHumano(new Ubicacion(-34.6083, -58.3709, "Balcarce 78", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", new Documento(TipoDocumento.DNI, "40123456", Sexo.MASCULINO), LocalDateTime.parse("2003-01-01T00:00:00")); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        
        LocalDateTime fechaAperturaH1   = LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDateTime fechaAperturaH2   = LocalDateTime.parse("2024-02-01T00:00:00");
        LocalDateTime fechaCaducidadV   = LocalDateTime.parse("2025-01-01T00:00:00");
        LocalDateTime fechaDonacion     = LocalDateTime.parse("2024-07-15T00:00:00");
        LocalDateTime fechaDistribucion = LocalDateTime.parse("2024-07-15T00:30:00");
        
        Heladera heladera1 = new Heladera("HeladeraPrueba1", new Ubicacion(-34.601978, -58.383865, "Tucumán 1171", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, fechaAperturaH1, -20f, 5f);
        Heladera heladera2 = new Heladera("HeladeraPrueba2", new Ubicacion(-34.6092, -58.3842, "Avenida de Mayo 1370", "Ciudad Autónoma de Buenos Aires", "Argentina"), new ArrayList<>(), 20, fechaAperturaH2, -20f, 5f);
        Vianda vianda = new Vianda("ComidaPrueba", null , colaborador, fechaCaducidadV, fechaDonacion, 0, 0, false);
        
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        colaborador.colaborar(donacionViandaCreator, fechaDonacion, vianda, heladera1);

        DistribucionViandasCreator distribucionViandasCreator = new DistribucionViandasCreator();
        colaborador.colaborar(distribucionViandasCreator, fechaDistribucion, heladera1, heladera2, 5, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);

        DonacionVianda donacionVianda = new DonacionVianda(colaborador, fechaDonacion, vianda, heladera1);
        DistribucionViandas distribucionViandas = new DistribucionViandas(colaborador, fechaDistribucion, heladera1, heladera2, 5, DistribucionViandas.MotivoDistribucion.FALTA_DE_VIANDAS_EN_DESTINO);

        ArrayList<Contribucion> contribuciones = new ArrayList<>();
        contribuciones.add(donacionVianda);
        contribuciones.add(distribucionViandas);

        Assertions.assertThat(colaborador.getContribuciones())
        .usingRecursiveFieldByFieldElementComparator()
        .containsExactlyInAnyOrderElementsOf(contribuciones);
    }
}

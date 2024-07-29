package com.tp_anual_dds.domain.ubicador;

import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual_dds.domain.area.Area;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.domain.tecnico.Tecnico;
import com.tp_anual_dds.sistema.Sistema;

public class UbicadorTecnico {

    public UbicadorTecnico() {}

    public Tecnico obtenerTecnicoCercanoA(Incidente incidente) {
        //Obtengo la Ubicación de la Heladera en cuestión
        HeladeraActiva heladera = incidente.getHeladera();
        Pair<Double, Double> ubicacionHeladera = Pair.of(heladera.getUbicacion().getLatitud(), heladera.getUbicacion().getLongitud());
        
        // Obtengo la lista de Técnicos que tengan a la Heladera en su área de cobertura
        ArrayList<Tecnico> tecnicos = Sistema.getTecnicos().stream()
            .filter(tecnico -> tecnico.getAreaDeCobertura()
            .estaDentro(ubicacionHeladera.getLeft(), ubicacionHeladera.getRight()))
            .collect(Collectors.toCollection(ArrayList::new));
        
        // Obtengo las Ubicaciones de los Técnicos
        ArrayList<Pair<Double, Double>> puntosMedios = tecnicos.stream()
            .map(tecnico -> tecnico.ubicacionAprox())
            .collect(Collectors.toCollection(ArrayList::new));
        
        // Obtengo la distancia minima entre la Heladera y un Técnico
        ArrayList<Double> distancias = puntosMedios.stream()
            .map(puntoMedio -> Area.distanciaEntre2Puntos(puntoMedio, ubicacionHeladera))
            .collect(Collectors.toCollection(ArrayList::new));
        
            OptionalDouble distanciaMinima = distancias.stream().mapToDouble(Double::doubleValue).min();
        
        if (!distanciaMinima.isPresent())
            throw new IllegalArgumentException("No hay ningún técnico que cubra la heladera " + heladera.getNombre());

        // Si la mónada "Optional distanciaMinima" no esté vacía, la desarmo. Sólo puede llegar vacía si no hay Técnicos cuya área de cobertura cubra la Heladera
        Double distancia = distanciaMinima.getAsDouble();
        Integer indexTecnico = distancias.indexOf(distancia);
        
        return tecnicos.get(indexTecnico);
    }
}
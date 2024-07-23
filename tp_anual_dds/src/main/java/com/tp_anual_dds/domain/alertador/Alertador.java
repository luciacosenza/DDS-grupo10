package com.tp_anual_dds.domain.alertador;

import java.util.ArrayList;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import com.tp_anual_dds.domain.area.Area;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.incidente.Incidente;
import com.tp_anual_dds.domain.tecnico.Tecnico;
import com.tp_anual_dds.sistema.Sistema;

public class Alertador {

    public Tecnico obtenerTecnicoCercanoA(Incidente incidente) {
        //Obtengo la Ubicacion de la Heladera en cuestion
        HeladeraActiva heladera = incidente.getHeladera();
        Pair<Double, Double> ubicacionHeladera = Pair.of(heladera.getUbicacion().getLatitud(), heladera.getUbicacion().getLongitud());
        
        // Obtengo la lista de Tecnicos que tengan a la Heladera en su areaDeCobertura
        ArrayList<Tecnico> tecnicos = Sistema.getTecnicos().stream().filter(tecnico -> tecnico.getAreaDeCobertura().estaDentro(ubicacionHeladera.getLeft(), ubicacionHeladera.getRight())).collect(Collectors.toCollection(ArrayList::new));
        
        // Obtengo las Ubicaciones de los Tecnicos
        ArrayList<Pair<Double, Double>> puntosMedios = tecnicos.stream().map(tecnico -> tecnico.ubicacionAprox()).collect(Collectors.toCollection(ArrayList::new));
        
        // Obtengo la distancia minima entre la Heladera y un Tecnico
        ArrayList<Double> distancias = puntosMedios.stream().map(puntoMedio -> Area.distanciaEntre2Puntos(puntoMedio, ubicacionHeladera)).collect(Collectors.toCollection(ArrayList::new));
        OptionalDouble distanciaMinima = distancias.stream().mapToDouble(Double::doubleValue).min();
        
        if(distanciaMinima.isPresent()) { // Si la monada no esta vacia, la desarmo. Solo puede llegar vacia si no hay Tecnicos que en su areaDeCobertura entre la Heladera
            Double distancia = distanciaMinima.getAsDouble();
            
            Integer indexTecnico = distancias.indexOf(distancia);
            
            return tecnicos.get(indexTecnico);
        }
        else {
            throw new IllegalArgumentException("No hay ningún Técnico que cubra esta heladera");
        }
    }

    public void alertarDe(Incidente incidente){
        Tecnico tecnicoAAlertar = obtenerTecnicoCercanoA(incidente);
        tecnicoAAlertar.agregarAPendientes(incidente);
        
        HeladeraActiva heladera = incidente.getHeladera();
        tecnicoAAlertar.getMedioDeContacto().contactar("Ocurrió un Incidente en la Heladera " + heladera.getNombre(), ". Necesitamos que atiendas lo antes posible el incidente de tipo " + incidente.getClass().getName() + " en la Heladera " + heladera.getNombre() + ".");
    }
    
}
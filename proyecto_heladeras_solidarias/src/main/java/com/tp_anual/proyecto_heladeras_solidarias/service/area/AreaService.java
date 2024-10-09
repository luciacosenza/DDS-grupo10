package com.tp_anual.proyecto_heladeras_solidarias.service.area;

import com.tp_anual.proyecto_heladeras_solidarias.model.area.Area;
import com.tp_anual.proyecto_heladeras_solidarias.repository.area.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class AreaService {
    private final AreaRepository areaRepository;

    public AreaService(AreaRepository vAreaRepository) {
        areaRepository = vAreaRepository;
    }

    public Area obtenerArea(Long areaId) {
        return areaRepository.findById(areaId).orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));
    }

    private Boolean entraEnX(Long areaId, Double x) {
        Area area = obtenerArea(areaId);

        return x > area.getX1() && x < area.getX2();
    }

    private Boolean entraEnY(Long areaId, Double y) {
        Area area = obtenerArea(areaId);

        return y > area.getY1() && y < area.getY2();
    }

    public Boolean estaDentro(Long areaId, Double x, Double y) {
        return entraEnX(areaId, x) && entraEnY(areaId, y);
    }

    public Pair<Double, Double> puntoMedio(Long areaId) {
        Area area = obtenerArea(areaId);

        Double xM = (area.getX2() - area.getX1()) / 2;
        Double yM = (area.getY2() - area.getY1()) / 2;

        return Pair.of(xM, yM);
    }

    // Este método es estático dado que es propio de un Área, pero no debería hacer falta crear una instancia de este tipo para usarlo
    public static Double distanciaEntre2Puntos(Pair<Double, Double> tupla1, Pair<Double, Double> tupla2) {
        Double xT1 = tupla1.getLeft();
        Double yT1 = tupla1.getRight();
        Double xT2 = tupla2.getLeft();
        Double yT2 = tupla2.getRight();

        return Math.sqrt(Math.pow(xT1 - xT2, 2) + Math.pow(yT1 - yT2, 2));
    }
}


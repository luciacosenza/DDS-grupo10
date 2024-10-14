package com.tp_anual.proyecto_heladeras_solidarias.repository.heladera;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HeladeraRepository extends JpaRepository<Heladera, Long> {

    @Query(value =  "SELECT h.* FROM heladera h WHERE h.id != :heladeraId AND h.estado = true " +
                    "ORDER BY ST_Distance(ST_MakePoint(:lng, :lat)::geography, " +
                    "ST_MakePoint(h.ubicacion.longitud, h.ubicacion.latitud)::geography) " +
                    "LIMIT :cantidad", nativeQuery = true)
    List<Heladera> findNearbyHeladeras(@Param("heladeraId") Long heladeraId,
                                       @Param("lat") Double lat,
                                       @Param("lng") Double lng,
                                       @Param("cantidad") Integer cantidad);
}
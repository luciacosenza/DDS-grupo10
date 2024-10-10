package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.AperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.acciones_en_heladera.SolicitudAperturaColaborador.MotivoSolicitud;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.service.tarjeta.GeneradorCodigo;
import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
public class TarjetaColaboradorActiva extends TarjetaColaborador {
    
    public TarjetaColaboradorActiva(String vCodigo, ColaboradorHumano vTitular) {
        super(vCodigo, vTitular, new ArrayList<>());
    }

    @Override
    public Boolean puedeUsar() {
        return true; // Se puede definir alguna lógica específica si es necesario
    }

    @Override
    public void agregarPermiso(PermisoApertura permiso) {
        permisos.add(permiso);
    }

    @Override
    public void eliminarPermiso(PermisoApertura permiso) {
        permisos.remove(permiso);
    }
}

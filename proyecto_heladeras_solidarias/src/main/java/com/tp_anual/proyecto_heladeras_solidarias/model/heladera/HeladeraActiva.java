package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta.TipoAlerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.FallaTecnica;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.service.suscripcion.GestorDeSuscripciones;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.Suscripcion.CondicionSuscripcion;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMin;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionViandasMax;
import com.tp_anual.proyecto_heladeras_solidarias.model.suscripcion.SuscripcionDesperfecto;

import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.service.heladera.GestorDeAperturas;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;
import jakarta.persistence.*;
import lombok.extern.java.Log;

@Entity
@Log
public class HeladeraActiva extends Heladera {
    
    public HeladeraActiva(String vNombre, Ubicacion vUbicacion, ArrayList<Vianda> vViandas, Integer vCapacidad, LocalDateTime vFechaApertura, Float vTempMin, Float vTempMax) {
        super(vNombre, vUbicacion, vCapacidad, vTempMin, vTempMax, vViandas, 0f, vFechaApertura, true);
    }

    @Override
    public void darDeAlta() {
        Sistema.agregarHeladera(this);
    }

    @Override
    public void darDeBaja() {
        Sistema.eliminarHeladera(this);
    }

    @Override
    public Integer viandasActuales() {
        return viandas.size();
    }

    @Override
    public void marcarComoInactiva() {
        setEstado(false);
    }
}
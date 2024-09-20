package com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.permisos_de_apertura;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraNula;


import lombok.extern.java.Log;
import jakarta.persistence.*;

@Entity
@Log
public class PermisoAperturaActivo extends PermisoApertura {
    
    public PermisoAperturaActivo() {
        heladeraPermitida = new HeladeraNula();
        fechaOtorgamiento = null;
    }

    @Override
    public HeladeraActiva getHeladeraPermitida() {
        return (HeladeraActiva) heladeraPermitida;
    }

    @Override
    public void actualizarFechaOtorgamiento(){
        fechaOtorgamiento = LocalDateTime.now();
    }

    @Override
    public Boolean esHeladeraPermitida(HeladeraActiva heladera) {
        return heladera == heladeraPermitida;
    }

    // Este método es llamado cuando se revocan los Permisos de Apertura sobre una Heladera
    @Override
    public void resetHeladeraPermitida() {
        heladeraPermitida = new HeladeraNula();
    }
}

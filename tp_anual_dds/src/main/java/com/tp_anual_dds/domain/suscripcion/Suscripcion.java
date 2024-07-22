package com.tp_anual_dds.domain.suscripcion;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.sistema.Sistema;

public class Suscripcion <T extends MedioDeContacto> {
    private final ColaboradorHumano colaborador;
    private final HeladeraActiva heladera;
    private Integer viandasDisponiblesMin;
    private Integer viandasParaLlenarMax;
    private Boolean notificarDesperfecto;
    private Class<T> medioDeContactoElegido;

    public Suscripcion(ColaboradorHumano vColaborador, HeladeraActiva vHeladera, Integer vViandasDisponiblesMin, Integer vViandasParaLlenarMax, Boolean vNotificarDesperfecto, Class<T> vMedioDeContactoElegido) {
        colaborador = vColaborador;
        heladera = vHeladera;
        viandasDisponiblesMin = vViandasDisponiblesMin;
        viandasParaLlenarMax = vViandasParaLlenarMax;
        notificarDesperfecto = vNotificarDesperfecto;
        medioDeContactoElegido = vMedioDeContactoElegido;
    }

    public ColaboradorHumano getColaborador() {
        return colaborador;
    }

    public HeladeraActiva getHeladera(){
        return heladera;
    }

    public Integer getViandasDisponiblesMin() {
        return viandasDisponiblesMin;
    }
    
    public Integer getViandasParaLlenarMax() {
        return viandasParaLlenarMax;
    }
    
    public Boolean getNotificarDesperfecto() {
        return notificarDesperfecto;
    }
    
    public Class<T> getMedioDeContactoElegido() {
        return medioDeContactoElegido;
    }

    public void darDeAlta() {
        Sistema.getGestorSuscripciones().agregarSuscripcion(this);
    }

    public void darDeBaja() {
        Sistema.getGestorSuscripciones().eliminarSuscripcion(this);
    }
}
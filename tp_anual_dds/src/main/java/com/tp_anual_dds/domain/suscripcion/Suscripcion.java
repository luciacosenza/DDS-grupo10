package com.tp_anual_dds.domain.suscripcion;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.sistema.Sistema;

public class Suscripcion {
    private final ColaboradorHumano colaborador;
    private final HeladeraActiva heladera;
    private Integer viandasDisponiblesMin;
    private Integer viandasParaLlenarMax;
    private Boolean notificarDesperfecto;
    private MedioDeContacto medioDeContactoElegido; // El Colaborador elige por qué Medio de Contacto ser notificado sobre cuestiones de la Suscripción

    public enum CondicionSuscripcion {
        VIANDAS_MIN,
        VIANDAS_MAX,
        DESPERFECTO
    }

    public Suscripcion(ColaboradorHumano vColaborador, HeladeraActiva vHeladera, Integer vViandasDisponiblesMin, Integer vViandasParaLlenarMax, Boolean vNotificarDesperfecto, MedioDeContacto vMedioDeContactoElegido) {
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

    public HeladeraActiva getHeladera() {
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
    
    public MedioDeContacto getMedioDeContactoElegido() {
        return medioDeContactoElegido;
    }

    public void setViandasDisponiblesMin(Integer vViandasDisponiblesMin) {
        viandasDisponiblesMin = vViandasDisponiblesMin;
    }

    public void setViandasParaLlenarMax(Integer vViandasParaLlenarMax) {
        viandasParaLlenarMax = vViandasParaLlenarMax;
    }

    public void setNotificarDesperfecto(Boolean vNotificarDesperfecto) {
        notificarDesperfecto = vNotificarDesperfecto;
    }
    
    public void darDeAlta() {
        Sistema.getGestorDeSuscripciones().agregarSuscripcion(this);
    }

    public void darDeBaja() {
        Sistema.getGestorDeSuscripciones().eliminarSuscripcion(this);
    }
}
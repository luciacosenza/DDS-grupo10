package com.tp_anual_dds.domain.colaborador;

import java.util.ArrayList;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class ColaboradorJuridico extends Colaborador {
    private String razonSocial;
    private TipoColaboradorJuridico tipo;
    private String rubro;

    public enum TipoColaboradorJuridico {
        GUBERNAMENTAL,
        ONG,
        EMPRESA,
        INSTITUCION
    }

    public ColaboradorJuridico(ArrayList<MedioDeContacto> vMediosDeContacto, Ubicacion vDomicilio, ArrayList<Contribucion> vContribuciones, Double vPuntos, String vRazonSocial, String vRubro, TipoColaboradorJuridico vTipo) {
        mediosDeContacto = vMediosDeContacto;
        domicilio = vDomicilio;
        contribuciones = vContribuciones;
        puntos = vPuntos;
        razonSocial = vRazonSocial;
        rubro = vRubro;
        tipo = vTipo;
    }
}

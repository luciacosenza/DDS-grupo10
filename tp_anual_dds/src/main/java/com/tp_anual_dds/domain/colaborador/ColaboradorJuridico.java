package com.tp_anual_dds.domain.colaborador;

import java.util.ArrayList;
import java.util.HashSet;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.contribuciones.CargaOfertaCreator;
import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.contribuciones.DonacionDineroCreator;
import com.tp_anual_dds.domain.contribuciones.HacerseCargoDeHeladeraCreator;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class ColaboradorJuridico extends Colaborador {
    private TipoColaboradorJuridico tipo;
    private String rubro;

    public enum TipoColaboradorJuridico {
        GUBERNAMENTAL,
        ONG,
        EMPRESA,
        INSTITUCION
    }

    public ColaboradorJuridico(ArrayList<MedioDeContacto> vMediosDeContacto, Ubicacion vDomicilio, ArrayList<Contribucion> vContribuciones, Double vPuntos, String vRazonSocial, String vRubro, TipoColaboradorJuridico vTipo) {
        persona = new PersonaJuridica(vRazonSocial);
        mediosDeContacto = vMediosDeContacto;
        domicilio = vDomicilio;
        contribuciones = vContribuciones;

        creatorsPermitidos = new HashSet<>();
        creatorsPermitidos.add(CargaOfertaCreator.class);
        creatorsPermitidos.add(DonacionDineroCreator.class);
        creatorsPermitidos.add(HacerseCargoDeHeladeraCreator.class);

        puntos = vPuntos;
        rubro = vRubro;
        tipo = vTipo;
    }

    @Override
    public PersonaJuridica getPersona() {
        return (PersonaJuridica) persona;
    }
}

package com.tp_anual_dds.domain.colaborador;

import java.util.ArrayList;
import java.util.HashSet;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.contribuciones.CargaOfertaCreator;
import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.contribuciones.DonacionDineroCreator;
import com.tp_anual_dds.domain.contribuciones.HacerseCargoDeHeladeraCreator;
import com.tp_anual_dds.domain.oferta.Oferta;
import com.tp_anual_dds.domain.persona.PersonaJuridica;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class ColaboradorJuridico extends Colaborador {
    public ColaboradorJuridico(Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos, String vRazonSocial, String vRubro, PersonaJuridica.TipoPersonaJuridica vTipo) {
        persona = new PersonaJuridica(vRazonSocial, vTipo, vRubro);
        domicilio = vDomicilio;
        mediosDeContacto = vMediosDeContacto;
        contribuciones = vContribuciones;
        beneficiosAdquiridos = vBeneficiosAdquiridos;

        creatorsPermitidos = new HashSet<>();
        creatorsPermitidos.add(CargaOfertaCreator.class);
        creatorsPermitidos.add(DonacionDineroCreator.class);
        creatorsPermitidos.add(HacerseCargoDeHeladeraCreator.class);

        puntos = vPuntos;
    }

    @Override
    public PersonaJuridica getPersona() {
        return (PersonaJuridica) persona;
    }
}

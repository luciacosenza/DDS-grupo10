package com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador;

import java.util.ArrayList;
import java.util.HashSet;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.CargaOfertaCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.DonacionDineroCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.HacerseCargoDeHeladeraCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;

public class ColaboradorJuridico extends Colaborador {
    public ColaboradorJuridico(Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos, String vRazonSocial, String vRubro, PersonaJuridica.TipoPersonaJuridica vTipo) {
        persona = new PersonaJuridica(vRazonSocial, vTipo, vRubro);
        domicilio = vDomicilio;
        mediosDeContacto = vMediosDeContacto;
        contribuciones = vContribuciones;
        contribucionesPendientes = new ArrayList<>();

        creatorsPermitidos = new HashSet<>();
        creatorsPermitidos.add(CargaOfertaCreator.class);
        creatorsPermitidos.add(DonacionDineroCreator.class);
        creatorsPermitidos.add(HacerseCargoDeHeladeraCreator.class);

        beneficiosAdquiridos = vBeneficiosAdquiridos;
        puntos = vPuntos;
    }

    @Override
    public PersonaJuridica getPersona() {
        return (PersonaJuridica) persona;
    }
}

package com.tp_anual.proyecto_heladeras_solidarias.model.colaborador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@DiscriminatorValue("Juridico")
@Log
@Getter
@Setter
public class ColaboradorJuridico extends Colaborador {

    public ColaboradorJuridico(PersonaJuridica vPersona, Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos) {
        super(vPersona, vDomicilio, vMediosDeContacto, vContribuciones, vBeneficiosAdquiridos, vPuntos);
        puntos = vPuntos;
    }

    @Override
    public PersonaJuridica getPersona() {
        return (PersonaJuridica) persona;
    }
}
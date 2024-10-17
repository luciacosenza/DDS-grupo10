package com.tp_anual.proyecto_heladeras_solidarias.model.colaborador;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaJuridica;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@DiscriminatorValue("Juridico")
@Log
@Getter
public class ColaboradorJuridico extends Colaborador {

    public ColaboradorJuridico() {
        super();
    }

    public ColaboradorJuridico(User vUsuario, PersonaJuridica vPersona, Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos) {
        super(vUsuario, vPersona, vDomicilio, vMediosDeContacto, vContribuciones, vBeneficiosAdquiridos, vPuntos);
        puntos = vPuntos;
    }

    // Todos los getters de atributos de persona fueron creados por lo que suponía referirse directo a la persona

    public PersonaJuridica getPersonaJuridica() {
        return (PersonaJuridica) persona;
    }

    public String getRazonSocial() {
        return getPersonaJuridica().getRazonSocial();
    }

    public PersonaJuridica.TipoPersonaJuridica getTipoPersonaJuridica() {
        return getPersonaJuridica().getTipo();
    }

    public String getRubro() {
        return getPersonaJuridica().getRubro();
    }

    @Override
    public String getNombre(Integer n) {
        return persona.getNombre(n);
    }
}
package com.tp_anual.proyecto_heladeras_solidarias.model.tecnico;

import java.util.ArrayList;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.User;

import com.tp_anual.proyecto_heladeras_solidarias.model.area.Area;
import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Incidente;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;

@Entity
@Log
@Getter
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario")
    protected User usuario; // final

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "persona")
    private PersonaFisica persona;  // final

    @Pattern(regexp = "^^\\d{2}\\d{8}\\d{1}$")
    private String cuil;    // final

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "medio_de_contacto")
    @Setter
    private MedioDeContacto medioDeContacto;    // TODO: Puede ser plural en un futuro

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "area_de_cobertura")
    @Setter
    private Area areaDeCobertura;

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "tecnico")
    private ArrayList<Incidente> pendientes = new ArrayList<>();    // final

    public Tecnico() {}

    public Tecnico(User vUsuario, PersonaFisica vPersona, String vCuil, MedioDeContacto vMedioDeContacto, Area vAreaDeCobertura) {
        usuario = vUsuario;
        persona = vPersona;
        cuil = vCuil;
        medioDeContacto = vMedioDeContacto;
        areaDeCobertura = vAreaDeCobertura;
    }

    public void agregarAPendientes(Incidente incidente) {
        pendientes.add(incidente);
    }
}

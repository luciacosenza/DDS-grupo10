package com.tp_anual.proyecto_heladeras_solidarias.domain.persona_en_situacion_vulnerable;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.domain.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.domain.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;

@Entity
@Log
@Getter
@Setter
public class PersonaEnSituacionVulnerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "persona_id")
    private final PersonaFisica persona;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ubicacion_id")
    private Ubicacion domicilio;

    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime fechaRegistro;

    private Integer menoresACargo;

    @OneToOne(mappedBy = "titular", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private TarjetaPersonaEnSituacionVulnerable tarjeta;

    public PersonaEnSituacionVulnerable(String vNombre, String vApellido, Documento vDocumento, LocalDateTime vFechaNacimiento, Ubicacion vDomicilio, LocalDateTime vFechaRegistro, Integer vMenoresACargo) {
        persona = new PersonaFisica(vNombre, vApellido, vDocumento, vFechaNacimiento);
        domicilio = vDomicilio;
        fechaRegistro = vFechaRegistro;
        menoresACargo = vMenoresACargo;
    }

    public Boolean poseeMenoresACargo() {
        return menoresACargo > 0;
    }

    public void darDeAlta() {
        Sistema.agregarPersonaEnSituacionVulnerable(this);
    }

    public void darDeBaja() {
        Sistema.eliminarPersonaEnSituacionVulnerable(this);
    }
}
package com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

@Entity
@Log
@Getter
public class PersonaEnSituacionVulnerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "persona")
    private PersonaFisica persona;  // final

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "domicilio")
    @Setter
    private Ubicacion domicilio;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;  // final

    @Min(value = 0)
    @Setter
    private Integer menoresACargo;

    private Boolean poseeDomicilio;

    @OneToOne(mappedBy = "titular", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private TarjetaPersonaEnSituacionVulnerable tarjeta;

    public PersonaEnSituacionVulnerable() {}

    public PersonaEnSituacionVulnerable(PersonaFisica vPersona, Ubicacion vDomicilio, LocalDateTime vFechaRegistro, Integer vMenoresACargo, Boolean vPoseeDomicilio) {
        persona = vPersona;
        domicilio = vDomicilio;
        fechaRegistro = vFechaRegistro;
        menoresACargo = vMenoresACargo;
        poseeDomicilio = vPoseeDomicilio;
    }

    public void agregarTarjeta(TarjetaPersonaEnSituacionVulnerable tarjetaPersonaEnSituacionVulnerable) {
        setTarjeta(tarjetaPersonaEnSituacionVulnerable);
    }
}
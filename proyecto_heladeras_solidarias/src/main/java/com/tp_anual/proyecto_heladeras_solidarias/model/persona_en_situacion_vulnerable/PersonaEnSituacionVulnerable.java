package com.tp_anual.proyecto_heladeras_solidarias.model.persona_en_situacion_vulnerable;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.documento.Documento;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.PersonaFisica;
import com.tp_anual.proyecto_heladeras_solidarias.model.tarjeta.TarjetaPersonaEnSituacionVulnerable;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;

@Entity
@Log
@Getter
@Setter
public class PersonaEnSituacionVulnerable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "persona")
    private final PersonaFisica persona;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "domicilio")
    private Ubicacion domicilio;

    @Temporal(TemporalType.TIMESTAMP)
    private final LocalDateTime fechaRegistro;

    @Min(value = 0)
    private Integer menoresACargo;

    @OneToOne(mappedBy = "titular", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private TarjetaPersonaEnSituacionVulnerable tarjeta;

    public PersonaEnSituacionVulnerable(String vNombre, String vApellido, Documento vDocumento, LocalDateTime vFechaNacimiento, Ubicacion vDomicilio, LocalDateTime vFechaRegistro, Integer vMenoresACargo) {
        persona = new PersonaFisica(vNombre, vApellido, vDocumento, vFechaNacimiento);
        domicilio = vDomicilio;
        fechaRegistro = vFechaRegistro;
        menoresACargo = vMenoresACargo;
    }
}
package com.tp_anual.proyecto_heladeras_solidarias.model.colaborador;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.Persona;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.NoUsuario;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_colaborador")
@Log
@Getter
public abstract class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario")
    @Setter
    protected Usuario usuario; // Tiene setter porque, generalmente empieza con usuario en null y para asignárselo se necesita un setter, pero sería final

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "persona")
    protected Persona persona;  // final
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "domicilio")
    @Setter
    protected Ubicacion domicilio;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "colaborador")
    @Setter
    protected List<MedioDeContacto> mediosDeContacto;  // final
    
    @OneToMany(mappedBy = "colaborador", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Contribucion> contribuciones; // final
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "colaborador")
    protected List<Oferta> beneficiosAdquiridos;   // final
    
    @Min(value = 0)
    @Setter
    protected Double puntos;

    protected Colaborador() {}

    protected Colaborador(Usuario vUsuario, Persona vPersona, Ubicacion vDomicilio, List<MedioDeContacto> vMediosDeContacto, List<Contribucion> vContribuciones, List<Oferta> vBeneficiosAdquiridos, Double vPuntos) {
        usuario = vUsuario;
        persona = vPersona;
        domicilio = vDomicilio;
        mediosDeContacto = vMediosDeContacto;
        contribuciones = vContribuciones;
        beneficiosAdquiridos = vBeneficiosAdquiridos;
        puntos = vPuntos;
    }

    public Usuario getUsuario() {
        return usuario != null ? usuario : new NoUsuario();
    }

    public abstract String getNombre(Integer n);

    // Obtenemos el Medio de Contacto correspondiente a la Clase que pasemos como argumento. Con este método, suponemos que el Colaborador no puede tener más de un Medio de Contacto del mismo tipo
    public <T extends MedioDeContacto> T getMedioDeContacto(Class<T> tipoMedioDeContacto) {
        for (MedioDeContacto contacto : mediosDeContacto) {
            if (tipoMedioDeContacto.isInstance(contacto)) 
                return tipoMedioDeContacto.cast(contacto);
        }

        log.log(Level.SEVERE, I18n.getMessage("colaborador.Colaborador.getContacto_err", persona.getNombre(2), tipoMedioDeContacto.getSimpleName()));
        throw new NoSuchElementException(I18n.getMessage("colaborador.Colaborador.getContacto_exception"));
    }

    public void sumarPuntos(Double puntosASumar) {
        puntos += puntosASumar;
    }

    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto) {
        mediosDeContacto.add(medioDeContacto);
    }

    public void agregarContribucion(Contribucion contribucion) {
        contribuciones.add(contribucion);
    }

    public void agregarBeneficio(Oferta oferta) {
        beneficiosAdquiridos.add(oferta);
    }

    public void obtenerDetalles() {
        Integer i;
        
        persona.obtenerDetalles();
        
        if (domicilio.getDireccion() != null && domicilio.getCodigoPostal() != null && domicilio.getCiudad() != null && domicilio.getPais() != null)
            System.out.println(I18n.getMessage("colaborador.Colaborador.obtenerDetalles_out_domicilio", domicilio.getDireccionCompleta()));
        
        if (!mediosDeContacto.isEmpty()) {
            System.out.println(I18n.getMessage("colaborador.Colaborador.obtenerDetalles_out_medios_de_contacto_title"));
            i = 0;
            for (MedioDeContacto medioDeContacto : mediosDeContacto) {
                System.out.println(I18n.getMessage("colaborador.Colaborador.obtenerDetalles_out_medios_de_contacto_body", i, medioDeContacto.getClass().getSimpleName()));
                // TODO: Ver si tendríamos que agregar los datos (número en el caso de teléfono, etc)
                i++;
            }
        }

        if (!contribuciones.isEmpty()) {
            System.out.println(I18n.getMessage("colaborador.Colaborador.obtenerDetalles_out_contribuciones_title"));
            i = 0;
            for (Contribucion contribucion : contribuciones) {
                System.out.println(I18n.getMessage("colaborador.Colaborador.obtenerDetalles_out_contribuciones_body", i, contribucion.getClass().getSimpleName()));
                i++;
        }
        }

        if (!beneficiosAdquiridos.isEmpty()) {
            System.out.println(I18n.getMessage("colaborador.Colaborador.obtenerDetalles_out_beneficios_adquiridos_title"));
            i = 0;

            for (Oferta beneficioAdquirido : beneficiosAdquiridos) {
                System.out.println(I18n.getMessage("colaborador.Colaborador.obtenerDetalles_out_beneficios_adquiridos_body", i, beneficioAdquirido.getNombre()));
                i++;
            }
        }

        if (puntos != null)
            System.out.println(I18n.getMessage("colaborador.Colaborador.obtenerDetalles_out_puntos", puntos));
    }
}
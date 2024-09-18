package com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.domain.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.domain.contribucion.ContribucionCreator;
import com.tp_anual.proyecto_heladeras_solidarias.domain.heladera.HeladeraActiva;
import com.tp_anual.proyecto_heladeras_solidarias.domain.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.domain.persona.Persona;
import com.tp_anual.proyecto_heladeras_solidarias.domain.ubicacion.Ubicacion;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.sistema.Sistema;

import jakarta.persistence.*;
import lombok.extern.java.Log;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_colaborador")
@Log
@Getter
@Setter
public abstract class Colaborador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_id")
    protected final Persona persona;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ubicacion_id")
    protected Ubicacion domicilio;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "medio_de_contacto_id")
    protected final ArrayList<MedioDeContacto> mediosDeContacto;
    
    @OneToMany(mappedBy = "colaborador", fetch = FetchType.EAGER)
    protected final ArrayList<Contribucion> contribuciones;
    
    @OneToMany(mappedBy = "colaborador", fetch = FetchType.EAGER)
    protected final ArrayList<Contribucion> contribucionesPendientes;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "oferta_id")
    protected final ArrayList<Oferta> beneficiosAdquiridos;
    
    @Transient
    protected Set<Class<? extends ContribucionCreator>> creatorsPermitidos;
    
    protected Double puntos;

    protected Colaborador(Persona vPersona, Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos) {
        persona = vPersona;
        domicilio = vDomicilio;
        mediosDeContacto = vMediosDeContacto;
        contribuciones = vContribuciones;
        contribucionesPendientes = new ArrayList<>();
        beneficiosAdquiridos = vBeneficiosAdquiridos;
        puntos = vPuntos;
    }

    public abstract Persona getPersona();

    // Obtenemos el Medio de Contacto correspondiente a la Clase que pasemos como argumento. Con este método, suponemos que el Colaborador no puede tener más de un Medio de Contacto del mismo tipo
    public <T extends MedioDeContacto> T getMedioDeContacto(Class<T> tipoMedioDeContacto) {
        for (MedioDeContacto contacto : mediosDeContacto) {
            if (tipoMedioDeContacto.isInstance(contacto)) 
                return tipoMedioDeContacto.cast(contacto);
        }

        log.log(Level.SEVERE, I18n.getMessage("colaborador.Colaborador.getContacto_err", persona.getNombre(2), tipoMedioDeContacto.getClass().getSimpleName()));
        throw new NoSuchElementException(I18n.getMessage("colaborador.Colaborador.getContacto_exception"));
    }

    public Boolean esCreatorPermitido(Class<? extends ContribucionCreator> creatorClass) {
        return creatorsPermitidos.contains(creatorClass);
    }

    public void sumarPuntos(Double puntosASumar) {
        puntos += puntosASumar;
    }

    public void agregarContacto(MedioDeContacto contacto) {
        mediosDeContacto.add(contacto);
    }

    public void agregarContribucion(Contribucion contribucion) {
        contribuciones.add(contribucion);
    }

    public void agregarContribucionPendiente(Contribucion contribucionPendiente) {
        contribucionesPendientes.add(contribucionPendiente);
    }

    public void agregarBeneficio(Oferta oferta) {
        beneficiosAdquiridos.add(oferta);
    }

    public void eliminarContribucionPendiente(Contribucion contribucion) {
        contribucionesPendientes.remove(contribucion);
    }

    public void darDeAlta() {
        Sistema.agregarColaborador(this);
    }

    public void darDeBaja() {
        Sistema.eliminarColaborador(this);
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

    // Este método equivale a seleccionar una Contribución, no a llevarla a cabo
    public Contribucion colaborar(ContribucionCreator creator, LocalDateTime fechaContribucion /* generalmente LocalDateTime.now() */, Object... args) {
        if (!esCreatorPermitido(creator.getClass())) {
            log.log(Level.SEVERE, I18n.getMessage("colaborador.Colaborador.colaborar_err", creator.getClass().getSimpleName(), persona.getNombre(2), persona.getTipoPersona()));
            throw new IllegalArgumentException(I18n.getMessage("colaborador.Colaborador.colaborar_exception"));
        }

        Contribucion contribucion = creator.crearContribucion(this, fechaContribucion, false , args);
        contribucion.validarIdentidad();
        agregarContribucionPendiente(contribucion);
        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.colaborar_info", contribucion.getClass().getSimpleName(), persona.getNombre(2)));

        return contribucion;
    }

    // Este es el método correspondiente a confirmar la ejecución / llevada a cabo de una Contribución
    public void confirmarContribucion(Contribucion contribucion, LocalDateTime fechaContribucion) {
        // La fecha de contribución generalmente será LocalDateTime.now(), salvo cuando se cargue una Contribución hecha con anterioridad (lo mismo que pasa en "colaborar()")
        contribucion.confirmar(fechaContribucion);
        agregarContribucion(contribucion);
        eliminarContribucionPendiente(contribucion);
        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.confirmarContribucion_info", contribucion.getClass().getSimpleName(), persona.getNombre(2)));
    }

    public void intentarAdquirirBeneficio(Oferta oferta) {
        // Primero chequea tener los puntos suficientes
        oferta.validarPuntos(this);
        oferta.darDeBaja();
        agregarBeneficio(oferta);
        log.log(Level.INFO, I18n.getMessage("colaborador.Colaborador.adquirirBeneficio_info", oferta.getNombre(), persona.getNombre(2)));
    }

    public void reportarFallaTecnica(HeladeraActiva heladera, String descripcion, String foto) {
        heladera.producirFallaTecnica(this, descripcion, foto);
    }
}
package com.tp_anual.proyecto_heladeras_solidarias.model.colaborador;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.contacto.MedioDeContacto;
import com.tp_anual.proyecto_heladeras_solidarias.model.contribucion.Contribucion;
import com.tp_anual.proyecto_heladeras_solidarias.model.oferta.Oferta;
import com.tp_anual.proyecto_heladeras_solidarias.model.persona.Persona;
import com.tp_anual.proyecto_heladeras_solidarias.model.ubicacion.Ubicacion;

import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.NoUsuario;
import com.tp_anual.proyecto_heladeras_solidarias.model.usuario.Usuario;
import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;

@Entity
@NamedNativeQuery(
        name = "Colaborador.findColaboradorParaEMail",
        query = "SELECT * FROM colaborador AS c " +
                "WHERE c.id IN (" +
                "SELECT c0.id FROM colaborador AS c0 " +
                "INNER JOIN medio_de_contacto AS m " +
                "ON c0.id = m.colaborador " +
                "INNER JOIN email AS e " +
                "ON m.id = e.id " +
                "WHERE e.direccion_correo = :email)",
        resultClass = Colaborador.class
)
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

    // Tuvimos que poner este atributo en Colaborador (no en Colaborador Humano) porque, al haber herencia, thymeleaf nos daba problemas para acceder al método (si estaba en Colaborador Humano)
    @Setter
    protected Boolean poseeViandas;

    protected Colaborador() {}

    protected Colaborador(Usuario vUsuario, Persona vPersona, Ubicacion vDomicilio, List<MedioDeContacto> vMediosDeContacto, List<Contribucion> vContribuciones, List<Oferta> vBeneficiosAdquiridos, Double vPuntos) {
        usuario = vUsuario;
        persona = vPersona;
        domicilio = vDomicilio;
        mediosDeContacto = vMediosDeContacto;
        contribuciones = vContribuciones;
        beneficiosAdquiridos = vBeneficiosAdquiridos;
        puntos = vPuntos;
        poseeViandas = false;
    }

    public Usuario getUsuario() {
        return usuario != null ? usuario : new NoUsuario();
    }

    public abstract String getNombre(Integer n);

    // Obtenemos el Medio de Contacto correspondiente a la Clase que pasemos como argumento. Con este método, suponemos que el Colaborador no puede tener más de un Medio de Contacto del mismo tipo
    public <T extends MedioDeContacto> T getMedioDeContacto(Class<T> tipoMedioDeContacto) {
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage = messageSource.getMessage("colaborador.Colaborador.getContacto_err", new Object[] {persona.getNombre(2), tipoMedioDeContacto.getSimpleName()}, Locale.getDefault());
        String exceptionMessage = messageSource.getMessage("colaborador.Colaborador.getContacto_exception", null, Locale.getDefault());

        for (MedioDeContacto contacto : mediosDeContacto) {
            if (tipoMedioDeContacto.isInstance(contacto)) 
                return tipoMedioDeContacto.cast(contacto);
        }

        log.log(Level.SEVERE, logMessage);
        throw new NoSuchElementException(exceptionMessage);
    }

    public <T extends MedioDeContacto> void setMedioDeContacto(Class<T> tipoMedioDeContacto, MedioDeContacto medioDeContacto) {
        MessageSource messageSource = SpringContext.getBean(MessageSource.class);
        String logMessage = messageSource.getMessage("colaborador.Colaborador.getContacto_err", new Object[]{persona.getNombre(2), tipoMedioDeContacto.getSimpleName()}, Locale.getDefault());
        String exceptionMessage = messageSource.getMessage("colaborador.Colaborador.getContacto_exception", null, Locale.getDefault());

        for (int i = 0; i < mediosDeContacto.size(); i++) {
            MedioDeContacto contacto = mediosDeContacto.get(i);
            if (tipoMedioDeContacto.isInstance(contacto)) {
                mediosDeContacto.set(i, medioDeContacto);
                return;
            }
        }

        log.log(Level.SEVERE, logMessage);
        throw new NoSuchElementException(exceptionMessage);
    }

    public void sumarPuntos(Double puntosASumar) {
        puntos += puntosASumar;
    }

    public void restarPuntos(Double puntosARestar) {
        puntos -= puntosARestar;
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

    // Ídem situación del atributo

    public void retirarViandas() {
        setPoseeViandas(true);
    }

    public void ingresarViandas() {
        setPoseeViandas(false);
    }

    public void obtenerDetalles() {
        Integer i;
        
        persona.obtenerDetalles();

        MessageSource messageSource = SpringContext.getBean(MessageSource.class);

        if (domicilio.getDireccion() != null && domicilio.getCodigoPostal() != null && domicilio.getCiudad() != null && domicilio.getPais() != null) {
            String logMessage1 = messageSource.getMessage("colaborador.Colaborador.obtenerDetalles_out_domicilio", new Object[]{domicilio.getDireccionCompleta()}, Locale.getDefault());
            System.out.println(logMessage1);
        }

        if (!mediosDeContacto.isEmpty()) {
            String logMessage2 = messageSource.getMessage("colaborador.Colaborador.obtenerDetalles_out_medios_de_contacto_title", null, Locale.getDefault());
            System.out.println(logMessage2);
            i = 0;
            for (MedioDeContacto medioDeContacto : mediosDeContacto) {
                String logMessage3 = messageSource.getMessage("colaborador.Colaborador.obtenerDetalles_out_medios_de_contacto_body", new Object[]{i, medioDeContacto.getClass().getSimpleName()}, Locale.getDefault());
                System.out.println(logMessage3);
                // TODO: Ver si tendríamos que agregar los datos (número en el caso de teléfono, etc)
                i++;
            }
        }

        if (!contribuciones.isEmpty()) {
            String logMessage4 = messageSource.getMessage("colaborador.Colaborador.obtenerDetalles_out_contribuciones_title", null, Locale.getDefault());
            System.out.println(logMessage4);
            i = 0;
            for (Contribucion contribucion : contribuciones) {
                String logMessage5 = messageSource.getMessage("colaborador.Colaborador.obtenerDetalles_out_contribuciones_body", new Object[]{i, contribucion.getClass().getSimpleName()}, Locale.getDefault());
                System.out.println(logMessage5);
                i++;
            }
        }

        if (!beneficiosAdquiridos.isEmpty()) {
            String logMessage6 = messageSource.getMessage("colaborador.Colaborador.obtenerDetalles_out_beneficios_adquiridos_title", null, Locale.getDefault());
            System.out.println(logMessage6);
            i = 0;

            for (Oferta beneficioAdquirido : beneficiosAdquiridos) {
                String logMessage7 = messageSource.getMessage("colaborador.Colaborador.obtenerDetalles_out_beneficios_adquiridos_body", new Object[]{i, beneficioAdquirido.getNombre()}, Locale.getDefault());
                System.out.println(logMessage7);
                i++;
            }
        }

        if (puntos != null) {
            String logMessage8 = messageSource.getMessage("colaborador.Colaborador.obtenerDetalles_out_puntos", new Object[]{puntos}, Locale.getDefault());
            System.out.println(logMessage8);
        }
    }

}
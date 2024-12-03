package com.tp_anual.proyecto_heladeras_solidarias.model.heladera.vianda;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.Heladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.heladera.HeladeraNula;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
public class Vianda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Setter
    private String comida;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera")
    @Setter
    private Heladera heladera;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador")
    @Setter
    private ColaboradorHumano colaborador;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Setter
    private LocalDate fechaCaducidad;

    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private LocalDateTime fechaDonacion;

    @Setter
    private Integer calorias;

    @Setter
    private Integer peso;

    @Setter
    private Boolean entregada;

    public Vianda() {}

    public Vianda(String vComida, ColaboradorHumano vColaborador, LocalDate vFechaCaducidad, LocalDateTime vFechaDonacion, Integer vCalorias, Integer vPeso, Boolean vEntregada) {
        comida = vComida;
        heladera = null;
        colaborador = vColaborador;
        fechaCaducidad = vFechaCaducidad;
        fechaDonacion = vFechaDonacion;
        calorias = vCalorias;
        peso = vPeso;
        entregada = vEntregada;
    }

    public Heladera getHeladera() {
        return heladera != null ? heladera : new HeladeraNula();    // Sin importar si estamos en el ciclo normal de vida del objeto, o si hicimos una recuperación desde la base de datos, si la Vianda no se encuentra en una Heladera, el getter retornará una HeladeraNula
    }

    private void marcarEntrega() {
        setEntregada(true);
    }

    private void desmarcarEntrega() {
        setEntregada(false);
    }

    public void establecerFechaDeDonacionActual() {
        setFechaDonacion(LocalDateTime.now());
    }

    public void agregarAHeladera(Heladera heladera) {
        setHeladera(heladera);
        marcarEntrega();
    };

    public void agregarAHeladeraPrimeraVez(Heladera heladera) {
        agregarAHeladera(heladera);
        establecerFechaDeDonacionActual();
    }

    public void quitarDeHeladera() {
        setHeladera(new HeladeraNula());
        desmarcarEntrega();
    }


}

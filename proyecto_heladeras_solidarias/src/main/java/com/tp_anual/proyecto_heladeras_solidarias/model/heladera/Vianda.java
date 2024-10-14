package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vianda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    private String comida;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera")
    private Heladera heladera;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador")
    private ColaboradorHumano colaborador;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaCaducidad;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaDonacion;

    private Integer calorias;

    private Integer peso;
    
    private Boolean entregada;

    public Vianda(String vComida, ColaboradorHumano vColaborador, LocalDateTime vFechaCaducidad, LocalDateTime vFechaDonacion, Integer vCalorias, Integer vPeso, Boolean vEntregada) {
        comida = vComida;
        heladera = new HeladeraNula();
        colaborador = vColaborador;
        fechaCaducidad = vFechaCaducidad;
        fechaDonacion = vFechaDonacion;
        calorias = vCalorias;
        peso = vPeso;
        entregada = vEntregada;
    }

    public void quitarDeHeladera() {
        setHeladera(new HeladeraNula());
    }

    public void marcarEntrega() {
        setEntregada(true);
    }

    public void desmarcarEntrega() {
        setEntregada(false);
    }
}

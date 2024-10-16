package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Setter
    private LocalDateTime fechaCaducidad;

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

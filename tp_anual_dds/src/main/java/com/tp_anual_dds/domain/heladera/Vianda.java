package com.tp_anual_dds.domain.heladera;

import java.time.LocalDateTime;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public class Vianda {
    private String comida;
    private Heladera heladera;
    private ColaboradorHumano colaborador;
    private LocalDateTime fechaCaducidad;
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

    public String getComida() {
        return comida;
    }

    public Heladera getHeladera() {
        return heladera;
    }

    public ColaboradorHumano getColaborador() {
        return colaborador;
    }

    public LocalDateTime getFechaCaducidad() {
        return fechaCaducidad;
    }

    public LocalDateTime getFechaDonacion() {
        return fechaDonacion;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public Integer getPeso() {
        return peso;
    }

    public Boolean fueEntregada() {
        return entregada;
    }

    public void setHeladera(Heladera vHeladera) {
        heladera = vHeladera;
    }

    public void setFechaDonacion(LocalDateTime vFechaDonacion) {
        fechaDonacion = vFechaDonacion;
    }

    public void setEntregada(Boolean vEntregada) {
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

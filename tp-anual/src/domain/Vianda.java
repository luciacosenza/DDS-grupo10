package domain;

import java.time.LocalDateTime;

public class Vianda {
    private String comida;
    private Heladera heladera;
    private Colaborador colaborador;
    private LocalDateTime fechaCaducidad;
    private LocalDateTime fechaDonacion;
    private Integer calorias;
    private Integer peso;
    private Boolean entregada;

    public Vianda(String vComida, Heladera vHeladera, Colaborador vColaborador, LocalDateTime vFechaCacudicad, LocalDateTime vFechaDonacion, Integer vCalorias, Integer vPeso, Boolean vEntregada) {
        comida = vComida;
        heladera = vHeladera;
        colaborador = vColaborador;
        fechaCaducidad = vFechaCacudicad;
        fechaDonacion = vFechaDonacion;
        calorias = vCalorias;
        peso = vPeso;
        entregada = vEntregada;
    }
}

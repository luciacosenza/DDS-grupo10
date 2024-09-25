package com.tp_anual.proyecto_heladeras_solidarias.model.heladera;

import java.time.LocalDateTime;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
public class Vianda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    protected Long id;

    @NotBlank
    private String comida;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heladera_id")
    @NotNull
    private Heladera heladera;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colaborador_id")
    @NotNull
    private ColaboradorHumano colaborador;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime fechaCaducidad;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private LocalDateTime fechaDonacion;

    private Integer calorias;

    private Integer peso;
    
    @NotNull
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

    // Este método debe ser llamado cuando una Vianda es retirada de una Heladera (se queda "sin Heladera", momentáneamente)
    public void quitarDeHeladera() {
        setHeladera(new HeladeraNula());
    }

    // Este método debe ser llamado tanto cuando se entregó una Vianda de Donación o cuando se depositó una Vianda como parte de lote de una Distribución de Viandas
    public void marcarEntrega() {
        setEntregada(true);
    }

    // Este método, al igual que "quitarDeHeladera()", debe ser llamado cuando una Vianda es retirada de una Heladera (está "sin entregar", momentáneamente)
    public void desmarcarEntrega() {
        setEntregada(false);
    }
}

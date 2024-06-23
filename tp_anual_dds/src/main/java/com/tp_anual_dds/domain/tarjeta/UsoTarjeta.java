package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.heladera.Heladera;

public class UsoTarjeta {
    private LocalDateTime fechaUso;
    private ArrayList<Heladera> heladeras;

    public UsoTarjeta(LocalDateTime vFechaUso, ArrayList<Heladera> vHeladeras) {
        fechaUso = vFechaUso;
        heladeras = vHeladeras;
    }
}
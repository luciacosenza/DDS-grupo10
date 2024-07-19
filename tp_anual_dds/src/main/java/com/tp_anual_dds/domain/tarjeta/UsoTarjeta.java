package com.tp_anual_dds.domain.tarjeta;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.heladera.HeladeraActiva;

public class UsoTarjeta {
    private LocalDateTime fechaUso;
    private ArrayList<HeladeraActiva> heladeras;

    public UsoTarjeta(LocalDateTime vFechaUso, ArrayList<HeladeraActiva> vHeladeras) {
        fechaUso = vFechaUso;
        heladeras = vHeladeras;
    }
}
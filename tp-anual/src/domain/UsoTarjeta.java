package domain;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class UsoTarjeta {
    private LocalDateTime fechaUso;
    private ArrayList<Heladera> heladeras;

    public UsoTarjeta(LocalDateTime vFechaUso, ArrayList<Heladera> vHeladeras) {
        fechaUso = vFechaUso;
        heladeras = vHeladeras;
    }
}
package src;

import java.util.ArrayList;
import java.time.LocalDate;

public class UsoTarjeta {
    private LocalDate fechaUso;
    private ArrayList<Heladera> heladeras;

    public UsoTarjeta(LocalDate vFechaUso, ArrayList<Heladera> vHeladeras) {
        fechaUso = vFechaUso;
        heladeras = vHeladeras;
    }
}
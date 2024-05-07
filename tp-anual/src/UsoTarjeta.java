package src;

import java.util.ArrayList;
import java.util.Date;

public class UsoTarjeta {
    private Date fechaUso;
    private ArrayList<Heladera> heladeras;

    public UsoTarjeta(Date vFechaUso, ArrayList<Heladera> vHeladeras) {
        fechaUso = vFechaUso;
        heladeras = vHeladeras;
    }
}
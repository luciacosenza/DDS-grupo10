package src;

import java.util.ArrayList;
import java.util.Date;

public class ColaboradorHumano extends Colaborador {
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;

    public ColaboradorHumano(ArrayList<Contacto> conta, String direc, ArrayList<Contribucion> contr, String nombr, String apell, Date fechaN) {
        contactos = conta;
        direccion = direc;
        contribuciones = contr;
        nombre = nombr;
        apellido = apell;
        fechaNacimiento = fechaN;
    }
}

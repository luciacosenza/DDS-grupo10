package src;

import java.util.ArrayList;
import java.time.LocalDate;

public class ColaboradorHumano extends Colaborador {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    public ColaboradorHumano(ArrayList<MedioDeContacto> vMediosDeContacto, String vDireccion, ArrayList<Contribucion> vContribuciones, String vNombre, String vApellido, LocalDate vFechaNacimiento) {
        mediosDeContacto = vMediosDeContacto;
        direccion = vDireccion;
        contribuciones = vContribuciones;
        nombre = vNombre;
        apellido = vApellido;
        fechaNacimiento = vFechaNacimiento;
    }
}

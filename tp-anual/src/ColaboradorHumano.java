package src;

import java.util.ArrayList;
import java.util.Date;

public class ColaboradorHumano extends Colaborador {
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;

    public ColaboradorHumano(ArrayList<MedioDeContacto> vMediosDeContacto, String vDireccion, ArrayList<Contribucion> vContribuciones, String vNombre, String vApellido, Date vFechaNacimiento) {
        mediosDeContacto = vMediosDeContacto;
        direccion = vDireccion;
        contribuciones = vContribuciones;
        nombre = vNombre;
        apellido = vApellido;
        fechaNacimiento = vFechaNacimiento;
    }
}

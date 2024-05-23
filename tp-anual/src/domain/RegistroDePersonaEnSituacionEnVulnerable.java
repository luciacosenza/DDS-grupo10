package domain;
import java.util.ArrayList;
import java.time.LocalDate;

public class RegistroDePersonaEnSituacionEnVulnerable extends Contribucion {
    private ArrayList<Tarjeta> tarjetasAsignadas;
    
    public RegistroDePersonaEnSituacionEnVulnerable(Colaborador vColaborador, LocalDate vFechaContribucion, ArrayList<Tarjeta> vTarjetasAsignadas) {
        colaborador = vColaborador;
        fechaContribucion = vFechaContribucion;
        tarjetasAsignadas = vTarjetasAsignadas;
    }

    public ArrayList<Tarjeta> tarjetasAsignadas(){
        return tarjetasAsignadas;
    }

    // obtenerDetalles()
    

    public void validarIdentidad(Colaborador colaboradorAspirante) {
        if(!esColaboradorHumano(colaboradorAspirante)) {
            throw new IllegalArgumentException("El colaborador aspirante no es un Colaborador Humano");
        }
    }

    // accionar()
}
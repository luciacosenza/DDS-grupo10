package validador;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Validador{
    private final String contrasenia; // Podemos evitar que el validador conozca la contraseña haciendo que sus métodos reciban sólo el string.

    public Validador(String vContrasenia) {
        contrasenia = vContrasenia;
    }

    private Boolean largoContraseniaValida() {
        return (contrasenia.length() >= 8 && contrasenia.length() <= 64);
    }

    private Boolean esTop10000MalaContrasenia() {
        try {
            return Files.lines(Paths.get("./10k-most-common.txt")).anyMatch(linea -> linea.contains(contrasenia));
        } catch(Exception e) {
            // De ser necesario: System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean esValida() {
        return this.largoContraseniaValida() && !(this.esTop10000MalaContrasenia());
    }
}
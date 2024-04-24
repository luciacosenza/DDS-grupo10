import java.nio.file.Files;
import java.nio.file.Paths;

public class Validador{
    private final String contrasenia; // de puede evitar que el validador conozca la contraseña haciendo que sus metodos reciban el string nomas.

    public Validador(String contrasenia){ // constructor
        this.contrasenia = contrasenia;
    }
    public Boolean largoContraseniaValida(){
        return (this.contrasenia.length() >= 8 && this.contrasenia.length() <= 64);
    }
    public Boolean esTop10000MalaContrasenia() {
        try{
            return Files.lines(Paths.get("./10k-most-common.txt")).anyMatch(l -> l.contains(this.contrasenia)); // vendria a ser una lamnda con un "filter/find"
        }catch(Exception e) {
            // en caaaso de necesitar: System.out.println(e.getMessage());
            return false;
        }
    }
    public Boolean esValida(){
        return this.largoContraseniaValida() && !(this.esTop10000MalaContrasenia());
    }
}
import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    
    private int numero;
    private String domicilio;
    private String localidad;
    private List <Empleado> empleados = new ArrayList<>();
    private List <Envio> envios = new ArrayList<>();
    

    public void llegadaEnvio(Envio unEnvio){
        envios.add(unEnvio);
    }

    public void salidaEnvio(Envio unEnvio){
        envios.remove(unEnvio);
    }

    public Envio buscarEnvio(int codigoDeRastreoSolicitado){
        for(Envio unEnvio : envios){
            if(unEnvio.codigoDeRastreo() == codigoDeRastreoSolicitado){
                return unEnvio;
            }
        }
        throw new Error();
    }
}
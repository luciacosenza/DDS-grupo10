import java.util.ArrayList;
import java.util.List;

public class Envio {
    private Persona remitente;
    private Persona destinatario;
    private Float precio;
    private int codigoDeRastreo;
    private Empleado cartero;
    private List <Sucursal> historialDeCamino = new ArrayList<>();
    private List <Producto> productos = new ArrayList<>();
    private String estado;
    

    public String informarEstadoEnvio(){
        return this.estado;
    }
    
    public void registrarIngreso(Sucursal unaSucursal) {
        unaSucursal.llegadaEnvio(this);
        historialDeCamino.add(unaSucursal);
    }

    public void registrarCartero (Empleado unCartero){
        this.cartero = unCartero;
    }
    
    public int codigoDeRastreo(){
        return this.codigoDeRastreo;
    }
}

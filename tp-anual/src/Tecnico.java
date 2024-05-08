package src;

public class Tecnico {
    private String nombre;
    private String apellido;
    private PersonaEnSituacionVulnerable.TipoDocumento tipoDocumento;
    private Integer documento;
    private Integer cuil;
    private MedioDeContacto medioDeContacto;
    private String areaDeCobertura; // Seguro no quede como String, hay que cambiarlo

    public Tecnico(String vNombre, String vApellido, PersonaEnSituacionVulnerable.TipoDocumento vTipoDocumento, Integer vDocumento, Integer vCuil, MedioDeContacto vMedioDeContacto, String vAreaDeCobertura) {
        nombre = vNombre;
        apellido = vApellido;
        tipoDocumento = vTipoDocumento;
        documento = vDocumento;
        cuil = vCuil;
        medioDeContacto = vMedioDeContacto;
        areaDeCobertura = vAreaDeCobertura;
    }
}

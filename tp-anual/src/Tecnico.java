package src;

public class Tecnico {
    private String nombre;
    private String apellido;
    private PersonaEnSituacionVulnerable.TipoDocumento tipoDocumento;
    private String documento;
    private String cuil;
    private MedioDeContacto medioDeContacto;
    private Area areaDeCobertura;

    public Tecnico(String vNombre, String vApellido, PersonaEnSituacionVulnerable.TipoDocumento vTipoDocumento, String vDocumento, String vCuil, MedioDeContacto vMedioDeContacto, Area vAreaDeCobertura) {
        nombre = vNombre;
        apellido = vApellido;
        tipoDocumento = vTipoDocumento;
        documento = vDocumento;
        cuil = vCuil;
        medioDeContacto = vMedioDeContacto;
        areaDeCobertura = vAreaDeCobertura;
    }
}

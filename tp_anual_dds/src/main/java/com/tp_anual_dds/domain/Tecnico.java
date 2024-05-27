package com.tp_anual_dds.domain;

public class Tecnico {
    private String nombre;
    private String apellido;
    private Documento documento;
    private String cuil;
    private MedioDeContacto medioDeContacto;
    private Area areaDeCobertura;

    public Tecnico(String vNombre, String vApellido, Documento vDocumento, String vCuil, MedioDeContacto vMedioDeContacto, Area vAreaDeCobertura) {
        nombre = vNombre;
        apellido = vApellido;
        documento = vDocumento;
        cuil = vCuil;
        medioDeContacto = vMedioDeContacto;
        areaDeCobertura = vAreaDeCobertura;
    }
}

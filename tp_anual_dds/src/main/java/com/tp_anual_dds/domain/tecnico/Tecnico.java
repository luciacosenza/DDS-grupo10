package com.tp_anual_dds.domain.tecnico;

import com.tp_anual_dds.domain.area.Area;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.documento.Documento;

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

package com.tp_anual_dds.domain;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class ColaboradorHumano extends Colaborador {
    private String nombre;
    private String apellido;
    private Documento documento;
    private LocalDateTime fechaNacimiento;

    public ColaboradorHumano(ArrayList<MedioDeContacto> vMediosDeContacto, Ubicacion vDomicilio, ArrayList<Contribucion> vContribuciones, Double vPuntos , String vNombre, String vApellido, Documento vDocumento , LocalDateTime vFechaNacimiento) {
        mediosDeContacto = vMediosDeContacto;
        domicilio = vDomicilio;
        contribuciones = vContribuciones;
        puntos = vPuntos;
        nombre = vNombre;
        apellido = vApellido;
        documento = vDocumento;
        fechaNacimiento = vFechaNacimiento;
    }
}

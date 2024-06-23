package com.tp_anual_dds.domain.colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class ColaboradorHumano extends Colaborador {
    private String nombre;
    private String apellido;
    private Documento documento;
    private LocalDateTime fechaNacimiento;
    protected TarjetaColaborador tarjeta;

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

    public String getNombre() {
        return nombre;
    }

    public TarjetaColaborador getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaColaborador tarjeta) {
        this.tarjeta = tarjeta;
    }
}

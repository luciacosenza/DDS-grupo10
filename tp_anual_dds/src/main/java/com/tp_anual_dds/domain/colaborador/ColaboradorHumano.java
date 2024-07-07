package com.tp_anual_dds.domain.colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.contribuciones.Contribucion;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.persona.PersonaFisica;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorNula;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class ColaboradorHumano extends Colaborador {
    protected TarjetaColaborador tarjeta;

    public ColaboradorHumano(Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, Double vPuntos , String vNombre, String vApellido, Documento vDocumento , LocalDateTime vFechaNacimiento) {
        persona = new PersonaFisica(vNombre, vApellido, vDocumento, vFechaNacimiento);
        domicilio = vDomicilio;
        mediosDeContacto = vMediosDeContacto;
        contribuciones = vContribuciones;
        puntos = vPuntos;
        tarjeta = new TarjetaColaboradorNula();
    }

    @Override
    public PersonaFisica getPersona() {
        return (PersonaFisica) persona;
    }

    public TarjetaColaborador getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(TarjetaColaborador vTarjeta) {
        tarjeta = vTarjeta;
    }
}

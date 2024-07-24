package com.tp_anual_dds.domain.colaborador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.contribucion.Contribucion;
import com.tp_anual_dds.domain.contribucion.DistribucionViandasCreator;
import com.tp_anual_dds.domain.contribucion.DonacionDineroCreator;
import com.tp_anual_dds.domain.contribucion.DonacionViandaCreator;
import com.tp_anual_dds.domain.contribucion.RegistroDePersonaEnSituacionVulnerableCreator;
import com.tp_anual_dds.domain.documento.Documento;
import com.tp_anual_dds.domain.heladera.HeladeraActiva;
import com.tp_anual_dds.domain.oferta.Oferta;
import com.tp_anual_dds.domain.persona.PersonaFisica;
import com.tp_anual_dds.domain.suscripcion.Suscripcion;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaborador;
import com.tp_anual_dds.domain.tarjeta.TarjetaColaboradorNula;
import com.tp_anual_dds.domain.ubicacion.Ubicacion;

public class ColaboradorHumano extends Colaborador {    // Implementa una Interfaz "ColaboradorHumanoObserver" a nivel conceptual
    protected TarjetaColaborador tarjeta;
    protected final ArrayList<Suscripcion> suscripciones = new ArrayList<>();   // Será una Suscripción por Heladera

    public ColaboradorHumano(Ubicacion vDomicilio, ArrayList<MedioDeContacto> vMediosDeContacto, ArrayList<Contribucion> vContribuciones, ArrayList<Oferta> vBeneficiosAdquiridos, Double vPuntos , String vNombre, String vApellido, Documento vDocumento , LocalDateTime vFechaNacimiento) {
        persona = new PersonaFisica(vNombre, vApellido, vDocumento, vFechaNacimiento);
        domicilio = vDomicilio;
        mediosDeContacto = vMediosDeContacto;
        contribuciones = vContribuciones;
        contribucionesPendientes = new ArrayList<>();

        creatorsPermitidos = new HashSet<>();
        creatorsPermitidos.add(DistribucionViandasCreator.class);
        creatorsPermitidos.add(DonacionDineroCreator.class);
        creatorsPermitidos.add(DonacionViandaCreator.class);
        creatorsPermitidos.add(RegistroDePersonaEnSituacionVulnerableCreator.class);

        beneficiosAdquiridos = vBeneficiosAdquiridos;
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

    public void agregarSuscripcion(Suscripcion suscripcion) {
        suscripciones.add(suscripcion);
    }

    public Suscripcion suscribirse(HeladeraActiva heladeraObjetivo, Integer viandasDisponiblesMin, Integer viandasParaLlenarMax, Boolean notificarDesperfecto, MedioDeContacto medioDeContacto) {
        Suscripcion suscripcion = new Suscripcion(this, heladeraObjetivo, viandasDisponiblesMin, viandasParaLlenarMax, notificarDesperfecto, medioDeContacto);
        suscripcion.darDeAlta();
        agregarSuscripcion(suscripcion);

        return suscripcion;
    }

    public void modificarSuscripcion(Suscripcion suscripcion, Suscripcion.AtributoSuscripcion flag, Integer nuevoValor) {
        // Tenemos un flag que nos indica qué atributo debe ser cambiado
        switch(flag) {
        
        case VIANDAS_MIN:
            suscripcion.setViandasDisponiblesMin(nuevoValor);
            break;
        
        case VIANDAS_MAX:
            suscripcion.setViandasParaLlenarMax(nuevoValor);
            break;
            
        case NOTIFICAR_DESPERFECTO:
            suscripcion.setNotificarDesperfecto(nuevoValor != 0);
            
        default:
            break;
        }
    }
}
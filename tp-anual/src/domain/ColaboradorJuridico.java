package domain;

import java.util.ArrayList;

public class ColaboradorJuridico extends Colaborador {
    private String razonSocial;
    private TipoColaboradorJuridico tipo;
    private String rubro;

    enum TipoColaboradorJuridico {
        GUBERNAMENTAL,
        ONG,
        EMPRESA,
        INSTITUCION
    }

    public ColaboradorJuridico(ArrayList<MedioDeContacto> vMediosDeContacto, Ubicacion vDomicilio, ArrayList<Contribucion> vContribuciones, Double vPuntos, String vRazonSocial, String vRubro, TipoColaboradorJuridico vTipo) {
        mediosDeContacto = vMediosDeContacto;
        domicilio = vDomicilio;
        contribuciones = vContribuciones;
        puntos = vPuntos;
        razonSocial = vRazonSocial;
        rubro = vRubro;
        tipo = vTipo;
    }
}

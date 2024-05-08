package src;

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

    public ColaboradorJuridico(ArrayList<MedioDeContacto> vMediosDeContacto, String vDireccion, ArrayList<Contribucion> vContribuciones, String vRazonSocial, String vRubro, TipoColaboradorJuridico vTipo) {
        mediosDeContacto = vMediosDeContacto;
        direccion = vDireccion;
        contribuciones = vContribuciones;
        razonSocial = vRazonSocial;
        rubro = vRubro;
        tipo = vTipo;
    }
}

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

    public ColaboradorJuridico(ArrayList<Contacto> vContactos, String vDireccion, ArrayList<Contribucion> vContribuciones, String vRazonSocial, String vRubro, TipoColaboradorJuridico vTipo) {
        contactos = vContactos;
        direccion = vDireccion;
        contribuciones = vContribuciones;
        razonSocial = vRazonSocial;
        rubro = vRubro;
        tipo = vTipo;
    }
}

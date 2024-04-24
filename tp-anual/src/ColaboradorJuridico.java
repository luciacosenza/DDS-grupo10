package src;

import java.util.ArrayList;

public class ColaboradorJuridico extends Colaborador {
    private String razonSocial;
    private tipoColaboradorJuridico tipo;
    private String rubro;

    enum tipoColaboradorJuridico {
        GUBERNAMENTAL,
        ONG,
        EMPRESA,
        INSTITUCION
    }

    public ColaboradorJuridico(ArrayList<Contacto> conta, String direc, ArrayList<Contribucion> contr, String razon, String rubr, tipoColaboradorJuridico tip) {
        contactos = conta;
        direccion = direc;
        contribuciones = contr;
        razonSocial = razon;
        rubro = rubr;
        tipo = tip;
    }
}

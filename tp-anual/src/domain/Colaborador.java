package domain;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Colaborador {
    protected ArrayList<MedioDeContacto> mediosDeContacto;
    protected Ubicacion domicilio;
    protected ArrayList<Contribucion> contribuciones;
    protected ArrayList<Oferta> beneficiosAdquiridos;
    protected Double puntos;

    // darDeAlta()
    // darDeBaja()
    // modificar()

    public void sumarPuntos(Double puntosASumar) {
        puntos += puntosASumar;
    }

    public Boolean tieneDomicilio() {
        return domicilio != null;
    }

    public void adquirirBeneficio(Oferta oferta) {
        beneficiosAdquiridos.add(oferta);
    }

    public Double puntos() {
        Double puntos = 0.0;

        // Coeficientes Variables
        Float coeficienteDonacionDinero = 0.5f;
        Float coeficienteDistribucionDeViandas = 1f;
        Float coeficienteDonacionVianda = 1.5f;
        Float coeficienteRegistroDePersonaEnSituacionVulnerable = 2f;
        Float coeficienteHacerseCargoDeHeladera = 5f;

        // Sumatoria de Puntos
        for (Contribucion contribucion : contribuciones) {
            
            switch (contribucion.getClass().getSimpleName()) {
            case "DonacionDinero":
                DonacionDinero donacionDinero = (DonacionDinero) contribucion;
                puntos += donacionDinero.donadoHastaLaFecha() * coeficienteDonacionDinero;
                break;
            case "DistribucionDeViandas":
                DistribucionViandas distribucionViandas = (DistribucionViandas) contribucion;
                puntos += distribucionViandas.cantidadViandasAMover() * coeficienteDistribucionDeViandas;
                break;
            case "DonacionVianda":
                puntos += coeficienteDonacionVianda;
                break;
            case "RegistroDePersonaEnSituacionVulnerable":
                puntos += coeficienteRegistroDePersonaEnSituacionVulnerable;
                break;
            case "HacerseCargoDeHeladera":
                HacerseCargoDeHeladera hacerseCargoDeHeladera = (HacerseCargoDeHeladera) contribucion;
                puntos += contribuciones.stream().filter( con -> con.getClass() == HacerseCargoDeHeladera.class ).collect(Collectors.toList()).size() * hacerseCargoDeHeladera.mesesHaciendoseCargo() * coeficienteHacerseCargoDeHeladera;
                break;
            default:
                break;
            }
        }

        return puntos;
    }

}
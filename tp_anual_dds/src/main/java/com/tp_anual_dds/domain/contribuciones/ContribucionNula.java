package com.tp_anual_dds.domain.contribuciones;

public class ContribucionNula extends Contribucion {
    public ContribucionNula() {
        colaborador = null;
        fechaContribucion = null;
        completada = false;
    }

    @Override
    protected void validarIdentidad() {}

    @Override
    protected void accionar() {}

    @Override
    protected void calcularPuntos() {}

    @Override
    public void contribuir() {}

    @Override
    public void confirmar() {}
}

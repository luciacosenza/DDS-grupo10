package domain;

public class Oferta {

    private String nombre;
    private Integer costo;
    private Categoria categoria;
    private String imagen;  // Por ahora es solo un link
    
    enum Categoria {
        GASTRONOMIA,
        ELECTRONICA,
        ARTICULOS_PARA_EL_HOGAR
        // Completar
    }

    public Oferta(String vNombre, Integer vCosto, Categoria vCategoria, String vImagen) {
        nombre = vNombre;
        costo = vCosto;
        categoria = vCategoria;
        imagen = vImagen;
    }

    public void validarPuntos(Colaborador colaborador) {
        Double puntosColaborador = colaborador.puntos();
        
        if (puntosColaborador < costo) {
            throw new IllegalArgumentException("No se cuenta con los puntos necesarios para adquirir este beneficio");
        }
    }
}
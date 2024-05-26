package domain;

public interface SensorSubject {
    public void agregar(Heladera observer);
    
    public void eliminar(Heladera observer);

    public void notificar();
}

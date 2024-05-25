package domain;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.DonacionDinero;
import domain.ColaboradorHumano;
import domain.DonacionDinero;
import java.time.LocalDate;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DonacionDinero_Test {
    @Test
    @DisplayName("Test cálculo de puntos en la donación")
    public void DonacionPorSegundoTest(){
        ColaboradorHumano colaborador = new ColaboradorHumano(null, null, null , "Juan", "Pichi", null);
        DonacionDinero donacion = new DonacionDinero(colaborador, LocalDate.now(), 10d, null);
        colaborador.colaborar(donacion);
    }
}

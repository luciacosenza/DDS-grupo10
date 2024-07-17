package com.tp_anual_dds.domain.colaborador;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.contacto.EMail;
import com.tp_anual_dds.domain.contacto.WhatsApp;
import com.tp_anual_dds.domain.contribuciones.DonacionVianda;
import com.tp_anual_dds.domain.contribuciones.DonacionViandaCreator;
import com.tp_anual_dds.domain.contribuciones.HacerseCargoDeHeladeraCreator;
import com.tp_anual_dds.domain.heladera.Vianda;

public class ColaboradorTest {
    
    @Test
    @DisplayName("Testeo la obtención de un Contacto del Colaborador")
    public void GetContactoTest() {
        ColaboradorHumano colaborador = new ColaboradorHumano(null, new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", null, null); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        EMail eMail = new EMail("correoprueba@gmail.com");
        colaborador.agregarContacto(eMail);

        Assertions.assertEquals(colaborador.getContacto(EMail.class), eMail);
    }

    @Test
    @DisplayName("Testeo la NoSuchElementException al pasar un MedioDeContacto que no posee el Colaborador")
    public void NoSuchElementGetContactoTest() {
        ColaboradorHumano colaborador = new ColaboradorHumano(null, new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", null, null); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        EMail eMail = new EMail("correoprueba@gmail.com");
        colaborador.agregarContacto(eMail);

        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            colaborador.getContacto(WhatsApp.class);
        });
    
        Assertions.assertEquals("El colaborador no cuenta con ese medio de contacto", exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la IllegalArgumentException para Contribuciones que el Colaborador no es capaz de hacer")
    public void IllegalArgumentColaborarTest() {
        ColaboradorHumano colaborador = new ColaboradorHumano(null, new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", null, null); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        HacerseCargoDeHeladeraCreator hacerseCargoDeHeladeraCreator = new HacerseCargoDeHeladeraCreator();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            colaborador.colaborar(hacerseCargoDeHeladeraCreator);
        });

        Assertions.assertEquals("No es una forma válida de colaborar", exception.getMessage());
    }

    @Test
    @DisplayName("Testeo la correcta creación de la Contribucion y que se agregue a las contribuciones del Colaborador")
    public void ColaborarTest() {
        ColaboradorHumano colaborador = new ColaboradorHumano(null, new ArrayList<>(), new ArrayList<>(), 0d, "NombrePrueba", "ApellidoPrueba", null, null); // Uso ColaboradorHumano porque Colaborador es abstract y el metodo es igual para ambos (Humano y Juridico)
        Vianda vianda = new Vianda("ComidaPrueba", null, colaborador, null, null, 0, 0, false);
        DonacionViandaCreator donacionViandaCreator = new DonacionViandaCreator();
        colaborador.colaborar(donacionViandaCreator);

        DonacionVianda donacionVianda = new DonacionVianda(colaborador, null, vianda, null)
    }
}

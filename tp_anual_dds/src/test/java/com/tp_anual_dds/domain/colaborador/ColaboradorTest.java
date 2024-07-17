package com.tp_anual_dds.domain.colaborador;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.tp_anual_dds.domain.contacto.EMail;
import com.tp_anual_dds.domain.contacto.WhatsApp;

public class ColaboradorTest {
    
    @Test
    @DisplayName("Testeo la obtención de un Contacto del Colaborador")
    public void GetContacto() {
        ColaboradorHumano colaborador = new ColaboradorHumano(null, new ArrayList<>(), null, null, null, null, null, null);
        EMail eMail = new EMail("correoprueba@gmail.com");
        colaborador.agregarContacto(eMail);

        Assertions.assertEquals(colaborador.getContacto(EMail.class), eMail);
    }

    @Test
    @DisplayName("Testeo la NoSuchElementException al pasar un MedioDeContacto que no posee el Colaborador")
    public void NoSuchElementGetContacto() {
        ColaboradorHumano colaborador = new ColaboradorHumano(null, new ArrayList<>(), null, null, null, null, null, null);
        EMail eMail = new EMail("correoprueba@gmail.com");
        colaborador.agregarContacto(eMail);

        NoSuchElementException exception = Assertions.assertThrows(NoSuchElementException.class, () -> {
            colaborador.getContacto(WhatsApp.class);
        });
    
        Assertions.assertEquals("El colaborador no cuenta con ese medio de contacto", exception.getMessage());
    }
}

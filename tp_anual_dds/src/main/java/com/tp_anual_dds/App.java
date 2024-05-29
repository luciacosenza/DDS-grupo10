package com.tp_anual_dds;

import com.tp_anual_dds.domain.*;

public class App 
{
    public static void main( String[] args ) {

                // Crear una instancia de EmailSenderService
                EmailSenderService emailSender = new EmailSenderService();
                
                // Llamar al método enviarMail
                emailSender.enviarMail("todilah640@adrais.com", "Prueba", "Funciona!");
         }
}


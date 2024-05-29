package com.tp_anual_dds.domain;


import com.tp_anual_dds.domain.EmailSenderService;
public class Main {
    public static void main(String[] args) {
        // Crear una instancia de EmailSenderService
        EmailSenderService emailSender = new EmailSenderService();
        
        // Llamar al método enviarMail
        emailSender.enviarMail("todilah640@adrais.com", "Prueba", "Funciona!");
    }
}
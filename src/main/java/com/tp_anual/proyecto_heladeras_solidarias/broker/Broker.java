package com.tp_anual.proyecto_heladeras_solidarias.broker;

import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.utils.SpringContext;
import lombok.extern.java.Log;
import org.springframework.context.MessageSource;

@Log
public class Broker {

    private LinkedBlockingQueue<Mensaje> queue = new LinkedBlockingQueue<>();

    public Broker() {
        new Thread( () -> {
            // Lo pongo en espera activa
            while(true) { 
                try {
                    Mensaje mensaje = queue.take();
                    procesarMensaje(mensaje);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();

                    MessageSource messageSource = SpringContext.getBean(MessageSource.class);
                    String logMessage1 = messageSource.getMessage("broker.Broker_err_hilo", null, Locale.getDefault());

                    log.log(Level.SEVERE, logMessage1);
                } catch (Exception e) {
                    MessageSource messageSource = SpringContext.getBean(MessageSource.class);
                    String logMessage2 = messageSource.getMessage("broker.Broker_err_procesamiento_mensaje", null, Locale.getDefault());

                    log.log(Level.SEVERE, logMessage2);
                }
            }
        }).start();
    }

    public void agregarMensaje(Mensaje mensaje) throws InterruptedException {
        queue.put(mensaje);
    }

    public void procesarMensaje(Mensaje mensaje) {
        mensaje.procesar();
    }
}
package com.tp_anual.proyecto_heladeras_solidarias.broker;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class Broker {
    private static final Logger logger = Logger.getLogger(Broker.class.getName());
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
                    logger.log(Level.SEVERE, I18n.getMessage("broker.Broker.interrupcion_hilo"));
                } catch (Exception e) {
                    logger.log(Level.SEVERE, I18n.getMessage("broker.Broker.error_procesamiento_mensaje"));
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
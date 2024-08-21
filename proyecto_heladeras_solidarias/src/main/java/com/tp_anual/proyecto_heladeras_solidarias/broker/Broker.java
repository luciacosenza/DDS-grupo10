package com.tp_anual.proyecto_heladeras_solidarias.broker;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    logger.log(Level.SEVERE, "El hilo del Broker fue interrumpido");
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error al procesar el mensaje: %o", e.getMessage());
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
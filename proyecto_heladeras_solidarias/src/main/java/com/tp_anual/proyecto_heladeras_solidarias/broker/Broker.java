package com.tp_anual.proyecto_heladeras_solidarias.broker;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import lombok.extern.java.Log;

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
                    log.log(Level.SEVERE, I18n.getMessage("broker.Broker_err_hilo"));
                } catch (Exception e) {
                    log.log(Level.SEVERE, I18n.getMessage("broker.Broker_err_procesamiento_mensaje"));
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
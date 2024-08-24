package com.tp_anual.proyecto_heladeras_solidarias.message_loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MessageLoader {
    private final Properties properties;

    public MessageLoader() {
        properties = new Properties();
        
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("messages.properties")) {
            if (input == null) {
                System.out.println("No se pudo encontrar el archivo messages.properties");
                return; // Corta la ejecución del método
            }
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Falló el load"); // TODO Hay que cambiar esto (normalizar errores)
        }
    }

    public String obtenerMensaje(String key) {
        return properties.getProperty(key);
    }
}

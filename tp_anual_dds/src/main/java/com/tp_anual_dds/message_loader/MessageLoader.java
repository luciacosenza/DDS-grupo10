package com.tp_anual_dds.message_loader;

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
            e.printStackTrace();    // TODO Hay que cambiar esto
        }
    }

    public String obtenerMensaje(String key) {
        return properties.getProperty(key);
    }
}

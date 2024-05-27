package com.tp_anual_dds.migrador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;


public class Migrador {
    public void Migrar() {
        String csv = "archivo.csv";
        String linea;
        String separador = ",";

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(separador);
                procesarColaboracion(datos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void procesarColaboracion(String[] datos) {
        String tipoDoc = datos[0];
        String documento = datos[1];
        String nombre = datos[2];
        String apellido = datos[3];
        String mail = datos[4];
        String fechaColaboracionStr = datos[5];
        String formaColaboracion = datos[6];
        int cantidad = Integer.parseInt(datos[7]);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime fechaColaboracion;
        try {
            fechaColaboracion = LocalDateTime.parse(fechaColaboracionStr, dateFormat);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return;
        }
    }
}
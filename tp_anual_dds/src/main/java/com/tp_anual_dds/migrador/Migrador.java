package com.tp_anual_dds.migrador;

import com.tp_anual_dds.domain.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Migrador {
    public void Migrar(String csv) {
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

    private void procesarColaboracion(String[] datos) {
        Documento.TipoDocumento tipoDoc = Documento.convertirStrATipoDocumento(datos[0].toLowerCase().replaceAll("\\s+", "").replaceAll("[^a-zA-Z]", ""));
        String numDoc = datos[1];
        String nombre = datos[2];
        String apellido = datos[3];
        String direcMail = datos[4];
        String fechaColaboracionStr = datos[5];
        String formaColaboracion = datos[6];
        Integer cantColabs = Integer.valueOf(datos[7]);

        Documento documento = new Documento(tipoDoc, numDoc, null);
        
        Mail mail = new Mail(direcMail);
        ArrayList<MedioDeContacto> contactos = new ArrayList<>();
        contactos.add(mail);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime fechaColaboracion;
        try {
            fechaColaboracion = LocalDateTime.parse(fechaColaboracionStr, dateFormat);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return;
        }
        
        ColaboradorHumano colaborador = null;   // Deberia ir: "obtenerColaborador(documento, nombre, apellido);" Pero no tenemos forma de implementarlo, dado que todavia no tenemos una database)
        
        if (colaborador == null) {
            colaborador = new ColaboradorHumano(contactos, null, null, null, nombre, apellido, documento, null);
            // mail.enviarMail();
        }

        // registrarColaboracion();
        
    }
}

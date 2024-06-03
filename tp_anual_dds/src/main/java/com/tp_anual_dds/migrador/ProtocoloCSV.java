package com.tp_anual_dds.migrador;

import com.tp_anual_dds.conversores.ConversorTipoDocumento;
import com.tp_anual_dds.domain.ColaboradorHumano;
import com.tp_anual_dds.domain.Contribucion;
import com.tp_anual_dds.domain.Documento;
import com.tp_anual_dds.domain.MedioDeContacto;
import com.tp_anual_dds.domain.EMail;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class ProtocoloCSV extends ProtocoloExtraccion {
    public ArrayList<ColaboradorHumano> extract(String csv) {
        String linea;
        String separador = ",";
        ColaboradorHumano colaborador;
        ArrayList<ColaboradorHumano> colaboradoresExtraidos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(separador);
                colaborador = procesarColaborador(datos);

                if(colaborador == null) {
                    continue;
                }

                colaboradoresExtraidos.add(colaborador);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return colaboradoresExtraidos;
    }

    protected ColaboradorHumano procesarColaborador(String[] datos) {
        String tipoDocStr = datos[0];
        String numDoc = datos[1];
        String nombre = datos[2];
        String apellido = datos[3];
        String direcMail = datos[4];
        String fechaContribucionStr = datos[5];
        String formaContribucionStr = datos[6];
        Integer cantColabs = Integer.valueOf(datos[7]);

        // Transforma a documento
        String tipoDocStrCleaned = quitarEspacios(quitarNumericosYEspeciales(tipoDocStr));
        Documento.TipoDocumento tipoDoc = ConversorTipoDocumento.convertirStrATipoDocumento(tipoDocStrCleaned);
        Documento documento = new Documento(tipoDoc, numDoc, null);
        
        // Transforma a eMail
        EMail mail = new EMail(direcMail);
        ArrayList<MedioDeContacto> contactos = new ArrayList<>();
        contactos.add(mail);

        // Transforma a fechaContribucion
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime fechaContribucion;
        
        try {
            fechaContribucion = LocalDateTime.parse(fechaContribucionStr, dateFormat);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }

        // Transforma a colaborador
        ColaboradorHumano colaborador = null;   // Deberia ir: "obtenerColaborador(documento, nombre, apellido);" pero no tenemos forma de implementarlo, dado que todavia no tenemos una database
        
        if (colaborador == null) {
            colaborador = new ColaboradorHumano(contactos, null, null, null, nombre, apellido, documento, null);
        }
        
        // Agrega contribuciones a colaborador
        for(Integer i = 0; i < cantColabs; i++){
            Contribucion contribucion = registrarContribucion(formaContribucionStr, colaborador, fechaContribucion);
            colaborador.agregarContribucion(contribucion);
        }

        return colaborador;
    }
}

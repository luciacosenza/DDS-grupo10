package com.tp_anual_dds.migrador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tp_anual_dds.conversor.ConversorFormaContribucion;
import com.tp_anual_dds.conversor.ConversorTipoDocumento;
import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;
import com.tp_anual_dds.domain.contacto.EMail;
import com.tp_anual_dds.domain.contacto.MedioDeContacto;
import com.tp_anual_dds.domain.contribucion.Contribucion;
import com.tp_anual_dds.domain.contribucion.ContribucionCreator;
import com.tp_anual_dds.domain.documento.Documento;

public class TransformacionDeDatos {
    private String quitarEspacios(String string) {
        return string.replaceAll("\\s+", "");
    }

    private String quitarNumericosYEspeciales(String string) {
        return string.replaceAll("[^a-zA-Z]", "");
    }

    private String limpiarTexto(String string) {
        return quitarEspacios(quitarNumericosYEspeciales(string));
    }

    private Contribucion registrarContribucion(String formaContribucionStr, ColaboradorHumano colaborador, LocalDateTime fechaContribucion) {
        ContribucionCreator creator = ConversorFormaContribucion.convertirStrAContribucionCreator(formaContribucionStr);
        return creator.crearContribucion(colaborador, fechaContribucion); // Posible error al querer crear una contribucion a traves de un Creator sin pasarle el resto de argumentos necesarios
    }
    
    private ColaboradorHumano procesarColaborador(String[] data) {
        if(data.length != 8) { 
            System.err.println("Error: Fila de datos incompleta: " + Arrays.toString(data));
            return null;
        }
        
        String tipoDocStr = data[0];
        String numDoc = data[1];
        String nombre = data[2];
        String apellido = data[3];
        String direcMail = data[4];
        String fechaContribucionStr = data[5];
        String formaContribucionStr = data[6];
        Integer cantColabs = Integer.valueOf(data[7]);

        // Transforma a documento
        Documento.TipoDocumento tipoDoc = ConversorTipoDocumento.convertirStrATipoDocumento(tipoDocStr);
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
        ColaboradorHumano colaborador = new ColaboradorHumano(null, contactos, new ArrayList<>(), new ArrayList<>(), null, nombre, apellido, documento, null); // Los atributos que no estan en el csv los ponemos en null (luego veremos que hacer con eso)
        
        // Agrega contribuciones a colaborador
        for(Integer i = 0; i < cantColabs; i++){
            Contribucion contribucion = registrarContribucion(formaContribucionStr, colaborador, fechaContribucion);
            colaborador.agregarContribucion(contribucion);
        }

        return colaborador;
    }    

    public ArrayList<ColaboradorHumano> transform(ArrayList<String[]> data) {
        Map<String, ColaboradorHumano> colaboradoresProcesados = new HashMap<>();

        for(String[] dataColaborador : data) {
            ColaboradorHumano colaborador = procesarColaborador(dataColaborador);

            if(colaborador == null) {
                continue;
            }
            
            String clave = colaborador.getPersona().getDocumento().getTipo().name()
                + "-" + colaborador.getPersona().getDocumento().getNumero()
                + "-" + colaborador.getPersona().getDocumento().getSexo().name();

            // Junta los repetidos
            if (colaboradoresProcesados.containsKey(clave)) {
                ColaboradorHumano colaboradorExistente = colaboradoresProcesados.get(clave);
                colaboradorExistente.getContribuciones().addAll(colaborador.getContribuciones());
            } else {
                colaboradoresProcesados.put(clave, colaborador);
            }
        }

        return new ArrayList<>(colaboradoresProcesados.values());
    }
}

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
    // registrarContribucion()
    
    private Colaborador procesarColaboracion(String[] datos) {
        Documento.TipoDocumento tipoDoc = Documento.convertirStrATipoDocumento(datos[0].toLowerCase().replaceAll("\\s+", "").replaceAll("[^a-zA-Z]", ""));
        String numDoc = datos[1];
        String nombre = datos[2];
        String apellido = datos[3];
        String direcMail = datos[4];
        String fechaColaboracionStr = datos[5];
        String formaColaboracion = datos[6];
        Integer cantColabs = Integer.valueOf(datos[7]);

        Documento documento = new Documento(tipoDoc, numDoc, null);
        
        EMail mail = new EMail(direcMail);
        ArrayList<MedioDeContacto> contactos = new ArrayList<>();
        contactos.add(mail);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime fechaColaboracion;
        try {
            fechaColaboracion = LocalDateTime.parse(fechaColaboracionStr, dateFormat);

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
        
        ColaboradorHumano colaborador = null;   // Deberia ir: "obtenerColaborador(documento, nombre, apellido);" Pero no tenemos forma de implementarlo, dado que todavia no tenemos una database)
        
        if (colaborador == null) {
            colaborador = new ColaboradorHumano(contactos, null, null, null, nombre, apellido, documento, null);

            String asunto = "Gracias por tu apoyo! Aquí están tus credenciales de acceso al nuevo sistema";
            String cuerpo =
            """
            Estimado/a %s,

            Nos complace informarte que hemos recibido tu colaboración. Gracias por tu compromiso con nuestra causa!

            Como parte de nuestro esfuerzo por mejorar y simplificar la gestión de nuestros donantes y colaboradores, hemos migrado nuestros datos a un nuevo sistema.
            Te creamos y asignamos una cuenta para que puedas seguir colaborando y mantenerte informado sobre nuestras actividades.

            A continuación, te proporcionamos tus credenciales de acceso:

            Usuario: %s
            Contraseña temporal (modificable): %%s (completar y quitar el primer "%" al completar)

            Por favor, seguí estos pasos para acceder a tu cuenta:
            1. Visita nuestro sitio web %%s (completar y quitar el primer "%" al completar)
            2. Inicia sesión con las credenciales proporcionadas.
            3. Se te pedirá que confirmes tus datos y completes cualquier información faltante.

            Tu colaboración es fundamental para nosotros y queremos asegurarnos de que tus datos estén correctos en nuestro sistema.
            Una vez que hayas ingresado, vas a poder verificar y actualizar tu información personal (incluyendo tu contraseña).

            Si tenes alguna pregunta o necesitas asistencia, no dudes en contactarnos a %%s (completar y quitar el primer "%" al completar) o al %%s (completar y quitar el primer "%" al completar).

            Gracias nuevamente por tu apoyo.

            Atentamente,

            [Nombre del Responsable]
            [Cargo]
            [Nombre de la ONG]
            [Datos de Contacto de la ONG]
            """
            .formatted(nombre, direcMail);
            
            mail.contactar(asunto, cuerpo);
        }

        // registrarContribucion();

        return colaborador;
    }

    // sincronizarContribuciones()

    public ArrayList<Colaborador> migrar(String csv) {    // El tipo de retorno es temporal, hasta tener una database (pensamos que lo unico posible por ahora es retornar la lista de colaboradores cargados a quien la pida)
        String linea;
        String separador = ",";
        Colaborador colaborador;
        ArrayList<Colaborador> colaboradoresAMigrar = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csv))) {
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(separador);
                colaborador = procesarColaboracion(datos);

                if(colaborador == null) {
                    continue;
                }

                colaboradoresAMigrar.add(colaborador);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // sincronizarContribuciones(colaboradoresAMigrar);
        
        return colaboradoresAMigrar;
    }
}

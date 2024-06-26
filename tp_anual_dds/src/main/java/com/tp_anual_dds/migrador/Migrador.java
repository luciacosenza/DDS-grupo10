package com.tp_anual_dds.migrador;

import java.util.ArrayList;

import com.tp_anual_dds.domain.colaborador.ColaboradorHumano;

public class Migrador {
    private static EnvioDeDatos protocoloEnvio = new EnvioEMail();
    private static ExtraccionDeDatos protocoloExtraccion = new ExtraccionCSV();

    private static final String ASUNTO = "Gracias por tu apoyo! Aquí están tus credenciales de acceso al nuevo sistema";
    private static final String CUERPO =
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
            """;

    public static void setExtraccionDeDatosStrategy(ExtraccionDeDatos protocolo) {
        protocoloExtraccion = protocolo;
    }
    
    public static void setEnvioDeDatosStrategy(EnvioDeDatos protocolo) {
        protocoloEnvio = protocolo;
    }

    // private static ArrayList<Colaborador> sincronizarContribuciones() no hara falta, dado que cuando tengamos una database, esta hara que cada Colaborador sea unico

    public static ArrayList<ColaboradorHumano> migrar(String csv) {     // El tipo de retorno (una lista de colaboradores) es temporal, hasta tener una database (pensamos que lo unico posible, por ahora, es retornar la lista de colaboradores cargados a quien la pida)
        ArrayList<ColaboradorHumano> colaboradoresAMigrar = protocoloExtraccion.extract(csv);
        
        for(ColaboradorHumano colaborador : colaboradoresAMigrar) {
            protocoloEnvio.send(colaborador, ASUNTO, CUERPO);
        }
        
        // sincronizarContribuciones(colaboradoresAMigrar);
        // Lo que haria este metodo es impedir que exista un "mismo" colaborador duplicado pero con distintas contribuciones, juntando todas las contribuciones de un mismo colaborador en un mismo objeto y eliminando el resto 
        
        return colaboradoresAMigrar;
    }
}

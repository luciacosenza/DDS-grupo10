package com.tp_anual.proyecto_heladeras_solidarias.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class Migrador {
    private static final Logger logger = Logger.getLogger(Migrador.class.getName());
    private static ExtraccionDeDatos protocoloExtraccion;
    private static final TransformacionDeDatos transformador = new TransformacionDeDatos();
    private static EnvioDeDatos protocoloEnvio;

    private static final String ASUNTO = "Gracias por tu apoyo! Aquí están tus credenciales de acceso al nuevo Sistema";
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

    public static void confirmarLoading() {
        logger.log(Level.INFO, I18n.getMessage("migrador.Migrador.confirmarLoading_info"));
    }

    public static void migrar(String csv) throws IOException, URISyntaxException {        
        ArrayList<String[]> dataColaboradores = protocoloExtraccion.extract(csv);
        ArrayList<ColaboradorHumano> colaboradoresAMigrar = transformador.transform(dataColaboradores);

        for (ColaboradorHumano colaborador : colaboradoresAMigrar) {
            colaborador.darDeAlta();
            protocoloEnvio.send(colaborador, ASUNTO, CUERPO);
        }

        confirmarLoading();
    }
}

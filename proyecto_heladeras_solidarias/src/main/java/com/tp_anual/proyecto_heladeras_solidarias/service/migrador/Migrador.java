package com.tp_anual.proyecto_heladeras_solidarias.service.migrador;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.tp_anual.proyecto_heladeras_solidarias.model.colaborador.ColaboradorHumano;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;
import com.tp_anual.proyecto_heladeras_solidarias.service.colaborador.ColaboradorService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Log
public class Migrador {

    private final ExtraccionDeDatos protocoloExtraccion;
    private final TransformacionDeDatos transformador;
    private final EnvioDeDatos protocoloEnvio;
    private final ColaboradorService colaboradorService;

    private final String ASUNTO = "Gracias por tu apoyo! Aquí están tus credenciales de acceso al nuevo Sistema";

    private final String CUERPO =
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

    public Migrador(@Qualifier("extraccionCSV") ExtraccionDeDatos vProtocoloExtraccion, TransformacionDeDatos vTransformador, @Qualifier("envioEMail") EnvioDeDatos vProtocoloEnvio, ColaboradorService vColaboradorService) {
        protocoloExtraccion = vProtocoloExtraccion;
        transformador = vTransformador;
        protocoloEnvio = vProtocoloEnvio;
        colaboradorService = vColaboradorService;
    }

    public void confirmarLoading() {
        log.log(Level.INFO, I18n.getMessage("migrador.Migrador.confirmarLoading_info"));
    }

    public ArrayList<ColaboradorHumano> migrar(String csv, Boolean contactar) throws IOException, URISyntaxException {
        ArrayList<String[]> dataColaboradores = protocoloExtraccion.extract(csv);
        ArrayList<ColaboradorHumano> colaboradoresAMigrar = transformador.transform(dataColaboradores);

        for (ColaboradorHumano colaborador : colaboradoresAMigrar) {
            colaboradorService.guardarColaborador(colaborador);

            if (contactar)
                protocoloEnvio.send(colaborador, ASUNTO, CUERPO);
        }

        confirmarLoading();

        return colaboradoresAMigrar;
    }
}

package com.tp_anual.proyecto_heladeras_solidarias.domain.contacto;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tp_anual.proyecto_heladeras_solidarias.domain.colaborador.Colaborador;
import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

public class EMailSenderService {
	private static final Logger logger = Logger.getLogger(Colaborador.class.getName());
    private static final Properties propiedades = new Properties();
	private static final String usuario = "proyecto.heladeras.solidarias@gmail.com";		
	private static final String password = ""; // TODO Hay que completar esto, encriptando la contraseña
	private static Session sesion;

	private static void init() {
		propiedades.put("mail.smtp.auth", "true");
		propiedades.put("mail.smtp.starttls.enable", "true");
		propiedades.put("mail.smtp.host", "smtp.gmail.com");
		propiedades.put("mail.smtp.port","587");

		sesion = Session.getInstance(propiedades, new javax.mail.Authenticator() {
            @Override
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, password);
            }
        });
	}

    public static void enviarEMail(String receptor, String asunto, String cuerpo) {
        init();

        try {
            MimeMessage mensaje = new MimeMessage(sesion);
			mensaje.setFrom(new InternetAddress(usuario));
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			mensaje.setSubject(asunto);
			mensaje.setText(cuerpo);

			Transport.send(mensaje);

		} catch (MessagingException me) {
			logger.log(Level.SEVERE, I18n.getMessage("contacto.EMailSenderService.enviarEMail_err", receptor));
			throw new RuntimeException(I18n.getMessage("contacto.EMailSenderService.enviarEMail_exception"));
        }
    }
}
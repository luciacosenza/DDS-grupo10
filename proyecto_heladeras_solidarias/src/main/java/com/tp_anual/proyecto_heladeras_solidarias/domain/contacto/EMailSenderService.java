package com.tp_anual.proyecto_heladeras_solidarias.domain.contacto;

import java.util.Properties;
import java.util.logging.Level;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.tp_anual.proyecto_heladeras_solidarias.i18n.I18n;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.java.Log;

@Log
public class EMailSenderService {
	private static final Properties propiedades = new Properties();
	private static final Dotenv dotenv = Dotenv.load();
    private static final String  username = dotenv.get("EMAIL_USERNAME");
    private static final String password = dotenv.get("EMAIL_PASSWORD");
	private static Session sesion;

	static {
		System.out.println("EMAIL_USER: " + username);
		System.out.println("EMAIL_PASSWORD: " + password);
	}

	private static void init() {
		propiedades.put("mail.smtp.auth", "true");
		propiedades.put("mail.smtp.starttls.enable", "true");
		propiedades.put("mail.smtp.host", "smtp.gmail.com");
		propiedades.put("mail.smtp.port","587");

		sesion = Session.getInstance(propiedades, new javax.mail.Authenticator() {
            @Override
			protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
	}

    public static void enviarEMail(String receptor, String asunto, String cuerpo) {
        init();

        try {
            MimeMessage mensaje = new MimeMessage(sesion);
			mensaje.setFrom(new InternetAddress(username));
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			mensaje.setSubject(asunto);
			mensaje.setText(cuerpo);

			Transport.send(mensaje);

		} catch (MessagingException me) {
			log.log(Level.SEVERE, I18n.getMessage("contacto.EMailSenderService.enviarEMail_err", receptor));
			throw new RuntimeException(I18n.getMessage("contacto.EMailSenderService.enviarEMail_exception"));
        }
    }
}
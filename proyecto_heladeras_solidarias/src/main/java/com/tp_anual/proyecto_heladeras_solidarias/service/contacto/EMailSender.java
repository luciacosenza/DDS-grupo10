package com.tp_anual.proyecto_heladeras_solidarias.service.contacto;

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
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
@Log
public class EMailSender {
	private final Properties propiedades;
	private final Dotenv dotenv;
	private final String  username;
    private final String password;
	private Session sesion;

	public EMailSender() {
		propiedades = new Properties();

		// Carga las variables de entorno usando Dotenv
		dotenv = Dotenv.load();
		username = dotenv.get("EMAIL_USERNAME");
		password = dotenv.get("EMAIL_PASSWORD");
	}

	@PostConstruct
	private void init() {
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

    public void enviarEMail(String receptor, String asunto, String cuerpo) {
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
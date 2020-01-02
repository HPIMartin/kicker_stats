package dev.schoenberg.kicker_stats.core.helper.mail;

import static org.simplejavamail.email.EmailBuilder.*;
import static org.simplejavamail.mailer.MailerBuilder.*;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;

public class SimpleMailMailer implements dev.schoenberg.kicker_stats.core.mail.Mailer {
	private final MailerConfiguration config;

	public SimpleMailMailer(MailerConfiguration config) {
		this.config = config;
	}

	@Override
	public void send(Mail mail) {
		getMailer().sendMail(getMail(mail));
	}

	private Email getMail(Mail mail) {
		return startingBlank().from("noreply@schoenberg.dev").to(mail.name, mail.address).bcc("kickerstats@schoenberg.dev")
				.withSubject("Registration for Kickerstats")
				// .withHTMLText("<img src='cid:wink1'><b>We should meet up!</b><img src='cid:wink2'>")
				.withPlainText(mail.body)
				// .withEmbeddedImage("wink1", imageByteArray, "image/png")
				// .withEmbeddedImage("wink2", imageDatesource)
				.withBounceTo("bounce-kickerstats@schoenberg.dev").buildEmail();
	}

	private Mailer getMailer() {
		return withSMTPServer(config.url, config.port, config.user, config.password)
				// .withTransportStrategy(SMTP_TLS)
				.withDebugLogging(true).buildMailer();
	}
}

package dev.schoenberg.kicker_stats.core.helper.mail;

import static com.dumbster.smtp.SimpleSmtpServer.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;

import dev.schoenberg.kicker_stats.core.Mailer.Mail;

public class SimpleMailMailerTest {
	private static final int NONSTANDARD_PORT = 13370;

	private SimpleMailMailer tested;
	private SimpleSmtpServer mailServer;

	@Before
	public void setUp() throws Exception {
		tested = new SimpleMailMailer(new MailerConfiguration("localhost", NONSTANDARD_PORT, "", ""));
		mailServer = start(NONSTANDARD_PORT);
	}

	@After
	public void tearDown() throws Exception {
		mailServer.stop();
	}

	@Test
	public void sendMail() {
		Mail mail = new Mail("", "test@test.de", "", "");

		tested.send(mail);

		assertThat(mailServer.getReceivedEmailSize()).isEqualTo(1);
		// SmtpMessage message = (SmtpMessage) mailServer.getReceivedEmail().next();
	}
}

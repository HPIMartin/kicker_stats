package dev.schoenberg.kicker_stats.core;

public class TestMailer implements Mailer {
	public Mail sendMail;
	public boolean failOnSend = false;

	@Override
	public void send(Mail mail) {
		if (failOnSend) {
			throw new RuntimeException();
		}
		sendMail = mail;
	}
}

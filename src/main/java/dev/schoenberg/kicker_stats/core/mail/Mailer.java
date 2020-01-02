package dev.schoenberg.kicker_stats.core.mail;

public interface Mailer {
	void send(Mail mail);

	public class Mail {
		public final String name;
		public final String address;
		public final String body;

		public Mail(String name, String address, String body) {
			this.name = name;
			this.address = address;
			this.body = body;
		}
	}
}

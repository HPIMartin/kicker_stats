package dev.schoenberg.kicker_stats.core.helper.mail;

public class MailerConfiguration {
	public final String url;
	public final int port;
	public final String user;
	public final String password;

	public MailerConfiguration(String url, int port, String user, String password) {
		this.url = url;
		this.port = port;
		this.user = user;
		this.password = password;
	}
}

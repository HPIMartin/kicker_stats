package dev.schoenberg.kicker_stats.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginDto {
	@XmlElement(name = "mail")
	public String mail;

	@XmlElement(name = "password")
	public String password;
}

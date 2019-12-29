package dev.schoenberg.kicker_stats.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayerDto {
	@XmlElement(name = "name")
	public String name;

	@XmlElement(name = "mail")
	public String mail;

	protected PlayerDto() {
	}

	public PlayerDto(String name, String mail) {
		this.name = name;
		this.mail = mail;
	}
}

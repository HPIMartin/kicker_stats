package dev.schoenberg.kicker_stats.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewPlayerDto {
	@XmlElement(name = "name")
	public String name;

	@XmlElement(name = "email")
	public String email;
}

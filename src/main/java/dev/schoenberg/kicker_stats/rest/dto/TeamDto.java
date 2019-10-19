package dev.schoenberg.kicker_stats.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TeamDto {
	@XmlElement(name = "playerOneId")
	public String playerOneId;

	@XmlElement(name = "playerTwoId")
	public String playertwoId;
}

package dev.schoenberg.kicker_stats.rest.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlayersDto {
	@XmlElement(name = "players")
	public List<PlayerDto> players;

	protected PlayersDto() {}

	public PlayersDto(List<PlayerDto> players) {
		this.players = players;
	}
}

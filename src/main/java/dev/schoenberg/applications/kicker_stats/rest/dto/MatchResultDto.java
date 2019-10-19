package dev.schoenberg.applications.kicker_stats.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MatchResultDto {
	@XmlElement(name = "teamOne")
	public TeamDto team1;

	@XmlElement(name = "teamTwo")
	public TeamDto team2;

	@XmlElement(name = "scoreTeamOne")
	public int scoreTeam1;

	@XmlElement(name = "scoreTeamTwo")
	public int scoreTeam2;
}

package dev.schoenberg.kicker_stats.persistence.entity.match;

import javax.persistence.Table;

import dev.schoenberg.kicker_stats.persistence.entity.AbstractEntity;

@Table(name = "matchTeams")
public class MatchTeamEntity extends AbstractEntity {
	public MatchTeamEntity() {}
}

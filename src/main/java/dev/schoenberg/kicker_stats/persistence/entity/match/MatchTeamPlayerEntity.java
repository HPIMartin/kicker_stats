package dev.schoenberg.kicker_stats.persistence.entity.match;

import javax.persistence.Table;

import com.j256.ormlite.field.DatabaseField;

import dev.schoenberg.kicker_stats.persistence.entity.AbstractEntity;
import dev.schoenberg.kicker_stats.persistence.entity.player.PlayerEntity;

@Table(name = "matchTeamPlayers")
public class MatchTeamPlayerEntity extends AbstractEntity {
	public static final String PLAYER_COLUMN = "player";
	public static final String MATCH_TEAM_COLUMN = "matchTeam";

	@DatabaseField(columnName = PLAYER_COLUMN, foreign = true, canBeNull = false, foreignAutoRefresh = true)
	public PlayerEntity player;

	@DatabaseField(columnName = MATCH_TEAM_COLUMN, foreign = true, canBeNull = false, foreignAutoRefresh = true)
	public MatchTeamEntity matchTeam;

	protected MatchTeamPlayerEntity() {}

	public MatchTeamPlayerEntity(PlayerEntity player, MatchTeamEntity matchTeam) {
		this.player = player;
		this.matchTeam = matchTeam;
	}
}

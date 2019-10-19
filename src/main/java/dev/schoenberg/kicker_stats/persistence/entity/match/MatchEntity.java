package dev.schoenberg.kicker_stats.persistence.entity.match;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.j256.ormlite.field.DatabaseField;

import dev.schoenberg.kicker_stats.persistence.entity.AbstractEntity;

@Entity(name = "matches")
public class MatchEntity extends AbstractEntity {
	public static final String WINNER_COLUMN = "winner";
	public static final String LOSER_COLUMN = "loser";

	@DatabaseField(columnName = WINNER_COLUMN, foreign = true, canBeNull = false, foreignAutoRefresh = true)
	public MatchTeamEntity winner;

	@DatabaseField(columnName = LOSER_COLUMN, foreign = true, canBeNull = false, foreignAutoRefresh = true)
	public MatchTeamEntity loser;

	@Column(name = "winnerScore")
	public int winnerScore;

	@Column(name = "loserScore")
	public int loserScore;

	protected MatchEntity() {}

	public MatchEntity(MatchTeamEntity winner, MatchTeamEntity loser, int winnerScore, int loserScore) {
		this.winner = winner;
		this.loser = loser;
		this.winnerScore = winnerScore;
		this.loserScore = loserScore;
	}
}

package dev.schoenberg.kicker_stats.persistence;

import static dev.schoenberg.kicker_stats.persistence.helper.DaoRepository.*;
import static java.nio.file.Files.*;
import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import dev.schoenberg.kicker_stats.PersistenceTest;
import dev.schoenberg.kicker_stats.persistence.entity.event.EventEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchTeamEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchTeamPlayerEntity;
import dev.schoenberg.kicker_stats.persistence.entity.player.PlayerEntity;
import dev.schoenberg.kicker_stats.persistence.helper.DaoRepository;
import dev.schoenberg.kicker_stats.persistence.helper.TableInitializer;

@Category(PersistenceTest.class)
public class DatabaseExplorationTest {
	private static String url;
	private static Path tempDir;
	private static Path dbFile;

	@BeforeClass
	public static void setupClass() throws IOException {
		tempDir = createTempDirectory("kicker_stats");
		dbFile = Paths.get(tempDir.toString(), "temp.db");
		url = "jdbc:sqlite:" + dbFile;
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		Files.delete(dbFile);
		Files.delete(tempDir);
	}

	@Test
	public void exploreDatabase() throws IOException, SQLException {
		String winningPlayer1 = "Max";
		String winningPlayer2 = "Martin";
		String losingPlayer1 = "Schmil";
		String losingPlayer2 = "Öle";
		UUID matchId;

		try (ConnectionSource connectionSource = new JdbcConnectionSource(url)) {
			TableInitializer.init(connectionSource);
			DaoRepository.initDaos(connectionSource);

			eventDao.create(new EventEntity("writeStuff"));

			matchId = writeMatchExample(winningPlayer1, winningPlayer2, losingPlayer1, losingPlayer2);
		}

		try (ConnectionSource connectionSource = new JdbcConnectionSource(url)) {
			DaoRepository.initDaos(connectionSource);

			eventDao.create(new EventEntity("readStuff"));

			readMatchExample(winningPlayer1, winningPlayer2, losingPlayer1, losingPlayer2, matchId);
		}
	}

	private UUID writeMatchExample(String winningPlayer1, String winningPlayer2, String losingPlayer1, String losingPlayer2) throws SQLException {
		PlayerEntity winner1 = playerDao.createIfNotExists(new PlayerEntity(winningPlayer1));
		PlayerEntity winner2 = playerDao.createIfNotExists(new PlayerEntity(winningPlayer2));
		PlayerEntity loser1 = playerDao.createIfNotExists(new PlayerEntity(losingPlayer1));
		PlayerEntity loser2 = playerDao.createIfNotExists(new PlayerEntity(losingPlayer2));

		MatchTeamEntity team1 = matchTeamDao.createIfNotExists(new MatchTeamEntity());
		MatchTeamEntity team2 = matchTeamDao.createIfNotExists(new MatchTeamEntity());

		matchTeamPlayerDao.createIfNotExists(new MatchTeamPlayerEntity(winner1, team1));
		matchTeamPlayerDao.createIfNotExists(new MatchTeamPlayerEntity(winner2, team1));
		matchTeamPlayerDao.createIfNotExists(new MatchTeamPlayerEntity(loser1, team2));
		matchTeamPlayerDao.createIfNotExists(new MatchTeamPlayerEntity(loser2, team2));

		return matchDao.createIfNotExists(new MatchEntity(team1, team2, 6, 3)).id;
	}

	private void readMatchExample(String winningPlayer1, String winningPlayer2, String losingPlayer1, String losingPlayer2, UUID matchId) throws SQLException {
		MatchEntity game2 = matchDao.queryForId(matchId);
		List<PlayerEntity> winnerTeam = lookupPlayersForMatchTeam(game2.winner);
		assertThat(winnerTeam.size()).isEqualTo(2);
		assertThat(winnerTeam).anyMatch(p -> p.name.equals(winningPlayer1));
		assertThat(winnerTeam).anyMatch(p -> p.name.equals(winningPlayer2));

		List<PlayerEntity> loserTeam = lookupPlayersForMatchTeam(game2.loser);
		assertThat(loserTeam.size()).isEqualTo(2);
		assertThat(loserTeam).anyMatch(p -> p.name.equals(losingPlayer1));
		assertThat(loserTeam).anyMatch(p -> p.name.equals(losingPlayer2));
	}

	private static List<PlayerEntity> lookupPlayersForMatchTeam(MatchTeamEntity team) throws SQLException {
		QueryBuilder<MatchTeamPlayerEntity, UUID> matchTeamPlayerQuery = matchTeamPlayerDao.queryBuilder();
		matchTeamPlayerQuery.selectColumns(MatchTeamPlayerEntity.PLAYER_COLUMN);
		matchTeamPlayerQuery.where().eq(MatchTeamPlayerEntity.MATCH_TEAM_COLUMN, team);

		QueryBuilder<PlayerEntity, UUID> playerQuery = playerDao.queryBuilder();
		playerQuery.where().in(PlayerEntity.ID_COLUMN, matchTeamPlayerQuery);
		return playerQuery.query();
	}
}

package dev.schoenberg.kicker_stats.persistence.service;

import static com.j256.ormlite.dao.DaoManager.*;

import java.util.UUID;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import dev.schoenberg.kicker_stats.persistence.entity.event.EventEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchTeamEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchTeamPlayerEntity;
import dev.schoenberg.kicker_stats.persistence.entity.player.PlayerEntity;

public class DaoRepository {
	public static Dao<EventEntity, UUID> eventDao;

	public static Dao<MatchEntity, UUID> matchDao;
	public static Dao<MatchTeamEntity, UUID> matchTeamDao;
	public static Dao<MatchTeamPlayerEntity, UUID> matchTeamPlayerDao;

	public static Dao<PlayerEntity, UUID> playerDao;

	public static void initDaos(ConnectionSource connection) {
		try {
			eventDao = createDao(connection, EventEntity.class);

			matchDao = createDao(connection, MatchEntity.class);
			matchTeamDao = createDao(connection, MatchTeamEntity.class);
			matchTeamPlayerDao = createDao(connection, MatchTeamPlayerEntity.class);

			playerDao = createDao(connection, PlayerEntity.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

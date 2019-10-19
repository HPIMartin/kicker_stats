package dev.schoenberg.kicker_stats.persistence.service;

import static com.j256.ormlite.table.TableUtils.*;

import com.j256.ormlite.support.ConnectionSource;

import dev.schoenberg.kicker_stats.persistence.entity.event.EventEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchTeamEntity;
import dev.schoenberg.kicker_stats.persistence.entity.match.MatchTeamPlayerEntity;
import dev.schoenberg.kicker_stats.persistence.entity.player.PlayerEntity;

public class TableInitializer {
	public static void init(ConnectionSource connection) {
		try {
			createTableIfNotExists(connection, EventEntity.class);

			createTableIfNotExists(connection, MatchEntity.class);
			createTableIfNotExists(connection, MatchTeamEntity.class);
			createTableIfNotExists(connection, MatchTeamPlayerEntity.class);

			createTableIfNotExists(connection, PlayerEntity.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

package dev.schoenberg.kicker_stats.persistence.repository;

import static dev.schoenberg.kicker_stats.core.helper.exceptionWrapper.ExceptionWrapper.*;
import static java.util.stream.Collectors.*;

import java.util.List;

import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.core.repository.PlayerRepository;
import dev.schoenberg.kicker_stats.persistence.entity.player.PlayerEntity;
import dev.schoenberg.kicker_stats.persistence.helper.DaoRepository;

public class PlayerOrmLiteRepository implements PlayerRepository {
	@Override
	public void create(Player player) {
		PlayerEntity entity = new PlayerEntity(player.name, player.email);
		silentThrow(() -> DaoRepository.playerDao.create(entity));
	}

	@Override
	public List<Player> getPlayers() {
		List<PlayerEntity> players = silentThrow(() -> DaoRepository.playerDao.queryForAll());
		return players.stream().map(this::convert).collect(toList());
	}

	private Player convert(PlayerEntity entity) {
		return new Player(entity.name, entity.email);
	}
}

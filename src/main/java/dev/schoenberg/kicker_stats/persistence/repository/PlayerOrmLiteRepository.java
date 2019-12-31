package dev.schoenberg.kicker_stats.persistence.repository;

import static dev.schoenberg.kicker_stats.core.helper.exceptionWrapper.ExceptionWrapper.*;
import static dev.schoenberg.kicker_stats.persistence.entity.player.PlayerEntity.*;
import static java.util.stream.Collectors.*;

import java.util.List;

import dev.schoenberg.kicker_stats.core.domain.Admin;
import dev.schoenberg.kicker_stats.core.domain.Player;
import dev.schoenberg.kicker_stats.core.exception.NotFoundException;
import dev.schoenberg.kicker_stats.core.repository.PlayerRepository;
import dev.schoenberg.kicker_stats.persistence.entity.player.PlayerEntity;
import dev.schoenberg.kicker_stats.persistence.helper.DaoRepository;

public class PlayerOrmLiteRepository implements PlayerRepository {
	@Override
	public void create(Player player) {
		PlayerEntity entity = new PlayerEntity(player.name, player.mail, player.hashedPassword, player.isAdmin());
		silentThrow(() -> DaoRepository.playerDao.create(entity));
	}

	@Override
	public List<Player> getPlayers() {
		List<PlayerEntity> players = silentThrow(() -> DaoRepository.playerDao.queryForAll());
		return players.stream().map(this::convert).collect(toList());
	}

	@Override
	public Player byMail(String mail) {
		List<PlayerEntity> players = silentThrow(() -> DaoRepository.playerDao.queryForEq(MAIL_COLUMN, mail));
		return convert(players.stream().findAny().orElseThrow(NotFoundException::new));
	}

	private Player convert(PlayerEntity entity) {
		return construct(entity, entity.isAdmin ? Admin::new : Player::new);
	}

	private Player construct(PlayerEntity entity, PlayerConstructor constructor) {
		return constructor.create(entity.name, entity.mail, entity.hashedPassword);
	}

	@FunctionalInterface
	private interface PlayerConstructor {
		Player create(String name, String mail, String hashedPwd);
	}
}

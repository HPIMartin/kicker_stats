package dev.schoenberg.kicker_stats.persistence.entity.player;

import javax.persistence.Column;
import javax.persistence.Entity;

import dev.schoenberg.kicker_stats.persistence.entity.AbstractEntity;

@Entity(name = "players")
public class PlayerEntity extends AbstractEntity {
	public static final String EMAIL_COLUMN = "email";

	@Column(name = "name")
	public String name;

	@Column(name = EMAIL_COLUMN)
	public String email;

	protected PlayerEntity() {}

	public PlayerEntity(String name, String email) {
		this.name = name;
		this.email = email;
	}
}

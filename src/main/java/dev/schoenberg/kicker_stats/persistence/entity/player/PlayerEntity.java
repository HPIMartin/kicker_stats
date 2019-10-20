package dev.schoenberg.kicker_stats.persistence.entity.player;

import javax.persistence.Column;
import javax.persistence.Entity;

import dev.schoenberg.kicker_stats.persistence.entity.AbstractEntity;

@Entity(name = "players")
public class PlayerEntity extends AbstractEntity {
	@Column(name = "name")
	public String name;

	@Column(name = "email")
	public String email;

	protected PlayerEntity() {}

	public PlayerEntity(String name, String email) {
		this.name = name;
		this.email = email;
	}
}

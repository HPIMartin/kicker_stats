package dev.schoenberg.kicker_stats.persistence.entity.player;

import javax.persistence.Column;
import javax.persistence.Entity;

import dev.schoenberg.kicker_stats.persistence.entity.AbstractEntity;

@Entity(name = "players")
public class PlayerEntity extends AbstractEntity {
	@Column(name = "name")
	public String name;

	protected PlayerEntity() {}

	public PlayerEntity(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PlayerEntity)) {
			return false;
		}
		return name.equals(((PlayerEntity) o).name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}

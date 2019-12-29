package dev.schoenberg.kicker_stats.persistence.entity.player;

import javax.persistence.Column;
import javax.persistence.Entity;

import dev.schoenberg.kicker_stats.persistence.entity.AbstractEntity;

@Entity(name = "players")
public class PlayerEntity extends AbstractEntity {
	public static final String MAIL_COLUMN = "mail";

	@Column(name = "name")
	public String name;

	@Column(name = MAIL_COLUMN)
	public String mail;

	@Column(name = "isAdmin")
	public boolean isAdmin;

	protected PlayerEntity() {
	}

	public PlayerEntity(String name, String mail, boolean isAdmin) {
		this.name = name;
		this.mail = mail;
		this.isAdmin = isAdmin;
	}
}

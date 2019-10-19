package dev.schoenberg.kicker_stats.persistence.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
	public static final String ID_COLUMN = "id";

	@Id
	@Column(name = ID_COLUMN)
	public UUID id;

	protected AbstractEntity() {
		this.id = UUID.randomUUID();
	}
}

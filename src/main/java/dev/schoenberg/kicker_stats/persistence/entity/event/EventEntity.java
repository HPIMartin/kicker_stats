package dev.schoenberg.kicker_stats.persistence.entity.event;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import dev.schoenberg.kicker_stats.persistence.entity.AbstractEntity;

@Table(name = "event")
public class EventEntity extends AbstractEntity {
	@Column(name = "created")
	public Date created;

	@Column(name = "content")
	public String content;

	protected EventEntity() {}

	public EventEntity(String content) {
		this.content = content;
		this.created = new Date();
	}
}

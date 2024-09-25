package org.abos.twi.gatcha.core.quest;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Objects;

public record QuestId(QuestKind kind, String stat, int amount) {

    public QuestId {
        Objects.requireNonNull(kind);
        Objects.requireNonNull(stat);
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
    }

    public QuestId(final @NotNull QuestLineId questLineId, final @Range(from = 1, to = Integer.MAX_VALUE) int amount) {
        this(questLineId.kind(), questLineId.stat(), amount);
    }

    public QuestLineId lineId() {
        return new QuestLineId(kind, stat);
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%d", kind.name(), stat, amount);
    }
}

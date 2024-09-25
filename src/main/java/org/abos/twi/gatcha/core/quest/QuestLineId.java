package org.abos.twi.gatcha.core.quest;

import java.util.Objects;

public record QuestLineId(QuestKind kind, String stat) {

    public QuestLineId {
        Objects.requireNonNull(kind);
        Objects.requireNonNull(stat);
    }

}

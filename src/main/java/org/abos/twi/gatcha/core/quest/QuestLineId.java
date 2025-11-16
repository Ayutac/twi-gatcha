package org.abos.twi.gatcha.core.quest;

import java.io.Serializable;
import java.util.Objects;

public record QuestLineId(QuestKind kind, String stat) implements Serializable {

    public QuestLineId {
        Objects.requireNonNull(kind);
        Objects.requireNonNull(stat);
    }

}

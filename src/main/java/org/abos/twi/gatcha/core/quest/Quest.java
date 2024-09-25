package org.abos.twi.gatcha.core.quest;

import org.abos.common.Describable;
import org.abos.common.Registerable;
import org.abos.twi.gatcha.core.InventoryMap;
import org.abos.twi.gatcha.core.Player;
import org.abos.twi.gatcha.core.PlayerStats;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Quest(QuestId id, String description, InventoryMap reward) implements Describable, Registerable<Quest> {

    public Quest {
        Objects.requireNonNull(id);
        Objects.requireNonNull(description);
        Objects.requireNonNull(reward);
    }

    @Override
    public @NotNull String getId() {
        return id.toString();
    }

    @Override
    public @NotNull String getName() {
        return getId();
    }

    @Override
    public @NotNull String getDescription() {
        return description;
    }

    public boolean fulfilledBy(final @NotNull PlayerStats playerStats) {
        return playerStats.getQuestCounter(id.lineId()) >= id.amount();
    }

    public boolean hasBeenTaken(final @NotNull PlayerStats playerStats) {
        return playerStats.hasQuestBeenTaken(id);
    }

    public boolean maybeGive(final @NotNull Player player) {
        if (fulfilledBy(player.getStats()) && !hasBeenTaken(player.getStats())) {
            player.getInventory().addAll(reward);
            player.getStats().takeQuest(id);
            return true;
        }
        return false;
    }

}

package org.abos.twi.gatcha.core.effect;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class HealthDurationEffect extends DurationEffect {

    protected @Range(from = 0, to = Integer.MAX_VALUE) int health;

    public HealthDurationEffect(@NotNull EffectType type, @Range(from = 0, to = Integer.MAX_VALUE) int power, @Range(from = 0, to = Integer.MAX_VALUE) int duration) {
        super(type, power, duration);
    }

    public @Range(from = 0, to = Integer.MAX_VALUE) int getHealth() {
        return health;
    }

    public void setHealth(final @Range(from = 0, to = Integer.MAX_VALUE) int health) {
        this.health = health;
    }
}

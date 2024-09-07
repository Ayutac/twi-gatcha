package org.abos.twi.gatcha.core.effect;

import org.abos.common.Vec2i;
import org.abos.twi.gatcha.core.battle.Battle;
import org.abos.twi.gatcha.core.battle.CharacterInBattle;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class NoEffect implements AttackEffect {

    private final @NotNull EffectType type;

    public NoEffect(final @NotNull EffectType type, Object... args) {
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public @NotNull EffectType getEffectType() {
        return null;
    }

    @Override
    public void apply(CharacterInBattle from, Vec2i target, Battle battle) {
        // intentionally left empty
    }
}
